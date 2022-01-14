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

package com.puppycrawl.tools.checkstyle.bdd;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public final class InlineConfigParser {

    /** A pattern matching the symbol: "\" or "/". */
    private static final Pattern SLASH_PATTERN = Pattern.compile("[\\\\/]");

    /** A pattern to find the string: "// violation". */
    private static final Pattern VIOLATION_PATTERN = Pattern
            .compile(".*//\\s*violation(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// violation above". */
    private static final Pattern VIOLATION_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*violation above(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// violation below". */
    private static final Pattern VIOLATION_BELOW_PATTERN = Pattern
            .compile(".*//\\s*violation below(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// X violations". */
    private static final Pattern MULTIPLE_VIOLATIONS_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations$");

    /** A pattern to find the string: "// X violations above". */
    private static final Pattern MULTIPLE_VIOLATIONS_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations above$");

    /** A pattern to find the string: "// X violations below". */
    private static final Pattern MULTIPLE_VIOLATIONS_BELOW_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations below$");

    /** A pattern to find the string: "// filtered violation". */
    private static final Pattern FILTERED_VIOLATION_PATTERN = Pattern
            .compile(".*//\\s*filtered violation(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// filtered violation above". */
    private static final Pattern FILTERED_VIOLATION_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*filtered violation above(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// filtered violation below". */
    private static final Pattern FILTERED_VIOLATION_BELOW_PATTERN = Pattern
            .compile(".*//\\s*filtered violation below(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// violation X lines above". */
    private static final Pattern VIOLATION_SOME_LINES_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*violation (\\d+) lines above(?:\\W+'(.*)')?$");

    /** A pattern to find the string: "// violation X lines below". */
    private static final Pattern VIOLATION_SOME_LINES_BELOW_PATTERN = Pattern
            .compile(".*//\\s*violation (\\d+) lines below(?:\\W+'(.*)')?$");

    /** The String "(null)". */
    private static final String NULL_STRING = "(null)";

    /** Stop instances being created. **/
    private InlineConfigParser() {
    }

    public static TestInputConfiguration parse(String inputFilePath) throws Exception {
        return parse(inputFilePath, false);
    }

    /**
     * Parses the input file provided.
     *
     * @param inputFilePath the input file path.
     * @param setFilteredViolations flag to set filtered violations.
     * @throws Exception if unable to read file or file not formatted properly.
     */
    private static TestInputConfiguration parse(String inputFilePath,
                                                boolean setFilteredViolations) throws Exception {
        final TestInputConfiguration.Builder testInputConfigBuilder =
                new TestInputConfiguration.Builder();
        try {
            final Path filePath = Paths.get(inputFilePath);
            final List<String> lines = readFile(filePath);
            setModules(testInputConfigBuilder, inputFilePath, lines);
            setViolations(testInputConfigBuilder, lines, setFilteredViolations);
        }
        catch (Exception ex) {
            throw new CheckstyleException("Config comment not specified properly in "
                    + inputFilePath, ex);
        }
        return testInputConfigBuilder.build();
    }

    public static TestInputConfiguration parseWithFilteredViolations(String inputFilePath)
            throws Exception {
        return parse(inputFilePath, true);
    }

    private static void setModules(TestInputConfiguration.Builder testInputConfigBuilder,
                                   String inputFilePath, List<String> lines)
            throws Exception {
        if (!lines.get(0).startsWith("/*")) {
            throw new CheckstyleException("Config not specified on top."
                + "Please see other inputs for examples of what is required.");
        }
        int lineNo = 1;
        do {
            final ModuleInputConfiguration.Builder moduleInputConfigBuilder =
                    new ModuleInputConfiguration.Builder();
            setModuleName(moduleInputConfigBuilder, inputFilePath, lines.get(lineNo));
            setProperties(moduleInputConfigBuilder, inputFilePath, lines, lineNo + 1);
            testInputConfigBuilder.addChildModule(moduleInputConfigBuilder.build());
            do {
                lineNo++;
            } while (lines.get(lineNo).isEmpty() || !lines.get(lineNo - 1).isEmpty());
        } while (!lines.get(lineNo).startsWith("*/"));
    }

    private static String getFullyQualifiedClassName(String filePath, String moduleName)
            throws CheckstyleException {
        String fullyQualifiedClassName;
        if (moduleName.startsWith("com.")) {
            fullyQualifiedClassName = moduleName;
        }
        else {
            final String path = SLASH_PATTERN.matcher(filePath).replaceAll("\\.");
            final int endIndex = path.lastIndexOf(moduleName.toLowerCase(Locale.ROOT));
            if (endIndex == -1) {
                throw new CheckstyleException("Unable to resolve module name: " + moduleName
                + ". Please check for spelling errors or specify fully qualified class name.");
            }
            final int beginIndex = path.indexOf("com.puppycrawl");
            fullyQualifiedClassName = path.substring(beginIndex, endIndex) + moduleName;
            if (!fullyQualifiedClassName.endsWith("Filter")) {
                fullyQualifiedClassName += "Check";
            }
        }
        return fullyQualifiedClassName;
    }

    private static String getFilePath(String fileName, String inputFilePath) {
        final int lastSlashIndex = Math.max(inputFilePath.lastIndexOf('\\'),
                inputFilePath.lastIndexOf('/'));
        final String root = inputFilePath.substring(0, lastSlashIndex + 1);
        return root + fileName;
    }

    private static String getResourcePath(String fileName, String inputFilePath) {
        final String filePath = getUriPath(fileName, inputFilePath);
        final int lastSlashIndex = filePath.lastIndexOf('/');
        final String root = filePath.substring(filePath.indexOf("puppycrawl") - 5,
                lastSlashIndex + 1);
        return root + fileName;
    }

    private static String getUriPath(String fileName, String inputFilePath) {
        return new File(getFilePath(fileName, inputFilePath)).toURI().toString();
    }

    private static String getResolvedPath(String fileValue, String inputFilePath) {
        final String resolvedFilePath;
        if (fileValue.startsWith("(resource)")) {
            resolvedFilePath =
                    getResourcePath(fileValue.substring(fileValue.indexOf(')') + 1),
                            inputFilePath);
        }
        else if (fileValue.startsWith("(uri)")) {
            resolvedFilePath =
                    getUriPath(fileValue.substring(fileValue.indexOf(')') + 1), inputFilePath);
        }
        else {
            resolvedFilePath = getFilePath(fileValue, inputFilePath);
        }
        return resolvedFilePath;
    }

    private static List<String> readFile(Path filePath) throws CheckstyleException {
        try {
            return Files.readAllLines(filePath);
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + filePath, ex);
        }
    }

    private static void setModuleName(ModuleInputConfiguration.Builder moduleInputConfigBuilder,
                                      String filePath, String moduleName)
            throws CheckstyleException {
        final String fullyQualifiedClassName = getFullyQualifiedClassName(filePath, moduleName);
        moduleInputConfigBuilder.setModuleName(fullyQualifiedClassName);
    }

    private static void setProperties(ModuleInputConfiguration.Builder inputConfigBuilder,
                                      String inputFilePath,
                                      List<String> lines,
                                      int beginLineNo)
                    throws IOException {
        final StringBuilder stringBuilder = new StringBuilder(128);
        int lineNo = beginLineNo;
        for (String line = lines.get(lineNo); !line.isEmpty() && !"*/".equals(line);
                ++lineNo, line = lines.get(lineNo)) {
            stringBuilder.append(line).append('\n');
        }
        final Properties properties = new Properties();
        properties.load(new StringReader(stringBuilder.toString()));
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = entry.getKey().toString();
            final String value = entry.getValue().toString();
            if (key.startsWith("message.")) {
                inputConfigBuilder.addModuleMessage(key.substring(8), value);
            }
            else if (value.startsWith("(file)")) {
                final String fileName = value.substring(value.indexOf(')') + 1);
                final String filePath = getResolvedPath(fileName, inputFilePath);
                inputConfigBuilder.addNonDefaultProperty(key, filePath);
            }
            else if (value.startsWith("(default)")) {
                final String defaultValue = value.substring(value.indexOf(')') + 1);
                if (NULL_STRING.equals(defaultValue)) {
                    inputConfigBuilder.addDefaultProperty(key, null);
                }
                else {
                    inputConfigBuilder.addDefaultProperty(key, defaultValue);
                }
            }
            else {
                if (NULL_STRING.equals(value)) {
                    inputConfigBuilder.addNonDefaultProperty(key, null);
                }
                else {
                    inputConfigBuilder.addNonDefaultProperty(key, value);
                }
            }
        }
    }

    private static void setViolations(TestInputConfiguration.Builder inputConfigBuilder,
                                      List<String> lines, boolean useFilteredViolations) {
        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            setViolations(inputConfigBuilder, lines, useFilteredViolations, lineNo);
        }
    }

    /**
     * Sets the violations.
     *
     * @param inputConfigBuilder the input file path.
     * @param lines all the lines in the file.
     * @param useFilteredViolations flag to set filtered violations.
     * @param lineNo current line.
     * @noinspection IfStatementWithTooManyBranches
     */
    private static void setViolations(TestInputConfiguration.Builder inputConfigBuilder,
                                      List<String> lines, boolean useFilteredViolations,
                                      int lineNo) {
        final Matcher violationMatcher =
                VIOLATION_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationAboveMatcher =
                VIOLATION_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationBelowMatcher =
                VIOLATION_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsMatcher =
                MULTIPLE_VIOLATIONS_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsAboveMatcher =
                MULTIPLE_VIOLATIONS_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsBelowMatcher =
                MULTIPLE_VIOLATIONS_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationSomeLinesAboveMatcher =
                VIOLATION_SOME_LINES_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationSomeLinesBelowMatcher =
                VIOLATION_SOME_LINES_BELOW_PATTERN.matcher(lines.get(lineNo));
        if (violationMatcher.matches()) {
            inputConfigBuilder.addViolation(lineNo + 1, violationMatcher.group(1));
        }
        else if (violationAboveMatcher.matches()) {
            inputConfigBuilder.addViolation(lineNo, violationAboveMatcher.group(1));
        }
        else if (violationBelowMatcher.matches()) {
            inputConfigBuilder.addViolation(lineNo + 2, violationBelowMatcher.group(1));
        }
        else if (violationSomeLinesAboveMatcher.matches()) {
            final int linesAbove = Integer.parseInt(violationSomeLinesAboveMatcher.group(1)) - 1;
            inputConfigBuilder.addViolation(lineNo - linesAbove,
                    violationSomeLinesAboveMatcher.group(2));
        }
        else if (violationSomeLinesBelowMatcher.matches()) {
            final int linesBelow = Integer.parseInt(violationSomeLinesBelowMatcher.group(1)) + 1;
            inputConfigBuilder.addViolation(lineNo + linesBelow,
                    violationSomeLinesBelowMatcher.group(2));
        }
        else if (multipleViolationsMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsMatcher.group(1)), lineNo + 1)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (multipleViolationsAboveMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsAboveMatcher.group(1)), lineNo)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (multipleViolationsBelowMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsBelowMatcher.group(1)),
                            lineNo + 2)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (useFilteredViolations) {
            setFilteredViolation(inputConfigBuilder, lineNo + 1, lines.get(lineNo));
        }
    }

    private static void setFilteredViolation(TestInputConfiguration.Builder inputConfigBuilder,
                                             int lineNo, String line) {
        final Matcher violationMatcher =
                FILTERED_VIOLATION_PATTERN.matcher(line);
        final Matcher violationAboveMatcher =
                FILTERED_VIOLATION_ABOVE_PATTERN.matcher(line);
        final Matcher violationBelowMatcher =
                FILTERED_VIOLATION_BELOW_PATTERN.matcher(line);
        if (violationMatcher.matches()) {
            inputConfigBuilder.addFilteredViolation(lineNo, violationMatcher.group(1));
        }
        else if (violationAboveMatcher.matches()) {
            inputConfigBuilder.addFilteredViolation(lineNo - 1, violationAboveMatcher.group(1));
        }
        else if (violationBelowMatcher.matches()) {
            inputConfigBuilder.addFilteredViolation(lineNo + 1, violationBelowMatcher.group(1));
        }
    }
}
