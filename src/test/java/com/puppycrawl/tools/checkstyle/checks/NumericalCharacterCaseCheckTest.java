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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.NumericalCharacterCaseCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumericalCharacterCaseCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/numericalcharactercase";
    }

    @Test
    public void testGetRequiredTokens() {
        final NumericalCharacterCaseCheck check = new NumericalCharacterCaseCheck();
        final int[] expectedTokens = {TokenTypes.NUM_LONG, TokenTypes.NUM_INT,
            TokenTypes.NUM_FLOAT, TokenTypes.NUM_DOUBLE,
        };
        assertWithMessage("Default required tokens are valid")
            .that(check.getRequiredTokens())
            .isEqualTo(expectedTokens);
    }

    @Test
    public void testAcceptableTokens() {
        final int[] expected = {TokenTypes.NUM_LONG, TokenTypes.NUM_INT,
            TokenTypes.NUM_FLOAT, TokenTypes.NUM_DOUBLE,
        };
        final NumericalCharacterCaseCheck check = new NumericalCharacterCaseCheck();
        final int[] actual = check.getAcceptableTokens();
        assertWithMessage("Invalid size of tokens")
                .that(actual.length)
                .isEqualTo(4);
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testCheck()
            throws Exception {
        final String[] expected = {
            "11:16: " + getCheckMessage(MSG_KEY),
            "14:16: " + getCheckMessage(MSG_KEY),
            "17:18: " + getCheckMessage(MSG_KEY),
            "20:22: " + getCheckMessage(MSG_KEY),
            "23:18: " + getCheckMessage(MSG_KEY),
            "26:19: " + getCheckMessage(MSG_KEY),
            "29:18: " + getCheckMessage(MSG_KEY),
            "32:19: " + getCheckMessage(MSG_KEY),
            "33:19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNumericalCharacterCaseCheck.java"), expected
        );
    }
}
