////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.Checker.EXCEPTION_MSG;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_FINISHED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_STARTED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.filters.SuppressionFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.DebugAuditAdapter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.DebugFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestBeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestFileSetCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * CheckerTest.
 * @noinspection ClassWithTooManyDependencies
 */
public class CheckerTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    private static Method getFireAuditFinished() throws NoSuchMethodException {
        final Class<Checker> checkerClass = Checker.class;
        final Method fireAuditFinished = checkerClass.getDeclaredMethod("fireAuditFinished");
        fireAuditFinished.setAccessible(true);
        return fireAuditFinished;
    }

    private static Method getFireAuditStartedMethod() throws NoSuchMethodException {
        final Class<Checker> checkerClass = Checker.class;
        final Method fireAuditStarted = checkerClass.getDeclaredMethod("fireAuditStarted");
        fireAuditStarted.setAccessible(true);
        return fireAuditStarted;
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checker";
    }

    @Test
    public void testDestroy() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);
        final TestFileSetCheck fileSet = new TestFileSetCheck();
        checker.addFileSetCheck(fileSet);
        final DebugFilter filter = new DebugFilter();
        checker.addFilter(filter);
        final TestBeforeExecutionFileFilter fileFilter = new TestBeforeExecutionFileFilter();
        checker.addBeforeExecutionFileFilter(fileFilter);

        // should remove all listeners, file sets, and filters
        checker.destroy();

        final File tempFile = File.createTempFile("junit", null, temporaryFolder);
        checker.process(Collections.singletonList(tempFile));
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);

        assertFalse(auditAdapter.wasCalled(), "Checker.destroy() doesn't remove listeners.");
        assertFalse(fileSet.wasCalled(), "Checker.destroy() doesn't remove file sets.");
        assertFalse(filter.wasCalled(), "Checker.destroy() doesn't remove filters.");
        assertFalse(fileFilter.wasCalled(), "Checker.destroy() doesn't remove file filters.");
    }

    @Test
    public void testAddListener() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        // Let's try fire some events
        getFireAuditStartedMethod().invoke(checker);
        assertTrue(auditAdapter.wasCalled(), "Checker.fireAuditStarted() doesn't call listener");
        assertTrue(auditAdapter.wasEventPassed(), "Checker.fireAuditStarted() doesn't pass event");

        auditAdapter.resetListener();
        getFireAuditFinished().invoke(checker);
        assertTrue(auditAdapter.wasCalled(), "Checker.fireAuditFinished() doesn't call listener");
        assertTrue(auditAdapter.wasEventPassed(), "Checker.fireAuditFinished() doesn't pass event");

        auditAdapter.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue(auditAdapter.wasCalled(), "Checker.fireFileStarted() doesn't call listener");
        assertTrue(auditAdapter.wasEventPassed(), "Checker.fireFileStarted() doesn't pass event");

        auditAdapter.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue(auditAdapter.wasCalled(), "Checker.fireFileFinished() doesn't call listener");
        assertTrue(auditAdapter.wasEventPassed(), "Checker.fireFileFinished() doesn't pass event");

        auditAdapter.resetListener();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue(auditAdapter.wasCalled(), "Checker.fireErrors() doesn't call listener");
        assertTrue(auditAdapter.wasEventPassed(), "Checker.fireErrors() doesn't pass event");
    }

    @Test
    public void testRemoveListener() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        final DebugAuditAdapter aa2 = new DebugAuditAdapter();
        checker.addListener(auditAdapter);
        checker.addListener(aa2);
        checker.removeListener(auditAdapter);

        // Let's try fire some events
        getFireAuditStartedMethod().invoke(checker);
        assertTrue(aa2.wasCalled(), "Checker.fireAuditStarted() doesn't call listener");
        assertFalse(auditAdapter.wasCalled(),
                "Checker.fireAuditStarted() does call removed listener");

        aa2.resetListener();
        getFireAuditFinished().invoke(checker);
        assertTrue(aa2.wasCalled(), "Checker.fireAuditFinished() doesn't call listener");
        assertFalse(auditAdapter.wasCalled(),
                "Checker.fireAuditFinished() does call removed listener");

        aa2.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue(aa2.wasCalled(), "Checker.fireFileStarted() doesn't call listener");
        assertFalse(auditAdapter.wasCalled(),
                "Checker.fireFileStarted() does call removed listener");

        aa2.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue(aa2.wasCalled(), "Checker.fireFileFinished() doesn't call listener");
        assertFalse(auditAdapter.wasCalled(),
                "Checker.fireFileFinished() does call removed listener");

        aa2.resetListener();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue(aa2.wasCalled(), "Checker.fireErrors() doesn't call listener");
        assertFalse(auditAdapter.wasCalled(), "Checker.fireErrors() does call removed listener");
    }

    @Test
    public void testAddBeforeExecutionFileFilter() throws Exception {
        final Checker checker = new Checker();
        final TestBeforeExecutionFileFilter filter = new TestBeforeExecutionFileFilter();

        checker.addBeforeExecutionFileFilter(filter);

        filter.resetFilter();
        checker.process(Collections.singletonList(new File("dummy.java")));
        assertTrue(filter.wasCalled(), "Checker.acceptFileStarted() doesn't call filter");
    }

    @Test
    public void testRemoveBeforeExecutionFileFilter() throws Exception {
        final Checker checker = new Checker();
        final TestBeforeExecutionFileFilter filter = new TestBeforeExecutionFileFilter();
        final TestBeforeExecutionFileFilter f2 = new TestBeforeExecutionFileFilter();
        checker.addBeforeExecutionFileFilter(filter);
        checker.addBeforeExecutionFileFilter(f2);
        checker.removeBeforeExecutionFileFilter(filter);

        f2.resetFilter();
        checker.process(Collections.singletonList(new File("dummy.java")));
        assertTrue(f2.wasCalled(), "Checker.acceptFileStarted() doesn't call filter");
        assertFalse(filter.wasCalled(), "Checker.acceptFileStarted() does call removed filter");
    }

    @Test
    public void testAddFilter() {
        final Checker checker = new Checker();
        final DebugFilter filter = new DebugFilter();

        checker.addFilter(filter);

        filter.resetFilter();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue(filter.wasCalled(), "Checker.fireErrors() doesn't call filter");
    }

    @Test
    public void testRemoveFilter() {
        final Checker checker = new Checker();
        final DebugFilter filter = new DebugFilter();
        final DebugFilter f2 = new DebugFilter();
        checker.addFilter(filter);
        checker.addFilter(f2);
        checker.removeFilter(filter);

        f2.resetFilter();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue(f2.wasCalled(), "Checker.fireErrors() doesn't call filter");
        assertFalse(filter.wasCalled(), "Checker.fireErrors() does call removed filter");
    }

    @Test
    public void testFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile",
                File.createTempFile("junit", null, temporaryFolder).getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        final List<File> files = new ArrayList<>();
        final File file = new File("file.pdf");
        files.add(file);
        final File otherFile = new File("file.java");
        files.add(otherFile);
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        checker.setCacheFile(File.createTempFile("junit", null, temporaryFolder).getPath());
        final int counter = checker.process(files);

        // comparing to 1 as there is only one legal file in input
        final int numLegalFiles = 1;
        final PropertyCacheFile cache = Whitebox.getInternalState(checker, "cacheFile");
        assertEquals(numLegalFiles, counter, "There were more legal files than expected");
        assertEquals(numLegalFiles, auditAdapter.getNumFilesStarted(),
                "Audit was started on larger amount of files than expected");
        assertEquals(numLegalFiles, auditAdapter.getNumFilesFinished(),
                "Audit was finished on larger amount of files than expected");
        assertNull(cache.get(new File("file.java").getCanonicalPath()),
                "Cache shout not contain any file");
    }

    @Test
    public void testIgnoredFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        final File tempFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", tempFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        final List<File> allIgnoredFiles = new ArrayList<>();
        final File ignoredFile = new File("file.pdf");
        allIgnoredFiles.add(ignoredFile);
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        checker.setCacheFile(File.createTempFile("junit", null, temporaryFolder).getPath());
        final int counter = checker.process(allIgnoredFiles);

        // comparing to 0 as there is no legal file in input
        final int numLegalFiles = 0;
        assertEquals(numLegalFiles, counter, "There were more legal files than expected");
        assertEquals(numLegalFiles, auditAdapter.getNumFilesStarted(),
                "Audit was started on larger amount of files than expected");
        assertEquals(numLegalFiles, auditAdapter.getNumFilesFinished(),
                "Audit was finished on larger amount of files than expected");
    }

    @Test
    public void testSetters() {
        // all  that is set by reflection, so just make code coverage be happy
        final Checker checker = new Checker();
        checker.setBasedir("some");
        checker.setSeverity("ignore");

        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        checker.setFileExtensions((String[]) null);
        checker.setFileExtensions(".java", "xml");

        try {
            checker.setCharset("UNKNOWN-CHARSET");
            fail("Exception is expected");
        }
        catch (UnsupportedEncodingException ex) {
            assertEquals("unsupported charset: 'UNKNOWN-CHARSET'", ex.getMessage(),
                    "Error message is not expected");
        }
    }

    @Test
    public void testNoClassLoaderNoModuleFactory() {
        final Checker checker = new Checker();

        try {
            checker.finishLocalSetup();
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("if no custom moduleFactory is set, moduleClassLoader must be specified",
                    ex.getMessage(), "Error message is not expected");
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker checker = new Checker();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        checker.setModuleClassLoader(classLoader);
        checker.finishLocalSetup();
        final Context actualCtx = Whitebox.getInternalState(checker, "childContext");

        assertNotNull(actualCtx.get("moduleFactory"),
                "Default module factory should be created when it is not specified");
    }

    @Test
    public void testFinishLocalSetupFullyInitialized() throws Exception {
        final Checker checker = new Checker();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        checker.setModuleClassLoader(contextClassLoader);
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), contextClassLoader);
        checker.setModuleFactory(factory);
        checker.setBasedir("testBaseDir");
        checker.setLocaleLanguage("it");
        checker.setLocaleCountry("IT");
        checker.finishLocalSetup();

        final Context context = Whitebox.getInternalState(checker, "childContext");
        final String encoding = System.getProperty("file.encoding", StandardCharsets.UTF_8.name());
        assertEquals(encoding, context.get("charset"), "Charset was different than expected");
        assertEquals("error", context.get("severity"), "Severity is set to unexpected value");
        assertEquals("testBaseDir", context.get("basedir"), "Basedir is set to unexpected value");

        final Field sLocale = LocalizedMessage.class.getDeclaredField("sLocale");
        sLocale.setAccessible(true);
        final Locale locale = (Locale) sLocale.get(null);
        assertEquals(Locale.ITALY, locale, "Locale is set to unexpected value");
    }

    @Test
    public void testSetupChildExceptions() {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        final Configuration config = new DefaultConfiguration("java.lang.String");
        try {
            checker.setupChild(config);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("java.lang.String is not allowed as a child in Checker", ex.getMessage(),
                    "Error message is not expected");
        }
    }

    @Test
    public void testSetupChildInvalidProperty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addAttribute("$$No such property", null);
        try {
            createChecker(checkConfig);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker"
                        + " - cannot initialize module " + checkConfig.getName()
                        + " - Property '$$No such property'"
                        + " does not exist, please check the documentation", ex.getMessage(),
                    "Error message is not expected");
        }
    }

    @Test
    public void testSetupChildListener() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        final Configuration config = new DefaultConfiguration(
            DebugAuditAdapter.class.getCanonicalName());
        checker.setupChild(config);

        final List<AuditListener> listeners = Whitebox.getInternalState(checker, "listeners");
        assertTrue(listeners.get(listeners.size() - 1) instanceof DebugAuditAdapter,
                "Invalid child listener class");
    }

    @Test
    public void testDestroyCheckerWithWrongCacheFileNameLength() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(new DefaultConfiguration("default config"));
        // We set wrong file name length in order to reproduce IOException on OS Linux, OS Windows.
        // The maximum file name length which is allowed in most UNIX, Windows file systems is 255.
        // See https://en.wikipedia.org/wiki/Filename;
        checker.setCacheFile(String.format(Locale.ENGLISH, "%0300d", 0));
        try {
            checker.destroy();
            fail("Exception did not happen");
        }
        catch (IllegalStateException ex) {
            assertTrue(ex.getCause() instanceof IOException,
                    "Cause of exception differs from IOException");
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndCheckWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse(ExternalResourceHolder.class.isAssignableFrom(HiddenFieldCheck.class),
                "ExternalResourceHolder has changed his parent");
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());

        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final File tmpFile = File.createTempFile("file", ".java", temporaryFolder);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, tmpFile.getPath(), expected);
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        // one more time to reuse cache
        verify(checkerConfig, tmpFile.getPath(), expected);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        assertEquals(cacheAfterFirstRun, cacheAfterSecondRun,
                "Cache from first run differs from second run cache");
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(createModuleConfig(TranslationCheck.class));

        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checker.setCacheFile(cacheFile.getPath());

        checker.setupChild(createModuleConfig(TranslationCheck.class));
        final File tmpFile = File.createTempFile("file", ".java", temporaryFolder);
        final List<File> files = new ArrayList<>(1);
        files.add(tmpFile);
        checker.process(files);

        // invoke destroy to persist cache
        checker.destroy();

        final Properties cache = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cache.load(reader);
        }

        // There should 2 objects in cache: processed file (file.java) and checker configuration.
        final int expectedNumberOfObjectsInCache = 2;
        assertEquals(expectedNumberOfObjectsInCache, cache.size(), "Cache has unexpected size");

        final String expectedConfigHash = "B8535A811CA90BE8B7A14D40BCA62B4FC2447B46";
        assertEquals(expectedConfigHash, cache.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
                "Cache has unexpected hash");

        assertNotNull(cache.getProperty(tmpFile.getPath()), "Cache file has null path");
    }

    @Test
    public void testClearExistingCache() throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        checker.clearCache();
        // invoke destroy to persist cache
        checker.destroy();

        final Properties cacheAfterClear = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterClear.load(reader);
        }

        assertEquals(1, cacheAfterClear.size(), "Cache has unexpected size");
        assertNotNull(cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
                "Cache has null hash");

        final String pathToEmptyFile =
                File.createTempFile("file", ".java", temporaryFolder).getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        // file that should be audited is not in cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        assertNotNull(cacheAfterSecondRun.getProperty(pathToEmptyFile), "Cache has null path");
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals(cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
                cacheHash, "Cash have changed it hash");
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 2;
        assertEquals(expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size(),
                "Cache has changed number of items");
    }

    @Test
    public void testClearCache() throws Exception {
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("myConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(violationCheck);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        checker.process(Collections.singletonList(new File("dummy.java")));
        checker.clearCache();
        // invoke destroy to persist cache
        final PropertyCacheFile cache = Whitebox.getInternalState(checker, "cacheFile");
        cache.persist();

        final Properties cacheAfterClear = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterClear.load(reader);
        }

        assertEquals(1, cacheAfterClear.size(), "Cache has unexpected size");
    }

    @Test
    public void setFileExtension() {
        final Checker checker = new Checker();
        checker.setFileExtensions(".test1", "test2");
        final String[] actual = Whitebox.getInternalState(checker, "fileExtensions");
        assertArrayEquals(new String[] {".test1", ".test2"}, actual,
                "Extensions are not expected");
    }

    @Test
    public void testClearCacheWhenCacheFileIsNotSet() {
        // The idea of the test is to check that when cache file is not set,
        // the invocation of clearCache method does not throw an exception.
        final Checker checker = new Checker();
        checker.clearCache();
        assertNull(Whitebox.getInternalState(checker, "cacheFile"),
                "If cache file is not set the cache should default to null");
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testCatchErrorInProcessFilesMethod() throws Exception {
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public long lastModified() {
                throw expectedError;
            }
        };

        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause()).isInstanceOf(IOError.class);
            assertWithMessage("Error cause is not InternalError")
                    .that(error.getCause().getCause()).isInstanceOf(InternalError.class);
            assertEquals(errorMessage, error.getCause().getCause().getMessage(),
                    "Error message is not expected");
        }
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testCatchErrorWithNoFileName() throws Exception {
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public long lastModified() {
                throw expectedError;
            }

            @Override
            public String getAbsolutePath() {
                return null;
            }
        };

        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause()).isInstanceOf(IOError.class);
            assertWithMessage("Error cause is not InternalError")
                    .that(error.getCause().getCause()).isInstanceOf(InternalError.class);
            assertEquals(errorMessage, error.getCause().getCause().getMessage(),
                    "Error message is not expected");
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndFilterWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse(ExternalResourceHolder.class.isAssignableFrom(DummyFilter.class),
                "ExternalResourceHolder has changed its parent");
        final DefaultConfiguration filterConfig = createModuleConfig(DummyFilter.class);

        final DefaultConfiguration checkerConfig = createRootConfig(filterConfig);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String pathToEmptyFile =
                File.createTempFile("file", ".java", temporaryFolder).getPath();

        verify(checkerConfig, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        // One more time to use cache.
        verify(checkerConfig, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        final String cacheFilePath = cacheAfterSecondRun.getProperty(pathToEmptyFile);
        assertEquals(cacheAfterFirstRun.getProperty(pathToEmptyFile),
                cacheFilePath, "Cache file has changed its path");
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals(cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
                cacheHash, "Cache has changed its hash");
        final int expectedNumberOfObjectsInCache = 2;
        assertEquals(expectedNumberOfObjectsInCache, cacheAfterFirstRun.size(),
                "Number of items in cache differs from expected");
        assertEquals(expectedNumberOfObjectsInCache, cacheAfterSecondRun.size(),
                "Number of items in cache differs from expected");
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    // -@cs[ExecutableStatementCount] This test needs to verify many things.
    @Test
    public void testCacheAndCheckWhichAddsNewResourceLocationButKeepsSameCheckerInstance()
            throws Exception {
        // Use case (https://github.com/checkstyle/checkstyle/pull/3092#issuecomment-218162436):
        // Imagine that cache exists in a file. New version of Checkstyle appear.
        // New release contains update to a some check to have additional external resource.
        // User update his configuration and run validation as usually.
        // Cache should not be reused.

        final DynamicalResourceHolderCheck check = new DynamicalResourceHolderCheck();
        final String firstExternalResourceLocation = getPath("InputCheckerImportControlOne.xml");
        final String firstExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + firstExternalResourceLocation;
        check.setFirstExternalResourceLocation(firstExternalResourceLocation);

        final DefaultConfiguration checkerConfig = createRootConfig(null);
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addFileSetCheck(check);
        checker.addFilter(new DummyFilterSet());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        final String pathToEmptyFile =
                File.createTempFile("file", ".java", temporaryFolder).getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        final int expectedNumberOfObjectsInCacheAfterFirstRun = 4;
        assertEquals(expectedNumberOfObjectsInCacheAfterFirstRun, cacheAfterFirstRun.size(),
                "Number of items in cache differs from expected");

        // Change a list of external resources which are used by the check
        final String secondExternalResourceLocation = "InputCheckerImportControlTwo.xml";
        final String secondExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + secondExternalResourceLocation;
        check.setSecondExternalResourceLocation(secondExternalResourceLocation);

        checker.addFileSetCheck(check);
        checker.configure(checkerConfig);

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        final String cacheFilePath = cacheAfterSecondRun.getProperty(pathToEmptyFile);
        assertEquals(cacheAfterFirstRun.getProperty(pathToEmptyFile),
                cacheFilePath, "Cache file has changed its path");
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertEquals(cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
                cacheHash, "Cache has changed its hash");
        final String resourceKey = cacheAfterSecondRun.getProperty(firstExternalResourceKey);
        assertEquals(cacheAfterFirstRun.getProperty(firstExternalResourceKey),
                resourceKey, "Cache has changed its resource key");
        assertNotNull(cacheAfterFirstRun.getProperty(firstExternalResourceKey),
                "Cache has null as a resource key");
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 4;
        assertEquals(expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size(),
                "Number of items in cache differs from expected");
        assertNull(cacheAfterFirstRun.getProperty(secondExternalResourceKey),
                "Cache has not null as a resource key");
        assertNotNull(cacheAfterSecondRun.getProperty(secondExternalResourceKey),
                "Cache has null as a resource key");
    }

    @Test
    public void testClearLazyLoadCacheInDetailAST() throws Exception {
        final DefaultConfiguration checkConfig1 =
            createModuleConfig(CheckWhichDoesNotRequireCommentNodes.class);
        final DefaultConfiguration checkConfig2 =
            createModuleConfig(CheckWhichRequiresCommentNodes.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig1);
        treeWalkerConfig.addChild(checkConfig2);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);

        final String filePath = getPath("InputCheckerClearDetailAstLazyLoadCache.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, filePath, expected);
    }

    @Test
    public void testCacheOnViolationSuppression() throws Exception {
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);

        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addAttribute("file", getPath("InputCheckerSuppressAll.xml"));

        final DefaultConfiguration checkerConfig = createRootConfig(violationCheck);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(filterConfig);

        final String fileViolationPath =
                File.createTempFile("ViolationFile", ".java", temporaryFolder).getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, fileViolationPath, expected);

        try (InputStream input = Files.newInputStream(cacheFile.toPath())) {
            final Properties details = new Properties();
            details.load(input);

            assertNotNull(details.getProperty(fileViolationPath),
                    "suppressed violation file saved in cache");
        }
    }

    @Test
    public void testHaltOnException() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CheckWhichThrowsError.class);
        final String filePath = getPath("InputChecker.java");
        try {
            verify(checkConfig, filePath);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Exception was thrown while processing " + filePath, ex.getMessage(),
                    "Error message is not expected");
        }
    }

    @Test
    public void testExceptionWithCache() throws Exception {
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);

        final DefaultConfiguration checkConfig =
                createModuleConfig(CheckWhichThrowsError.class);

        final DefaultConfiguration treewalkerConfig =
                createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treewalkerConfig);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(treewalkerConfig);

        final Checker checker = createChecker(checkerConfig);

        final String filePath = getPath("InputChecker.java");
        try {
            checker.process(Collections.singletonList(new File(filePath)));
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Exception was thrown while processing " + filePath, ex.getMessage(),
                    "Error message is not expected");

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertEquals(1, cache.size(), "Cache has unexpected size");
            assertNull(cache.getProperty(filePath), "testFile is not in cache");
        }
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testCatchErrorWithCache() throws Exception {
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            @Override
            public String getAbsolutePath() {
                return "testFile";
            }

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public File getAbsoluteFile() {
                throw expectedError;
            }
        };

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause()).isInstanceOf(IOError.class);
            assertEquals(errorMessage, error.getCause().getCause().getMessage(),
                    "Error message is not expected");

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertEquals(1, cache.size(), "Cache has unexpected size");
            assertNull(cache.getProperty("testFile"), "testFile is not in cache");
        }
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testCatchErrorWithCacheWithNoFileName() throws Exception {
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public String getAbsolutePath() {
                throw expectedError;
            }
        };

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause()).isInstanceOf(IOError.class);
            assertEquals(errorMessage, error.getCause().getCause().getMessage(),
                    "Error message is not expected");

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertEquals(1, cache.size(), "Cache has unexpected size");
        }
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testExceptionWithNoFileName() {
        final String errorMessage = "Security Exception";
        final RuntimeException expectedError = new SecurityException(errorMessage);

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public String getAbsolutePath() {
                throw expectedError;
            }
        };

        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("SecurityException is expected!");
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error cause differs from SecurityException")
                    .that(ex.getCause()).isInstanceOf(SecurityException.class);
            assertEquals(errorMessage, ex.getCause().getMessage(),
                    "Error message is not expected");
        }
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testExceptionWithCacheAndNoFileName() throws Exception {
        final File cacheFile = File.createTempFile("junit", null, temporaryFolder);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String errorMessage = "Security Exception";
        final RuntimeException expectedError = new SecurityException(errorMessage);

        final File mock = new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             * @noinspection ProhibitedExceptionThrown
             */
            @Override
            public String getAbsolutePath() {
                throw expectedError;
            }
        };

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("SecurityException is expected!");
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error cause differs from SecurityException")
                    .that(ex.getCause()).isInstanceOf(SecurityException.class);
            assertEquals(errorMessage, ex.getCause().getMessage(),
                    "Error message is not expected");

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertEquals(1, cache.size(), "Cache has unexpected size");
        }
    }

    @Test
    public void testHaltOnExceptionOff() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CheckWhichThrowsError.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addChild(treeWalkerConfig);

        checkerConfig.addAttribute("haltOnException", "false");

        final String filePath = getPath("InputChecker.java");
        final String[] expected = {
            "1: " + getCheckMessage(EXCEPTION_MSG, "java.lang.IndexOutOfBoundsException: test"),
        };

        verify(checkerConfig, filePath, expected);
    }

    @Test
    public void testTabViolationDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VerifyPositionAfterTabFileSet.class);
        final String[] expected = {
            "2:9: violation",
            "3:17: violation",
        };
        verify(checkConfig, getPath("InputCheckerTabCharacter.txt"),
            expected);
    }

    @Test
    public void testTabViolation() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VerifyPositionAfterTabFileSet.class);
        final DefaultConfiguration checkerConfig = createRootConfig(checkConfig);
        checkerConfig.addAttribute("tabWidth", "4");
        final String[] expected = {
            "2:5: violation",
            "3:9: violation",
        };
        verify(checkerConfig, getPath("InputCheckerTabCharacter.txt"),
            expected);
    }

    @Test
    public void testCheckerProcessCallAllNeededMethodsOfFileSets() throws Exception {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        checker.process(Collections.singletonList(new File("dummy.java")));
        final List<String> expected =
            Arrays.asList("beginProcessing", "finishProcessing", "destroy");
        assertArrayEquals(expected.toArray(), fileSet.getMethodCalls().toArray(),
                "Method calls were not expected");
    }

    @Test
    public void testSetFileSetCheckSetsMessageDispatcher() {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        assertEquals(checker, fileSet.getInternalMessageDispatcher(),
                "Message dispatcher was not expected");
    }

    @Test
    public void testAddAuditListenerAsChild() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader()) {
            @Override
            public Object createModule(String name) throws CheckstyleException {
                Object adapter = auditAdapter;
                if (!name.equals(DebugAuditAdapter.class.getName())) {
                    adapter = super.createModule(name);
                }
                return adapter;
            }
        };
        checker.setModuleFactory(factory);
        checker.setupChild(createModuleConfig(DebugAuditAdapter.class));
        // Let's try fire some events
        checker.process(Collections.singletonList(new File("dummy.java")));
        assertTrue(auditAdapter.wasCalled(), "Checker.fireAuditStarted() doesn't call listener");
    }

    @Test
    public void testAddBeforeExecutionFileFilterAsChild() throws Exception {
        final Checker checker = new Checker();
        final TestBeforeExecutionFileFilter fileFilter = new TestBeforeExecutionFileFilter();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader()) {
            @Override
            public Object createModule(String name) throws CheckstyleException {
                Object filter = fileFilter;
                if (!name.equals(TestBeforeExecutionFileFilter.class.getName())) {
                    filter = super.createModule(name);
                }
                return filter;
            }
        };
        checker.setModuleFactory(factory);
        checker.setupChild(createModuleConfig(TestBeforeExecutionFileFilter.class));
        checker.process(Collections.singletonList(new File("dummy.java")));
        assertTrue(fileFilter.wasCalled(), "Checker.acceptFileStarted() doesn't call listener");
    }

    @Test
    public void testFileSetCheckInitWhenAddedAsChild() throws Exception {
        final Checker checker = new Checker();
        final DummyFileSet fileSet = new DummyFileSet();
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader()) {
            @Override
            public Object createModule(String name) throws CheckstyleException {
                Object check = fileSet;
                if (!name.equals(DummyFileSet.class.getName())) {
                    check = super.createModule(name);
                }
                return check;
            }
        };
        checker.setModuleFactory(factory);
        checker.finishLocalSetup();
        checker.setupChild(createModuleConfig(DummyFileSet.class));
        assertTrue(fileSet.isInitCalled(), "FileSetCheck.init() wasn't called");
    }

    // -@cs[CheckstyleTestMakeup] must use raw class to directly initialize DefaultLogger
    @Test
    public void testDefaultLoggerClosesItStreams() throws Exception {
        final Checker checker = new Checker();
        try (CloseAndFlushTestByteArrayOutputStream testInfoOutputStream =
                new CloseAndFlushTestByteArrayOutputStream();
            CloseAndFlushTestByteArrayOutputStream testErrorOutputStream =
                new CloseAndFlushTestByteArrayOutputStream()) {
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.addListener(new DefaultLogger(testInfoOutputStream,
                OutputStreamOptions.CLOSE, testErrorOutputStream, OutputStreamOptions.CLOSE));

            final File tmpFile = File.createTempFile("file", ".java", temporaryFolder);
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checker, tmpFile.getPath(), expected);

            assertWithMessage("Output stream close count")
                    .that(testInfoOutputStream.getCloseCount())
                    .isEqualTo(1);
            assertWithMessage("Output stream flush count")
                    .that(testInfoOutputStream.getFlushCount())
                    .isEqualTo(TestUtil.adjustFlushCountForOutputStreamClose(3));
            assertWithMessage("Error stream close count")
                    .that(testErrorOutputStream.getCloseCount())
                    .isEqualTo(1);
            assertWithMessage("Error stream flush count")
                    .that(testErrorOutputStream.getFlushCount())
                    .isEqualTo(TestUtil.adjustFlushCountForOutputStreamClose(1));
        }
    }

    // -@cs[CheckstyleTestMakeup] must use raw class to directly initialize DefaultLogger
    @Test
    public void testXmlLoggerClosesItStreams() throws Exception {
        final Checker checker = new Checker();
        try (CloseAndFlushTestByteArrayOutputStream testInfoOutputStream =
                new CloseAndFlushTestByteArrayOutputStream()) {
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.addListener(new XMLLogger(testInfoOutputStream, OutputStreamOptions.CLOSE));

            final File tmpFile = File.createTempFile("file", ".java", temporaryFolder);
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);

            assertWithMessage("Output stream close count")
                    .that(testInfoOutputStream.getCloseCount())
                    .isEqualTo(1);
            assertWithMessage("Output stream flush count")
                    .that(testInfoOutputStream.getFlushCount())
                    .isEqualTo(TestUtil.adjustFlushCountForOutputStreamClose(0));
        }
    }

    @Test
    public void testDuplicatedModule() throws Exception {
        // we need to test a module with two instances, one with id and the other not
        final DefaultConfiguration moduleConfig1 =
                createModuleConfig(NewlineAtEndOfFileCheck.class);
        final DefaultConfiguration moduleConfig2 =
                createModuleConfig(NewlineAtEndOfFileCheck.class);
        moduleConfig2.addAttribute("id", "ModuleId");
        final DefaultConfiguration root = new DefaultConfiguration("root");
        root.addChild(moduleConfig1);
        root.addChild(moduleConfig2);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(root);
        // BriefUtLogger does not print the module name or id postfix,
        // so we need to set logger manually
        final ByteArrayOutputStream out = Whitebox.getInternalState(this, "stream");
        final DefaultLogger logger = new DefaultLogger(out, OutputStreamOptions.CLOSE, out,
                OutputStreamOptions.NONE, new AuditEventDefaultFormatter());
        checker.addListener(logger);

        final String path = File.createTempFile("file", ".java", temporaryFolder).getPath();
        final String violationMessage =
                getCheckMessage(NewlineAtEndOfFileCheck.class, MSG_KEY_NO_NEWLINE_EOF);
        final String[] expected = {
            "1: " + violationMessage + " [NewlineAtEndOfFile]",
            "1: " + violationMessage + " [ModuleId]",
        };

        // super.verify does not work here, for we change the logger
        out.flush();
        final int errs = checker.process(Collections.singletonList(new File(path)));
        try (ByteArrayInputStream inputStream =
                new ByteArrayInputStream(out.toByteArray());
            LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            // we need to ignore the unrelated lines
            final List<String> actual = lnr.lines()
                    .filter(line -> !getCheckMessage(AUDIT_STARTED_MESSAGE).equals(line))
                    .filter(line -> !getCheckMessage(AUDIT_FINISHED_MESSAGE).equals(line))
                    .limit(expected.length)
                    .sorted()
                    .collect(Collectors.toList());
            Arrays.sort(expected);

            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = "[ERROR] " + path + ":" + expected[i];
                assertEquals(expectedResult, actual.get(i), "error message " + i);
            }

            assertEquals(expected.length, errs, "unexpected output: " + lnr.readLine());
        }

        checker.destroy();
    }

    public static class DummyFilter implements Filter {

        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }

    }

    public static class DummyFileSetViolationCheck extends AbstractFileSetCheck
        implements ExternalResourceHolder {

        @Override
        protected void processFiltered(File file, FileText fileText) {
            log(1, "test");
        }

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> externalResourceLocation = new HashSet<>(1);
            externalResourceLocation.add("non_existent_external_resource.xml");
            return externalResourceLocation;
        }

    }

    public static class DummyFilterSet extends FilterSet implements ExternalResourceHolder {

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> strings = new HashSet<>();
            strings.add("test");
            return strings;
        }

    }

    public static final class DynamicalResourceHolderCheck extends AbstractFileSetCheck
        implements ExternalResourceHolder {

        private String firstExternalResourceLocation;
        private String secondExternalResourceLocation;

        public void setFirstExternalResourceLocation(String firstExternalResourceLocation) {
            this.firstExternalResourceLocation = firstExternalResourceLocation;
        }

        public void setSecondExternalResourceLocation(String secondExternalResourceLocation) {
            this.secondExternalResourceLocation = secondExternalResourceLocation;
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            // there is no need in implementation of the method
        }

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> locations = new HashSet<>();
            locations.add(firstExternalResourceLocation);
            // Attempt to change the behaviour of the check dynamically
            if (secondExternalResourceLocation != null) {
                locations.add(secondExternalResourceLocation);
            }
            return locations;
        }

    }

    public static class CheckWhichDoesNotRequireCommentNodes extends AbstractCheck {

        /** Number of children of method definition token. */
        private static final int METHOD_DEF_CHILD_COUNT = 7;

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public void visitToken(DetailAST ast) {
            if (ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(
                    TokenTypes.BLOCK_COMMENT_BEGIN) != null) {
                log(ast, "AST has incorrect structure structure."
                    + " The check does not require comment nodes but there were comment nodes"
                    + " in the AST.");
            }
            final int childCount = ast.getChildCount();
            if (childCount != METHOD_DEF_CHILD_COUNT) {
                final String msg = String.format(Locale.getDefault(),
                    "AST node in no comment tree has wrong number of children. "
                            + "Expected is %d but was %d",
                    METHOD_DEF_CHILD_COUNT, childCount);
                log(ast, msg);
            }
            // count children where comment lives
            int actualChildCount = 0;
            for (DetailAST child = ast.getFirstChild().getFirstChild(); child != null; child =
                    child.getNextSibling()) {
                actualChildCount++;
            }
            final int cacheChildCount = ast.getFirstChild().getChildCount();
            if (cacheChildCount != actualChildCount) {
                final String msg = String.format(Locale.getDefault(),
                        "AST node with no comment has wrong number of children. "
                                + "Expected is %d but was %d",
                        cacheChildCount, actualChildCount);
                log(ast, msg);
            }
        }

    }

    public static class CheckWhichRequiresCommentNodes extends AbstractCheck {

        /** Number of children of method definition token. */
        private static final int METHOD_DEF_CHILD_COUNT = 7;

        @Override
        public boolean isCommentNodesRequired() {
            return true;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.METHOD_DEF};
        }

        @Override
        public void visitToken(DetailAST ast) {
            if (ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(
                    TokenTypes.BLOCK_COMMENT_BEGIN) == null) {
                log(ast, "Incorrect AST structure.");
            }
            final int childCount = ast.getChildCount();
            if (childCount != METHOD_DEF_CHILD_COUNT) {
                final String msg = String.format(Locale.getDefault(),
                    "AST node in comment tree has wrong number of children. "
                            + "Expected is %d but was %d",
                    METHOD_DEF_CHILD_COUNT, childCount);
                log(ast, msg);
            }
            // count children where comment lives
            int actualChildCount = 0;
            for (DetailAST child = ast.getFirstChild().getFirstChild(); child != null; child =
                    child.getNextSibling()) {
                actualChildCount++;
            }
            final int cacheChildCount = ast.getFirstChild().getChildCount();
            if (cacheChildCount != actualChildCount) {
                final String msg = String.format(Locale.getDefault(),
                        "AST node with comment has wrong number of children. "
                                + "Expected is %d but was %d",
                        cacheChildCount, actualChildCount);
                log(ast, msg);
            }
        }

    }

    public static class CheckWhichThrowsError extends AbstractCheck {

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.CLASS_DEF};
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.CLASS_DEF};
        }

        @Override
        public int[] getRequiredTokens() {
            return new int[] {TokenTypes.CLASS_DEF};
        }

        @Override
        public void visitToken(DetailAST ast) {
            throw new IndexOutOfBoundsException("test");
        }

    }

    public static final class DummyFileSet extends AbstractFileSetCheck {

        private final List<String> methodCalls = new ArrayList<>();

        private boolean initCalled;

        @Override
        public void init() {
            super.init();
            initCalled = true;
        }

        @Override
        public void beginProcessing(String charset) {
            methodCalls.add("beginProcessing");
            super.beginProcessing(charset);
        }

        @Override
        public void finishProcessing() {
            methodCalls.add("finishProcessing");
            super.finishProcessing();
        }

        @Override
        protected void processFiltered(File file, FileText fileText) {
            methodCalls.add("processFiltered");
        }

        @Override
        public void destroy() {
            methodCalls.add("destroy");
            super.destroy();
        }

        public List<String> getMethodCalls() {
            return Collections.unmodifiableList(methodCalls);
        }

        public boolean isInitCalled() {
            return initCalled;
        }

        public MessageDispatcher getInternalMessageDispatcher() {
            return getMessageDispatcher();
        }

    }

    public static class VerifyPositionAfterTabFileSet extends AbstractFileSetCheck {

        @Override
        protected void processFiltered(File file, FileText fileText) {
            int lineNumber = 0;
            for (String line : getFileContents().getLines()) {
                final int position = line.lastIndexOf('\t');
                lineNumber++;

                if (position != -1) {
                    log(lineNumber, position + 1, "violation");
                }
            }
        }

    }

}
