///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedCatchParameterShouldBeUnnamedCheck.MSG_UNUSED_CATCH_PARAMETER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnusedCatchParameterShouldBeUnnamedCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedcatchparametershouldbeunnamed";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedCatchParameterShouldBeUnnamedCheck checkObj =
                            new UnusedCatchParameterShouldBeUnnamedCheck();
        final int[] expected = {
            TokenTypes.LITERAL_CATCH,
            TokenTypes.IDENT,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testUnusedCatchParameterShouldBeUnnamed() throws Exception {
        final String[] expected = {
            "17:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "24:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "31:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "52:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "65:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "A"),
            "72:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "A"),
            "79:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "103:16: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedCatchParameterShouldBeUnnamed.java"),
                expected);
    }

    @Test
    public void testUnusedCatchParameterShouldBeUnnamedWithResourceAndFinally() throws Exception {
        final String[] expected = {
            "19:18: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "40:18: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "61:18: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnusedCatchParameterShouldBeUnnamedWithResourceAndFinally.java"),
                expected);
    }

    @Test
    public void testUnusedCatchParameterShouldBeUnnamedNested() throws Exception {
        final String[] expected = {
            "15:18: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
            "18:22: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "ex"),
            "31:22: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "ex"),
            "42:18: " + getCheckMessage(MSG_UNUSED_CATCH_PARAMETER, "e"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnusedCatchParameterShouldBeUnnamedNested.java"),
                expected);
    }


}
