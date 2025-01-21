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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class RegexpCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexp";
    }

    @Test
    public void testExample0() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example0.java"), expected);
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "1: Required pattern '// This code is copyrighted\\.' missing in file.",
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "20: Found duplicate pattern '// This code is copyrighted\\n// \\(c\\) MyCompany'.",
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "1: Required pattern 'Copyright' missing in file.",
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "17: Line matches the illegal pattern 'System\\.out\\.println'.",
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "18: Line matches the illegal pattern 'System\\.out\\.println'.",
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "5: Line matches the illegal pattern '(?i)debug'.",
            "19: Line matches the illegal pattern '(?i)debug'.",
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "5: Line matches the illegal pattern '(?i)debug'.",
            "20: Line matches the illegal pattern '(?i)debug'.",
            "24: Line matches the illegal pattern '(?i)debug'.",
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "1: Required pattern '\\A// Copyright \\(C\\) \\d\\d\\d\\d MyCompany\\n"
                + "// All rights reserved' missing in file.",
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String fileWithConfig = getPath("Example12.java");
        final String targetFile = getPath("Example12.properties");
        final String[] expected = {

        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample13() throws Exception {
        final String fileWithConfig = getPath("Example13.java");
        final String targetFile = getPath("Example13.properties");
        final String[] expected = {

        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String fileWithConfig = getPath("Example14.java");
        final String targetFile = getPath("Example14.properties");
        final String[] expected = {

        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }
}
