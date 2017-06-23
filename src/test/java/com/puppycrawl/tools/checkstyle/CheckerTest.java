////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.filters.SuppressionFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class CheckerTest extends BaseCheckTestSupport {

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

    @Test
    public void testDestroy() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);
        final DebugFilter filter = new DebugFilter();
        checker.addFilter(filter);
        final TestBeforeExecutionFileFilter fileFilter = new TestBeforeExecutionFileFilter();
        checker.addBeforeExecutionFileFilter(fileFilter);

        // should remove al listeners and filters
        checker.destroy();

        checker.process(Collections.singletonList(new File("Some File Name")));
        final SortedSet<LocalizedMessage> messages = new TreeSet<>();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);

        assertFalse("Checker.destroy() doesn't remove listeners.", auditAdapter.wasCalled());
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
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
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
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
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
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
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
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call filter", f2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed filter", filter.wasCalled());

    }

    @Test
    public void testFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", "UTF-8");
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
        assertEquals(numLegalFiles, counter);
        assertEquals(numLegalFiles, auditAdapter.getNumFilesStarted());
        assertEquals(numLegalFiles, auditAdapter.getNumFilesFinished());
    }

    @Test
    public void testIgnoredFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", "UTF-8");
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
        assertEquals(numLegalFiles, counter);
        assertEquals(numLegalFiles, auditAdapter.getNumFilesStarted());
        assertEquals(numLegalFiles, auditAdapter.getNumFilesFinished());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSetters() {
        // all  that is set by reflection, so just make code coverage be happy
        final Checker checker = new Checker();
        checker.setClassLoader(getClass().getClassLoader());
        checker.setClassloader(getClass().getClassLoader());
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
            assertEquals("unsupported charset: 'UNKNOWN-CHARSET'", ex.getMessage());
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
            assertEquals("if no custom moduleFactory is set, "
                            + "moduleClassLoader must be specified", ex.getMessage());
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        checker.finishLocalSetup();
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

        final Context context = (Context) Whitebox.getInternalState(checker, "childContext");
        assertEquals(System.getProperty("file.encoding", "UTF-8"), context.get("charset"));
        assertEquals(contextClassLoader, context.get("classLoader"));
        assertEquals("error", context.get("severity"));
        assertEquals("testBaseDir", context.get("basedir"));

        final Field sLocale = LocalizedMessage.class.getDeclaredField("sLocale");
        sLocale.setAccessible(true);
        final Locale locale = (Locale) sLocale.get(null);
        assertEquals(Locale.ITALY, locale);
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
            assertEquals("java.lang.String is not allowed as a child in Checker", ex.getMessage());
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
            assertTrue(ex.getCause() instanceof IOException);
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     * @noinspection InstanceMethodNamingConvention
     */
    @Test
    public void testCacheAndCheckWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse(ExternalResourceHolder.class.isAssignableFrom(HiddenFieldCheck.class));
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyleConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);

        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final File tmpFile = temporaryFolder.newFile("file.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        // one more time to reuse cache
        verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals(cacheAfterFirstRun, cacheAfterSecondRun);
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(createCheckConfig(TranslationCheck.class));

        final File cacheFile = temporaryFolder.newFile();
        checker.setCacheFile(cacheFile.getPath());

        checker.setupChild(createCheckConfig(TranslationCheck.class));
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
        assertEquals(expectedNumberOfObjectsInCache, cache.size());

        final String expectedConfigHash = "68EE3C3B4593FD8D86159C670C504542E20C6FA0";
        assertEquals(expectedConfigHash, cache.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));

        assertNotNull(cache.getProperty(tmpFile.getPath()));
    }

    @Test
    public void testClearExistingCache() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("myConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        checker.clearCache();
        // invoke destroy to persist cache
        checker.destroy();

        final Properties cacheAfterClear = new Properties();
        cacheAfterClear.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals(1, cacheAfterClear.size());
        assertNotNull(cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        // file that should be audited is not in cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertNotNull(cacheAfterSecondRun.getProperty(pathToEmptyFile));
        assertEquals(
            cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 2;
        assertEquals(expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size());
    }

    @Test
    public void testClearCacheWhenCacheFileIsNotSet() {
        // The idea of the test is to check that when cache file is not set,
        // the invocation of clearCache method does not throw an exception.
        final Checker checker = new Checker();
        checker.clearCache();
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
            assertThat(error.getCause(), instanceOf(IOError.class));
            assertThat(error.getCause().getCause(), instanceOf(InternalError.class));
            assertEquals(errorMessage, error.getCause().getCause().getMessage());
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     * @noinspection InstanceMethodNamingConvention
     */
    @Test
    public void testCacheAndFilterWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertFalse(ExternalResourceHolder.class.isAssignableFrom(DummyFilter.class));
        final DefaultConfiguration filterConfig = createCheckConfig(DummyFilter.class);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(filterConfig);
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        // One more time to use cache.
        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals(
            cacheAfterFirstRun.getProperty(pathToEmptyFile),
            cacheAfterSecondRun.getProperty(pathToEmptyFile)
        );
        assertEquals(
            cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        final int expectedNumberOfObjectsInCache = 2;
        assertEquals(expectedNumberOfObjectsInCache, cacheAfterFirstRun.size());
        assertEquals(expectedNumberOfObjectsInCache, cacheAfterSecondRun.size());
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     * @noinspection InstanceMethodNamingConvention
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
        final String firstExternalResourceLocation = getPath("checks" + File.separator
            + "imports" + File.separator + "import-control_one.xml");
        final String firstExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + firstExternalResourceLocation;
        check.setFirstExternalResourceLocation(firstExternalResourceLocation);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        final File cacheFile = temporaryFolder.newFile();
        checkerConfig.addAttribute("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addFileSetCheck(check);
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterFirstRun = new Properties();
        cacheAfterFirstRun.load(Files.newBufferedReader(cacheFile.toPath()));

        final int expectedNumberOfObjectsInCacheAfterFirstRun = 3;
        assertEquals(expectedNumberOfObjectsInCacheAfterFirstRun, cacheAfterFirstRun.size());

        // Change a list of external resources which are used by the check
        final String secondExternalResourceLocation = "checks" + File.separator
            + "imports" + File.separator + "import-control_one-re.xml";
        final String secondExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + secondExternalResourceLocation;
        check.setSecondExternalResourceLocation(secondExternalResourceLocation);

        verify(checker, pathToEmptyFile, expected);
        final Properties cacheAfterSecondRun = new Properties();
        cacheAfterSecondRun.load(Files.newBufferedReader(cacheFile.toPath()));

        assertEquals(
            cacheAfterFirstRun.getProperty(pathToEmptyFile),
            cacheAfterSecondRun.getProperty(pathToEmptyFile)
        );
        assertEquals(
            cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY),
            cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY)
        );
        assertEquals(
            cacheAfterFirstRun.getProperty(firstExternalResourceKey),
            cacheAfterSecondRun.getProperty(firstExternalResourceKey)
        );
        assertNotNull(cacheAfterFirstRun.getProperty(firstExternalResourceKey));
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 4;
        assertEquals(expectedNumberOfObjectsInCacheAfterSecondRun, cacheAfterSecondRun.size());
        assertNull(cacheAfterFirstRun.getProperty(secondExternalResourceKey));
        assertNotNull(cacheAfterSecondRun.getProperty(secondExternalResourceKey));
    }

    @Test
    public void testClearLazyLoadCacheInDetailAST() throws Exception {
        final DefaultConfiguration checkConfig1 =
            createCheckConfig(CheckWhichDoesNotRequireCommentNodes.class);
        final DefaultConfiguration checkConfig2 =
            createCheckConfig(CheckWhichRequiresCommentNodes.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig1);
        treeWalkerConfig.addChild(checkConfig2);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyleConfig");
        checkerConfig.addChild(treeWalkerConfig);

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String filePath = getPath("api/InputClearDetailAstLazyLoadCache.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, filePath, filePath, expected);
    }

    @Test
    public void testCacheOnViolationSuppression() throws Exception {
        final File cacheFile = temporaryFolder.newFile();
        final DefaultConfiguration violationCheck =
                createCheckConfig(DummyFileSetViolationCheck.class);
        final DefaultConfiguration defaultConfig = new DefaultConfiguration("defaultConfiguration");
        defaultConfig.addAttribute("cacheFile", cacheFile.getPath());
        defaultConfig.addChild(violationCheck);

        final DefaultConfiguration filterConfig = createCheckConfig(SuppressionFilter.class);
        filterConfig.addAttribute("file", getPath("suppress_all.xml"));
        defaultConfig.addChild(filterConfig);

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addListener(new BriefUtLogger(stream));
        checker.configure(defaultConfig);

        final String fileViolationPath = temporaryFolder.newFile("ViolationFile.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, fileViolationPath, expected);

        try (FileInputStream input = new FileInputStream(cacheFile)) {
            final Properties details = new Properties();
            details.load(input);

            assertNotNull("suppressed violation file saved in cache",
                    details.getProperty(fileViolationPath));
        }
    }

    @Test
    public void testHaltOnExceptionOff() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CheckWhichThrowsError.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyleConfig");
        checkerConfig.addChild(treeWalkerConfig);

        checkerConfig.addAttribute("haltOnException", "false");

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String filePath = getPath("InputMain.java");
        final String[] expected = {
            "0: " + getCheckMessage(EXCEPTION_MSG, "java.lang.IndexOutOfBoundsException: test"),
        };

        verify(checker, filePath, filePath, expected);
    }

    @Test
    public void testCheckerProcessCallAllNeededMethodsOfFileSets() throws Exception {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        checker.process(Collections.singletonList(new File("dummy.java")));
        final List<String> expected =
            Arrays.asList("beginProcessing", "finishProcessing", "destroy");
        assertArrayEquals(expected.toArray(), fileSet.getMethodCalls().toArray());
    }

    @Test
    public void testSetFileSetCheckSetsMessageDispatcher() throws Exception {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        assertEquals(checker, fileSet.getInternalMessageDispatcher());
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
        checker.setupChild(createCheckConfig(DebugAuditAdapter.class));
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
        checker.setupChild(createCheckConfig(TestBeforeExecutionFileFilter.class));
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
        checker.setupChild(createCheckConfig(DummyFileSet.class));
        assertTrue("FileSetCheck.init() wasn't called", fileSet.isInitCalled());
    }

    @Test
    public void testDefaultLoggerClosesItStreams() throws Exception {
        final Checker checker = new Checker();
        final CloseAndFlushTestByteArrayOutputStream testInfoOutputStream =
            new CloseAndFlushTestByteArrayOutputStream();
        final CloseAndFlushTestByteArrayOutputStream testErrorOutputStream =
            new CloseAndFlushTestByteArrayOutputStream();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addListener(new DefaultLogger(testInfoOutputStream,
            true, testErrorOutputStream, true));

        final File tmpFile = temporaryFolder.newFile("file.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);

        assertEquals(1, testInfoOutputStream.getCloseCount());
        assertEquals(3, testInfoOutputStream.getFlushCount());
        assertEquals(1, testErrorOutputStream.getCloseCount());
        assertEquals(1, testErrorOutputStream.getFlushCount());
    }

    @Test
    public void testXmlLoggerClosesItStreams() throws Exception {
        final Checker checker = new Checker();
        final CloseAndFlushTestByteArrayOutputStream testInfoOutputStream =
            new CloseAndFlushTestByteArrayOutputStream();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addListener(new XMLLogger(testInfoOutputStream, true));

        final File tmpFile = temporaryFolder.newFile("file.java");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, tmpFile.getPath(), tmpFile.getPath(), expected);

        assertEquals(1, testInfoOutputStream.getCloseCount());
        assertEquals(0, testInfoOutputStream.getFlushCount());
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
        protected void processFiltered(File file, List<String> lines) throws CheckstyleException {
            log(0, "test");
        }

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> externalResourceLocation = new HashSet<>(1);
            externalResourceLocation.add("non_existing_external_resource.xml");
            return externalResourceLocation;
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
        protected void processFiltered(File file, List<String> lines) throws CheckstyleException {
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
            if (ast.branchContains(TokenTypes.BLOCK_COMMENT_BEGIN)) {
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
            if (!ast.branchContains(TokenTypes.BLOCK_COMMENT_BEGIN)) {
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
        protected void processFiltered(File file, List<String> lines) throws CheckstyleException {
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
