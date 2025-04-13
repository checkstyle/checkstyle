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
import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck.MSG_MULTIPLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class MultipleVariableDeclarationsCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/multiplevariabledeclarations";
    }

    @Test
    public void testIt() throws Exception {

        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "12:5: " + getCheckMessage(MSG_MULTIPLE),
            "15:9: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "16:9: " + getCheckMessage(MSG_MULTIPLE),
            "20:5: " + getCheckMessage(MSG_MULTIPLE),
            "23:5: " + getCheckMessage(MSG_MULTIPLE),
            "42:9: " + getCheckMessage(MSG_MULTIPLE),
            "42:31: " + getCheckMessage(MSG_MULTIPLE),
            "42:44: " + getCheckMessage(MSG_MULTIPLE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleVariableDeclarations.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final MultipleVariableDeclarationsCheck check = new MultipleVariableDeclarationsCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_MULTIPLE),
            "14:5: " + getCheckMessage(MSG_MULTIPLE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleVariableDeclarations2.java"),
               expected);
    }

    @Test
    public void testUnnamedVariables() throws Exception {

        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "14:9: " + getCheckMessage(MSG_MULTIPLE_COMMA),
            "16:9: " + getCheckMessage(MSG_MULTIPLE),
            "18:9: " + getCheckMessage(MSG_MULTIPLE),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMultipleVariableDeclarationsUnnamedVariables.java"),
               expected);
    }

}
