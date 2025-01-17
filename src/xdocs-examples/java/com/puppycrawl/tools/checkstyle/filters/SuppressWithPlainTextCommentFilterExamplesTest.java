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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

@Disabled("until https://github.com/checkstyle/checkstyle/issues/13345")
public class SuppressWithPlainTextCommentFilterExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String fileWithConfig = getPath("Example1.java");
        final String targetFile = getPath("Example1.properties");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String fileWithConfig = getPath("Example2.java");
        final String targetFile = getPath("Example2.properties");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String fileWithConfig = getPath("Example3.java");
        final String targetFile = getPath("Example3.sql");
        final String[] expected = {
            "6:1: Line contains a tab character.",
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String fileWithConfig = getPath("Example4.java");
        final String targetFile = getPath("Example4.xml");
        final String[] expected = {
            "12: Type code is not allowed. Use type raw instead.",
        };

        verifyWithInlineConfigParserSeparateConfigAndTarget(fileWithConfig, targetFile, expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example5.txt"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example6.txt"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example7.txt"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example8.txt"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example9.java"), expected);
    }
}
