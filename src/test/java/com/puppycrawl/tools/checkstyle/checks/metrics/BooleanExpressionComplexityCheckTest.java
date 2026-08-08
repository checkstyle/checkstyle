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

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
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
            "22:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "40:46: " + getCheckMessage(MSG_KEY, 4, 3),
            "51:9: " + getCheckMessage(MSG_KEY, 6, 3),
            "58:34: " + getCheckMessage(MSG_KEY, 4, 3),
            "61:34: " + getCheckMessage(MSG_KEY, 4, 3),
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
            "17:12: " + getCheckMessage(MSG_KEY, 4, max),
            "26:23: " + getCheckMessage(MSG_KEY, 4, max),
            "38:23: " + getCheckMessage(MSG_KEY, 4, max),
            "49:27: " + getCheckMessage(MSG_KEY, 4, max),
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
    public void testComplexityUniformChain() throws Exception {

        final int max = 1;

        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY, 2, max),
            "37:9: " + getCheckMessage(MSG_KEY, 2, max),
            "42:9: " + getCheckMessage(MSG_KEY, 2, max),
            "48:9: " + getCheckMessage(MSG_KEY, 3, max),
            "54:9: " + getCheckMessage(MSG_KEY, 4, max),
            "59:9: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityUniformChain.java"), expected);
    }

    @Test
    public void testUniformChainOperators() throws Exception {

        final int max = 1;

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, 2, max),
            "27:9: " + getCheckMessage(MSG_KEY, 2, max),
            "36:9: " + getCheckMessage(MSG_KEY, 2, max),
            "41:9: " + getCheckMessage(MSG_KEY, 2, max),
            "46:9: " + getCheckMessage(MSG_KEY, 2, max),
            "55:9: " + getCheckMessage(MSG_KEY, 2, max),
            "64:9: " + getCheckMessage(MSG_KEY, 2, max),
            "73:9: " + getCheckMessage(MSG_KEY, 2, max),
            "82:9: " + getCheckMessage(MSG_KEY, 2, max),
            "87:9: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityUniformChainOperators.java"), expected);
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
    public void testExcludedNodeNesting() throws Exception {
        final int max = 0;
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY, 1, max),
        };
        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityExcludedNodeNesting.java"),
                expected);
    }

    @Test
    public void testUniformChainCastTarget() throws Exception {
        final int max = 1;
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, 2, max),
        };
        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityUniformChainCastTarget.java"), expected);
    }

    @Test
    public void testUniformChainMissingRightOperandDoesNotThrow() throws Exception {
        final DetailAstImpl land = new DetailAstImpl();
        land.initialize(new CommonToken(TokenTypes.LAND, "&&"));
        final DetailAstImpl operand = new DetailAstImpl();
        operand.initialize(new CommonToken(TokenTypes.IDENT, "a"));
        land.addChild(operand);
        final boolean result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "isUniformChain",
                Boolean.class, land);
        assertWithMessage("isUniformChain should safely return false for a boolean "
                + "operator node missing its right operand, not throw")
                .that(result)
                .isFalse();
    }

    @Test
    public void testUniformChainMissingRightOperandWithValidLeftChain() throws Exception {
        final DetailAstImpl outerLand = new DetailAstImpl();
        outerLand.initialize(new CommonToken(TokenTypes.LAND, "&&"));
        final DetailAstImpl innerLand = new DetailAstImpl();
        innerLand.initialize(new CommonToken(TokenTypes.LAND, "&&"));
        final DetailAstImpl leafA = new DetailAstImpl();
        leafA.initialize(new CommonToken(TokenTypes.IDENT, "a"));
        final DetailAstImpl leafB = new DetailAstImpl();
        leafB.initialize(new CommonToken(TokenTypes.IDENT, "b"));
        innerLand.addChild(leafA);
        innerLand.addChild(leafB);
        outerLand.addChild(innerLand);
        final boolean result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "isUniformChain",
                Boolean.class, outerLand);
        assertWithMessage("isUniformChain should be false when the right operand is "
                + "missing, even though the left operand alone forms a valid chain")
                .that(result)
                .isFalse();
    }

    @Test
    public void testLeafKeyMissingLeftHandSideDoesNotThrow() throws Exception {
        final DetailAstImpl equalNode = new DetailAstImpl();
        equalNode.initialize(new CommonToken(TokenTypes.EQUAL, "=="));
        final String result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "leafKey",
                String.class, equalNode);
        assertWithMessage("leafKey should safely return null for a relational node "
                + "missing its left-hand side, not throw")
                .that(result)
                .isNull();
    }

    @Test
    public void testCanonicalTextMissingDotOperandsDoesNotThrow() throws Exception {
        final DetailAstImpl dotNode = new DetailAstImpl();
        dotNode.initialize(new CommonToken(TokenTypes.DOT, "."));
        final String result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "canonicalText",
                String.class, dotNode);
        assertWithMessage("canonicalText should safely return null for a DOT node "
                + "missing both operands, not throw")
                .that(result)
                .isNull();
    }

    @Test
    public void testCanonicalTextDotLeftTextNullOnly() throws Exception {
        final DetailAstImpl dotNode = new DetailAstImpl();
        dotNode.initialize(new CommonToken(TokenTypes.DOT, "."));
        final DetailAstImpl unrecognizedLeft = new DetailAstImpl();
        unrecognizedLeft.initialize(new CommonToken(TokenTypes.PLUS, "+"));
        final DetailAstImpl rightIdent = new DetailAstImpl();
        rightIdent.initialize(new CommonToken(TokenTypes.IDENT, "field"));
        dotNode.addChild(unrecognizedLeft);
        dotNode.addChild(rightIdent);
        final String result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "canonicalText",
                String.class, dotNode);
        assertWithMessage("canonicalText should return null when the left operand's "
                + "shape is unrecognized, even though the right operand is present")
                .that(result)
                .isNull();
    }

    @Test
    public void testCanonicalTextDotRightNullOnly() throws Exception {
        final DetailAstImpl dotNode = new DetailAstImpl();
        dotNode.initialize(new CommonToken(TokenTypes.DOT, "."));
        final DetailAstImpl leftIdent = new DetailAstImpl();
        leftIdent.initialize(new CommonToken(TokenTypes.IDENT, "obj"));
        dotNode.addChild(leftIdent);
        final String result = TestUtil.invokeStaticMethod(
                BooleanExpressionComplexityCheck.class, "canonicalText",
                String.class, dotNode);
        assertWithMessage("canonicalText should return null when the right operand "
                + "is missing, even though the left operand resolves fine")
                .that(result)
                .isNull();
    }

}
