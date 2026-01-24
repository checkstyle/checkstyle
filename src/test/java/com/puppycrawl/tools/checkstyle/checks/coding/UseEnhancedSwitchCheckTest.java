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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UseEnhancedSwitchCheckTest
        extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/useenhancedswitch";
    }

    @Test
    public void testTokensNotNull() {
        final UseEnhancedSwitchCheck check = new UseEnhancedSwitchCheck();
        final int[] expected = {TokenTypes.LITERAL_SWITCH};

        assertWithMessage("Acceptable tokens is not valid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens is not valid")
            .that(check.getDefaultTokens())
            .isEqualTo(expected);
        assertWithMessage("Required tokens is not valid")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testSwitchStatements() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
            "29:9: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
            "52:9: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputUseEnhancedSwitchSwitchStatements.java"),
            expected);
    }

    @Test
    public void testSwitchStatementsValidFallThrough() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
            "21:9: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputUseEnhancedSwitchSwitchStatementsValidFallThrough.java"),
            expected);
    }

    @Test
    public void testSwitchExpressions() throws Exception {
        final String[] expected = {
            "15:17: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
            "33:17: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputUseEnhancedSwitchSwitchExpressions.java"),
            expected);
    }

    @Test
    public void testSwitchExpressionsValidFallThrough() throws Exception {
        final String[] expected = {
            "11:20: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
            "22:21: " + getCheckMessage(UseEnhancedSwitchCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputUseEnhancedSwitchSwitchExpressionsValidFallThrough.java"),
            expected);
    }
}
