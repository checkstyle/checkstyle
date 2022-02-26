////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_ASSIGN;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_EXPR;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_IDENT;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LAMBDA;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LITERAL;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_RETURN;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_STRING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Test fixture for the UnnecessaryParenthesesCheck.
 *
 */
public class UnnecessaryParenthesesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessaryparentheses";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "18:22: " + getCheckMessage(MSG_ASSIGN),
            "18:29: " + getCheckMessage(MSG_EXPR),
            "18:31: " + getCheckMessage(MSG_IDENT, "i"),
            "18:46: " + getCheckMessage(MSG_ASSIGN),
            "19:15: " + getCheckMessage(MSG_ASSIGN),
            "20:14: " + getCheckMessage(MSG_IDENT, "x"),
            "20:17: " + getCheckMessage(MSG_ASSIGN),
            "21:15: " + getCheckMessage(MSG_ASSIGN),
            "22:14: " + getCheckMessage(MSG_IDENT, "x"),
            "22:17: " + getCheckMessage(MSG_ASSIGN),
            "25:22: " + getCheckMessage(MSG_ASSIGN),
            "25:30: " + getCheckMessage(MSG_IDENT, "i"),
            "25:46: " + getCheckMessage(MSG_ASSIGN),
            "29:17: " + getCheckMessage(MSG_LITERAL, "0"),
            "39:11: " + getCheckMessage(MSG_ASSIGN),
            "43:11: " + getCheckMessage(MSG_ASSIGN),
            "45:11: " + getCheckMessage(MSG_ASSIGN),
            "47:11: " + getCheckMessage(MSG_ASSIGN),
            "48:16: " + getCheckMessage(MSG_IDENT, "a"),
            "49:14: " + getCheckMessage(MSG_IDENT, "a"),
            "49:20: " + getCheckMessage(MSG_IDENT, "b"),
            "49:26: " + getCheckMessage(MSG_LITERAL, "600"),
            "49:40: " + getCheckMessage(MSG_LITERAL, "12.5f"),
            "49:56: " + getCheckMessage(MSG_IDENT, "arg2"),
            "50:14: " + getCheckMessage(MSG_STRING, "\"this\""),
            "50:25: " + getCheckMessage(MSG_STRING, "\"that\""),
            "51:11: " + getCheckMessage(MSG_ASSIGN),
            "51:14: " + getCheckMessage(MSG_STRING, "\"this is a really, really...\""),
            "53:16: " + getCheckMessage(MSG_RETURN),
            "57:21: " + getCheckMessage(MSG_LITERAL, "1"),
            "57:26: " + getCheckMessage(MSG_LITERAL, "13.5"),
            "58:22: " + getCheckMessage(MSG_LITERAL, "true"),
            "59:17: " + getCheckMessage(MSG_IDENT, "b"),
            "63:17: " + getCheckMessage(MSG_ASSIGN),
            "65:11: " + getCheckMessage(MSG_ASSIGN),
            "67:16: " + getCheckMessage(MSG_RETURN),
            "77:13: " + getCheckMessage(MSG_EXPR),
            "81:16: " + getCheckMessage(MSG_EXPR),
            "86:19: " + getCheckMessage(MSG_EXPR),
            "87:23: " + getCheckMessage(MSG_LITERAL, "4000"),
            "92:19: " + getCheckMessage(MSG_ASSIGN),
            "94:11: " + getCheckMessage(MSG_ASSIGN),
            "94:16: " + getCheckMessage(MSG_LITERAL, "3"),
            "95:26: " + getCheckMessage(MSG_ASSIGN),
            "106:11: " + getCheckMessage(MSG_ASSIGN),
            "106:14: " + getCheckMessage(MSG_STRING, "\"12345678901234567890123\""),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryParenthesesOperatorsAndCasts.java"), expected);
    }

    @Test
    public void test15Extensions() throws Exception {
        final String[] expected = {
            "28:23: " + getCheckMessage(MSG_EXPR),
            "28:51: " + getCheckMessage(MSG_LITERAL, "1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryParentheses15Extensions.java"), expected);
    }

    @Test
    public void testLambdas() throws Exception {
        final String[] expected = {
            "17:35: " + getCheckMessage(MSG_LAMBDA),
            "18:35: " + getCheckMessage(MSG_LAMBDA),
            "25:18: " + getCheckMessage(MSG_LAMBDA),
            "28:25: " + getCheckMessage(MSG_LAMBDA),
            "47:25: " + getCheckMessage(MSG_LAMBDA),
            "47:33: " + getCheckMessage(MSG_LAMBDA),
            "50:25: " + getCheckMessage(MSG_LAMBDA),
            "53:31: " + getCheckMessage(MSG_LAMBDA),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryParenthesesLambdas.java"), expected);
    }

    @Test
    public void testUnnecessaryParenthesesSwitchExpression() throws Exception {
        final String[] expected = {
            "21:31: " + getCheckMessage(MSG_ASSIGN),
            "24:13: " + getCheckMessage(MSG_LITERAL, 2),
            "25:39: " + getCheckMessage(MSG_ASSIGN),
            "30:18: " + getCheckMessage(MSG_ASSIGN),
            "32:16: " + getCheckMessage(MSG_IDENT, "g"),
            "36:18: " + getCheckMessage(MSG_ASSIGN),
            "46:31: " + getCheckMessage(MSG_ASSIGN),
            "48:13: " + getCheckMessage(MSG_LITERAL, 2),
            "49:39: " + getCheckMessage(MSG_ASSIGN),
            "53:18: " + getCheckMessage(MSG_ASSIGN),
            "58:18: " + getCheckMessage(MSG_ASSIGN),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnnecessaryParenthesesCheckSwitchExpression.java"),
                expected);
    }

    @Test
    public void testUnnecessaryParenthesesTextBlocks() throws Exception {
        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_STRING, "\"this\""),
            "19:34: " + getCheckMessage(MSG_STRING, "\"that\""),
            "19:45: " + getCheckMessage(MSG_STRING, "\"other\""),
            "20:23: " + getCheckMessage(MSG_STRING, "\"\\n     "
                + "   this\""),
            "22:12: " + getCheckMessage(MSG_STRING, "\"\\n     "
                + "   that\""),
            "24:12: " + getCheckMessage(MSG_STRING, "\"\\n     "
                + "   other\""),
            "27:23: " + getCheckMessage(MSG_STRING, "\"\\n        this is a test...\""),
            "28:32: " + getCheckMessage(MSG_STRING, "\"\\n        and another li...\""),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                "InputUnnecessaryParenthesesCheckTextBlocks.java"),
            expected);
    }

    @Test
    public void testUnnecessaryParenthesesPatterns() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                "InputUnnecessaryParenthesesCheckPatterns.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final UnnecessaryParenthesesCheck check = new UnnecessaryParenthesesCheck();
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
    public void testIfStatement() throws Exception {

        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_EXPR),
            "34:13: " + getCheckMessage(MSG_EXPR),
            "35:20: " + getCheckMessage(MSG_EXPR),
            "39:13: " + getCheckMessage(MSG_EXPR),
            "39:14: " + getCheckMessage(MSG_EXPR),
            "40:20: " + getCheckMessage(MSG_EXPR),
            "45:20: " + getCheckMessage(MSG_EXPR),
            "49:13: " + getCheckMessage(MSG_EXPR),
            "50:20: " + getCheckMessage(MSG_EXPR),
            "54:13: " + getCheckMessage(MSG_EXPR),
            "55:17: " + getCheckMessage(MSG_EXPR),
            "56:28: " + getCheckMessage(MSG_EXPR),
            "61:13: " + getCheckMessage(MSG_EXPR),
            "66:14: " + getCheckMessage(MSG_EXPR),
            "67:24: " + getCheckMessage(MSG_EXPR),
            "70:13: " + getCheckMessage(MSG_EXPR),
            "71:21: " + getCheckMessage(MSG_EXPR),
            "72:21: " + getCheckMessage(MSG_EXPR),
            "78:12: " + getCheckMessage(MSG_EXPR),
            "79:20: " + getCheckMessage(MSG_EXPR),
            "86:20: " + getCheckMessage(MSG_EXPR),
            "103:13: " + getCheckMessage(MSG_EXPR),
            "106:13: " + getCheckMessage(MSG_EXPR),
            "107:21: " + getCheckMessage(MSG_EXPR),
            "110:13: " + getCheckMessage(MSG_EXPR),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryParenthesesIfStatement.java"), expected);
    }

    @Test
    public void testIfStatement2() throws Exception {
        final String[] expected = {
            "28:17: " + getCheckMessage(MSG_EXPR),
            "39:17: " + getCheckMessage(MSG_EXPR),
            "51:25: " + getCheckMessage(MSG_EXPR),
            "57:13: " + getCheckMessage(MSG_EXPR),
            "59:28: " + getCheckMessage(MSG_EXPR),
            "60:28: " + getCheckMessage(MSG_EXPR),
            "61:20: " + getCheckMessage(MSG_EXPR),
            "63:20: " + getCheckMessage(MSG_EXPR),
            "74:20: " + getCheckMessage(MSG_EXPR),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryParenthesesIfStatement2.java"), expected);
    }

}
