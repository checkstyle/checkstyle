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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class RegexpOnFilenameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexponfilename";
    }

    @Test
    public void testExample1() throws Exception {
        final String configFilePath = getPath("Example1.java");
        final String TestExampleFilePath = getPath("Test Example1.xml");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "\\s"),
        };
        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                TestExampleFilePath, expected);
    }


    @Test
    public void testExample2() throws Exception {
        final String configFilePath = getPath("Example2.java");
        final String TestExampleFilePath = getPath("TestExample2.xml");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_MATCH, "", "TestExample2\\.xml"),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                TestExampleFilePath, expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String configFilePath = getPath("Example3.java");
        final String TestExampleFilePath = getPath("TestExample3.md");
        final String[] expected = {
            "1: " + getCheckMessage(RegexpOnFilenameCheck.MSG_MISMATCH,
                    "", "README"),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                TestExampleFilePath, expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String configFilePath = getPath("Example4.java");
        final String TestExampleFilePath = getPath("TestExample4.xml");
        final String[] expected = {
            "1: " + getCheckMessage(RegexpOnFilenameCheck.MSG_MISMATCH,
                    "[\\\\/]src[\\\\/]\\w+[\\\\/]resources[\\\\/]", ""),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                TestExampleFilePath, expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String configFilePath = getPath("Example5.java");
        final String TestExampleFilePath = getPath("testexample5.xml");
        final String[] expected = {
            "1: " + getCheckMessage(RegexpOnFilenameCheck.MSG_MISMATCH,
                    "", "^([A-Z][a-z0-9]+\\.?)+$"),
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(configFilePath,
                TestExampleFilePath, expected);
    }
}
