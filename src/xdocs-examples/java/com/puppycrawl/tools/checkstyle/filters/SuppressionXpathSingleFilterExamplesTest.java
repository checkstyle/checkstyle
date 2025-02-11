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

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

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
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;
import com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;

public class SuppressionXpathSingleFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathsinglefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "21:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod1", "^[a-z][a-zA-Z0-9]*$"),
            "20:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod2", "^[a-z][a-zA-Z0-9]*$"),
            "22:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethodA", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {
            "22:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethodA", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithoutFilter = {
            "20:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:9: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter",
                    "^[A-Z]+(\\.[A-Z]*)*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithoutFilter = {
            "17:3: " + getCheckMessage(RedundantModifierCheck.class,
                    RedundantModifierCheck.MSG_KEY, "public"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "17:23: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY,
                "177"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example6.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expectedWithoutFilter = {
            "21:1: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "Example7", "^Abstract.+$"),
            "22:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
            "27:1: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "AnotherClass", "^Abstract.+$"),
            "28:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {
            "27:1: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "AnotherClass", "^Abstract.+$"),
            "28:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example7.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod1", "^[a-z][a-zA-Z0-9]*$"),
            "21:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod2", "^[a-z][a-zA-Z0-9]*$"),
            "23:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod3", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {
            "23:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "MyMethod3", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expectedWithoutFilter = {
            "23:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "testVariable1", "^[A-Z][A-Z0-9]*$"),
            "25:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "testVariable2", "^[A-Z][A-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {
            "25:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
            "testVariable2", "^[A-Z][A-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:3: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS,
            "{", 3),
            "23:3: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS,
            "{", 3),
        };
        final String[] expectedWithFilter = {
            "23:3: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS,
            "{", 3),
        };

        verifyFilterWithInlineConfigParser(getPath("Example10.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expectedWithoutFilter = {
            "25:5: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE,
                    "age", ""),
            "29:5: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE,
                    "number", ""),
        };
        final String[] expectedWithFilter = {
            "29:5: " + getCheckMessage(RequireThisCheck.class, MSG_VARIABLE,
                    "number", ""),
        };

        verifyFilterWithInlineConfigParser(getPath("Example11.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:37: " + getCheckMessage(IllegalThrowsCheck.class, IllegalThrowsCheck.MSG_KEY,
                    "RuntimeException"),
            "23:37: " + getCheckMessage(IllegalThrowsCheck.class, IllegalThrowsCheck.MSG_KEY,
                    "RuntimeException"),
        };
        final String[] expectedWithFilter = {
            "23:37: " + getCheckMessage(IllegalThrowsCheck.class, IllegalThrowsCheck.MSG_KEY,
                    "RuntimeException"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example12.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample13() throws Exception {
        final String[] expectedWithoutFilter = {
            "24:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "TestMethod1", "^[a-z](_?[a-zA-Z0-9]+)*$"),
            "26:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "num", "^[A-Z][A-Z0-9]*$"),
            "30:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "TestMethod2", "^[a-z](_?[a-zA-Z0-9]+)*$"),
            "32:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "num", "^[A-Z][A-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {
            "30:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "TestMethod2", "^[a-z](_?[a-zA-Z0-9]+)*$"),
            "32:15: " + getCheckMessage(AbstractNameCheck.class, MSG_INVALID_PATTERN,
                    "num", "^[A-Z][A-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example13.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample14() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final List<String> messages =
                List.of("3:14: " + getCheckMessage(AbstractNameCheck.class,
                            MSG_INVALID_PATTERN, "myApplication",
                            "^[A-Z][a-zA-Z0-9]*$"));
        expected.put(getPath("src/myApplication.java"), messages);

        final Path path = Paths.get("src/xdocs-examples/resources/" + getPackageLocation() + "/");

        final String fileWithConfig = getPath("Example14.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(fileWithConfig);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
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
