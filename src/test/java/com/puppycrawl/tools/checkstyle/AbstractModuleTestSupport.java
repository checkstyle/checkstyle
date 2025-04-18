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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.LocalizedMessage.Utf8Control;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.TestInputViolation;
import com.puppycrawl.tools.checkstyle.internal.utils.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractModuleTestSupport extends AbstractPathTestSupport {

    protected static final String ROOT_MODULE_NAME = Checker.class.getSimpleName();

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * Returns log stream.
     *
     * @return stream with log
     */
    protected final ByteArrayOutputStream getStream() {
        return stream;
    }

    /**
     * Returns test logger.
     *
     * @return logger for tests
     */
    protected final BriefUtLogger getBriefUtLogger() {
        return new BriefUtLogger(stream);
    }

    /**
     * Creates a default module configuration {@link DefaultConfiguration} for a given object
     * of type {@link Class}.
     *
     * @param clazz a {@link Class} type object.
     * @return default module configuration for the given {@link Class} instance.
     */
    protected static DefaultConfiguration createModuleConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    /**
     * Creates {@link Checker} instance based on the given {@link Configuration} instance.
     *
     * @param moduleConfig {@link Configuration} instance.
     * @return {@link Checker} instance based on the given {@link Configuration} instance.
     * @throws Exception if an exception occurs during checker configuration.
     */
    protected final Checker createChecker(Configuration moduleConfig)
            throws Exception {
        final String moduleName = moduleConfig.getName();
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        if (ROOT_MODULE_NAME.equals(moduleName)) {
            checker.configure(moduleConfig);
        }
        else {
            configureChecker(checker, moduleConfig);
        }

        checker.addListener(getBriefUtLogger());
        return checker;
    }

    /**
     * Configures the {@code checker} instance with {@code moduleConfig}.
     *
     * @param checker {@link Checker} instance.
     * @param moduleConfig {@link Configuration} instance.
     * @throws Exception if an exception occurs during configuration.
     */
    protected void configureChecker(Checker checker, Configuration moduleConfig) throws Exception {
        final Class<?> moduleClass = Class.forName(moduleConfig.getName());

        if (ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(moduleClass)
                || ModuleReflectionUtil.isTreeWalkerFilterModule(moduleClass)) {
            final Configuration config = createTreeWalkerConfig(moduleConfig);
            checker.configure(config);
        }
        else {
            final Configuration config = createRootConfig(moduleConfig);
            checker.configure(config);
        }
    }

    /**
     * Creates {@link DefaultConfiguration} for the {@link TreeWalker}
     * based on the given {@link Configuration} instance.
     *
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the {@link TreeWalker}
     *     based on the given {@link Configuration} instance.
     */
    protected static DefaultConfiguration createTreeWalkerConfig(Configuration config) {
        final DefaultConfiguration rootConfig =
                new DefaultConfiguration(ROOT_MODULE_NAME);
        final DefaultConfiguration twConf = createModuleConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        rootConfig.addProperty("charset", StandardCharsets.UTF_8.name());
        rootConfig.addChild(twConf);
        twConf.addChild(config);
        return rootConfig;
    }

    /**
     * Creates {@link DefaultConfiguration} for the given {@link Configuration} instance.
     *
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the given {@link Configuration} instance.
     */
    protected static DefaultConfiguration createRootConfig(Configuration config) {
        final DefaultConfiguration rootConfig = new DefaultConfiguration(ROOT_MODULE_NAME);
        if (config != null) {
            rootConfig.addChild(config);
        }
        return rootConfig;
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     *
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/" + getResourceLocation()
                + "/resources-noncompilable/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    /**
     * Returns URI-representation of the path for the given file name.
     * The path is formed base on the root location.
     *
     * @param filename file name.
     * @return URI-representation of the path for the file with the given file name.
     */
    protected final String getUriString(String filename) {
        return new File("src/test/resources/" + getPackageLocation() + "/" + filename).toURI()
                .toString();
    }

    /**
     * Performs verification of the file with the given file path using specified configuration
     * and the array of expected messages. Also performs verification of the config with filters
     * specified in the input file.
     *
     * @param filePath file path to verify.
     * @param expectedUnfiltered an array of expected unfiltered config.
     * @param expectedFiltered an array of expected config with filters.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verifyFilterWithInlineConfigParser(String filePath,
                                                            String[] expectedUnfiltered,
                                                            String... expectedFiltered)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parseWithFilteredViolations(filePath);
        final DefaultConfiguration configWithoutFilters =
                testInputConfiguration.createConfigurationWithoutFilters();
        final List<TestInputViolation> violationsWithoutFilters =
                new ArrayList<>(testInputConfiguration.getViolations());
        violationsWithoutFilters.addAll(testInputConfiguration.getFilteredViolations());
        Collections.sort(violationsWithoutFilters);
        verifyViolations(configWithoutFilters, filePath, violationsWithoutFilters);
        verify(configWithoutFilters, filePath, expectedUnfiltered);
        final DefaultConfiguration configWithFilters =
                testInputConfiguration.createConfiguration();
        verifyViolations(configWithFilters, filePath, testInputConfiguration.getViolations());
        verify(configWithFilters, filePath, expectedFiltered);
    }

    /**
     * Performs verification of the file with given file path using configurations parsed from
     * xml header of the file and the array expected messages. Also performs verification of
     * the config specified in input file.
     *
     * @param filePath file path to verify
     * @param expected an array of expected messages
     * @throws Exception if exception occurs
     */
    protected final void verifyWithInlineXmlConfig(String filePath, String... expected)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parseWithXmlHeader(filePath);
        final Configuration xmlConfig =
                testInputConfiguration.getXmlConfiguration();
        verifyViolations(xmlConfig, filePath, testInputConfiguration.getViolations());
        verify(xmlConfig, filePath, expected);
    }

    /**
     * Verifies the specified file using the provided configuration and expected messages.
     * Additionally, it validates the configuration specified within the input file.
     * This method is an overload of {@link #verifyWithInlineConfigParser(String, String...)}
     * that supports {@code List<String>} instead of varargs to avoid PMD's
     * UnnecessaryVarargsArrayCreation warning.
     *
     * @param filePath the path of the file to verify.
     * @param expected a list of expected messages.
     * @throws Exception if an error occurs during the verification process.
     */
    protected final void verifyWithInlineConfigParser(String filePath, List<String> expected)
            throws Exception {
        verifyWithInlineConfigParser(filePath, expected.toArray(new String[0]));
    }

    /**
     * Performs verification of the file with the given file path using specified configuration
     * and the array expected messages. Also performs verification of the config specified in
     * input file.
     *
     * @param filePath file path to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verifyWithInlineConfigParser(String filePath, String... expected)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(filePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        final List<String> actualViolations = getActualViolationsForFile(parsedConfig, filePath);
        verifyViolations(filePath, testInputConfiguration.getViolations(), actualViolations);
        assertWithMessage("Violations for %s differ.", filePath)
            .that(actualViolations)
            .containsExactlyElementsIn(expected);
    }

    /**
     * Performs verification of two files with their given file paths using specified
     * configuration of one file only. Also performs verification of the config specified
     * in the input file. This method needs to be implemented when two given files need to be
     * checked through a single check only.
     *
     * @param filePath1 file path of first file to verify
     * @param filePath2 file path of second file to verify
     * @param expected an array of expected messages
     * @throws Exception if exception occurs during verification process
     */
    protected final void verifyWithInlineConfigParser(String filePath1,
                                                      String filePath2,
                                                      String... expected)
            throws Exception {
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(filePath1);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();
        final TestInputConfiguration testInputConfiguration2 =
                InlineConfigParser.parse(filePath2);
        verifyViolations(parsedConfig, filePath1, testInputConfiguration1.getViolations());
        verifyViolations(parsedConfig, filePath2, testInputConfiguration2.getViolations());
        verify(createChecker(parsedConfig),
                new File[] {new File(filePath1), new File(filePath2)},
                filePath1,
                expected);
    }

    /**
     * Performs verification of two files with their given file paths.
     * using specified configuration of one file only. Also performs
     * verification of the config specified in the input file. This method
     * needs to be implemented when two given files need to be
     * checked through a single check only.
     *
     * @param filePath1 file path of first file to verify
     * @param filePath2 file path of first file to verify
     * @param expectedFromFile1 list of expected message
     * @param expectedFromFile2 list of expected message
     * @throws Exception if exception occurs during verification process
     */
    protected final void verifyWithInlineConfigParser(String filePath1,
                                                      String filePath2,
                                                      List<String> expectedFromFile1,
                                                      List<String> expectedFromFile2)
            throws Exception {
        final TestInputConfiguration testInputConfiguration = InlineConfigParser.parse(filePath1);
        final DefaultConfiguration parsedConfig = testInputConfiguration.createConfiguration();
        final TestInputConfiguration testInputConfiguration2 = InlineConfigParser.parse(filePath2);
        final DefaultConfiguration parsedConfig2 = testInputConfiguration.createConfiguration();
        final File[] inputs = {new File(filePath1), new File(filePath2)};
        verifyViolations(parsedConfig, filePath1, testInputConfiguration.getViolations());
        verifyViolations(parsedConfig2, filePath2, testInputConfiguration2.getViolations());
        verify(createChecker(parsedConfig), inputs, ImmutableMap.of(
            filePath1, expectedFromFile1,
            filePath2, expectedFromFile2));
    }

    /**
     * Verifies the target file against the configuration specified in a separate configuration
     * file.
     * This method is intended for use cases when the configuration is stored in one file and the
     * content to verify is stored in another file.
     *
     * @param fileWithConfig file path of the configuration file
     * @param targetFile file path of the target file to be verified
     * @param expected an array of expected messages
     * @throws Exception if an exception occurs during verification process
     */
    protected final void verifyWithInlineConfigParserSeparateConfigAndTarget(String fileWithConfig,
                                                                             String targetFile,
                                                                             String... expected)
            throws Exception {
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();
        final List<TestInputViolation> inputViolations =
                InlineConfigParser.getViolationsFromInputFile(targetFile);
        final List<String> actualViolations = getActualViolationsForFile(parsedConfig, targetFile);
        verifyViolations(targetFile, inputViolations, actualViolations);
        assertWithMessage("Violations for %s differ.", targetFile)
                .that(actualViolations)
                .containsExactlyElementsIn(expected);
    }

    /**
     * Performs verification of the file with the given file path using specified configuration
     * and the array expected messages. Also performs verification of the config specified in
     * input file
     *
     * @param filePath file path to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verifyWithInlineConfigParserTwice(String filePath, String... expected)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(filePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        verifyViolations(parsedConfig, filePath, testInputConfiguration.getViolations());
        verify(parsedConfig, filePath, expected);
    }

    /**
     * Verifies logger output using the inline configuration parser.
     * Expects an input file with configuration and violations, and a report file with expected
     * output.
     *
     * @param inputFile path to the file with configuration and violations
     * @param expectedReportFile path to the expected logger report file
     * @param logger logger to test
     * @param outputStream output stream where the logger writes its actual output
     * @throws Exception if an exception occurs during verification
     */
    protected void verifyWithInlineConfigParserAndLogger(String inputFile,
                                                         String expectedReportFile,
                                                         AuditListener logger,
                                                         ByteArrayOutputStream outputStream)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(inputFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        final List<File> filesToCheck = Collections.singletonList(new File(inputFile));
        final String basePath = Path.of("").toAbsolutePath().toString();

        final Checker checker = createChecker(parsedConfig);
        checker.setBasedir(basePath);
        checker.addListener(logger);
        checker.process(filesToCheck);

        verifyContent(expectedReportFile, outputStream);
    }

    /**
     * Performs verification of the file with the given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link AbstractModuleTestSupport#verify(Checker, File[], String, String...)} method inside.
     *
     * @param config configuration.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Configuration config, String fileName, String... expected)
            throws Exception {
        verify(createChecker(config), fileName, fileName, expected);
    }

    /**
     * Performs verification of the file with the given file name.
     * Uses provided {@link Checker} instance.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link AbstractModuleTestSupport#verify(Checker, String, String, String...)} method inside.
     *
     * @param checker {@link Checker} instance.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verify(Checker checker, String fileName, String... expected)
            throws Exception {
        verify(checker, fileName, fileName, expected);
    }

    /**
     * Performs verification of the file with the given file name.
     * Uses provided {@link Checker} instance.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link AbstractModuleTestSupport#verify(Checker, File[], String, String...)} method inside.
     *
     * @param checker {@link Checker} instance.
     * @param processedFilename file name to verify.
     * @param messageFileName message file name.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Checker checker,
                          String processedFilename,
                          String messageFileName,
                          String... expected)
            throws Exception {
        verify(checker,
                new File[] {new File(processedFilename)},
                messageFileName, expected);
    }

    /**
     *  Performs verification of the given files against the array of
     *  expected messages using the provided {@link Checker} instance.
     *
     *  @param checker {@link Checker} instance.
     *  @param processedFiles list of files to verify.
     *  @param messageFileName message file name.
     *  @param expected an array of expected messages.
     *  @throws Exception if exception occurs during verification process.
     */
    protected void verify(Checker checker,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        final Map<String, List<String>> expectedViolations = new HashMap<>();
        expectedViolations.put(messageFileName, Arrays.asList(expected));
        verify(checker, processedFiles, expectedViolations);
    }

    /**
     * Performs verification of the given files.
     *
     * @param checker {@link Checker} instance
     * @param processedFiles files to process.
     * @param expectedViolations a map of expected violations per files.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Checker checker,
                          File[] processedFiles,
                          Map<String, List<String>> expectedViolations)
            throws Exception {
        stream.flush();
        stream.reset();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final int errs = checker.process(theFiles);

        // process each of the lines
        final Map<String, List<String>> actualViolations = getActualViolations(errs);
        final Map<String, List<String>> realExpectedViolations =
                Maps.filterValues(expectedViolations, input -> !input.isEmpty());

        assertWithMessage("Files with expected violations and actual violations differ.")
            .that(actualViolations.keySet())
            .isEqualTo(realExpectedViolations.keySet());

        realExpectedViolations.forEach((fileName, violationList) -> {
            assertWithMessage("Violations for %s differ.", fileName)
                .that(actualViolations.get(fileName))
                .containsExactlyElementsIn(violationList);
        });

        checker.destroy();
    }

    /**
     * Runs 'verifyWithInlineConfigParser' with limited stack size and time duration.
     *
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verifyWithLimitedResources(String fileName, String... expected)
            throws Exception {
        // We return null here, which gives us a result to make an assertion about
        final Void result = TestUtil.getResultWithLimitedResources(() -> {
            verifyWithInlineConfigParser(fileName, expected);
            return null;
        });
        assertWithMessage("Verify should complete successfully.")
                .that(result)
                .isNull();
    }

    /**
     * Executes given config on a list of files only. Does not verify violations.
     *
     * @param config check configuration
     * @param filenames names of files to process
     * @throws Exception if there is a problem during checker configuration
     */
    protected final void execute(Configuration config, String... filenames) throws Exception {
        final Checker checker = createChecker(config);
        final List<File> files = Arrays.stream(filenames)
                .map(File::new)
                .collect(Collectors.toUnmodifiableList());
        checker.process(files);
        checker.destroy();
    }

    /**
     * Executes given config on a list of files only. Does not verify violations.
     *
     * @param checker check configuration
     * @param filenames names of files to process
     * @throws Exception if there is a problem during checker configuration
     */
    protected static void execute(Checker checker, String... filenames) throws Exception {
        final List<File> files = Arrays.stream(filenames)
                .map(File::new)
                .collect(Collectors.toUnmodifiableList());
        checker.process(files);
        checker.destroy();
    }

    /**
     * Performs verification of violation lines.
     *
     * @param config parsed config.
     * @param file file path.
     * @param testInputViolations List of TestInputViolation objects.
     * @throws Exception if exception occurs during verification process.
     */
    private void verifyViolations(Configuration config,
                                  String file,
                                  List<TestInputViolation> testInputViolations)
            throws Exception {
        final List<String> actualViolations = getActualViolationsForFile(config, file);
        final List<Integer> actualViolationLines = actualViolations.stream()
                .map(violation -> violation.substring(0, violation.indexOf(':')))
                .map(Integer::valueOf)
                .collect(Collectors.toUnmodifiableList());
        final List<Integer> expectedViolationLines = testInputViolations.stream()
                .map(TestInputViolation::getLineNo)
                .collect(Collectors.toUnmodifiableList());
        assertWithMessage("Violation lines for %s differ.", file)
                .that(actualViolationLines)
                .isEqualTo(expectedViolationLines);
        for (int index = 0; index < actualViolations.size(); index++) {
            assertWithMessage("Actual and expected violations differ.")
                    .that(actualViolations.get(index))
                    .matches(testInputViolations.get(index).toRegex());
        }
    }

    /**
     * Performs verification of violation lines.
     *
     * @param file file path.
     * @param testInputViolations List of TestInputViolation objects.
     * @param actualViolations for a file
     */
    private static void verifyViolations(String file,
                                  List<TestInputViolation> testInputViolations,
                                  List<String> actualViolations) {
        final List<Integer> actualViolationLines = actualViolations.stream()
                .map(violation -> violation.substring(0, violation.indexOf(':')))
                .map(Integer::valueOf)
                .collect(Collectors.toUnmodifiableList());
        final List<Integer> expectedViolationLines = testInputViolations.stream()
                .map(TestInputViolation::getLineNo)
                .collect(Collectors.toUnmodifiableList());
        assertWithMessage("Violation lines for %s differ.", file)
                .that(actualViolationLines)
                .isEqualTo(expectedViolationLines);
        for (int index = 0; index < actualViolations.size(); index++) {
            assertWithMessage("Actual and expected violations differ.")
                    .that(actualViolations.get(index))
                    .matches(testInputViolations.get(index).toRegex());
        }
    }

    /**
     * Verifies that the logger's actual output matches the expected report file.
     *
     * @param expectedOutputFile path to the expected logger report file
     * @param outputStream output stream containing the actual logger output
     * @throws IOException if an exception occurs while reading the file
     */
    private static void verifyContent(
            String expectedOutputFile,
            ByteArrayOutputStream outputStream) throws IOException {
        final String expectedContent = readFile(expectedOutputFile);
        final String actualContent =
                toLfLineEnding(outputStream.toString(StandardCharsets.UTF_8));
        assertWithMessage("Content should match")
                .that(actualContent)
                .isEqualTo(expectedContent);
    }

    /**
     * Tests the file with the check config.
     *
     * @param config check configuration.
     * @param file input file path.
     * @return list of actual violations.
     * @throws Exception if exception occurs during verification process.
     */
    private List<String> getActualViolationsForFile(Configuration config,
                                                    String file) throws Exception {
        stream.flush();
        stream.reset();
        final List<File> files = Collections.singletonList(new File(file));
        final Checker checker = createChecker(config);
        final Map<String, List<String>> actualViolations =
                getActualViolations(checker.process(files));
        checker.destroy();
        return actualViolations.getOrDefault(file, new ArrayList<>());
    }

    /**
     * Returns the actual violations for each file that has been checked against {@link Checker}.
     * Each file is mapped to their corresponding violation messages. Reads input stream for these
     * messages using instance of {@link InputStreamReader}.
     *
     * @param errorCount count of errors after checking set of files against {@link Checker}.
     * @return a {@link Map} object containing file names and the corresponding violation messages.
     * @throws IOException exception can occur when reading input stream.
     */
    private Map<String, List<String>> getActualViolations(int errorCount) throws IOException {
        // process each of the lines
        try (ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
            LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            final Map<String, List<String>> actualViolations = new HashMap<>();
            for (String line = lnr.readLine(); line != null && lnr.getLineNumber() <= errorCount;
                 line = lnr.readLine()) {
                // have at least 2 characters before the splitting colon,
                // to not split after the drive letter on Windows
                final String[] actualViolation = line.split("(?<=.{2}):", 2);
                final String actualViolationFileName = actualViolation[0];
                final String actualViolationMessage = actualViolation[1];

                actualViolations
                        .computeIfAbsent(actualViolationFileName, key -> new ArrayList<>())
                        .add(actualViolationMessage);
            }

            return actualViolations;
        }
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments  the arguments of message in 'messages.properties' file.
     * @return The message of the check with the arguments applied.
     */
    protected final String getCheckMessage(String messageKey, Object... arguments) {
        return internalGetCheckMessage(getMessageBundle(), messageKey, arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param clazz the related check class.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     * @return The message of the check with the arguments applied.
     */
    protected static String getCheckMessage(
            Class<?> clazz, String messageKey, Object... arguments) {
        return internalGetCheckMessage(getMessageBundle(clazz.getName()), messageKey, arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageBundle the bundle name.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     * @return The message of the check with the arguments applied.
     */
    private static String internalGetCheckMessage(
            String messageBundle, String messageKey, Object... arguments) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(
                messageBundle,
                Locale.ROOT,
                Thread.currentThread().getContextClassLoader(),
                new Utf8Control());
        final String pattern = resourceBundle.getString(messageKey);
        final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
        return formatter.format(arguments);
    }

    /**
     * Returns message bundle for a class specified by its class name.
     *
     * @return a string of message bundles for the class using class name.
     */
    private String getMessageBundle() {
        final String className = getClass().getName();
        return getMessageBundle(className);
    }

    /**
     * Returns message bundles for a class by providing class name.
     *
     * @param className name of the class.
     * @return message bundles containing package name.
     */
    private static String getMessageBundle(String className) {
        final String messageBundle;
        final String messages = "messages";
        final int endIndex = className.lastIndexOf('.');
        final Map<String, String> messageBundleMappings = new HashMap<>();
        messageBundleMappings.put("SeverityMatchFilterExamplesTest",
                "com.puppycrawl.tools.checkstyle.checks.naming.messages");

        if (endIndex < 0) {
            messageBundle = messages;
        }
        else {
            final String packageName = className.substring(0, endIndex);
            if ("com.puppycrawl.tools.checkstyle.filters".equals(packageName)) {
                messageBundle = messageBundleMappings.get(className.substring(endIndex + 1));
            }
            else {
                messageBundle = packageName + "." + messages;
            }
        }
        return messageBundle;
    }

    /**
     * Remove suppressed violation messages from actual violation messages.
     *
     * @param actualViolations actual violation messages
     * @param suppressedViolations suppressed violation messages
     * @return an array of actual violation messages minus suppressed violation messages
     */
    protected static String[] removeSuppressed(String[] actualViolations,
                                               String... suppressedViolations) {
        final List<String> actualViolationsList =
            Arrays.stream(actualViolations).collect(Collectors.toCollection(ArrayList::new));
        actualViolationsList.removeAll(Arrays.asList(suppressedViolations));
        return actualViolationsList.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

}
