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
    public void testInputInnerAssignmentCheckLocalVariables() throws Exception {
        final String[] expected = {
            "22:15: " + getCheckMessage(MSG_KEY),
            "22:19: " + getCheckMessage(MSG_KEY),
            "24:39: " + getCheckMessage(MSG_KEY),
            "26:35: " + getCheckMessage(MSG_KEY),
            "73:19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentCheckLocalVariables.java"), expected);
    }

    @Test
    public void testInputInnerAssignmentNoViolationIterativeStatements() throws Exception {
        final String[] expected = {
            "73:22: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentNoViolationIterativeStatements.java"), expected);
    }

    @Test
    public void testInnerAssignmentCheckLoopsAndConditionals() throws Exception {
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
            "25:16: " + getCheckMessage(MSG_KEY),
            "25:27: " + getCheckMessage(MSG_KEY),
            "26:32: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentCheckLoopsAndConditionals.java"), expected);
    }

    @Test
    public void testInputInnerAssignmentLambdaExpressions() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInnerAssignmentLambdaExpressions.java"),
                expected);
    }

    @Test
    public void testInputInnerAssignmentNotInLoopContext() throws Exception {
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

}
