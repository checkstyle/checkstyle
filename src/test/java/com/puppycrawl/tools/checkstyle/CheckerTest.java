////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.powermock.api.mockito.PowerMockito;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
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

        // should remove al listeners and filters
        checker.destroy();

        // Let's try fire some events
        getFireAuditStartedMethod().invoke(checker);
        getFireAuditFinished().invoke(checker);
        checker.fireFileStarted("Some File Name");
        checker.fireFileFinished("Some File Name");

        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);

        assertFalse("Checker.destroy() doesn't remove listeners.", auditAdapter.wasCalled());
        assertFalse("Checker.destroy() doesn't remove filters.", filter.wasCalled());
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
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
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
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
        messages.add(new LocalizedMessage(0, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", messages);
        assertTrue("Checker.fireErrors() doesn't call listener", aa2.wasCalled());
        assertFalse("Checker.fireErrors() does call removed listener", auditAdapter.wasCalled());

    }

    @Test
    public void testAddFilter() {
        final Checker checker = new Checker();
        final DebugFilter filter = new DebugFilter();

        checker.addFilter(filter);

        filter.resetFilter();
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
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
        final SortedSet<LocalizedMessage> messages = Sets.newTreeSet();
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
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);

        checker.finishLocalSetup();
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
    public void testDestroyCacheWithWrongFileNameLength() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(new DefaultConfiguration("default config"));
        // We set wrong file name length in order to reproduce IOException on OS Linux, OS Windows.
        // The maximum file name length which is allowed in most UNIX, Windows file systems is 255.
        // See https://en.wikipedia.org/wiki/Filename
        final int wrongFileNameLength = 300;
        checker.setCacheFile(Strings.padEnd("fileName", wrongFileNameLength, 'e'));
        try {
            checker.destroy();
            fail("Exception did not happen");
        }
        catch (IllegalStateException ex) {
            assertTrue(ex.getCause() instanceof IOException);
        }
    }

    @Test
    public void testCacheFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyleConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        // one more time to reuse cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testCacheFileChangeInConfig() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);

        // update Checker config
        checker.destroy();
        checker.configure(checkerConfig);

        final Checker otherChecker = new Checker();
        otherChecker.setLocaleCountry(locale.getCountry());
        otherChecker.setLocaleLanguage(locale.getLanguage());
        otherChecker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        otherChecker.configure(checkerConfig);
        otherChecker.addListener(new BriefUtLogger(stream));
        // here is diff with previous checker
        checkerConfig.addAttribute("fileExtensions", "java,javax");

        // one more time on updated config
        verify(otherChecker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(createCheckConfig(TranslationCheck.class));
        checker.setCacheFile(temporaryFolder.newFile().getPath());
        checker.setupChild(createCheckConfig(TranslationCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<File> files = new ArrayList<>();
        files.add(file);
        checker.process(files);
    }

    @Test
    public void testClearExistingCache() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("myConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        checker.clearCache();
        // one more time, but file that should be audited is not in cache
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testClearNonexistentCache() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createCheckConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("simpleConfig");
        checkerConfig.addAttribute("charset", "UTF-8");
        checkerConfig.addChild(treeWalkerConfig);

        final Checker checker = new Checker();
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        checker.clearCache();
        // one more time, but cache does not exist
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testCatchErrorInProcessFilesMethod() throws Exception {
        // The idea of the test is to satisfy coverage rate.
        // An Error indicates serious problems that a reasonable application should not try to
        // catch, but due to issue https://github.com/checkstyle/checkstyle/issues/2285
        // we catch errors in 'processFiles' method. Most such errors are abnormal conditions,
        // that is why we use PowerMockito to reproduse them.
        final File mock = PowerMockito.mock(File.class);
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        final String errorMessage = "Java Virtual Machine is broken"
            + " or has run out of resources necessary for it to continue operating.";
        final Error expectedError = new IOError(new InternalError(errorMessage));
        when(mock.lastModified()).thenThrow(expectedError);
        final Checker checker = new Checker();
        final List<File> filesToProcess = Lists.newArrayList();
        filesToProcess.add(mock);
        try {
            checker.process(filesToProcess);
            fail("IOError is expected!");
        }
        catch (Error error) {
            assertThat(error.getCause(), instanceOf(IOError.class));
            assertThat(error.getCause().getCause(), instanceOf(InternalError.class));
            assertEquals(errorMessage, error.getCause().getCause().getMessage());
        }
    }

    @Test
    public void testExternalConfigurationResourceDoesNotExist() throws Exception {
        final Checker mockChecker = createMockCheckerWithCacheForModule(DummyFileSetCheck.class);

        final String pathToEmptyFile = temporaryFolder.newFile("EmptyFile.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        // We invoke 'verify' twice to invalidate cache
        // and have two identical exceptions which happen on the same line between runs
        final int numberOfRuns = 2;
        for (int i = 0; i < numberOfRuns; i++) {
            verify(mockChecker, pathToEmptyFile, expected);
        }
    }

    @Test
    public void testInvalidateCacheDueToDifferentExceptionsBetweenRuns() throws Exception {
        final Checker mockChecker = createMockCheckerWithCacheForModule(DummyFileSetCheck.class);

        final String pathToEmptyFile = temporaryFolder.newFile("TestFile.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(mockChecker, pathToEmptyFile, expected);
        // Once again to invalidate cache because in the second run exception will happen
        // on different line
        verify(mockChecker, pathToEmptyFile, expected);
    }

    @Test
    public void testCacheIoExceptionWhenReadingExternalResource() throws Exception {
        final SuppressionFilter mock = PowerMockito.mock(SuppressionFilter.class);
        final Set<String> mockResourceLocations = new HashSet<>(1);
        mockResourceLocations.add("http://mock.sourceforge.net/suppressions_none.xml");
        when(mock.getExternalResourceLocations()).thenReturn(mockResourceLocations);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        checker.addFilter(mock);
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
        // One more time to use cahce.
        verify(checker, pathToEmptyFile, pathToEmptyFile, expected);
    }

    @Test
    public void testMultipleConfigs() throws Exception {
        final DefaultConfiguration headerCheckConfig =
            createCheckConfig(MockMissingExternalResourcesFileSetCheck.class);

        final DefaultConfiguration dummyFileSetCheckConfig =
            createCheckConfig(DummyFileSetCheck.class);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());
        checkerConfig.addChild(headerCheckConfig);
        checkerConfig.addChild(dummyFileSetCheckConfig);

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, expected);
        // Once again to invalidate cache because in the second run IOException will happen
        // on different line for DummyFileSetCheck and it will change the content
        verify(checker, pathToEmptyFile, expected);
    }

    @Test
    public void testFilterWhichDoesNotImplementExternalResourceHolderInterface() throws Exception {
        final DefaultConfiguration filterConfig = createCheckConfig(DummyFilter.class);

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addChild(filterConfig);
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        verify(checker, pathToEmptyFile, expected);
        // One more time to use cache.
        verify(checker, pathToEmptyFile, expected);
    }

    @Test
    public void testCheckAddsNewResourceLocationButKeepsSameCheckerInstance() throws Exception {

        // Use case (https://github.com/checkstyle/checkstyle/pull/3092#issuecomment-218162436):
        // Imagine that cache exists in a file. New version of Checkstyle appear.
        // New release contains update to a some check to have additional external resource.
        // User update his configuration and run validation as usually.
        // Cache should not be reused.

        final DynamicalResourceHolderCheck check = new DynamicalResourceHolderCheck();
        check.setFirstExternalResourceLocation(getPath("checks" + File.separator
            + "imports" + File.separator + "import-control_one.xml"));

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("checkstyle_checks");
        checkerConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addFileSetCheck(check);
        checker.configure(checkerConfig);
        checker.addListener(new BriefUtLogger(stream));

        final String pathToEmptyFile = temporaryFolder.newFile("file.java").getPath();
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checker, pathToEmptyFile, expected);

        // Change a list of external resources which are used by the check
        check.setSecondExternalResourceLocation("checks" + File.separator
            + "imports" + File.separator + "import-control_one-re.xml");

        verify(checker, pathToEmptyFile, expected);
    }

    private Checker createMockCheckerWithCacheForModule(
        Class<? extends ExternalResourceHolder> mockClass) throws IOException, CheckstyleException {

        final DefaultConfiguration mockConfig = createCheckConfig(mockClass);

        final DefaultConfiguration defaultConfig = new DefaultConfiguration("defaultConfiguration");
        defaultConfig.addAttribute("cacheFile", temporaryFolder.newFile().getPath());
        defaultConfig.addChild(mockConfig);

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addListener(new BriefUtLogger(stream));
        checker.configure(defaultConfig);
        return checker;
    }

    private static class DummyFilter implements Filter {

        @Override
        public boolean accept(AuditEvent event) {
            return false;
        }
    }

    private static class DummyFileSetCheck extends AbstractFileSetCheck
        implements ExternalResourceHolder {

        @Override
        protected void processFiltered(File file, List<String> lines) throws CheckstyleException { }

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> externalResourceLocation = new HashSet<>(1);
            externalResourceLocation.add("non_existing_external_resource.xml");
            return externalResourceLocation;
        }
    }

    private static class MockMissingExternalResourcesFileSetCheck extends AbstractFileSetCheck
        implements ExternalResourceHolder {

        @Override
        protected void processFiltered(File file, List<String> lines) throws CheckstyleException { }

        @Override
        public Set<String> getExternalResourceLocations() {
            final Set<String> externalResourceLocation = new HashSet<>(1);
            externalResourceLocation.add("missing_external_resource.xml");
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
}
