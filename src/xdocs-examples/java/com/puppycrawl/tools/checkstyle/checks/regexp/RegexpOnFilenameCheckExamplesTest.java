///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MATCH;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

public class RegexpOnFilenameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexponfilename";
    }

    @Test
    public void testExample1() throws Exception {
        final String configFilePath = getPath("Example1.java");
        final String testExampleFilePath = getPath("Test Example1.xml");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "\\s"),
        };

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");
        final List<File> filesInFolder = Files.walk(path.toAbsolutePath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        final Map<String, List<String>> violationsByFile = new HashMap<>();
        violationsByFile.put(testExampleFilePath, Arrays.asList(expected));

        verify(createChecker(parsedConfig),
                filesInFolder.toArray(new File[0]),
                violationsByFile);
    }

    @Test
    public void testExample2() throws Exception {
        final String configFilePath = getPath("Example2.java");
        final String testExampleFilePath1 = getPath("TestExample2.xml");
        final String testExampleFilePath2 = getPath("TestExample4.xml");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "^TestExample\\d+\\.xml$"),

        };

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");
        final List<File> filesInFolder = Files.walk(path.toAbsolutePath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        final Map<String, List<String>> violationsByFile = new HashMap<>();
        violationsByFile.put(testExampleFilePath1, Arrays.asList(expected));
        violationsByFile.put(testExampleFilePath2, Arrays.asList(expected));

        verify(createChecker(parsedConfig),
                filesInFolder.toArray(new File[0]),
                violationsByFile);
    }

    @Test
    public void testExample3() throws Exception {
        final String configFilePath = getPath("Example3.java");
        final String testExampleFilePath = getPath("TestExample3.md");
        final String[] expected = {
            "1: " + "No *.md files other then README.md",
        };

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");
        final List<File> filesInFolder = Files.walk(path.toAbsolutePath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        final Map<String, List<String>> violationsByFile = new HashMap<>();
        violationsByFile.put(testExampleFilePath, Arrays.asList(expected));

        verify(createChecker(parsedConfig),
                filesInFolder.toArray(new File[0]),
                violationsByFile);
    }

    @Test
    public void testExample4() throws Exception {
        final String configFilePath = getPath("Example4.java");
        final String testExampleFilePath1 = getPath("Test Example1.xml");
        final String testExampleFilePath2 = getPath("TestExample2.xml");
        final String testExampleFilePath3 = getPath("TestExample4.xml");
        final String testExampleFilePath4 = getPath("checkstyle.xml");
        final String[] expected = {
            "1: " + "pattern mismatch",
        };

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");
        final List<File> filesInFolder = Files.walk(path.toAbsolutePath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        final Map<String, List<String>> violationsByFile = new HashMap<>();
        violationsByFile.put(testExampleFilePath1, Arrays.asList(expected));
        violationsByFile.put(testExampleFilePath2, Arrays.asList(expected));
        violationsByFile.put(testExampleFilePath3, Arrays.asList(expected));
        violationsByFile.put(testExampleFilePath4, Arrays.asList(expected));

        verify(createChecker(parsedConfig),
                filesInFolder.toArray(new File[0]),
                violationsByFile);
    }

    @Test
    public void testExample5() throws Exception {
        final String configFilePath = getPath("Example5.java");
        final String testExampleFilePath1 = getPath("Test Example1.xml");
        final String testExampleFilePath2 = getPath("checkstyle.xml");
        final String[] expected = {
            "1: " + "only filenames in camelcase is allowed",
        };

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");
        final List<File> filesInFolder = Files.walk(path.toAbsolutePath())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        final Map<String, List<String>> violationsByFile = new HashMap<>();
        violationsByFile.put(testExampleFilePath1, Arrays.asList(expected));
        violationsByFile.put(testExampleFilePath2, Arrays.asList(expected));

        verify(createChecker(parsedConfig),
                filesInFolder.toArray(new File[0]),
                violationsByFile);
    }
}
