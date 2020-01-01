////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_ASSIGN;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_EXPR;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_IDENT;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LAMBDA;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LITERAL;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_RETURN;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_STRING;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

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
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);

        final String[] expected = {
            "5:22: " + getCheckMessage(MSG_ASSIGN),
            "5:29: " + getCheckMessage(MSG_EXPR),
            "5:31: " + getCheckMessage(MSG_IDENT, "i"),
            "5:46: " + getCheckMessage(MSG_ASSIGN),
            "6:15: " + getCheckMessage(MSG_ASSIGN),
            "7:14: " + getCheckMessage(MSG_IDENT, "x"),
            "7:17: " + getCheckMessage(MSG_ASSIGN),
            "8:15: " + getCheckMessage(MSG_ASSIGN),
            "9:14: " + getCheckMessage(MSG_IDENT, "x"),
            "9:17: " + getCheckMessage(MSG_ASSIGN),
            "12:22: " + getCheckMessage(MSG_ASSIGN),
            "12:30: " + getCheckMessage(MSG_IDENT, "i"),
            "12:46: " + getCheckMessage(MSG_ASSIGN),
            "16:17: " + getCheckMessage(MSG_LITERAL, "0"),
            "26:11: " + getCheckMessage(MSG_ASSIGN),
            "30:11: " + getCheckMessage(MSG_ASSIGN),
            "32:11: " + getCheckMessage(MSG_ASSIGN),
            "34:11: " + getCheckMessage(MSG_ASSIGN),
            "35:16: " + getCheckMessage(MSG_IDENT, "a"),
            "36:14: " + getCheckMessage(MSG_IDENT, "a"),
            "36:20: " + getCheckMessage(MSG_IDENT, "b"),
            "36:26: " + getCheckMessage(MSG_LITERAL, "600"),
            "36:40: " + getCheckMessage(MSG_LITERAL, "12.5f"),
            "36:56: " + getCheckMessage(MSG_IDENT, "arg2"),
            "37:14: " + getCheckMessage(MSG_STRING, "\"this\""),
            "37:25: " + getCheckMessage(MSG_STRING, "\"that\""),
            "38:11: " + getCheckMessage(MSG_ASSIGN),
            "38:14: " + getCheckMessage(MSG_STRING, "\"this is a really, really...\""),
            "40:16: " + getCheckMessage(MSG_RETURN),
            "44:21: " + getCheckMessage(MSG_LITERAL, "1"),
            "44:26: " + getCheckMessage(MSG_LITERAL, "13.5"),
            "45:22: " + getCheckMessage(MSG_LITERAL, "true"),
            "46:17: " + getCheckMessage(MSG_IDENT, "b"),
            "50:17: " + getCheckMessage(MSG_ASSIGN),
            "52:11: " + getCheckMessage(MSG_ASSIGN),
            "54:16: " + getCheckMessage(MSG_RETURN),
            "64:13: " + getCheckMessage(MSG_EXPR),
            "68:16: " + getCheckMessage(MSG_EXPR),
            "73:19: " + getCheckMessage(MSG_EXPR),
            "74:23: " + getCheckMessage(MSG_LITERAL, "4000"),
            "79:19: " + getCheckMessage(MSG_ASSIGN),
            "81:11: " + getCheckMessage(MSG_ASSIGN),
            "81:16: " + getCheckMessage(MSG_LITERAL, "3"),
            "82:39: " + getCheckMessage(MSG_ASSIGN),
            "93:11: " + getCheckMessage(MSG_ASSIGN),
            "93:14: " + getCheckMessage(MSG_STRING, "\"12345678901234567890123\""),
        };

        verify(checkConfig, getPath("InputUnnecessaryParenthesesOperatorsAndCasts.java"), expected);
    }

    @Test
    public void test15Extensions() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);
        final String[] expected = {
            "15:23: " + getCheckMessage(MSG_EXPR),
            "15:51: " + getCheckMessage(MSG_LITERAL, "1"),
        };
        verify(checkConfig, getPath("InputUnnecessaryParentheses15Extensions.java"), expected);
    }

    @Test
    public void testLambdas() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessaryParenthesesCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "10:35: " + getCheckMessage(MSG_LAMBDA),
            "11:35: " + getCheckMessage(MSG_LAMBDA),
            "18:18: " + getCheckMessage(MSG_LAMBDA),
            "19:57: " + getCheckMessage(MSG_LAMBDA),
            "38:25: " + getCheckMessage(MSG_LAMBDA),
            "38:33: " + getCheckMessage(MSG_LAMBDA),
            "41:25: " + getCheckMessage(MSG_LAMBDA),
            "44:31: " + getCheckMessage(MSG_LAMBDA),
        };
        verify(checkConfig, getPath("InputUnnecessaryParenthesesLambdas.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final UnnecessaryParenthesesCheck check = new UnnecessaryParenthesesCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
