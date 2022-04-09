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

package org.checkstyle.base;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.internal.utils.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public abstract class AbstractItModuleTestSupport extends AbstractPathTestSupport {

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

    protected static final String ROOT_MODULE_NAME = "root";

    private static final Pattern WARN_PATTERN = CommonUtil
            .createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]\\*?\\s?warn\\s?[*]/");

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * Find the module creation option to use for the module name.
     *
     * @param moduleName the module name.
     * @return the module creation option.
     */
    protected abstract ModuleCreationOption findModuleCreationOption(String moduleName);

    /**
     * Creates {@link DefaultConfiguration} instance for the given module class.
     *
     * @param clazz module class.
     * @return {@link DefaultConfiguration} instance.
     */
    protected abstract DefaultConfiguration createModuleConfig(Class<?> clazz);

    /**
     * Returns test logger.
     *
     * @return logger test logger
     */
    protected final BriefUtLogger getBriefUtLogger() {
        return new BriefUtLogger(stream);
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/'
     * as a non-compilable resource location.
     *
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/" + getResourceLocation() + "/resources-noncompilable/"
                + getPackageLocation() + "/" + filename).getCanonicalPath();
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
        final String name = moduleConfig.getName();

        return createChecker(moduleConfig, findModuleCreationOption(name));
    }

    /**
     * Creates {@link Checker} instance based on specified {@link Configuration}.
     *
     * @param moduleConfig {@link Configuration} instance.
     * @param moduleCreationOption {@code IN_TREEWALKER} if the {@code moduleConfig} should be added
     *                                                  under {@link TreeWalker}.
     * @return {@link Checker} instance.
     * @throws Exception if an exception occurs during checker configuration.
     */
    protected final Checker createChecker(Configuration moduleConfig,
                                    ModuleCreationOption moduleCreationOption)
            throws Exception {
        final Configuration dc;

        if (moduleCreationOption == ModuleCreationOption.IN_TREEWALKER) {
            dc = createTreeWalkerConfig(moduleConfig);
        }
        else if (ROOT_MODULE_NAME.equals(moduleConfig.getName())) {
            dc = moduleConfig;
        }
        else {
            dc = createRootConfig(moduleConfig);
        }

        final Checker checker = new Checker();
        // make sure the tests always run with English error messages
        // so the tests don't fail in supported locales like German
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(getBriefUtLogger());
        return checker;
    }

    /**
     * Creates {@link DefaultConfiguration} or the {@link Checker}.
     * based on the given {@link Configuration}.
     *
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the {@link Checker}.
     */
    protected final DefaultConfiguration createTreeWalkerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createModuleConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addProperty("charset", "iso-8859-1");
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
        dc.addChild(config);
        return dc;
    }

    /**
     * Performs verification of the file with given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings, warning line numbers are
     * represented by the array of integers.
     * This implementation uses overloaded
     * {@link AbstractItModuleTestSupport#verify(Checker, File[], String, String[], Integer...)}
     * method inside.
     *
     * @param config configuration.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @param warnsExpected an array of expected warning numbers.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Configuration config, String fileName, String[] expected,
            Integer... warnsExpected) throws Exception {
        verify(createChecker(config),
                new File[] {new File(fileName)},
                fileName, expected, warnsExpected);
    }

    /**
     * Performs verification of files. Uses provided {@link Checker} instance.
     *
     * @param checker {@link Checker} instance.
     * @param processedFiles files to process.
     * @param messageFileName message file name.
     * @param expected an array of expected messages.
     * @param warnsExpected an array of expected warning line numbers.
     * @throws Exception if exception occurs during verification process.
     */
    protected final void verify(Checker checker,
            File[] processedFiles,
            String messageFileName,
            String[] expected,
            Integer... warnsExpected)
            throws Exception {
        stream.flush();
        stream.reset();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final List<Integer> theWarnings = new ArrayList<>();
        Collections.addAll(theWarnings, warnsExpected);
        final int errs = checker.process(theFiles);

        // process each of the lines
        try (ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
            LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            int previousLineNumber = 0;
            for (int index = 0; index < expected.length; index++) {
                final String expectedResult = messageFileName + ":" + expected[index];
                final String actual = lnr.readLine();
                assertWithMessage("Error message at position %s of 'expected' does "
                        + "not match actual message", index)
                    .that(actual)
                    .isEqualTo(expectedResult);

                String parseInt = removeDeviceFromPathOnWindows(actual);
                parseInt = parseInt.substring(parseInt.indexOf(':') + 1);
                parseInt = parseInt.substring(0, parseInt.indexOf(':'));
                final int lineNumber = Integer.parseInt(parseInt);
                assertWithMessage(
                        "input file is expected to have a warning comment on line number %s",
                        lineNumber)
                    .that(previousLineNumber == lineNumber
                            || theWarnings.remove((Integer) lineNumber))
                    .isTrue();
                previousLineNumber = lineNumber;
            }

            assertWithMessage("unexpected output: %s", lnr.readLine())
                .that(errs)
                .isEqualTo(expected.length);
            assertWithMessage("unexpected warnings %s", theWarnings)
                .that(theWarnings)
                .isEmpty();
        }

        checker.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param aClass the package the message is located in.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments  the arguments of message in 'messages.properties' file.
     * @return The message of the check with the arguments applied.
     * @throws IOException if there is a problem loading the property file.
     */
    protected static String getCheckMessage(Class<? extends AbstractViolationReporter> aClass,
            String messageKey, Object... arguments) throws IOException {
        final Properties pr = new Properties();
        pr.load(aClass.getResourceAsStream("messages.properties"));
        final MessageFormat formatter = new MessageFormat(pr.getProperty(messageKey),
                Locale.ROOT);
        return formatter.format(arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties' file.
     *
     * @param messages the map of messages to scan.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     * @return The message of the check with the arguments applied.
     */
    protected static String getCheckMessage(Map<String, String> messages, String messageKey,
            Object... arguments) {
        String checkMessage = null;
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            if (messageKey.equals(entry.getKey())) {
                final MessageFormat formatter = new MessageFormat(entry.getValue(), Locale.ROOT);
                checkMessage = formatter.format(arguments);
                break;
            }
        }
        return checkMessage;
    }

    /**
     * Remove device from path string for windows path.
     *
     * @param path path to correct.
     * @return Path without device name.
     */
    private static String removeDeviceFromPathOnWindows(String path) {
        String fixedPath = path;
        final String os = System.getProperty("os.name", "Unix");
        if (os.startsWith("Windows")) {
            fixedPath = path.substring(path.indexOf(':') + 1);
        }
        return fixedPath;
    }

    /**
     * Returns an array of integers which represents the warning line numbers in the file
     * with the given file name.
     *
     * @param fileName file name.
     * @return an array of integers which represents the warning line numbers.
     * @throws IOException if I/O exception occurs while reading the file.
     */
    protected Integer[] getLinesWithWarn(String fileName) throws IOException {
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName), StandardCharsets.UTF_8)) {
            int lineNumber = 1;
            while (true) {
                final String line = br.readLine();
                if (line == null) {
                    break;
                }
                if (WARN_PATTERN.matcher(line).find()) {
                    result.add(lineNumber);
                }
                lineNumber++;
            }
        }
        return result.toArray(new Integer[0]);
    }

    @Override
    protected String getResourceLocation() {
        return "it";
    }

}
