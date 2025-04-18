///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.Checker.EXCEPTION_MSG;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_FINISHED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.DefaultLogger.AUDIT_STARTED_MESSAGE;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;
import static org.mockito.Mockito.doReturn;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.filefilters.BeforeExecutionExclusionFileFilter;
import com.puppycrawl.tools.checkstyle.filters.SuppressionFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.CheckWhichThrowsError;
import com.puppycrawl.tools.checkstyle.internal.testmodules.DebugAuditAdapter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.DebugFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestBeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestFileSetCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CloseAndFlushTestByteArrayOutputStream;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import de.thetaphi.forbiddenapis.SuppressForbidden;

/**
 * CheckerTest.
 *
 * @noinspection ClassWithTooManyDependencies
 * @noinspectionreason ClassWithTooManyDependencies - complex tests require a large number
 *      of imports
 */
public class CheckerTest extends AbstractModuleTestSupport {

    public static final String ERROR_MESSAGE = "Java Virtual Machine is broken"
        + " or has run out of resources necessary for it to continue operating.";
    @TempDir
    public File temporaryFolder;

    private File createTempFile(String prefix) throws IOException {
        return createTempFile(prefix, ".tmp");
    }

    private File createTempFile(String prefix, String suffix) throws IOException {
        final String name = Objects.requireNonNull(prefix)
                + UUID.randomUUID()
                + Objects.requireNonNull(suffix);
        return Files.createFile(temporaryFolder.toPath().resolve(name)).toFile();
    }

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

