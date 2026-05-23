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
            "15:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "59:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
            "59:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "63:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "63:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "66:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "66:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "66:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "66:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "69:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "69:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "69:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "32:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "32:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "35:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "35:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "38:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*="),
            "38:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*="),
            "41:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "41:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "-="),
            "44:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/="),
            "44:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/="),
            "47:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%="),
            "47:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%="),
            "50:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>="),
            "50:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>="),
            "53:6: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">>>="),
            "53:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">>>="),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "72:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "26:8: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "72:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "72:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "22:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "22:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "55:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "55:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "55:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "48:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "56:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "56:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "68:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "55:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

}
