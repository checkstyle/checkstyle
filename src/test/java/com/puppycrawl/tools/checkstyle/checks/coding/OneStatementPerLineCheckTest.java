///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneStatementPerLineCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline";
    }

    @Test
    public void testMultiCaseClass() throws Exception {
        final String[] expected = {
            "13:59: " + getCheckMessage(MSG_KEY),
            "93:21: " + getCheckMessage(MSG_KEY),
            "120:14: " + getCheckMessage(MSG_KEY),
            "146:15: " + getCheckMessage(MSG_KEY),
            "158:23: " + getCheckMessage(MSG_KEY),
            "178:19: " + getCheckMessage(MSG_KEY),
            "181:59: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLine.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
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
    public void testWithMultilineStatements() throws Exception {
        final String[] expected = {
            "49:21: " + getCheckMessage(MSG_KEY),
            "66:17: " + getCheckMessage(MSG_KEY),
            "74:17: " + getCheckMessage(MSG_KEY),
            "86:10: " + getCheckMessage(MSG_KEY),
            "95:28: " + getCheckMessage(MSG_KEY),
            "140:39: " + getCheckMessage(MSG_KEY),
            "173:46: " + getCheckMessage(MSG_KEY),
            "184:47: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultiline.java"),
            expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final String[] expected = {
            "39:4: " + getCheckMessage(MSG_KEY),
            "44:54: " + getCheckMessage(MSG_KEY),
            "45:54: " + getCheckMessage(MSG_KEY),
            "45:70: " + getCheckMessage(MSG_KEY),
            "46:46: " + getCheckMessage(MSG_KEY),
            "50:81: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

    @Test
    public void testResourceReferenceVariableIgnored() throws Exception {
        final String[] expected = {
            "32:42: " + getCheckMessage(MSG_KEY),
            "36:43: " + getCheckMessage(MSG_KEY),
            "42:46: " + getCheckMessage(MSG_KEY),
            "46:46: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResources.java"),
                expected);
    }

    @Test
    public void testResourcesIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResourcesIgnore.java"),
                expected);
    }

}
