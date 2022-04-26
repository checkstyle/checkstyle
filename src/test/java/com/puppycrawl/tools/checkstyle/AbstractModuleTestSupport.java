////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
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

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.TestInputViolation;
import com.puppycrawl.tools.checkstyle.internal.utils.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

public abstract class AbstractModuleTestSupport extends AbstractPathTestSupport {

    /**
     * Enum to specify options for checker creation.
     */
    public enum ModuleCreationOption {

        /**
         * Points that the module configurations
         * has to be added under {@link TreeWalker}.
         */
        IN_TREEWALKER,
        /**
         * Points that checker will be created as
         * a root of default configuration.
         */
        IN_CHECKER,

    }

    private static final String ROOT_MODULE_NAME = "root";

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * Returns log stream.
     *
     * @return stream with log
     */
    public ByteArrayOutputStream getStream() {
        return stream;
    }

    /**
     * Returns test logger.
     *
     * @return logger for tests
     */
    public final BriefUtLogger getBriefUtLogger() {
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
    public final Checker createChecker(Configuration moduleConfig)
            throws Exception {
        ModuleCreationOption moduleCreationOption = ModuleCreationOption.IN_CHECKER;

        final String moduleName = moduleConfig.getName();
        if (!ROOT_MODULE_NAME.equals(moduleName)) {
            try {
                final Class<?> moduleClass = Class.forName(moduleName);
                if (ModuleReflectionUtil.isCheckstyleTreeWalkerCheck(moduleClass)
                        || ModuleReflectionUtil.isTreeWalkerFilterModule(moduleClass)) {
                    moduleCreationOption = ModuleCreationOption.IN_TREEWALKER;
                }
            }
            catch (ClassNotFoundException ignore) {
                // ignore exception, assume it is not part of TreeWalker
            }
        }

        return createChecker(moduleConfig, moduleCreationOption);
    }

    /**
     * Creates {@link Checker} instance based on the given {@link Configuration} instance.
     *
     * @param moduleConfig {@link Configuration} instance.
     * @param moduleCreationOption {@code IN_TREEWALKER} if the {@code moduleConfig} should be added
*                                              under {@link TreeWalker}.
     * @return {@link Checker} instance based on the given {@link Configuration} instance.
     * @throws Exception if an exception occurs during checker configuration.
     */
    public final Checker createChecker(Configuration moduleConfig,
                                 ModuleCreationOption moduleCreationOption)
            throws Exception {
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        if (moduleCreationOption == ModuleCreationOption.IN_TREEWALKER) {
            final Configuration dc = createTreeWalkerConfig(moduleConfig);
            checker.configure(dc);
        }
        else if (ROOT_MODULE_NAME.equals(moduleConfig.getName())) {
            checker.configure(moduleConfig);
        }
        else {
            final Configuration dc = createRootConfig(moduleConfig);
            checker.configure(dc);
        }
        checker.addListener(new BriefUtLogger(stream));
        return checker;
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
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createModuleConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addProperty("charset", StandardCharsets.UTF_8.name());
        dc.addChild(twConf);
        twConf.addChild(config);
        return dc;
    }

    /**
     * Creates {@link DefaultConfiguration} for the given {@link Configuration} instance.
     *
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the given {@link Configuration} instance.
     */
    protected static DefaultConfiguration createRootConfig(Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration(ROOT_MODULE_NAME);
        if (config != null) {
            dc.addChild(config);
        }
        return dc;
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/'
     * as a non-compilable resource location.
     *
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    /**
     * Returns URI-representation of the path for the given file name.
     * The path is formed base on the root location.
     * This implementation uses 'src/test/resources/com/puppycrawl/tools/checkstyle/'
     * as a root location.
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
        verifyViolations(parsedConfig, filePath, testInputConfiguration.getViolations());
        verify(parsedConfig, filePath, expected);
    }

    /**
     * Performs verification of the file with the given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link AbstractModuleTestSupport#verify(Checker, File[], String, String...)} method inside.
     *
     * @param aConfig configuration.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Configuration aConfig, String fileName, String... expected)
            throws Exception {
        verify(createChecker(aConfig), fileName, fileName, expected);
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
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
        final List<Integer> expectedViolationLines = testInputViolations.stream()
                .map(TestInputViolation::getLineNo)
                .collect(Collectors.toList());
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
                Locale.getDefault(),
                Thread.currentThread().getContextClassLoader(),
                new Violation.Utf8Control());
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
        if (endIndex < 0) {
            messageBundle = messages;
        }
        else {
            final String packageName = className.substring(0, endIndex);
            messageBundle = packageName + "." + messages;
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
            Arrays.stream(actualViolations).collect(Collectors.toList());
        actualViolationsList.removeAll(Arrays.asList(suppressedViolations));
        return actualViolationsList.toArray(CommonUtil.EMPTY_STRING_ARRAY);
    }

}
