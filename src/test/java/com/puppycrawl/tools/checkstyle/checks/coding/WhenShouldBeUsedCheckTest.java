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
import static com.puppycrawl.tools.checkstyle.checks.coding.WhenShouldBeUsedCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhenShouldBeUsedCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/whenshouldbeused";
    }

    @Test
    public void testTokensNotNull() {
        final WhenShouldBeUsedCheck check = new WhenShouldBeUsedCheck();
        final int[] expected = {TokenTypes.LITERAL_CASE};

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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhenShouldBeUsedSwitchStatements.java"),
            expected);
    }

    @Test
    public void testSwitchRule() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_KEY),
            "18:13: " + getCheckMessage(MSG_KEY),
            "82:13: " + getCheckMessage(MSG_KEY),
            "96:13: " + getCheckMessage(MSG_KEY),
            "105:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhenShouldBeUsedSwitchRule.java"),
            expected);

    }

    @Test
    public void testSwitchExpression() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhenShouldBeUsedSwitchExpression.java"),
                expected);
    }

    @Test
    public void testNonPatternsSwitch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhenShouldBeUsedNonPatternsSwitch.java"),
                expected);
    }
}
