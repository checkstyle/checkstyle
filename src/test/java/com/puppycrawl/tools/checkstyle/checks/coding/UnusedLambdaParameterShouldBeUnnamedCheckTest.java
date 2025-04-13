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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLambdaParameterShouldBeUnnamedCheck.MSG_UNUSED_LAMBDA_PARAMETER;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class UnusedLambdaParameterShouldBeUnnamedCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedlambdaparametershouldbeunnamed";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedLambdaParameterShouldBeUnnamedCheck checkObj =
                            new UnusedLambdaParameterShouldBeUnnamedCheck();
        final int[] expected = {
            TokenTypes.LAMBDA,
            TokenTypes.IDENT,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testSingleLambdaParameter() throws Exception {
        final String[] expected = {
            "18:45: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "character"),
            "23:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "character"),
            "26:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "character"),
            "31:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "character"),
            "34:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "Character"),
            "40:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "character"),
            "45:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "C"),
            "51:45: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "s"),
            "54:32: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "C"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnusedLambdaParameterShouldBeUnnamedSingleLambdaParameter.java"),
                expected);
    }

    @Test
    public void testMultipleLambdaParameters() throws Exception {
        final String[] expected = {
            "18:56: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "18:59: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "26:24: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "29:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "33:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "33:24: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "39:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "X"),
            "45:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "45:24: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "53:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "X"),
            "83:21: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "90:24: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnusedLambdaParameterShouldBeUnnamedMultipleParameters.java"),
                expected);
    }

    @Test
    public void testNested() throws Exception {
        final String[] expected = {
            "17:56: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
            "17:59: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "21:51: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "29:51: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "45:51: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "53:24: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "55:61: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "64:61: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "64:64: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "w"),
            "75:64: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "w"),
            "85:73: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "y"),
            "87:60: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "z"),
            "94:28: " + getCheckMessage(MSG_UNUSED_LAMBDA_PARAMETER, "x"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputUnusedLambdaParameterShouldBeUnnamedNested.java"),
                expected);
    }

    @Test
    public void testClearState() throws Exception {
        final UnusedLambdaParameterShouldBeUnnamedCheck check =
                new UnusedLambdaParameterShouldBeUnnamedCheck();

        final DetailAST root = JavaParser.parseFile(
                new File(getNonCompilablePath(
                        "InputUnusedLambdaParameterShouldBeUnnamedSingleLambdaParameter.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> literalCatch = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.LAMBDA);

        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalCatch.orElseThrow(),
                        "lambdaParameters",
                        lambdaParameters -> {
                            return ((Collection<?>) lambdaParameters).isEmpty();
                        }))
                .isTrue();
    }
}
