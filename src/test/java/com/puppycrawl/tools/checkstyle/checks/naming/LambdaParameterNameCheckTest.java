///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

class LambdaParameterNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/lambdaparametername";
    }

    @Test
    void getRequiredTokens() {
        final int[] expected = {
            TokenTypes.LAMBDA,
        };

        final LambdaParameterNameCheck check = new LambdaParameterNameCheck();
        final int[] requiredTokens = check.getRequiredTokens();
        assertWithMessage("Invalid required tokens")
            .that(requiredTokens)
            .isEqualTo(expected);
    }

    @Test
    void acceptableTokens() {
        final int[] expected = {
            TokenTypes.LAMBDA,
        };

        final LambdaParameterNameCheck check = new LambdaParameterNameCheck();
        final int[] acceptableTokens = check.getAcceptableTokens();
        assertWithMessage("Invalid acceptable tokens")
            .that(acceptableTokens)
            .isEqualTo(expected);
    }

    @Test
    void parametersInLambda() throws Exception {

        final String pattern = "^(id)|([a-z][a-z0-9][a-zA-Z0-9]+)$";

        final String[] expected = {
            "15:68: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "18:66: " + getCheckMessage(MSG_INVALID_PATTERN, "st", pattern),
            "21:65: " + getCheckMessage(MSG_INVALID_PATTERN, "s1", pattern),
            "22:65: " + getCheckMessage(MSG_INVALID_PATTERN, "s2", pattern),
            "26:21: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLambdaParameterName.java"), expected);
    }

    @Test
    void lambdaParameterNameSwitchExpression() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "19:35: " + getCheckMessage(MSG_INVALID_PATTERN, "Word", pattern),
            "31:35: " + getCheckMessage(MSG_INVALID_PATTERN, "Word", pattern),
            "36:31: " + getCheckMessage(MSG_INVALID_PATTERN, "Word", pattern),
            "46:35: " + getCheckMessage(MSG_INVALID_PATTERN, "Word", pattern),
            "57:35: " + getCheckMessage(MSG_INVALID_PATTERN, "Word", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLambdaParameterNameSwitchExpression.java"),
            expected);
    }

    @Test
    void lambdaParameterNameUnnamed() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "30:36: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "34:36: " + getCheckMessage(MSG_INVALID_PATTERN, "_BAD", pattern),
            "37:36: " + getCheckMessage(MSG_INVALID_PATTERN, "BAD_", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLambdaParameterNameUnnamed.java"),
            expected);
    }

}
