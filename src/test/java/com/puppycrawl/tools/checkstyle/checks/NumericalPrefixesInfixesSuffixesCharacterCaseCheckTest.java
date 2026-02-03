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
import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_INFIX;
import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_PREFIX;
import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_SUFFIX;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumericalPrefixesInfixesSuffixesCharacterCaseCheckTest
        extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return
            "com/puppycrawl/tools/checkstyle/checks/numericalprefixesinfixessuffixescharactercase";
    }

    @Test
    public void testGetRequiredTokens() {
        final NumericalPrefixesInfixesSuffixesCharacterCaseCheck check =
            new NumericalPrefixesInfixesSuffixesCharacterCaseCheck();
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
        final NumericalPrefixesInfixesSuffixesCharacterCaseCheck check =
            new NumericalPrefixesInfixesSuffixesCharacterCaseCheck();
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
            "11:16: " + getCheckMessage(MSG_PREFIX),
            "14:16: " + getCheckMessage(MSG_PREFIX),
            "17:18: " + getCheckMessage(MSG_INFIX),
            "20:22: " + getCheckMessage(MSG_INFIX),
            "23:18: " + getCheckMessage(MSG_SUFFIX),
            "26:19: " + getCheckMessage(MSG_SUFFIX),
            "29:18: " + getCheckMessage(MSG_INFIX),
            "32:19: " + getCheckMessage(MSG_INFIX),
            "33:19: " + getCheckMessage(MSG_SUFFIX),
        };
        verifyWithInlineConfigParser(
                getPath("InputNumericalPrefixesInfixesSuffixesCharacterCaseCheck.java"), expected
        );
    }
}