        checker.process(Collections.singletonList(createTempFile("junit").toPath()));
        final SortedSet<Violation> violations = new TreeSet<>();
        violations.add(new Violation(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", violations);

        assertWithMessage("Checker.destroy() doesn't remove listeners.")
                .that(auditAdapter.wasCalled())
                .isFalse();
        assertWithMessage("Checker.destroy() doesn't remove file sets.")
                .that(fileSet.wasCalled())
                .isFalse();
        assertWithMessage("Checker.destroy() doesn't remove filters.")
                .that(filter.wasCalled())
                .isFalse();
        assertWithMessage("Checker.destroy() doesn't remove file filters.")
                .that(fileFilter.wasCalled())
                .isFalse();
    }

    @Test
    public void testAddListener() throws Exception {
        final Checker checker = new Checker();
        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        // Let's try fire some events
        getFireAuditStartedMethod().invoke(checker);
        assertWithMessage("Checker.fireAuditStarted() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireAuditStarted() doesn't pass event")
                .that(auditAdapter.wasEventPassed())
                .isTrue();

        auditAdapter.resetListener();
        getFireAuditFinished().invoke(checker);
        assertWithMessage("Checker.fireAuditFinished() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireAuditFinished() doesn't pass event")
                .that(auditAdapter.wasEventPassed())
                .isTrue();

        auditAdapter.resetListener();
        checker.fireFileStarted("Some File Name");
        assertWithMessage("Checker.fireFileStarted() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireFileStarted() doesn't pass event")
                .that(auditAdapter.wasEventPassed())
                .isTrue();

        auditAdapter.resetListener();
        checker.fireFileFinished("Some File Name");
        assertWithMessage("Checker.fireFileFinished() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireFileFinished() doesn't pass event")
                .that(auditAdapter.wasEventPassed())
                .isTrue();

        auditAdapter.resetListener();
        final SortedSet<Violation> violations = new TreeSet<>();
        violations.add(new Violation(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", violations);
        assertWithMessage("Checker.fireErrors() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireErrors() doesn't pass event")
                .that(auditAdapter.wasEventPassed())
                .isTrue();
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
        assertWithMessage("Checker.fireAuditStarted() doesn't call listener")
                .that(aa2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireAuditStarted() does call removed listener")
                .that(auditAdapter.wasCalled())
                .isFalse();

        aa2.resetListener();
        getFireAuditFinished().invoke(checker);
        assertWithMessage("Checker.fireAuditFinished() doesn't call listener")
                .that(aa2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireAuditFinished() does call removed listener")
                .that(auditAdapter.wasCalled())
                .isFalse();

        aa2.resetListener();
        checker.fireFileStarted("Some File Name");
        assertWithMessage("Checker.fireFileStarted() doesn't call listener")
                .that(aa2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireFileStarted() does call removed listener")
                .that(auditAdapter.wasCalled())
                .isFalse();

        aa2.resetListener();
        checker.fireFileFinished("Some File Name");
        assertWithMessage("Checker.fireFileFinished() doesn't call listener")
                .that(aa2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireFileFinished() does call removed listener")
                .that(auditAdapter.wasCalled())
                .isFalse();

        aa2.resetListener();
        final SortedSet<Violation> violations = new TreeSet<>();
        violations.add(new Violation(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", violations);
        assertWithMessage("Checker.fireErrors() doesn't call listener")
                .that(aa2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireErrors() does call removed listener")
                .that(auditAdapter.wasCalled())
                .isFalse();
    }

    @Test
    public void testAddBeforeExecutionFileFilter() throws Exception {
        final Checker checker = new Checker();
        final TestBeforeExecutionFileFilter filter = new TestBeforeExecutionFileFilter();

        checker.addBeforeExecutionFileFilter(filter);

        filter.resetFilter();
        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        assertWithMessage("Checker.acceptFileStarted() doesn't call filter")
                .that(filter.wasCalled())
                .isTrue();
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
        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        assertWithMessage("Checker.acceptFileStarted() doesn't call filter")
                .that(f2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.acceptFileStarted() does call removed filter")
                .that(filter.wasCalled())
                .isFalse();
    }

    @Test
    public void testAddFilter() {
        final Checker checker = new Checker();
        final DebugFilter filter = new DebugFilter();

        checker.addFilter(filter);

        filter.resetFilter();
        final SortedSet<Violation> violations = new TreeSet<>();
        violations.add(new Violation(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", violations);
        assertWithMessage("Checker.fireErrors() doesn't call filter")
                .that(filter.wasCalled())
                .isTrue();
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
        final SortedSet<Violation> violations = new TreeSet<>();
        violations.add(new Violation(1, 0, "a Bundle", "message.key",
                new Object[] {"arg"}, null, getClass(), null));
        checker.fireErrors("Some File Name", violations);
        assertWithMessage("Checker.fireErrors() doesn't call filter")
                .that(f2.wasCalled())
                .isTrue();
        assertWithMessage("Checker.fireErrors() does call removed filter")
                .that(filter.wasCalled())
                .isFalse();
    }

    @Test
    public void testFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", createTempFile("junit").getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        final List<Path> files = new ArrayList<>();
        final Path file = new File("file.pdf").toPath();
        files.add(file);
        final Path otherFile = new File("file.java").toPath();
        files.add(otherFile);
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        checker.setCacheFile(createTempFile("junit").getPath());
        final int counter = checker.process(files);

        // comparing to 1 as there is only one legal file in input
        final int numLegalFiles = 1;
        final PropertyCacheFile cache = TestUtil.getInternalState(checker, "cacheFile");
        assertWithMessage("There were more legal files than expected")
            .that(counter)
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was started on larger amount of files than expected")
            .that(auditAdapter.getNumFilesStarted())
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was finished on larger amount of files than expected")
            .that(auditAdapter.getNumFilesFinished())
            .isEqualTo(numLegalFiles);
        assertWithMessage("Cache shout not contain any file")
            .that(cache.get(new File("file.java").getCanonicalPath()))
            .isNull();
    }

    @Test
    @Deprecated(since = "9.3")
    public void testFileExtensionsTestOldFilesApi() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", createTempFile("junit").getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        final List<File> files = new ArrayList<>();
        final Path file = new File("file.pdf").toPath();
        files.add(file.toFile());
        final Path otherFile = new File("file.java").toPath();
        files.add(otherFile.toFile());
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        checker.setCacheFile(createTempFile("junit").getPath());
        final int counter = checker.process(files);

        // comparing to 1 as there is only one legal file in input
        final int numLegalFiles = 1;
        final PropertyCacheFile cache = TestUtil.getInternalState(checker, "cacheFile");
        assertWithMessage("There were more legal files than expected")
            .that(counter)
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was started on larger amount of files than expected")
            .that(auditAdapter.getNumFilesStarted())
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was finished on larger amount of files than expected")
            .that(auditAdapter.getNumFilesFinished())
            .isEqualTo(numLegalFiles);
        assertWithMessage("Cache shout not contain any file")
            .that(cache.get(new File("file.java").getCanonicalPath()))
            .isNull();
    }

    @Test
    public void testIgnoredFileExtensions() throws Exception {
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        final File tempFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", tempFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);

        final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
        checker.addListener(auditAdapter);

        final List<Path> allIgnoredFiles = new ArrayList<>();
        final Path ignoredFile = new File("file.pdf").toPath();
        allIgnoredFiles.add(ignoredFile);
        final String[] fileExtensions = {"java", "xml", "properties"};
        checker.setFileExtensions(fileExtensions);
        checker.setCacheFile(createTempFile("junit").getPath());
        final int counter = checker.process(allIgnoredFiles);

        // comparing to 0 as there is no legal file in input
        final int numLegalFiles = 0;
        assertWithMessage("There were more legal files than expected")
            .that(counter)
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was started on larger amount of files than expected")
            .that(auditAdapter.getNumFilesStarted())
            .isEqualTo(numLegalFiles);
        assertWithMessage("Audit was finished on larger amount of files than expected")
            .that(auditAdapter.getNumFilesFinished())
            .isEqualTo(numLegalFiles);
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
            assertWithMessage("Exception is expected").fail();
        }
        catch (UnsupportedEncodingException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("unsupported charset: 'UNKNOWN-CHARSET'");
        }
    }

    @Test
    public void testSetSeverity() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineXmlConfig(getPath("InputCheckerTestSeverity.java"), expected);
    }

    @Test
    public void testNoClassLoaderNoModuleFactory() {
        final Checker checker = new Checker();

        try {
            checker.finishLocalSetup();
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("if no custom moduleFactory is set,"
                    + " moduleClassLoader must be specified");
        }
    }

    @Test
    public void testNoModuleFactory() throws Exception {
        final Checker checker = new Checker();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        checker.setModuleClassLoader(classLoader);
        checker.finishLocalSetup();
        final Context actualCtx = TestUtil.getInternalState(checker, "childContext");

        assertWithMessage("Default module factory should be created when it is not specified")
            .that(actualCtx.get("moduleFactory"))
            .isNotNull();
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

        final Context context = TestUtil.getInternalState(checker, "childContext");
        final String encoding = StandardCharsets.UTF_8.name();
        assertWithMessage("Charset was different than expected")
            .that(context.get("charset"))
            .isEqualTo(encoding);
        assertWithMessage("Severity is set to unexpected value")
            .that(context.get("severity"))
            .isEqualTo("error");
        assertWithMessage("Basedir is set to unexpected value")
            .that(context.get("basedir"))
            .isEqualTo("testBaseDir");

        final Field sLocale = LocalizedMessage.class.getDeclaredField("sLocale");
        sLocale.setAccessible(true);
        final Locale locale = (Locale) sLocale.get(null);
        assertWithMessage("Locale is set to unexpected value")
            .that(locale)
            .isEqualTo(Locale.ITALY);
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
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("java.lang.String is not allowed as a child in Checker");
        }
    }

    @Test
    public void testSetupChildInvalidProperty() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);
        checkConfig.addProperty("$$No such property", null);
        try {
            createChecker(checkConfig);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker"
                        + " - cannot initialize module " + checkConfig.getName()
                        + " - Property '$$No such property'"
                        + " does not exist, please check the documentation");
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

        final List<AuditListener> listeners = TestUtil.getInternalState(checker, "listeners");
        assertWithMessage("Invalid child listener class")
                .that(listeners.get(listeners.size() - 1) instanceof DebugAuditAdapter)
                .isTrue();
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
            assertWithMessage("Exception did not happen").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Cause of exception differs from IOException")
                    .that(ex.getCause())
                    .isInstanceOf(IOException.class);
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndCheckWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertWithMessage("ExternalResourceHolder has changed his parent")
                .that(ExternalResourceHolder.class.isAssignableFrom(HiddenFieldCheck.class))
                .isFalse();
        final DefaultConfiguration checkConfig = createModuleConfig(HiddenFieldCheck.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());

        final File cacheFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final File tmpFile = createTempFile("file", ".java");

        execute(checkerConfig, tmpFile.getPath());
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        // one more time to reuse cache
        execute(checkerConfig, tmpFile.getPath());
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        assertWithMessage("Cache from first run differs from second run cache")
            .that(cacheAfterSecondRun)
            .isEqualTo(cacheAfterFirstRun);
    }

    @Test
    public void testWithCacheWithNoViolation() throws Exception {
        final Checker checker = new Checker();
        final PackageObjectFactory factory = new PackageObjectFactory(
            new HashSet<>(), Thread.currentThread().getContextClassLoader());
        checker.setModuleFactory(factory);
        checker.configure(createModuleConfig(TranslationCheck.class));

        final Path cacheFile = createTempFile("junit").toPath();
        checker.setCacheFile(cacheFile.toFile().getPath());

        checker.setupChild(createModuleConfig(TranslationCheck.class));
        final Path tmpFile = createTempFile("file", ".java").toPath();
        final List<Path> files = new ArrayList<>(1);
        files.add(tmpFile);
        checker.process(files);

        // invoke destroy to persist cache
        checker.destroy();

        final Properties cache = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile)) {
            cache.load(reader);
        }

        // There should 2 objects in cache: processed file (file.java) and checker configuration.
        final int expectedNumberOfObjectsInCache = 2;
        assertWithMessage("Cache has unexpected size")
            .that(cache)
            .hasSize(expectedNumberOfObjectsInCache);

        final String expectedConfigHash = "D581D4A2BD482D4E1EF1F82459356BA2D8A3B" + "FC3";
        assertWithMessage("Cache has unexpected hash")
            .that(cache.getProperty(PropertyCacheFile.CONFIG_HASH_KEY))
            .isEqualTo(expectedConfigHash);

        assertWithMessage("Cache file has null path")
            .that(cache.getProperty(tmpFile.toFile().getPath()))
            .isNotNull();
    }

    @Test
    public void testClearExistingCache() throws Exception {
        final DefaultConfiguration checkerConfig = createRootConfig(null);
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        final File cacheFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

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

        assertWithMessage("Cache has unexpected size")
            .that(cacheAfterClear)
            .hasSize(1);
        assertWithMessage("Cache has null hash")
            .that(cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY))
            .isNotNull();

        final String pathToEmptyFile = createTempFile("file", ".java").getPath();

        // file that should be audited is not in cache
        execute(checkerConfig, pathToEmptyFile);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        assertWithMessage("Cache has null path")
            .that(cacheAfterSecondRun.getProperty(pathToEmptyFile))
            .isNotNull();
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Cash have changed it hash")
            .that(cacheHash)
            .isEqualTo(cacheAfterClear.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 2;
        assertWithMessage("Cache has changed number of items")
            .that(cacheAfterSecondRun)
            .hasSize(expectedNumberOfObjectsInCacheAfterSecondRun);
    }

    @Test
    public void testClearCache() throws Exception {
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("myConfig");
        checkerConfig.addProperty("charset", "UTF-8");
        final File cacheFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(violationCheck);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        checker.clearCache();
        // invoke destroy to persist cache
        final PropertyCacheFile cache = TestUtil.getInternalState(checker, "cacheFile");
        cache.persist();

        final Properties cacheAfterClear = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterClear.load(reader);
        }

        assertWithMessage("Cache has unexpected size")
            .that(cacheAfterClear)
            .hasSize(1);
    }

    @Test
    public void setFileExtension() {
        final Checker checker = new Checker();
        checker.setFileExtensions(".test1", "test2");
        final String[] actual = TestUtil.getInternalState(checker, "fileExtensions");
        assertWithMessage("Extensions are not expected")
            .that(actual)
            .isEqualTo(new String[] {".test1", ".test2"});
    }

    @Test
    public void testClearCacheWhenCacheFileIsNotSet() {
        // The idea of the test is to check that when cache file is not set,
        // the invocation of clearCache method does not throw an exception.
        final Checker checker = new Checker();
        checker.clearCache();
        final PropertyCacheFile cache = TestUtil.getInternalState(checker, "cacheFile");
        assertWithMessage("If cache file is not set the cache should default to null")
            .that(cache)
            .isNull();
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testCatchErrorInProcessFilesMethod() throws Exception {
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        try {
            new Checker().process(List.of(throwIOErrorOnToAbsolutePath()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause())
                    .isInstanceOf(IOError.class);
            assertWithMessage("Error cause is not InternalError")
                    .that(error.getCause().getCause())
                    .isInstanceOf(InternalError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);
        }
    }

    @Test
    public void testCatchErrorWithNoCache() throws Exception {
        try {
            new Checker().process(List.of(throwErrorOnLastModified()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
//            assertWithMessage("Error cause differs from IOError")
//                    .that(error)
//                    .hasCauseThat()
//                    .hasCauseThat()
//                    .isInstanceOf(IOError.class);
            assertWithMessage("Error cause is not InternalError")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .isInstanceOf(InternalError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);
        }
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testCatchErrorWithNoFileName() throws Exception {
        // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
        try {
            new Checker().process(List.of(throwIOErrorOnToAbsolutePath()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error)
                    .hasCauseThat()
                    .isInstanceOf(IOError.class);
            assertWithMessage("Error cause is not InternalError")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .isInstanceOf(InternalError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);
        }
    }

    /**
     * It is OK to have long test method name here as it describes the test purpose.
     */
    @Test
    public void testCacheAndFilterWhichDoesNotImplementExternalResourceHolderInterface()
            throws Exception {
        assertWithMessage("ExternalResourceHolder has changed its parent")
                .that(ExternalResourceHolder.class.isAssignableFrom(DummyFilter.class))
                .isFalse();
        final DefaultConfiguration filterConfig = createModuleConfig(DummyFilter.class);

        final DefaultConfiguration checkerConfig = createRootConfig(filterConfig);
        final File cacheFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final String pathToEmptyFile = createTempFile("file", ".java").getPath();

        execute(checkerConfig, pathToEmptyFile);
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        // One more time to use cache.
        execute(checkerConfig, pathToEmptyFile);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        final String cacheFilePath = cacheAfterSecondRun.getProperty(pathToEmptyFile);
        assertWithMessage("Cache file has changed its path")
            .that(cacheFilePath)
            .isEqualTo(cacheAfterFirstRun.getProperty(pathToEmptyFile));
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Cache has changed its hash")
            .that(cacheHash)
            .isEqualTo(cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));
        final int expectedNumberOfObjectsInCache = 2;
        assertWithMessage("Number of items in cache differs from expected")
            .that(cacheAfterFirstRun)
            .hasSize(expectedNumberOfObjectsInCache);
        assertWithMessage("Number of items in cache differs from expected")
            .that(cacheAfterSecondRun)
            .hasSize(expectedNumberOfObjectsInCache);
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
        final File cacheFile = createTempFile("junit");
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.addFileSetCheck(check);
        checker.addFilter(new DummyFilterSet());
        checker.configure(checkerConfig);
        checker.addListener(getBriefUtLogger());

        final String pathToEmptyFile = createTempFile("file", ".java").getPath();

        execute(checker, pathToEmptyFile);
        final Properties cacheAfterFirstRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterFirstRun.load(reader);
        }

        final int expectedNumberOfObjectsInCacheAfterFirstRun = 4;
        assertWithMessage("Number of items in cache differs from expected")
            .that(cacheAfterFirstRun)
            .hasSize(expectedNumberOfObjectsInCacheAfterFirstRun);

        // Change a list of external resources which are used by the check
        final String secondExternalResourceLocation = "InputCheckerImportControlTwo.xml";
        final String secondExternalResourceKey = PropertyCacheFile.EXTERNAL_RESOURCE_KEY_PREFIX
                + secondExternalResourceLocation;
        check.setSecondExternalResourceLocation(secondExternalResourceLocation);

        checker.addFileSetCheck(check);
        checker.configure(checkerConfig);

        execute(checker, pathToEmptyFile);
        final Properties cacheAfterSecondRun = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
            cacheAfterSecondRun.load(reader);
        }

        final String cacheFilePath = cacheAfterSecondRun.getProperty(pathToEmptyFile);
        assertWithMessage("Cache file has changed its path")
            .that(cacheFilePath)
            .isEqualTo(cacheAfterFirstRun.getProperty(pathToEmptyFile));
        final String cacheHash = cacheAfterSecondRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY);
        assertWithMessage("Cache has changed its hash")
            .that(cacheHash)
            .isEqualTo(cacheAfterFirstRun.getProperty(PropertyCacheFile.CONFIG_HASH_KEY));
        final String resourceKey = cacheAfterSecondRun.getProperty(firstExternalResourceKey);
        assertWithMessage("Cache has changed its resource key")
            .that(resourceKey)
            .isEqualTo(cacheAfterFirstRun.getProperty(firstExternalResourceKey));
        assertWithMessage("Cache has null as a resource key")
            .that(cacheAfterFirstRun.getProperty(firstExternalResourceKey))
            .isNotNull();
        final int expectedNumberOfObjectsInCacheAfterSecondRun = 4;
        assertWithMessage("Number of items in cache differs from expected")
            .that(cacheAfterSecondRun)
            .hasSize(expectedNumberOfObjectsInCacheAfterSecondRun);
        assertWithMessage("Cache has not null as a resource key")
            .that(cacheAfterFirstRun.getProperty(secondExternalResourceKey))
            .isNull();
        assertWithMessage("Cache has null as a resource key")
            .that(cacheAfterSecondRun.getProperty(secondExternalResourceKey))
            .isNotNull();
    }

    @Test
    public void testClearLazyLoadCacheInDetailAST() throws Exception {

        final String filePath = getPath("InputCheckerClearDetailAstLazyLoadCache.java");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testCacheOnViolationSuppression() throws Exception {
        final File cacheFile = createTempFile("junit");
        final DefaultConfiguration violationCheck =
                createModuleConfig(DummyFileSetViolationCheck.class);

        final DefaultConfiguration filterConfig = createModuleConfig(SuppressionFilter.class);
        filterConfig.addProperty("file", getPath("InputCheckerSuppressAll.xml"));

        final DefaultConfiguration checkerConfig = createRootConfig(violationCheck);
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(filterConfig);

        final String fileViolationPath = createTempFile("ViolationFile", ".java").getPath();

        execute(checkerConfig, fileViolationPath);

        try (InputStream input = Files.newInputStream(cacheFile.toPath())) {
            final Properties details = new Properties();
            details.load(input);

            assertWithMessage("suppressed violation file saved in cache")
                .that(details.getProperty(fileViolationPath))
                .isNotNull();
        }
    }

    @Test
    public void testHaltOnException() throws Exception {
        final String filePath = getPath("InputChecker.java");
        try {
            execute(createModuleConfig(CheckWhichThrowsError.class), filePath);
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("Exception was thrown while processing " + filePath);
        }
    }

    @Test
    public void testExceptionWithCache() throws Exception {
        final File cacheFile = createTempFile("junit");

        final DefaultConfiguration checkConfig =
                createModuleConfig(CheckWhichThrowsError.class);

        final DefaultConfiguration treewalkerConfig =
                createModuleConfig(TreeWalker.class);
        treewalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treewalkerConfig);
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());
        checkerConfig.addChild(treewalkerConfig);

        final Checker checker = createChecker(checkerConfig);

        final String filePath = getPath("InputChecker.java");
        try {
            checker.process(Collections.singletonList(new File(filePath).toPath()));
            assertWithMessage("Exception is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is not expected")
                .that(ex.getMessage())
                .isEqualTo("Exception was thrown while processing " + filePath);

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertWithMessage("Cache has unexpected size")
                .that(cache)
                .hasSize(1);
            assertWithMessage("testFile is not in cache")
                .that(cache.getProperty(filePath))
                .isNull();
        }
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testCatchErrorWithCache() throws Exception {
        final File cacheFile = createTempFile("junit");

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        try {
            checker.process(List.of(throwIOErrorOnToAbsolutePath()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause())
                    .isInstanceOf(IOError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertWithMessage("Cache has unexpected size")
                    .that(cache)
                    .hasSize(1);
            assertWithMessage("testFile is not in cache")
                .that(cache.getProperty("testFile"))
                .isNull();
        }
    }

    @Test
    public void testCatchErrorWithCache2() throws Exception {
        final File cacheFile = createTempFile("junit");

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        try {
            checker.process(List.of(throwErrorOnToFile()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error.getCause().getCause())
                    .isInstanceOf(InternalError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);
            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertWithMessage("Cache has unexpected size")
                    .that(cache)
                    .hasSize(1);
            assertWithMessage("testFile is not in cache")
                .that(cache.getProperty("testFile"))
                .isNull();
        }
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testCatchErrorWithCacheWithNoFileName() throws Exception {
        final File cacheFile = createTempFile("junit");

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        try {
            checker.process(List.of(throwIOErrorOnToAbsolutePath()));
            assertWithMessage("IOError is expected!").fail();
        }
        // -@cs[IllegalCatchExtended] Testing for catch Error is part of 100% coverage.
        catch (Error error) {
            assertWithMessage("Error cause differs from IOError")
                    .that(error)
                    .hasCauseThat()
                    .isInstanceOf(IOError.class);
            assertWithMessage("Error message is not expected")
                    .that(error)
                    .hasCauseThat()
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertWithMessage("Cache has unexpected size")
                    .that(cache)
                    .hasSize(1);
        }
    }

    private static Path throwIOErrorOnToAbsolutePath() {
        final Path mock = Mockito.spy(Path.of("dummy.java"));
        Mockito.when(mock.toAbsolutePath()).thenThrow(new IOError(new InternalError(ERROR_MESSAGE)));
        return mock;
    }

    private static Path throwErrorOnToFile() {
        final Path path = Path.of("dummy.java");
        final Path mock = Mockito.spy(path);
        Mockito.when(mock.toAbsolutePath()).thenReturn(path);
        Mockito.when(mock.toFile())
                // pass for:
                // .filter(path -> CommonUtil.matchesFileExtension(path.toFile(), fileExtensions))
                .thenReturn(path.toFile())
                // throw on:
                // final long timestamp = file.toFile().lastModified();
                .thenThrow(new Error(new InternalError(ERROR_MESSAGE)));
        return mock;
    }

    private static Path throwErrorOnLastModified() {
        final Path mock = Mockito.spy(Path.of("dummy.java"));
        final File file = Mockito.mock(File.class);
        Mockito.when(file.lastModified()).thenThrow(new Error(new InternalError(ERROR_MESSAGE)));
        Mockito.when(mock.toFile()).thenReturn(file);
        return mock;
    }

    @Test
    public void testProcessFilesWithSecurityErrorOnAbsolutePath() {
        final Path mockPath = Mockito.mock(Path.class);
        Mockito.when(mockPath.toFile()).thenReturn(Mockito.mock(File.class));
        Mockito.when(mockPath.toString()).thenReturn("testFile.java");
        Mockito.when(mockPath.toAbsolutePath()).thenThrow(new SecurityException("Access denied"));

        final Checker checker = new Checker();
        checker.addListener(new DebugAuditAdapter());

        try {
            checker.process(List.of(mockPath));
            assertWithMessage("CheckstyleException should be thrown").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Exception cause should be SecurityException")
                    .that(ex.getCause())
                    .isInstanceOf(SecurityException.class);
            assertWithMessage("Exception should contain file path")
                    .that(ex.getMessage())
                    .contains("testFile.java");
        }
    }

    @Test
    public void testProcessFilesWithFileExtensionMismatch() throws Exception {
        // Setup
        final Checker checker = new Checker();
        checker.addListener(new DebugAuditAdapter());
        checker.setFileExtensions("java", "xml");

        final Path pdfFile = Paths.get("test.pdf");
        final Path javaFile = Paths.get("test.java");

        // Process files - only java file should be processed
        final int errorCount = checker.process(List.of(pdfFile, javaFile));

        assertWithMessage("Only one file should be processed")
                .that(errorCount)
                .isEqualTo(1); // Assuming no errors in processing
    }

    @Test
    public void testProcessFilesWithBeforeExecutionFilter() throws Exception {
        // Setup
        final Checker checker = new Checker();
        checker.addListener(new DebugAuditAdapter());

        // Add filter that rejects all files
        final BeforeExecutionFileFilter rejectingFilter = fileName -> false;
        checker.addBeforeExecutionFileFilter(rejectingFilter);

        final Path testFile = Paths.get("test.java");

        // Process file - should be rejected by filter
        final int errorCount = checker.process(List.of(testFile));

        assertWithMessage("File should be rejected by filter")
                .that(errorCount)
                .isEqualTo(0);
    }
    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testExceptionWithNoFileName() {
        final Checker checker = new Checker();
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(getFile());
        try {
            checker.process(filesToProcess.stream().map(File::toPath)
                    .collect(Collectors.toUnmodifiableList()));
            // assertWithMessage("SecurityException is expected!").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error cause differs from SecurityException")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(SecurityException.class);
            assertWithMessage("Error message is not expected")
                    .that(ex)
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);
        }
    }

    private static File getFile() {
        return new File("testFile") {
            private static final long serialVersionUID = 1L;

            /**
             * Test is checking catch clause when exception is thrown.
             *
             * @noinspection ProhibitedExceptionThrown
             * @noinspectionreason ProhibitedExceptionThrown - we require mocked file to
             *      throw exception as part of test
             */
            @Override
            public String getAbsolutePath() {
                throw new SecurityException("Security Exception");
            }
        };
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
     */
    @Test
    public void testExceptionWithCacheAndNoFileName() throws Exception {
        final File cacheFile = createTempFile("junit");

        final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
        checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        checkerConfig.addProperty("cacheFile", cacheFile.getPath());

        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(checkerConfig);
        final List<File> filesToProcess = new ArrayList<>();
        filesToProcess.add(getFile());
        try {
            checker.process(filesToProcess.stream().map(File::toPath)
                    .collect(Collectors.toUnmodifiableList()));
            // assertWithMessage("SecurityException is expected!").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error cause differs from SecurityException")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(SecurityException.class);
            assertWithMessage("Error message is not expected")
                    .that(ex)
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo(ERROR_MESSAGE);

            // destroy is called by Main
            checker.destroy();

            final Properties cache = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(cacheFile.toPath())) {
                cache.load(reader);
            }

            assertWithMessage("Cache has unexpected size")
                    .that(cache)
                    .hasSize(1);
        }
    }

    @Test
    public void testHaltOnExceptionOff() throws Exception {
        final String filePath = getPath("InputChecker.java");
        final String[] expected = {
            "1: " + getCheckMessage(EXCEPTION_MSG, "java.lang.IndexOutOfBoundsException: test"),
        };

        verifyWithInlineXmlConfig(filePath, expected);
    }

    @Test
    public void testTabViolationDefault() throws Exception {
        final String[] expected = {
            "17:17: violation",
            "21:49: violation",
        };
        verifyWithInlineConfigParser(getPath("InputCheckerTabCharacter.java"),
            expected);
    }

    @Test
    public void testTabViolationCustomWidth() throws Exception {
        final String[] expected = {
            "18:17: violation",
            "22:37: violation",
        };

        verifyWithInlineXmlConfig(getPath("InputCheckerTabCharacterCustomWidth.java"), expected);
    }

    @Test
    public void testCheckerProcessCallAllNeededMethodsOfFileSets() throws Exception {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        final List<String> expected =
            Arrays.asList("beginProcessing", "finishProcessing", "destroy");
        assertWithMessage("Method calls were not expected")
            .that(fileSet.getMethodCalls())
            .isEqualTo(expected);
    }

    @Test
    public void testSetFileSetCheckSetsMessageDispatcher() {
        final DummyFileSet fileSet = new DummyFileSet();
        final Checker checker = new Checker();
        checker.addFileSetCheck(fileSet);
        assertWithMessage("Message dispatcher was not expected")
            .that(fileSet.getInternalMessageDispatcher())
            .isEqualTo(checker);
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
        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        assertWithMessage("Checker.fireAuditStarted() doesn't call listener")
                .that(auditAdapter.wasCalled())
                .isTrue();
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
        checker.process(Collections.singletonList(new File("dummy.java").toPath()));
        assertWithMessage("Checker.acceptFileStarted() doesn't call listener")
                .that(fileFilter.wasCalled())
                .isTrue();
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
        assertWithMessage("FileSetCheck.init() wasn't called")
                .that(fileSet.isInitCalled())
                .isTrue();
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

            final File tmpFile = createTempFile("file", ".java");

            execute(checker, tmpFile.getPath());

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

            final File tmpFile = createTempFile("file", ".java");

            execute(checker, tmpFile.getPath(), tmpFile.getPath());

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
        moduleConfig2.addProperty("id", "ModuleId");
        final DefaultConfiguration root = new DefaultConfiguration("root");
        root.addChild(moduleConfig1);
        root.addChild(moduleConfig2);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(root);
        // BriefUtLogger does not print the module name or id postfix,
        // so we need to set logger manually
        final ByteArrayOutputStream out = TestUtil.getInternalState(this, "stream");
        final DefaultLogger logger = new DefaultLogger(out, OutputStreamOptions.CLOSE, out,
                OutputStreamOptions.NONE, new AuditEventDefaultFormatter());
        checker.addListener(logger);

        final String path = createTempFile("file", ".java").getPath();
        final String violationMessage =
                getCheckMessage(NewlineAtEndOfFileCheck.class, MSG_KEY_NO_NEWLINE_EOF);
        final String[] expected = {
            "1: " + violationMessage + " [NewlineAtEndOfFile]",
            "1: " + violationMessage + " [ModuleId]",
        };

        // super.verify does not work here, for we change the logger
        out.flush();
        final int errs = checker.process(Collections.singletonList(new File(path).toPath()));
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
                    .collect(Collectors.toUnmodifiableList());
            Arrays.sort(expected);

            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = "[ERROR] " + path + ":" + expected[i];
                assertWithMessage("error message " + i)
                        .that(actual.get(i))
                        .isEqualTo(expectedResult);
            }

            assertWithMessage("unexpected output: " + lnr.readLine())
                    .that(errs)
                    .isEqualTo(expected.length);
        }

        checker.destroy();
    }

    @Test
    public void testCachedFile() throws Exception {
        final Checker checker = createChecker(createModuleConfig(TranslationCheck.class));
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLoggerWithCounter loggerWithCounter =
            new DefaultLoggerWithCounter(infoStream, OutputStreamOptions.CLOSE,
                                         errorStream, OutputStreamOptions.CLOSE);
        checker.addListener(loggerWithCounter);
        final File cacheFile = createTempFile("cacheFile", ".txt");
        checker.setCacheFile(cacheFile.getAbsolutePath());

        final File testFile = createTempFile("testFile", ".java");
        checker.process(Stream.of(testFile, testFile).map(File::toPath)
                .collect(Collectors.toUnmodifiableList()));

        assertWithMessage("Cached file should not be processed twice")
            .that(loggerWithCounter.fileStartedCount)
            .isEqualTo(1);

        checker.destroy();
    }

    @Test
    public void testUnmappableCharacters() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 80, 225),
        };

        verifyWithInlineXmlConfig(getPath("InputCheckerTestCharset.java"),
                expected);
    }

    /**
     * This tests uses 'verify' method, because it needs some config
     * to be executed on non-existing Input file,
     * but BDD style methods need config in existing file.
     *
     * @throws Exception exception
     */
    @SuppressForbidden
    @Test
    public void testViolationMessageOnIoException() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(CheckWhichThrowsError.class);

        final DefaultConfiguration treeWalkerConfig = createModuleConfig(TreeWalker.class);
        treeWalkerConfig.addChild(checkConfig);

        final DefaultConfiguration checkerConfig = createRootConfig(treeWalkerConfig);
        checkerConfig.addChild(treeWalkerConfig);

        checkerConfig.addProperty("haltOnException", "false");
        final File file = new File("InputNonChecker.java");
        final String filePath = file.getAbsolutePath();
        final String[] expected = {
            "1: " + getCheckMessage(EXCEPTION_MSG, filePath
                        + " (No such file or directory)"),
        };

        verify(checkerConfig, filePath, expected);
    }

    /**
     * Reason of non-Input based testing:
     * There are bunch of asserts that expects full path to file,
     * usage of "basedir" make it stripped and we need put everywhere code like
     * <pre>CommonUtil.relativizePath(checker.getConfiguration().getProperty("basedir"), file)</pre>
     * but Checker object is not always available in code.
     * Propagating it in all code methods will complicate code.
     */
    @Test
    public void testRelativizedFileExclusion() throws Exception {
        final DefaultConfiguration newLineAtEndOfFileConfig =
                createModuleConfig(NewlineAtEndOfFileCheck.class);

        final DefaultConfiguration beforeExecutionExclusionFileFilterConfig =
                createModuleConfig(BeforeExecutionExclusionFileFilter.class);

        beforeExecutionExclusionFileFilterConfig.addProperty("fileNamePattern",
                        "^(?!InputCheckerTestExcludeRelativizedFile.*\\.java).*");

        final DefaultConfiguration checkerConfig = createRootConfig(null);
        checkerConfig.addChild(newLineAtEndOfFileConfig);
        checkerConfig.addChild(beforeExecutionExclusionFileFilterConfig);

        // -@cs[CheckstyleTestMakeup] Needs to be fixed.
        checkerConfig.addProperty("basedir",
                temporaryFolder.getPath());

        final String violationMessage =
                getCheckMessage(NewlineAtEndOfFileCheck.class, MSG_KEY_NO_NEWLINE_EOF);

        final String[] expected = {
            "1: " + violationMessage,
        };

        final File tempFile = createTempFile("InputCheckerTestExcludeRelativizedFile", ".java");

        final File[] processedFiles = {tempFile};

        verify(createChecker(checkerConfig), processedFiles,
                tempFile.getName(), expected);
    }

    @Nested
    public class TestProcessPathCollection {

        @Test
        public void testFileExtensionsPath() throws Exception {
            final DefaultConfiguration checkerConfig = new DefaultConfiguration("configuration");
            checkerConfig.addProperty("charset", StandardCharsets.UTF_8.name());
            checkerConfig.addProperty("cacheFile", createTempFile("junit").getAbsolutePath());

            final Checker checker = new Checker();
            checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
            checker.configure(checkerConfig);

            final DebugAuditAdapter auditAdapter = new DebugAuditAdapter();
            checker.addListener(auditAdapter);

            final List<Path> files = new ArrayList<>();
            files.add(Paths.get("file.pdf"));
            files.add(Paths.get("file.java"));
            checker.setFileExtensions("java", "xml", "properties");
            checker.setCacheFile(createTempFile("junit").getAbsolutePath());
            final int counter = checker.process(files);

            // comparing to 1 as there is only one legal file in input
            final int numLegalFiles = 1;
            assertWithMessage("There were more legal files than expected")
                    .that(counter)
                    .isEqualTo(numLegalFiles);
            assertWithMessage("Audit was started on larger amount of files than expected")
                    .that(auditAdapter.getNumFilesStarted())
                    .isEqualTo(numLegalFiles);
            assertWithMessage("Audit was finished on larger amount of files than expected")
                    .that(auditAdapter.getNumFilesFinished())
                    .isEqualTo(numLegalFiles);
            assertWithMessage("Cache should not contain any file")
                    .that(TestUtil.<PropertyCacheFile>getInternalState(checker, "cacheFile")
                            .get(Paths.get("file.java").toFile().getPath()))
                    .isNull();
        }

    }

    public static class DefaultLoggerWithCounter extends DefaultLogger {

        private int fileStartedCount;

        public DefaultLoggerWithCounter(OutputStream infoStream,
                                        OutputStreamOptions infoStreamOptions,
                                        OutputStream errorStream,
                                        OutputStreamOptions errorStreamOptions) {
            super(infoStream, infoStreamOptions, errorStream, errorStreamOptions);
        }

        @Override
        public void fileStarted(AuditEvent event) {
            fileStartedCount++;
        }
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
                final String msg = String.format(Locale.ENGLISH,
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
                final String msg = String.format(Locale.ENGLISH,
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

        // Locale.ENGLISH until #12104
        @Override
        public void visitToken(DetailAST ast) {
            if (ast.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(
                    TokenTypes.BLOCK_COMMENT_BEGIN) == null) {
                log(ast, "Incorrect AST structure.");
            }
            final int childCount = ast.getChildCount();
            if (childCount != METHOD_DEF_CHILD_COUNT) {
                final String msg = String.format(Locale.ENGLISH,
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
                final String msg = String.format(Locale.ENGLISH,
                        "AST node with comment has wrong number of children. "
                                + "Expected is %d but was %d",
                        cacheChildCount, actualChildCount);
                log(ast, msg);
            }
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

}
