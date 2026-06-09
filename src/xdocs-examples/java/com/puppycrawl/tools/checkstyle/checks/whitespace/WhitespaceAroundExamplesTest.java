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
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "21:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "24:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "24:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "24:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "28:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "37:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "41:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "27:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "27:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "35:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "35:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "53:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "53:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "17:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "17:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "23:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "29:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "15:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "15:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "15:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "21:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "21:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "21:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "24:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "24:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "24:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "28:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:8: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "37:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "41:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

}
