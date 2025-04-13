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
import static com.puppycrawl.tools.checkstyle.checks.coding.MissingNullCaseInSwitchCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingNullCaseInSwitchCheckTest extends
        AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/missingnullcaseinswitch";
    }

    @Test
    public void testTokensNotNull() {
        final MissingNullCaseInSwitchCheck check = new MissingNullCaseInSwitchCheck();
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
    public void testMissingNullCaseInSwitchWithPattern() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_KEY),
            "31:9: " + getCheckMessage(MSG_KEY),
            "51:20: " + getCheckMessage(MSG_KEY),
            "68:20: " + getCheckMessage(MSG_KEY),
            "88:17: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingNullCaseInSwitchWithPattern.java"),
            expected);
    }

    @Test
    public void testMissingNullCaseInSwitchWithPattern2() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_KEY),
            "40:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingNullCaseInSwitchWithPattern2.java"),
            expected);
    }

    @Test
    public void testMissingNullCaseInSwitchWithRecordPattern() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_KEY),
            "32:9: " + getCheckMessage(MSG_KEY),
            "52:20: " + getCheckMessage(MSG_KEY),
            "69:20: " + getCheckMessage(MSG_KEY),
            "90:17: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingNullCaseInSwitchWithRecordPattern.java"),
            expected);
    }

    @Test
    public void testMissingNullCaseInSwitchWithStringLiterals() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_KEY),
            "23:9: " + getCheckMessage(MSG_KEY),
            "31:9: " + getCheckMessage(MSG_KEY),
            "51:20: " + getCheckMessage(MSG_KEY),
            "68:20: " + getCheckMessage(MSG_KEY),
            "89:17: " + getCheckMessage(MSG_KEY),
            "98:17: " + getCheckMessage(MSG_KEY),
            "103:17: " + getCheckMessage(MSG_KEY),
            "108:17: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingNullCaseInSwitchWithStringLiterals.java"),
            expected);
    }

    @Test
    public void testMissingNullCaseInSwitchWithPrimitives() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingNullCaseInSwitchWithPrimitives.java"),
            expected);
    }
}

