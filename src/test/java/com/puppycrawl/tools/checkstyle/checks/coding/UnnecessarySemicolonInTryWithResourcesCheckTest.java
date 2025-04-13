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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInTryWithResourcesCheck.MSG_SEMI;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnnecessarySemicolonInTryWithResourcesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/"
            + "unnecessarysemicolonintrywithresources";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "17:42: " + getCheckMessage(MSG_SEMI),
            "19:72: " + getCheckMessage(MSG_SEMI),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessarySemicolonInTryWithResourcesDefault.java"),
            expected);
    }

    @Test
    public void testNoBraceAfterAllowed() throws Exception {
        final String[] expected = {
            "16:42: " + getCheckMessage(MSG_SEMI),
            "19:13: " + getCheckMessage(MSG_SEMI),
            "22:36: " + getCheckMessage(MSG_SEMI),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessarySemicolonInTryWithResourcesNoBraceAfterAllowed.java"),
            expected);
    }

    @Test
    public void testTokensAreCorrect() {
        final UnnecessarySemicolonInTryWithResourcesCheck check =
            new UnnecessarySemicolonInTryWithResourcesCheck();
        final int[] expected = {
            TokenTypes.RESOURCE_SPECIFICATION,
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
