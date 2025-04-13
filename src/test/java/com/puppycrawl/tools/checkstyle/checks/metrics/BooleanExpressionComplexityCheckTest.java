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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck.MSG_KEY;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class BooleanExpressionComplexityCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/booleanexpressioncomplexity";
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "38:46: " + getCheckMessage(MSG_KEY, 4, 3),
            "48:9: " + getCheckMessage(MSG_KEY, 6, 3),
            "54:34: " + getCheckMessage(MSG_KEY, 4, 3),
            "56:34: " + getCheckMessage(MSG_KEY, 4, 3),
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
        try {
            booleanExpressionComplexityCheckObj.visitToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unknown type: interface[0x-1]");
        }
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
            "24:23: " + getCheckMessage(MSG_KEY, 4, max),
            "35:23: " + getCheckMessage(MSG_KEY, 4, max),
            "45:27: " + getCheckMessage(MSG_KEY, 4, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
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
                getNonCompilablePath("InputBooleanExpressionComplexityRecordLeaves.java"),
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

}
