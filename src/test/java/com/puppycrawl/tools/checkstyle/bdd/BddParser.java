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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public final class BddParser {

    /** Stop instances being created. **/
    private BddParser() {
    }

    /**
     * Parses the input file provided.
     *
     * @throws CheckstyleException if unable to read file or file not formatted properly.
     */
    public static ParsedInput parse(File inputFile) throws CheckstyleException {
        final Path filePath = Paths.get(inputFile.getAbsolutePath());
        final List<String> lines = readFile(filePath);
        final String checkName = getCheckName(filePath, lines);
        final Map<String, String> defaultProperties = getDefaultProperties(filePath, lines);
        final Map<String, String> nonDefaultProperties = getNonDefaultProperties(filePath, lines);
        final Map<String, String> properties = new HashMap<>();
        properties.putAll(defaultProperties);
        properties.putAll(nonDefaultProperties);
        final List<String> violations = getViolationList();
        return new ParsedInput(checkName, properties,
                defaultProperties, nonDefaultProperties, violations);
    }

    private static List<String> readFile(Path filePath) throws CheckstyleException {
        final List<String> lines;
        try (BufferedReader br = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {
            lines = new ArrayList<>(br.lines().collect(Collectors.toList()));
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + filePath, ex);
        }
        return lines;
    }

    private static Map<String, String> getDefaultProperties(Path filePath, List<String> lines)
                    throws CheckstyleException {
        final Map<String, String> defaultProperties = new HashMap<>();
        int lineNo = 2;
        for (String line = lines.get(lineNo); !line.contains("*/"); line = lines.get(++lineNo)) {
            if (line.contains("(default)")) {
                try {
                    final int equalPosition = line.indexOf('=');
                    final int defaultEndPosition = line.indexOf(')');
                    final String property = line.substring(0, equalPosition - 1);
                    final String value = line.substring(defaultEndPosition + 1);
                    defaultProperties.put(property, value);
                }
                catch (IndexOutOfBoundsException ex) {
                    throw new CheckstyleException("Config not formatted properly in file "
                            + filePath, ex);
                }
            }
        }
        return defaultProperties;
    }

    private static Map<String, String> getNonDefaultProperties(Path filePath, List<String> lines)
                    throws CheckstyleException {
        final Map<String, String> nonDefaultProperties = new HashMap<>();
        int lineNo = 2;
        for (String line = lines.get(lineNo); !line.contains("*/"); line = lines.get(++lineNo)) {
            if (!line.contains("(default)") && line.length() > 0) {
                try {
                    final int equalPosition = line.indexOf('=');
                    final String property = line.substring(0, equalPosition - 1);
                    final String value = line.substring(equalPosition + 2);
                    nonDefaultProperties.put(property, value);
                }
                catch (IndexOutOfBoundsException ex) {
                    throw new CheckstyleException("Config not formatted properly in file "
                            + filePath, ex);
                }
            }
        }
        return nonDefaultProperties;
    }

    private static String getCheckName(Path filePath, List<String> lines)
                    throws CheckstyleException {
        final String checkName;
        try {
            checkName = lines.get(1);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new CheckstyleException("Config not specified in " + filePath, ex);
        }
        return checkName;
    }

    private static List<String> getViolationList() {
        // to be implemented
        return new ArrayList<>();
    }
}
