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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class BaseCheckTestSupport {
    protected static final String LF_REGEX = "\\\\n";

    protected static final String CLRF_REGEX = "\\\\r\\\\n";

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * Returns test logger.
     * @return logger for tests
     */
    public BriefUtLogger getBriefUtLogger() {
        return new BriefUtLogger(stream);
    }

    protected static DefaultConfiguration createCheckConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    /**
     * Creates {@link Checker} instance based on the given {@link Configuration} instance.
     * @param checkConfig {@link Configuration} instance.
     * @return {@link Checker} instance based on the given {@link Configuration} instance.
     * @throws Exception if an exception occurs during checker configuration.
     */
    public Checker createChecker(Configuration checkConfig)
            throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(getBriefUtLogger());
        return checker;
    }

    /**
     * Creates {@link DefaultConfiguration} for the {@link Checker}
     * based on the given {@link Configuration} instance.
     * @param config {@link Configuration} instance.
     * @return {@link DefaultConfiguration} for the {@link Checker}
     * based on the given {@link Configuration} instance.
     */
    protected DefaultConfiguration createCheckerConfig(Configuration config) {
        final DefaultConfiguration dc =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", "UTF-8");
        dc.addChild(twConf);
        twConf.addChild(config);
        return dc;
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the root location.
     * This implementation uses 'src/test/resources/com/puppycrawl/tools/checkstyle/'
     * as a root location.
     * @param filename file name.
     * @return canonical path for the file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected String getPath(String filename) throws IOException {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename)
                .getCanonicalPath();
    }

    /**
     * Returns URI-representation of the path for the given file name.
     * The path is formed base on the root location.
     * This implementation uses 'src/test/resources/com/puppycrawl/tools/checkstyle/'
     * as a root location.
     * @param filename file name.
     * @deprecated This method is now used in AbstractModuleTestSupport.
     * @return URI-representation of the path for the file with the given file name.
     */
    @Deprecated
    protected String getUriString(String filename) {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename).toURI()
                .toString();
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
    protected String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                + filename).getCanonicalPath();
    }

    /**
     * Performs verification of the given text ast tree representation.
     * This implementation uses
     * {@link BaseCheckTestSupport#verifyAst(String, String, AstTreeStringPrinter.PrintOptions)}
     * method inside.
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName)
            throws Exception {
        verifyAst(expectedTextPrintFileName, actualJavaFileName,
                AstTreeStringPrinter.PrintOptions.WITHOUT_COMMENTS);
    }

    /**
     * Performs verification of the given text ast tree representation.
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @param withComments whether to perform verification of comment nodes in tree.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName,
                                    AstTreeStringPrinter.PrintOptions withComments)
            throws Exception {
        final String expectedContents = readFile(expectedTextPrintFileName);

        final String actualContents = AstTreeStringPrinter.printFileAst(
                new File(actualJavaFileName), withComments).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated AST from Java file should match pre-defined AST", expectedContents,
                actualContents);
    }

    /**
     * Verifies the javadoc tree generated for the supplied javadoc file against the expected tree
     * in the supplied text file.
     * @param expectedTextPrintFilename name of the text file having the expected tree.
     * @param actualJavadocFilename name of the file containing the javadoc.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavadocTree(String expectedTextPrintFilename,
                                            String actualJavadocFilename) throws Exception {

        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = DetailNodeTreeStringPrinter.printFileAst(
                new File(actualJavadocFilename)).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated tree from the javadoc file should match the pre-defined tree",
                expectedContents, actualContents);
    }

    /**
     * Verifies the java and javadoc AST generated for the supplied java file against
     * the expected AST in supplied text file.
     * @param expectedTextPrintFilename name of the file having the expected ast.
     * @param actualJavaFilename name of the java file.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavaAndJavadocAst(String expectedTextPrintFilename,
                                                  String actualJavaFilename) throws Exception {

        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = AstTreeStringPrinter.printJavaAndJavadocTree(
                new File(actualJavaFilename)).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated AST from the java file should match the pre-defined AST",
                expectedContents, actualContents);
    }

    /** Reads the contents of a file.
     * @param filename the name of the file whose contents are to be read
     * @return contents of the file with all {@code \r\n} replaced by {@code \n}
     * @throws IOException if I/O exception occurs while reading
     */
    protected static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(
                Paths.get(filename)), StandardCharsets.UTF_8)
                .replaceAll(CLRF_REGEX, LF_REGEX);
    }

    /**
     * Performs verification of the file with the given file name. Uses specified configuration.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link BaseCheckTestSupport#verify(Checker, File[], String, String...)} method inside.
     * @param aConfig configuration.
     * @param fileName file name to verify.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verify(Configuration aConfig, String fileName, String... expected)
            throws Exception {
        verify(createChecker(aConfig), fileName, fileName, expected);
    }

    /**
     * Performs verification of the file with the given file name.
     * Uses provided {@link Checker} instance.
     * Expected messages are represented by the array of strings.
     * This implementation uses overloaded
     * {@link BaseCheckTestSupport#verify(Checker, String, String, String...)} method inside.
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
     * {@link BaseCheckTestSupport#verify(Checker, File[], String, String...)} method inside.
     * @param checker {@link Checker} instance.
     * @param processedFilename file name to verify.
     * @param messageFileName message file name.
     * @param expected an array of expected messages.
     * @throws Exception if exception occurs during verification process.
     */
    protected void verify(Checker checker,
                          String processedFilename,
                          String messageFileName,
                          String... expected)
            throws Exception {
        verify(checker,
                new File[] {new File(processedFilename)},
                messageFileName, expected);
    }

    /**
     *  We keep two verify methods with separate logic only for convenience of debugging
     *  We have minimum amount of multi-file test cases
     */
    protected void verify(Checker checker,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        stream.flush();
        final List<File> theFiles = new ArrayList<>();
        Collections.addAll(theFiles, processedFiles);
        final int errs = checker.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());
        try (LineNumberReader lnr = new LineNumberReader(
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
    protected void verify(Checker checker,
                          File[] processedFiles,
                          Map<String, List<String>> expectedViolations)
            throws Exception {
        stream.flush();
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
        final Map<String, ValueDifference<List<String>>> differingViolations =
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
        final ByteArrayInputStream inputStream =
                new ByteArrayInputStream(stream.toByteArray());

        try (LineNumberReader lnr = new LineNumberReader(
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
     */
    protected String getCheckMessage(String messageKey, Object... arguments) {
        return internalGetCheckMessage(getMessageBundle(), messageKey, arguments);
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param clazz the related check class.
     * @param messageKey the key of message in 'messages.properties' file.
     * @param arguments the arguments of message in 'messages.properties' file.
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
