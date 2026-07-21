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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ParenPadExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "23:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "24:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:15: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "46:16: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "50:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "26:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:15: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "49:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:16: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "24:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "43:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "50:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

}
