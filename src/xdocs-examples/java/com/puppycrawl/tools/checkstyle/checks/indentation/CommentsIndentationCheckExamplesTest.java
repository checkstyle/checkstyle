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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class CommentsIndentationCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/commentsindentation";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:7: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_BLOCK, 21, 6, 4),
            "31:9: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 30, 8, 4),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "33:9: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 32, 8, 4),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 20, 4, 2),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21:2: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 22, 1, 2),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 21, 12, 8),
            "26:6: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, "25, 27", 5, "6, 6"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 16, 4, 8),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "22:15: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 25, 14, 4),
            "23:10: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 25, 9, 4),
            "24:8: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, 25, 7, 4),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "24:6: " + getCheckMessage(
                    CommentsIndentationCheck.MSG_KEY_SINGLE, "23, 25", 5, "6, 4"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

}
