///
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
///

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpOnFilenameCheck.MSG_MATCH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages = List.of("1: " + getCheckMessage(MSG_MATCH, "", "\\s"));
        expected.put(getPath("Test Example1.xml"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String configFilePath = getPath("Example1.java");
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("1: xml files should not match '^TestExample\\d+\\.xml$'");
        expected.put(getPath("TestExample2.xml"), messages);
        expected.put(getPath("TestExample4.xml"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String configFilePath = getPath("Example2.java");
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        expected.put(getPath("TestExample3.md"),
                List.of("1: No *.md files other than README.md"));

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String configFilePath = getPath("Example3.java");
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("1: Only property and xml files to be located in the resource folder");
        expected.put(getPath("TestExample3.md"), messages);
        // java files should not be mentioned in user visible output Example4.java
        expected.put(getPath("Example1.java"), messages);
        expected.put(getPath("Example2.java"), messages);
        expected.put(getPath("Example3.java"), messages);
        expected.put(getPath("Example4.java"), messages);
        expected.put(getPath("Example5.java"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String configFilePath = getPath("Example4.java");
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages = List.of("1: only filenames in camelcase is allowed");
        expected.put(getPath("checkstyle.xml"), messages);
        expected.put(getPath("Test Example1.xml"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String configFilePath = getPath("Example5.java");
        final TestInputConfiguration testInputConfiguration1 =
                InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration1.createConfiguration();

        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    private static File[] getFilesInFolder(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path.toAbsolutePath())) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toArray(File[]::new);
        }
    }

}
