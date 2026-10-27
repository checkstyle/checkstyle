///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.util.Deque;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class BooleanExpressionComplexityCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/booleanexpressioncomplexity";
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "39:46: " + getCheckMessage(MSG_KEY, 4, 3),
            "50:9: " + getCheckMessage(MSG_KEY, 6, 3),
            "57:34: " + getCheckMessage(MSG_KEY, 4, 3),
            "60:34: " + getCheckMessage(MSG_KEY, 4, 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexity.java"), expected);
    }

    @Test
    public void testNoBitwise() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexity2.java"), expected);
    }

    @Test
    public void testNullPointerException() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityNPE.java"), expected);
    }

    @Test
    public void testWrongToken() {
        final BooleanExpressionComplexityCheck booleanExpressionComplexityCheckObj =
            new BooleanExpressionComplexityCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.INTERFACE_DEF, "interface"));
        final IllegalArgumentException exc =
                getExpectedThrowable(IllegalArgumentException.class,
                        () -> booleanExpressionComplexityCheckObj.visitToken(ast));
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("Unknown type: interface[0x-1]");
    }

    @Test
    public void testSmall() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexitySmall.java"), expected);
    }

    @Test
    public void testBooleanExpressionComplexityRecordsAndCompactCtors() throws Exception {

        final int max = 3;

        final String[] expected = {
            "16:12: " + getCheckMessage(MSG_KEY, 4, max),
            "25:23: " + getCheckMessage(MSG_KEY, 4, max),
            "37:23: " + getCheckMessage(MSG_KEY, 4, max),
            "48:27: " + getCheckMessage(MSG_KEY, 4, max),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputBooleanExpressionComplexityRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testLeaves() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityLeaves.java"), expected);
    }

    @Test
    public void testRecordLeaves() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityRecordLeaves.java"),
                expected);
    }

    @Test
    public void testWhenExpression() throws Exception {

        final int max = 0;

        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_KEY, 6, max),
            "21:17: " + getCheckMessage(MSG_KEY, 6, max),
            "25:27: " + getCheckMessage(MSG_KEY, 6, max),
            "29:48: " + getCheckMessage(MSG_KEY, 1, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputBooleanExpressionComplexityWhenExpression.java"),
                expected);
    }

    @Test
    public void testClearState() throws Exception {
        final BooleanExpressionComplexityCheck check = new BooleanExpressionComplexityCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputBooleanExpressionComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final DetailAST method = TestUtil.findTokenInAstByPredicate(root, ast -> {
            return ast.getType() == TokenTypes.METHOD_DEF;
        }).orElseThrow();
        final DetailAST operator = TestUtil.findTokenInAstByPredicate(method, ast -> {
            return ast.getType() == TokenTypes.LAND;
        }).orElseThrow();

        check.visitToken(method);
        check.visitToken(operator);
        final Object context = TestUtil.getInternalState(check, "context", Object.class);
        assertWithMessage("Context should be checking before beginTree")
                .that(TestUtil.getInternalState(context, "checking", Boolean.class))
                .isTrue();
        assertWithMessage("Context should count the visited operator")
                .that(TestUtil.getInternalState(context, "count", Integer.class))
                .isEqualTo(1);

        check.beginTree(root);

        assertWithMessage("Context stack is not cleared on beginTree")
                .that(TestUtil.getInternalState(check, "contextStack", Deque.class))
                .isEmpty();
        final Object resetContext = TestUtil.getInternalState(check, "context", Object.class);
        assertWithMessage("Context checking flag is not reset on beginTree")
                .that(TestUtil.getInternalState(resetContext, "checking", Boolean.class))
                .isFalse();
        assertWithMessage("Context count is not reset on beginTree")
                .that(TestUtil.getInternalState(resetContext, "count", Integer.class))
                .isEqualTo(0);
    }

}
