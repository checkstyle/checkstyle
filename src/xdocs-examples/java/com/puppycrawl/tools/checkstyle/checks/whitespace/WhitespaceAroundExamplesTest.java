///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class WhitespaceAroundExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearound";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "11:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "13:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "13:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "19:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "19:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "24:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "31:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "35:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "29:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "13:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "18:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "18:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*="),
            "24:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*="),
            "29:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "29:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "-="),
            "34:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/="),
            "34:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/="),
            "39:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%="),
            "39:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%="),
            "44:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>="),
            "44:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>="),
            "49:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>>="),
            "49:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>>="),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

}
