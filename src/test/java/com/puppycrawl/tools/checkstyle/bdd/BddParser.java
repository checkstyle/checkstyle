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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class BddParser {

    /** The absolute input file path. */
    private final Path filePath;

    /** The input file name. */
    private final String fileName;

    /** List of lines in the input file. */
    private final List<String> lines;

    /**
     * Instantiates the BddParser object with the input file and path.
     *
     * @param file file name
     * @param path absolute file path
     */
    public BddParser(String file, String path) {
        fileName = file;
        filePath = Paths.get(path);
        lines = new ArrayList<>();
    }

    /**
     * Parses the input file provided to the BddParser Instance.
     *
     * @throws CheckstyleException if unable to read file.
     */
    public final void parse() throws CheckstyleException {
        try (BufferedReader br = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {
            lines.addAll(br.lines().collect(Collectors.toList()));
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + fileName + ": " + ex);
        }
    }

    /**
     * Parses the input file and retreives the check config.
     *
     * @return check config.
     * @throws CheckstyleException if config is absent.
     */
    public DefaultConfiguration getCheckConfig() throws CheckstyleException {
        final String checkName;
        try {
            checkName = lines.get(1);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new CheckstyleException("Config not specified in " + fileName + ": " + ex);
        }
        final DefaultConfiguration checkConfig = createModuleConfig(checkName);

        addCheckAttributes(checkConfig);

        return checkConfig;
    }

    private static DefaultConfiguration createModuleConfig(String className) {
        return new DefaultConfiguration(className);
    }

    private void addCheckAttributes(DefaultConfiguration checkConfig) throws CheckstyleException {
        int lineNo = 2;
        for (String line = lines.get(lineNo); !line.contains("*/"); line = lines.get(++lineNo)) {
            if (line.contains("(default)") || line.isEmpty()) {
                continue;
            }
            try {
                final int equalPosition = line.indexOf('=');
                final String attributeName = line.substring(0, equalPosition - 1);
                final String value = line.substring(equalPosition + 2);
                checkConfig.addAttribute(attributeName, value);
            }
            catch (IndexOutOfBoundsException ex) {
                throw new CheckstyleException("Config not formatted properly in file "
                        + fileName + ": " + ex);
            }
        }
    }
}
