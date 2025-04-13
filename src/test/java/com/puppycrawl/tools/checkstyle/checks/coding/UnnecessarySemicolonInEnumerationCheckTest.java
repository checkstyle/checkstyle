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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInEnumerationCheck.MSG_SEMI;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Test fixture for the UnnecessarySemicolonInEnumerationCheck.
 *
 */
public class UnnecessarySemicolonInEnumerationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/"
            + "unnecessarysemicoloninenumeration";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "30:12: " + getCheckMessage(MSG_SEMI),
            "33:13: " + getCheckMessage(MSG_SEMI),
            "36:14: " + getCheckMessage(MSG_SEMI),
            "39:14: " + getCheckMessage(MSG_SEMI),
            "42:54: " + getCheckMessage(MSG_SEMI),
            "45:15: " + getCheckMessage(MSG_SEMI),
            "48:56: " + getCheckMessage(MSG_SEMI),
            "52:9: " + getCheckMessage(MSG_SEMI),
            "57:33: " + getCheckMessage(MSG_SEMI),
            "61:9: " + getCheckMessage(MSG_SEMI),
            "64:10: " + getCheckMessage(MSG_SEMI),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessarySemicolonInEnumeration.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final UnnecessarySemicolonInEnumerationCheck check =
                new UnnecessarySemicolonInEnumerationCheck();
        final int[] expected = {
            TokenTypes.ENUM_DEF,
        };
        assertWithMessage("Acceptable required tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default required tokens are invalid")
            .that(check.getDefaultTokens())
            .isEqualTo(expected);
        assertWithMessage("Required required tokens are invalid")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }
}
