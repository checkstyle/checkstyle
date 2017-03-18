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

package com.google.checkstyle.test.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.BriefUtLogger;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class BaseCheckTestSupport {
    private static final Pattern WARN_PATTERN = CommonUtils
            .createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]\\s?warn\\s?[*]/");

    private static final String XML_NAME = "/google_checks.xml";

    private static Configuration configuration;

    protected final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * Returns {@link Configuration} based on Google's checks xml-configuration (google_checks.xml).
     * This implementation uses {@link ConfigurationLoader} in order to load configuration
     * from xml-file.
     * @return {@link Configuration} based on Google's checks xml-configuration (google_checks.xml).
     * @throws CheckstyleException if exception occurs during configuration loading.
     */
    protected static Configuration getConfiguration() throws CheckstyleException {
        if (configuration == null) {
            configuration = ConfigurationLoader.loadConfiguration(XML_NAME, new PropertiesExpander(
                    System.getProperties()));
        }

        return configuration;
    }

    /**
     * Creates {@link DefaultConfiguration} instance for the given check class.
     * @param clazz check class.
     * @return {@link DefaultConfiguration} instance.
     */
    private static DefaultConfiguration createCheckConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    /**
     * Creates {@link Checker} instance based on specified {@link Configuration}.
     * @param checkConfig {@link Configuration} instance.
     * @return {@link Checker} instance.
     * @throws CheckstyleException if an exception occurs during checker configuration.
     */
    protected Checker createChecker(Configuration checkConfig)
            throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker checker = new Checker();
        // make sure the tests always run with English error messages
        // so the tests don't fail in supported locales like German
        final Locale locale = Locale.ENGLISH;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefUtLogger(stream));
        return checker;
    }

    /**
     * Creates {@link DefaultConfiguration} or the {@link Checker}.
     * based on the given {@link Configuration}.
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the {@link Checker}.
     */
    protected DefaultConfiguration createCheckerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", "iso-8859-1");
        dc.addChild(twConf);
        twConf.addChild(config);
        return dc;
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed based on the specific root location.
     * This implementation uses 'src/it/resources/com/google/checkstyle/test/' as a root location.
     * @param fileName file name.
     * @return canonical path for the the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected String getPath(String fileName) throws IOException {
        return new File("src/it/resources/com/google/checkstyle/test/" + fileName)
                .getCanonicalPath();
    }

    /**
     * Performs verification of the file with given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings, warning line numbers are
     * represented by the array of integers.
     * This implementation uses overloaded
     * {@link BaseCheckTestSupport#verify(Checker, File[], String, String[], Integer...)} method
     * inside.
     * @param config configuration.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @param warnsExpected an array of expected warning numbers.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verify(Configuration config, String fileName, String[] expected,
            Integer... warnsExpected) throws Exception {
        verify(createChecker(config),
                new File[] {new File(fileName)},
                fileName, expected, warnsExpected);
    }

    /**
     * Performs verification of files. Uses provided {@link Checker} instance.
     * @param checker {@link Checker} instance.
     * @param processedFiles files to process.
     * @param messageFileName message file name.
     * @param expected an array of expected messages.
     * @param warnsExpected an array of expected warning line numbers.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verify(Checker checker,
            File[] processedFiles,
            String messageFileName,
            String[] expected,
            Integer... warnsExpected)
            throws Exception {
        stream.flush();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final List<Integer> theWarnings = new ArrayList<>();
        Collections.addAll(theWarnings, warnsExpected);
        final int errs = checker.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
        try (LineNumberReader lnr = new LineNumberReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            int previousLineNumber = 0;
            for (int i = 0; i < expected.length; i++) {
                final String expectedResult = messageFileName + ":" + expected[i];
                final String actual = lnr.readLine();
                assertEquals("error message " + i, expectedResult, actual);

                String parseInt = removeDeviceFromPathOnWindows(actual);
                parseInt = parseInt.substring(parseInt.indexOf(':') + 1);
                parseInt = parseInt.substring(0, parseInt.indexOf(':'));
                final int lineNumber = Integer.parseInt(parseInt);
                assertTrue("input file is expected to have a warning comment on line number "
                        + lineNumber, previousLineNumber == lineNumber
                            || theWarnings.remove((Integer) lineNumber));
                previousLineNumber = lineNumber;
            }

            assertEquals("unexpected output: " + lnr.readLine(),
                    expected.length, errs);
            assertEquals("unexpected warnings " + theWarnings, 0, theWarnings.size());
        }

        checker.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments  the arguments of message in 'messages.properties' file.
     */
    protected String getCheckMessage(Class<? extends AbstractViolationReporter> aClass,
            String messageKey, Object... arguments) {
        final Properties pr = new Properties();
        try {
            pr.load(aClass.getResourceAsStream("messages.properties"));
        }
        catch (IOException ex) {
            return null;
        }
        final MessageFormat formatter = new MessageFormat(pr.getProperty(messageKey),
                Locale.ROOT);
        return formatter.format(arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties' file.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
     */
    protected String getCheckMessage(Map<String, String> messages, String messageKey,
            Object... arguments) {
        for (Map.Entry<String, String> entry : messages.entrySet()) {
            if (messageKey.equals(entry.getKey())) {
                final MessageFormat formatter = new MessageFormat(entry.getValue(), Locale.ROOT);
                return formatter.format(arguments);
            }
        }
        return null;
    }

    /**
     * Returns {@link Configuration} instance for the given check name.
     * This implementation uses {@link BaseCheckTestSupport#getConfiguration()} method inside.
     * @param checkName check name.
     * @return {@link Configuration} instance for the given check name.
     * @throws CheckstyleException if exception occurs during configuration loading.
     */
    protected static Configuration getCheckConfig(String checkName) throws CheckstyleException {
        final Configuration result;
        final List<Configuration> configs = getCheckConfigs(checkName);
        if (configs.size() == 1) {
            result = configs.get(0);
        }
        else {
            throw new IllegalStateException("multiple instances of the same Check are detected");
        }
        return result;
    }

    /**
     * Returns {@link Configuration} instance for the given check name.
     * This implementation uses {@link BaseCheckTestSupport#getConfiguration()} method inside.
     * @param checkName check name.
     * @return {@link Configuration} instance for the given check name.
     * @throws CheckstyleException if exception occurs during configuration loading.
     */
    protected static Configuration getCheckConfig(String checkName, String checkId)
            throws CheckstyleException {
        final Configuration result;
        final List<Configuration> configs = getCheckConfigs(checkName);
        if (configs.size() == 1) {
            result = configs.get(0);
        }
        else {
            result = configs.stream().filter(conf -> {
                try {
                    return conf.getAttribute("id").equals(checkId);
                }
                catch (CheckstyleException ex) {
                    throw new IllegalStateException("problem to get ID attribute from " + conf, ex);
                }
            })
            .findFirst().orElseGet(null);
        }
        return result;
    }

    /**
     * Returns a list of all {@link Configuration} instances for the given check name.
     * This implementation uses {@link BaseCheckTestSupport#getConfiguration()} method inside.
     * @param checkName check name.
     * @return {@link Configuration} instance for the given check name.
     * @throws CheckstyleException if exception occurs during configuration loading.
     */
    protected static List<Configuration> getCheckConfigs(String checkName)
            throws CheckstyleException {
        final List<Configuration> result = new ArrayList<>();
        for (Configuration currentConfig : getConfiguration().getChildren()) {
            if ("TreeWalker".equals(currentConfig.getName())) {
                for (Configuration checkConfig : currentConfig.getChildren()) {
                    if (checkName.equals(checkConfig.getName())) {
                        result.add(checkConfig);
                    }
                }
            }
            else if (checkName.equals(currentConfig.getName())) {
                result.add(currentConfig);
            }
        }
        return result;
    }

    private static String removeDeviceFromPathOnWindows(String path) {
        final String os = System.getProperty("os.name", "Unix");
        if (os.startsWith("Windows")) {
            return path.substring(path.indexOf(':') + 1);
        }
        return path;
    }

    /**
     * Returns an array of integers which represents the warning line numbers in the file
     * with the given file name.
     * @param fileName file name.
     * @return an array of integers which represents the warning line numbers.
     * @throws IOException if I/O exception occurs while reading the file.
     */
    protected Integer[] getLinesWithWarn(String fileName) throws IOException {
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
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
        return result.toArray(new Integer[result.size()]);
    }
}
