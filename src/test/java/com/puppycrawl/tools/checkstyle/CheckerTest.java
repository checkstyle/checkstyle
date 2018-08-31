////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.Checker.EXCEPTION_MSG;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_FINISHED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_STARTED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CheckerTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

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

        checker.process(Collections.singletonList(temporaryFolder.newFile()));
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);

        assertFalse("Checker.destroy() doesn't remove listeners.", auditAdapter.wasCalled());
        assertFalse("Checker.destroy() doesn't remove file sets.", fileSet.wasCalled());
        assertFalse("Checker.destroy() doesn't remove filters.", filter.wasCalled());
        assertFalse("Checker.destroy() doesn't remove file filters.", fileFilter.wasCalled());
    }

    @Test
    public void testAddListener() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        // Let's try fire some events
        getFireAuditStartedMethod().invoke(checker);
        assertTrue("Checker.fireAuditStarted() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        getFireAuditFinished().invoke(checker);
        assertTrue("Checker.fireAuditFinished() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", auditAdapter.wasCalled());

        auditAdapter.resetListener();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call listener", auditAdapter.wasCalled());
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
        assertTrue("Checker.fireAuditStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditStarted() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        getFireAuditFinished().invoke(checker);
        assertTrue("Checker.fireAuditFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireAuditFinished() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        checker.fireFileStarted("Some File Name");
        assertTrue("Checker.fireFileStarted() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileStarted() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        checker.fireFileFinished("Some File Name");
        assertTrue("Checker.fireFileFinished() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireFileFinished() does call removed listener",
                auditAdapter.wasCalled());

        aa2.resetListener();
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed listener", auditAdapter.wasCalled());
    }

    @Test
    public void testAddBeforeExecutionFileFilter() throws Exception {
        final Checker checker = new Checker();
        final TestBeforeExecutionFileFilter filter = new TestBeforeExecutionFileFilter();

        checker.addBeforeExecutionFileFilter(filter);

        filter.resetFilter();
        checker.process(Collections.singletonList(new File("dummy.java")));
        assertTrue("Checker.acceptFileStarted() doesn't call filter", filter.wasCalled());
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
        assertTrue("Checker.acceptFileStarted() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.acceptFileStarted() does call removed filter", filter.wasCalled());
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
        assertTrue("Checker.fireErrors() doesn't call filter", filter.wasCalled());
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
        assertTrue("Checker.fireErrors() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed filter", filter.wasCalled());
    }

    @Test
    public void testFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

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
        checker.setCacheFile(temporaryFolder.newFile().getPath());
        final int counter = checker.process(files);

        // comparing to 1 as there is only one legal file in input
        final int numLegalFiles = 1;
        final PropertyCacheFile cache = Whitebox.getInternalState(checker, "cacheFile");
        assertEquals("There were more legal files than expected",
                numLegalFiles, counter);
        assertEquals("Audit was started on larger amount of files than expected",
                numLegalFiles, auditAdapter.getNumFilesStarted());
        assertEquals("Audit was finished on larger amount of files than expected",
                numLegalFiles, auditAdapter.getNumFilesFinished());
        assertNull("Cache shout not contain any file",
                cache.get(new File("file.java").getCanonicalPath()));
    }

    @Test
    public void testIgnoredFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

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
        checker.setCacheFile(temporaryFolder.newFile().getPath());
        final int counter = checker.process(allIgnoredFiles);

        // comparing to 0 as there is no legal file in input
        final int numLegalFiles = 0;
        assertEquals("There were more legal files than expected",
                numLegalFiles, counter);
        assertEquals("Audit was started on larger amount of files than expected",
                numLegalFiles, auditAdapter.getNumFilesStarted());
        assertEquals("Audit was finished on larger amount of files than expected",
                numLegalFiles, auditAdapter.getNumFilesFinished());
    }

    @Test
    public void testSetters() {
        // all  that is set by reflection, so just make code coverage be happy
        final Checker checker = new Checker();
        checker.setClassLoader(getClass().getClassLoader());
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
            assertEquals("Error message is not expected",
                    "unsupported charset: 'UNKNOWN-CHARSET'", ex.getMessage());
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
            assertEquals("Error message is not expected",
                    "if no custom moduleFactory is set, moduleClassLoader must be specified",
                    ex.getMessage());
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker checker = new Checker();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        checker.setModuleClassLoader(classLoader);
        checker.finishLocalSetup();
        final Context actualCtx = Whitebox.getInternalState(checker, "childContext");

        assertNotNull("Default module factory should be created when it is not specified",
            actualCtx.get("moduleFactory"));
        assertEquals("Invalid classLoader", classLoader, actualCtx.get("classLoader"));
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
        assertEquals("Charset was different than expected",
                System.getProperty("file.encoding", StandardCharsets.UTF_8.name()),
                context.get("charset"));
        assertEquals("Was used insufficient classloader",
                contextClassLoader, context.get("classLoader"));
        assertEquals("Severity is set to unexpected value",
                "error", context.get("severity"));
        assertEquals("Basedir is set to unexpected value",
                "testBaseDir", context.get("basedir"));

        final Field sLocale = LocalizedMessage.class.getDeclaredField("sLocale");
        sLocale.setAccessible(true);
        final Locale locale = (Locale) sLocale.get(null);
        assertEquals("Locale is set to unexpected value", Locale.ITALY, locale);
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
            assertEquals("Error message is not expected",
                    "java.lang.String is not allowed as a child in Checker", ex.getMessage());
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
            assertEquals("Error message is not expected",
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker"
                        + " - Property '$$No such property' in module " + checkConfig.getName()
                        + " does not exist, please check the documentation", ex.getMessage());
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
        assertTrue("Invalid child listener class",
            listeners.get(listeners.size() - 1) instanceof DebugAuditAdapter);
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
            assertTrue("Cause of exception differs from IOException",
                    ex.getCause() instanceof IOException);
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndCheckWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse("ExternalResourceHolder has changed his parent",
                ExternalResourceHolder.class.isAssignableFrom(HiddenFieldCheck.class));
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());

        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final File tmpFile = temporaryFolder.newFile("file.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, tmpFile.getPath(), expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        // one more time to reuse cache
        verify(checkerConfig, tmpFile.getPath(), expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals("Cache from first run differs from second run cache",
                cacheAfterFirstRun, cacheAfterSecondRun);
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(createModuleConfig(TranslationCheck.class));

        final File cacheFile = temporaryFolder.newFile();
        checker.setCacheFile(cacheFile.getPath());

        checker.setupChild(createModuleConfig(TranslationCheck.class));
        final File tmpFile = temporaryFolder.newFile("file.java");
        final List<File> files = new ArrayList<>(1);
        files.add(tmpFile);
        checker.process(files);

        // invoke destroy to persist cache
        checker.destroy();

        final Properties cache = new Properties();
        cache.load(Files.newBufferedReader(cacheFile.toPath()));

        // There should 2 objects in cache: processed file (file.java) and checker configuration.
        final int expectedNumberOfObjectsInCache = 2;
        assertEquals("Cache has unexpected size",
                expectedNumberOfObjectsInCache, cache.size());

        final String expectedConfigHash = "B8535A811CA90BE8B7A14D40BCA62B4FC2447B46";
        assertEquals("Cache has unexpected hash",
                expectedConfigHash, cache.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));

        assertNotNull("Cache file has null path",
                cache.getProperty(tmpFile.getPath()));
    }

    @Test
    public void testClearExistingCache() throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);
        checkerConfig.addAttribute("charset", StandardCharsets.UTF_8.name());
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        checker.clearCache();
        // invoke destroy to persist cache
        checker.destroy();

        final Properties cacheAfterClear = new Properties();
        cacheAfterClear.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals("Cache has unexpected size",
                1, cacheAfterClear.size());
        assertNotNull("Cache has null hash",
                cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        // file that should be audited is not in cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertNotNull("Cache has null path",
                cacheAfterSecondRun.getProperty(pathToEmptyFile));
        assertEquals("Cash have changed it hash",
            cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 2;
        assertEquals("Cache has changed number of items",
                expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size());
    }

    @Test
    public void testClearCache() throws Exception {
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("myConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        final File cacheFile = temporaryFolder.newFile();
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
        cacheAfterClear.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals("Cache has unexpected size",
                1, cacheAfterClear.size());
    }

    @Test
    public void setFileExtension() {
        final Checker checker = new Checker();
        checker.setFileExtensions(".test1", "test2");
        final String[] actual = Whitebox.getInternalState(checker, "fileExtensions");
        assertArrayEquals("Extensions are not expected",
                new String[] {".test1", ".test2"}, actual);
    }

    @Test
    public void testClearCacheWhenCacheFileIsNotSet() {
        // The idea of the test is to check that when cache file is not set,
        // the invocation of clearCache method does not throw an exception.
        final Checker checker = new Checker();
        checker.clearCache();
        assertNull("If cache file is not set the cache should default to null",
            Whitebox.getInternalState(checker, "cacheFile"));
    }

    @Test
    public void testCatchErrorInProcessFilesMethod() throws Exception {
        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduce them.
        final File mock = PowerMockito.mock(File.class);
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.lastModified()).thenThrow(expectedError);
        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertThat("Error cause differs from IOError",
                    error.getCause(), instanceOf(IOError.class));
            assertThat("Error cause is not InternalError",
                    error.getCause().getCause(), instanceOf(InternalError.class));
            assertEquals("Error message is not expected",
                    errorMessage, error.getCause().getCause().getMessage());
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndFilterWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse("ExternalResourceHolder has changed its parent",
                ExternalResourceHolder.class.isAssignableFrom(DummyFilter.class));
        final DefaultConfiguration filterConfig = createModuleConfig(DummyFilter.class);

        final DefaultConfiguration checkerConfig = createRootConfig(filterConfig);
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        verify(checkerConfig, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        // One more time to use cache.
        verify(checkerConfig, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals(
                "Cache file has changed its path",
            cacheAfterFirstRun.getProperty(pathToEmptyFile),
            cacheAfterSecondRun.getProperty(pathToEmptyFile)
        );
        assertEquals(
                "Cache has changed its hash",
            cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        final int expectedNumberOfObjectsInCache = 2;
        assertEquals("Number of items in cache differs from expected",
                expectedNumberOfObjectsInCache, cacheAfterFirstRun.size());
        assertEquals("Number of items in cache differs from expected",
                expectedNumberOfObjectsInCache, cacheAfterSecondRun.size());
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
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addFileSetCheck(check);
        checker.addFilter(new DummyFilterSet());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        final int expectedNumberOfObjectsInCacheAfterFirstRun = 4;
        assertEquals("Number of items in cache differs from expected",
                expectedNumberOfObjectsInCacheAfterFirstRun, cacheAfterFirstRun.size());

        // Change a list of external resources which are used by the check
        final String secondExternalResourceLocation = "InputCheckerImportControlTwo.xml";
        final String secondExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + secondExternalResourceLocation;
        check.setSecondExternalResourceLocation(secondExternalResourceLocation);

        checker.addFileSetCheck(check);
        checker.configure(checkerConfig);

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals("Cache file has changed its path",
            cacheAfterFirstRun.getProperty(pathToEmptyFile),
            cacheAfterSecondRun.getProperty(pathToEmptyFile)
        );
        assertEquals(
                "Cache has changed its hash",
            cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        assertEquals("Cache has changed its resource key",
            cacheAfterFirstRun.getProperty(firstExternalResourceKey),
            cacheAfterSecondRun.getProperty(firstExternalResourceKey)
        );
        assertNotNull("Cache has null as a resource key",
                cacheAfterFirstRun.getProperty(firstExternalResourceKey));
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 4;
        assertEquals("Number of items in cache differs from expected",
                expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size());
        assertNull("Cache has not null as a resource key",
                cacheAfterFirstRun.getProperty(secondExternalResourceKey));
        assertNotNull("Cache has null as a resource key",
                cacheAfterSecondRun.getProperty(secondExternalResourceKey));
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
        final File cacheFile = temporaryFolder.newFile();
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);

        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addAttribute("file", getPath("InputCheckerSuppressAll.xml"));

        final DefaultConfiguration checkerConfig = createRootConfig(violationCheck);
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(filterConfig);

        final String fileViolationPath = temporaryFolder.newFile("ViolationFile.java").getPath();
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkerConfig, fileViolationPath, expected);

        try (InputStream input = Files.newInputStream(cacheFile.toPath())) {
            final Properties details = new Properties();
            details.load(input);

            assertNotNull("suppressed violation file saved in cache",
                    details.getProperty(fileViolationPath));
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
            assertEquals("Error message is not expected",
                    "Exception was thrown while processing " + filePath, ex.getMessage());
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
    public void testCheckerProcessCallAllNeededMethodsOfFileSets() throws Exception {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        checker.process(Collections.singletonList(new File("dummy.java")));
        final List<String> expected =
            Arrays.asList("beginProcessing", "finishProcessing", "destroy");
        assertArrayEquals("Method calls were not expected",
                expected.toArray(), fileSet.getMethodCalls().toArray());
    }

    @Test
    public void testSetFileSetCheckSetsMessageDispatcher() {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        assertEquals("Message dispatcher was not expected",
                checker, fileSet.getInternalMessageDispatcher());
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
        assertTrue("Checker.fireAuditStarted() doesn't call listener", auditAdapter.wasCalled());
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
        assertTrue("Checker.acceptFileStarted() doesn't call listener", fileFilter.wasCalled());
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
        assertTrue("FileSetCheck.init() wasn't called", fileSet.isInitCalled());
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
                true, testErrorOutputStream, true));

            final File tmpFile = temporaryFolder.newFile("file.java");
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checker, tmpFile.getPath(), expected);

            assertEquals("Close count was not expected",
                    1, testInfoOutputStream.getCloseCount());
            assertEquals("Flush count was not expected",
                    3, testInfoOutputStream.getFlushCount());
            assertEquals("Close count was not expected",
                    1, testErrorOutputStream.getCloseCount());
            assertEquals("Flush count was not expected",
                    1, testErrorOutputStream.getFlushCount());
        }
    }

    // -@cs[CheckstyleTestMakeup] must use raw class to directly initialize DefaultLogger
    @Test
    public void testXmlLoggerClosesItStreams() throws Exception {
        final Checker checker = new Checker();
        try (CloseAndFlushTestByteArrayOutputStream testInfoOutputStream =
                new CloseAndFlushTestByteArrayOutputStream()) {
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.addListener(new XMLLogger(testInfoOutputStream, true));

            final File tmpFile = temporaryFolder.newFile("file.java");
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);

            assertEquals("Close count was not expected",
                    1, testInfoOutputStream.getCloseCount());
            assertEquals("Flush count was not expected",
                    0, testInfoOutputStream.getFlushCount());
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
        final DefaultLogger logger =
                new DefaultLogger(out, true, out, false, new AuditEventDefaultFormatter());
        checker.addListener(logger);

        final String path = temporaryFolder.newFile("file.java").getPath();
        final String errorMessage =
                getCheckMessage(NewlineAtEndOfFileCheck.class, MSG_KEY_NO_NEWLINE_EOF);
        final String[] expected = {
            "1: " + errorMessage + " [NewlineAtEndOfFile]",
            "1: " + errorMessage + " [ModuleId]",
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
                assertEquals("error message " + i, expectedResult, actual.get(i));
            }

            assertEquals("unexpected output: " + lnr.readLine(), expected.length, errs);
        }

        checker.destroy();
    }

    private static class DummyFilter implements Filter {

        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }

    }

    private static class DummyFileSetViolationCheck extends AbstractFileSetCheck
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

    private static class DummyFilterSet extends FilterSet implements ExternalResourceHolder {

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> strings = new HashSet<>();
            strings.add("test");
            return strings;
        }

    }

    private static class DynamicalResourceHolderCheck extends AbstractFileSetCheck
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

    private static class CheckWhichDoesNotRequireCommentNodes extends AbstractCheck {

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

    private static class CheckWhichRequiresCommentNodes extends AbstractCheck {

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

    private static class CheckWhichThrowsError extends AbstractCheck {

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

    private static class DummyFileSet extends AbstractFileSetCheck {

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

}
