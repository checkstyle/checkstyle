///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class WhitespaceAroundExamplesTest extends AbstractModuleTestSupport {
    @Override
    protected String getResourceLocation() {
        return "xdocs-examples";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearound";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "11:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "11:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "11:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "15:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
            "15:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "19:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "19:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "22:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "22:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "22:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "22:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "25:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "25:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "25:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "15:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "18:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "18:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "21:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*="),
            "21:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "-="),
            "27:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/="),
            "27:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/="),
            "30:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%="),
            "30:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%="),
            "33:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>="),
            "33:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>="),
            "36:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>>="),
            "36:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>>="),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "12:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "13:8: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "13:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "13:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "13:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "15:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "16:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "14:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "14:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "13:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "13:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "13:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "13:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "16:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }
}
