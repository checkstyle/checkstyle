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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TypecastParenPadCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/typecastparenpad";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "86:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:22: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypecastParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final String[] expected = {
            "84:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "84:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "85:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "85:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "87:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "87:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "238:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "238:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypecastParenPadWhitespaceTestSpace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputTypecastParenPadWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final TypecastParenPadCheck typecastParenPadCheckObj = new TypecastParenPadCheck();
        final int[] actual = typecastParenPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RPAREN,
            TokenTypes.TYPECAST,
        };
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

}
