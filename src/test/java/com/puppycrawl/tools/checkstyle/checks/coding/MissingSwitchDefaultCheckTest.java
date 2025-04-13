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
import static com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingSwitchDefaultCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/missingswitchdefault";
    }

    @Test
    public void testMissingSwitchDefault() throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, "default"),
            "35:17: " + getCheckMessage(MSG_KEY, "default"),
            "46:17: " + getCheckMessage(MSG_KEY, "default"),
            "53:9: " + getCheckMessage(MSG_KEY, "default"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingSwitchDefault.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final MissingSwitchDefaultCheck check = new MissingSwitchDefaultCheck();
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
    public void testMissingSwitchDefaultSwitchExpressions() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "default"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultCheckSwitchExpressions.java"),
            expected);
    }

    @Test
    public void testNullCaseLabel() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultCheckNullCaseLabel.java"),
            expected);
    }

    @Test
    public void testMissingSwitchDefaultSwitchExpressionsTwo() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "default"),
            "26:9: " + getCheckMessage(MSG_KEY, "default"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultCheckSwitchExpressionsTwo.java"),
                expected);
    }

    @Test
    public void testMissingSwitchDefaultSwitchExpressionsThree() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultCheckSwitchExpressionsThree.java"),
                expected);
    }

    @Test
    public void testMissingSwitchDefaultCaseLabelElements() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultCaseLabelElements.java"),
                expected);
    }

    @Test
    public void testMissingSwitchDefaultRecordPattern() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingSwitchDefaultRecordPattern.java"),
                expected);
    }

    @Test
    public void testMissingSwitchDefaultWithSwitchExpressionUnderMethodCall() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputMissingSwitchDefaultCheckSwitchExpressionUnderMethodCall.java"),
                expected);
    }

}
