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
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class BaseCheckTestSupport {
    protected final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    protected static DefaultConfiguration createCheckConfig(Class<?> clazz) {
        return new DefaultConfiguration(clazz.getName());
    }

    public Checker createChecker(Configuration checkConfig)
            throws Exception {
        final DefaultConfiguration dc = createCheckerConfig(checkConfig);
        final Checker checker = new Checker();
        // make sure the tests always run with default error messages (language-invariant)
        // so the tests don't fail in supported locales like German
        final Locale locale = Locale.ROOT;
        checker.setLocaleCountry(locale.getCountry());
        checker.setLocaleLanguage(locale.getLanguage());
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(dc);
        checker.addListener(new BriefUtLogger(stream));
        return checker;
    }

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

    protected String getPath(String filename) throws IOException {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename)
                .getCanonicalPath();
    }

    protected String getUriString(String filename) {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + filename).toURI()
                .toString();
    }

    protected static String getSrcPath(String filename) throws IOException {
        return new File("src/test/java/com/puppycrawl/tools/checkstyle/" + filename)
                .getCanonicalPath();
    }

    protected String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                + filename).getCanonicalPath();
    }

    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName)
            throws Exception {
        verifyAst(expectedTextPrintFileName, actualJavaFileName, false);
    }

    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName,
            boolean withComments) throws Exception {
        final String expectedContents = Files.toString(new File(expectedTextPrintFileName),
                Charsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n");
        final String actualContents = AstTreeStringPrinter.printFileAst(
                new File(actualJavaFileName), withComments).replaceAll("\\\\r\\\\n", "\\\\n");

        assertEquals("Generated AST from Java file should match pre-defined AST", expectedContents,
                actualContents);
    }

    protected void verify(Configuration aConfig, String fileName, String... expected)
            throws Exception {
        verify(createChecker(aConfig), fileName, fileName, expected);
    }

    protected void verify(Checker checker, String fileName, String... expected)
            throws Exception {
        verify(checker, fileName, fileName, expected);
    }

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
     *  We keep two verify methods with separate logic only for convenience of debuging
     *  We have minimum amount of multi-file test cases
     */
    protected void verify(Checker checker,
                          File[] processedFiles,
                          String messageFileName,
                          String... expected)
            throws Exception {
        stream.flush();
        final List<File> theFiles = Lists.newArrayList();
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

    protected void verify(Checker checker,
                          File[] processedFiles,
                          Map<String, List<String>> expectedViolations)
            throws Exception {
        stream.flush();
        final List<File> theFiles = Lists.newArrayList();
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

        final StringBuilder message = new StringBuilder();
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
        final Properties pr = new Properties();
        try {
            pr.load(getClass().getResourceAsStream("messages.properties"));
        }
        catch (IOException ex) {
            return null;
        }
        final MessageFormat formatter = new MessageFormat(pr.getProperty(messageKey),
                Locale.ROOT);
        return formatter.format(arguments);
    }
}
