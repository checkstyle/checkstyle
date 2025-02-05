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

package com.puppycrawl.tools.checkstyle.filters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;

public class SuppressionXpathSingleFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathsinglefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        List<String> emptyMessage = List.of();
        List<String> fileThreeMessages = List.of(
            "4:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$")
        );
        final String EXAMPLE_FILES_PKG = "example1files";
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileOne.java"), emptyMessage);
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileTwo.java"), emptyMessage);
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileThree.java"), fileThreeMessages);
        final String configFilePath = getPath("Example1.java");
        final TestInputConfiguration testInputConfiguration = InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig = testInputConfiguration.createConfiguration();
        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/" + EXAMPLE_FILES_PKG);
        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethodA", "^[a-z][a-zA-Z0-9]*$"),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        List<String> emptyMessage = List.of();
        List<String> fileTwoMessages = List.of(
            "4:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z](_?[a-zA-Z0-9]+)*$")
        );
        final String EXAMPLE_FILES_PKG = "example3files";
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileOne.java"), emptyMessage);
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileTwo.java"), fileTwoMessages);
        final String configFilePath = getPath("Example3.java");
        final TestInputConfiguration testInputConfiguration = InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig = testInputConfiguration.createConfiguration();
        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/" + EXAMPLE_FILES_PKG);
        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        List<String> emptyMessage = List.of();
        // The inner package is 'File', to ensure the suppression of PackageNameCheck
        final String EXAMPLE_FILES_PKG = "example4files/File";
        expected.put(getPath(EXAMPLE_FILES_PKG + "/" + "FileOne.java"), emptyMessage);
        final String configFilePath = getPath("Example4.java");
        final TestInputConfiguration testInputConfiguration = InlineConfigParser.parse(configFilePath);
        final DefaultConfiguration parsedConfig = testInputConfiguration.createConfiguration();
        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/" + EXAMPLE_FILES_PKG);
        verify(createChecker(parsedConfig), getFilesInFolder(path), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z](_?[a-zA-Z0-9]+)*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example6.java"), expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "24:1: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "FileTwo", "^Abstract.+$"),
            "26:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "20:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod3", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "23:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "testVariable2", "^[A-Z][A-Z0-9]*$" ),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "23:3: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS,
            "{", 3),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE,
                    "number", ""),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "21:37: " + getCheckMessage(IllegalThrowsCheck.class, IllegalThrowsCheck.MSG_KEY,
                    "RuntimeException"),
        };

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }

    @Test
    public void testExample13() throws Exception {
        final String[] expected = {
            "27:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "TestMethod2", "^[a-z](_?[a-zA-Z0-9]+)*$"),
            "29:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "num", "^[A-Z][A-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example13.java"), expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example14.txt"), expected);
    }

    private static File[] getFilesInFolder(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path.toAbsolutePath())) {
            return stream.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toArray(File[]::new);
        }
    }
}
