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
import static com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InnerAssignmentCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/innerassignment";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "22:15: " + getCheckMessage(MSG_KEY),
            "22:19: " + getCheckMessage(MSG_KEY),
            "27:39: " + getCheckMessage(MSG_KEY),
            "29:35: " + getCheckMessage(MSG_KEY),
            "76:19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignment.java"), expected);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expected = {
            "73:22: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentMethod.java"), expected);
    }

    @Test
    public void testDemoBug1195047Comment3() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_KEY),
            "19:24: " + getCheckMessage(MSG_KEY),
            "20:19: " + getCheckMessage(MSG_KEY),
            "21:17: " + getCheckMessage(MSG_KEY),
            "22:29: " + getCheckMessage(MSG_KEY),
            "23:20: " + getCheckMessage(MSG_KEY),
            "24:17: " + getCheckMessage(MSG_KEY),
            "24:31: " + getCheckMessage(MSG_KEY),
            "24:41: " + getCheckMessage(MSG_KEY),
            "29:16: " + getCheckMessage(MSG_KEY),
            "29:27: " + getCheckMessage(MSG_KEY),
            "33:32: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentDemoBug1195047Comment3.java"), expected);
    }

    @Test
    public void testLambdaExpression() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentLambdaExpressions.java"),
                expected);
    }

    @Test
    public void testInnerAssignmentNotInLoopContext() throws Exception {
        final String[] expected = {
            "12:28: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentNotInLoopContext.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final InnerAssignmentCheck check = new InnerAssignmentCheck();
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
    public void testInnerAssignmentSwitchAndSwitchExpression() throws Exception {
        final String[] expected = {
            "28:23: " + getCheckMessage(MSG_KEY),
            "38:25: " + getCheckMessage(MSG_KEY),
            "40:25: " + getCheckMessage(MSG_KEY),
            "41:26: " + getCheckMessage(MSG_KEY),
            "49:25: " + getCheckMessage(MSG_KEY),
            "51:31: " + getCheckMessage(MSG_KEY),
            "52:26: " + getCheckMessage(MSG_KEY),
            "59:42: " + getCheckMessage(MSG_KEY),
            "61:34: " + getCheckMessage(MSG_KEY),
            "94:25: " + getCheckMessage(MSG_KEY),
            "96:26: " + getCheckMessage(MSG_KEY),
            "98:27: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputInnerAssignmentSwitchAndSwitchExpression.java"),
                expected);
    }
}
