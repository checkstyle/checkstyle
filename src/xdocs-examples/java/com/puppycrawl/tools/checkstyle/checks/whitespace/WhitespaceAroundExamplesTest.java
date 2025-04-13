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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class WhitespaceAroundExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearound";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "19:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
            "19:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "26:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "29:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "29:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "29:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "21:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "27:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*="),
            "27:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*="),
            "30:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "30:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "-="),
            "33:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/="),
            "33:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/="),
            "36:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%="),
            "36:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%="),
            "39:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>="),
            "39:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>="),
            "42:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>>="),
            "42:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>>="),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:8: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "18:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "18:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "18:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "20:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "20:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "21:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "21:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "19:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "19:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "18:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "18:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "18:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "18:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "21:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }
}
