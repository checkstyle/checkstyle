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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.internal.utils.BriefUtLogger;
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
     * @return stream with log
     */
    public ByteArrayOutputStream getStream() {
        return stream;
    }

    /**
     * Returns test logger.
     * @return logger for tests
     */
    public final BriefUtLogger getBriefUtLogger() {
        return new BriefUtLogger(stream);
    }

    protected static DefaultConfiguration createModuleConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    /**
     * Creates {@link Checker} instance based on the given {@link Configuration} instance.
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
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the {@link TreeWalker}
     *     based on the given {@link Configuration} instance.
     */
    protected static DefaultConfiguration createTreeWalkerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createModuleConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", StandardCharsets.UTF_8.name());
        dc.addChild(twConf);
        twConf.addChild(config);
        return dc;
    }

    /**
     * Creates {@link DefaultConfiguration} for the given {@link Configuration} instance.
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
     * @param filename file name.
     * @return URI-representation of the path for the file with the given file name.
     */
    protected final String getUriString(String filename) {
        return new File("src/test/resources/" + getPackageLocation() + "/" + filename).toURI()
                .toString();
    }

    /**
     * Performs verification of the file with the given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link AbstractModuleTestSupport#verify(Checker, File[], String, String...)} method inside.
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
     *  We keep two verify methods with separate logic only for convenience of debugging.
     *  We have minimum amount of multi-file test cases.
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
        stream.flush();
        stream.reset();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final int errs = checker.process(theFiles);

        // process each of the lines
        try (ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
            LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            final List<String> actuals = lnr.lines().limit(expected.length)
                    .sorted().collect(Collectors.toList());
            Arrays.sort(expected);

            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = messageFileName + ":" + expected[i];
                assertEquals("error message " + i, expectedResult, actuals.get(i));
            }

            assertEquals("unexpected output: " + lnr.readLine(),
                    expected.length, errs);
        }

        checker.destroy();
    }

    /**
     * Performs verification of the given files.
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
        final MapDifference<String, List<String>> violationDifferences =
                Maps.difference(realExpectedViolations, actualViolations);

        final Map<String, List<String>> missingViolations =
                violationDifferences.entriesOnlyOnLeft();
        final Map<String, List<String>> unexpectedViolations =
                violationDifferences.entriesOnlyOnRight();
        final Map<String, MapDifference.ValueDifference<List<String>>> differingViolations =
                violationDifferences.entriesDiffering();

        final StringBuilder message = new StringBuilder(256);
        if (!missingViolations.isEmpty()) {
            message.append("missing violations: ").append(missingViolations);
        }
        if (!unexpectedViolations.isEmpty()) {
            if (message.length() > 0) {
                message.append('\n');
            }
            message.append("unexpected violations: ").append(unexpectedViolations);
        }
        if (!differingViolations.isEmpty()) {
            if (message.length() > 0) {
                message.append('\n');
            }
            message.append("differing violations: ").append(differingViolations);
        }

        assertTrue(message.toString(),
                missingViolations.isEmpty()
                        && unexpectedViolations.isEmpty()
                        && differingViolations.isEmpty());

        checker.destroy();
    }

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
                // to not split after the drive letter on windows
                final String[] actualViolation = line.split("(?<=.{2}):", 2);
                final String actualViolationFileName = actualViolation[0];
                final String actualViolationMessage = actualViolation[1];

                List<String> actualViolationsPerFile =
                        actualViolations.get(actualViolationFileName);
                if (actualViolationsPerFile == null) {
                    actualViolationsPerFile = new ArrayList<>();
                    actualViolations.put(actualViolationFileName, actualViolationsPerFile);
                }
                actualViolationsPerFile.add(actualViolationMessage);
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
                new LocalizedMessage.Utf8Control());
        final String pattern = resourceBundle.getString(messageKey);
        final MessageFormat formatter = new MessageFormat(pattern, Locale.ROOT);
        return formatter.format(arguments);
    }

    private String getMessageBundle() {
        final String className = getClass().getName();
        return getMessageBundle(className);
    }

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

}
