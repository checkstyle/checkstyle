////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public final class InlineConfigParser {

    /** A pattern matching the symbol: "/". */
    private static final Pattern SLASH_PATTERN = Pattern.compile("[\\\\/]");

    /** A pattern to find the string: "// violation". */
    private static final Pattern VIOLATION_PATTERN = Pattern
            .compile(".*//\\s*violation(?:$|\\W+.*$)");

    /** Stop instances being created. **/
    private InlineConfigParser() {
    }

    /**
     * Parses the input file provided.
     *
     * @param inputFilePath the input file path.
     * @throws Exception if unable to read file or file not formatted properly.
     */
    public static InputConfiguration parse(String inputFilePath) throws Exception {
        final Path filePath = Paths.get(inputFilePath);
        final List<String> lines = readFile(filePath);
        final InputConfiguration.Builder inputConfigBuilder = new InputConfiguration.Builder();
        setCheckName(inputConfigBuilder, inputFilePath, lines);
        setCheckProperties(inputConfigBuilder, lines);
        setViolationLineNumbers(inputConfigBuilder, lines);
        return inputConfigBuilder.build();
    }

    private static String getPackageFromFilePath(String filePath) {
        final String path = SLASH_PATTERN.matcher(filePath).replaceAll("\\.");
        final int beginIndex = path.indexOf("com.puppycrawl");
        final int endIndex = path.lastIndexOf('.',
                path.lastIndexOf('.', path.lastIndexOf('.') - 1) - 1);
        return path.substring(beginIndex, endIndex);
    }

    private static List<String> readFile(Path filePath) throws CheckstyleException {
        try {
            return Files.readAllLines(filePath);
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + filePath, ex);
        }
    }

    private static void setCheckName(InputConfiguration.Builder inputConfigBuilder,
                                     String filePath, List<String> lines)
                    throws CheckstyleException {
        if (lines.size() < 2) {
            throw new CheckstyleException("Config not specified in " + filePath);
        }
        final String checkName = lines.get(1);
        final String checkPath = getPackageFromFilePath(filePath) + "." + checkName + "Check";
        inputConfigBuilder.setCheckName(checkPath);
    }

    private static void setCheckProperties(InputConfiguration.Builder inputConfigBuilder,
                                           List<String> lines)
                    throws Exception {
        final StringBuilder stringBuilder = new StringBuilder(128);
        int lineNo = 2;
        for (String line = lines.get(lineNo); !line.contains("*/");
                ++lineNo, line = lines.get(lineNo)) {
            stringBuilder.append(line).append('\n');
        }
        final Properties properties = new Properties();
        try (Reader reader = new StringReader(stringBuilder.toString())) {
            properties.load(reader);
        }
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = entry.getKey().toString();
            final String value = entry.getValue().toString();
            if (value.startsWith("(default)")) {
                inputConfigBuilder.addDefaultProperty(key,
                        value.substring(value.indexOf(')') + 1));
            }
            else {
                inputConfigBuilder.addNonDefaultProperty(key, value);
            }
        }
    }

    private static void setViolationLineNumbers(InputConfiguration.Builder inputConfigBuilder,
                                    List<String> lines) {
        for (int lineNo = 2; lineNo < lines.size(); lineNo++) {
            if (VIOLATION_PATTERN.matcher(lines.get(lineNo)).matches()) {
                inputConfigBuilder.addViolation(lineNo + 1);
            }
        }
    }
}
