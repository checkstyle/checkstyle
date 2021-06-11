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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public final class InlineConfigParser {

    /** Stop instances being created. **/
    private InlineConfigParser() {
    }

    /**
     * Parses the input file provided.
     *
     * @param inputFile the input file path.
     * @throws CheckstyleException if unable to read file or file not formatted properly.
     */
    public static InputConfiguration parse(String inputFile) throws CheckstyleException {
        final Path filePath = Paths.get(inputFile);
        final List<String> lines = readFile(filePath);
        final InputConfiguration.Builder inputConfigBuilder = new InputConfiguration.Builder();
        setCheckName(inputConfigBuilder, filePath, lines);
        return inputConfigBuilder.build();
    }

    private static List<String> readFile(Path filePath) throws CheckstyleException {
        try (BufferedReader br = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {
            return new ArrayList<>(br.lines().collect(Collectors.toList()));
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + filePath, ex);
        }
    }

    private static void setCheckName(InputConfiguration.Builder inputConfigBuilder,
                                     Path filePath, List<String> lines)
                    throws CheckstyleException {
        try {
            inputConfigBuilder.setCheckName(lines.get(1));
        }
        catch (IndexOutOfBoundsException ex) {
            throw new CheckstyleException("Config not specified in " + filePath, ex);
        }
    }
}
