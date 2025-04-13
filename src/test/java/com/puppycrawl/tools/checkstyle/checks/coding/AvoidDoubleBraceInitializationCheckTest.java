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
import static com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidDoubleBraceInitializationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/avoiddoublebraceinitialization";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "15:53: " + getCheckMessage(MSG_KEY),
            "20:40: " + getCheckMessage(MSG_KEY),
            "29:40: " + getCheckMessage(MSG_KEY),
            "35:40: " + getCheckMessage(MSG_KEY),
            "40:40: " + getCheckMessage(MSG_KEY),
            "46:40: " + getCheckMessage(MSG_KEY),
            "51:40: " + getCheckMessage(MSG_KEY),
            "57:40: " + getCheckMessage(MSG_KEY),
            "62:40: " + getCheckMessage(MSG_KEY),
            "70:41: " + getCheckMessage(MSG_KEY),
            "73:33: " + getCheckMessage(MSG_KEY),
            "77:33: " + getCheckMessage(MSG_KEY),
            "79:33: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidDoubleBraceInitialization.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final AvoidDoubleBraceInitializationCheck check =
            new AvoidDoubleBraceInitializationCheck();
        final int[] expected = {
            TokenTypes.OBJBLOCK,
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
