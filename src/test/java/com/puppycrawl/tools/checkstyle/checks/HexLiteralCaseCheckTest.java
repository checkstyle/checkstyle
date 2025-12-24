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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HexLiteralCaseCheckTest
        extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/hexliteralcase";
    }

    @Test
    public void testGetRequiredTokens() {
        final HexLiteralCaseCheck check = new HexLiteralCaseCheck();
        final int[] expectedTokens = {TokenTypes.NUM_LONG, TokenTypes.NUM_INT};
        assertWithMessage("Default required tokens are valid")
            .that(check.getRequiredTokens())
            .isEqualTo(expectedTokens);
    }

    @Test
    public void testAcceptableTokens() {
        final int[] expected = {TokenTypes.NUM_LONG, TokenTypes.NUM_INT};
        final HexLiteralCaseCheck check = new HexLiteralCaseCheck();
        final int[] actual = check.getAcceptableTokens();
        assertWithMessage("Invalid size of tokens")
                .that(actual.length)
                .isEqualTo(2);
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testCheck()
            throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY),
            "12:14: " + getCheckMessage(MSG_KEY),
            "14:16: " + getCheckMessage(MSG_KEY),
            "16:16: " + getCheckMessage(MSG_KEY),
            "19:21: " + getCheckMessage(MSG_KEY),
            "21:22: " + getCheckMessage(MSG_KEY),
            "23:14: " + getCheckMessage(MSG_KEY),
            "25:15: " + getCheckMessage(MSG_KEY),
            "27:15: " + getCheckMessage(MSG_KEY),
            "30:14: " + getCheckMessage(MSG_KEY),
            "31:14: " + getCheckMessage(MSG_KEY),

        };
        verifyWithInlineConfigParser(
                getPath("InputHexLiteralCaseCheck.java"), expected
        );
    }

}
