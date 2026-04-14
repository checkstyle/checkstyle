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
            "18:21: " + getCheckMessage(MSG_KEY, 6, max),
            "22:17: " + getCheckMessage(MSG_KEY, 6, max),
            "26:27: " + getCheckMessage(MSG_KEY, 6, max),
            "30:48: " + getCheckMessage(MSG_KEY, 1, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputBooleanExpressionComplexityWhenExpression.java"),
                expected);
    }

    @Test
    public void testIgnoreUniformChains() throws Exception {

        final String[] expected = {
            "34:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "60:9: " + getCheckMessage(MSG_KEY, 4, 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputBooleanExpressionComplexityUniformChain.java"), expected);
    }

}
