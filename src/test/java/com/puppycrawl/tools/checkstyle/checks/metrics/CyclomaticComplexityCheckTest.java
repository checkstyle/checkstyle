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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CyclomaticComplexityCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/cyclomaticcomplexity";
    }

    @Test
    public void testSwitchBlockAsSingleDecisionPointSetToTrue() throws Exception {

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexitySwitchBlocks.java"), expected);
    }

    @Test
    public void testSwitchBlockAsSingleDecisionPointSetToFalse() throws Exception {

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 5, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexitySwitchBlocks2.java"), expected);
    }

    @Test
    public void testEqualsMaxComplexity() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexitySwitchBlocks3.java"), expected);
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "20:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "32:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "45:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "55:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "73:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "86:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "98:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "110:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "114:13: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexity.java"), expected);
    }

    @Test
    public void testCyclomaticComplexityRecords() throws Exception {

        final int max = 0;

        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY, 11, max),
            "48:9: " + getCheckMessage(MSG_KEY, 11, max),
            "83:5: " + getCheckMessage(MSG_KEY, 11, max),
            "115:5: " + getCheckMessage(MSG_KEY, 11, max),
            "148:5: " + getCheckMessage(MSG_KEY, 11, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCyclomaticComplexityRecords.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final CyclomaticComplexityCheck cyclomaticComplexityCheckObj =
            new CyclomaticComplexityCheck();
        final int[] actual = cyclomaticComplexityCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LAND,
            TokenTypes.LOR,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_WHEN,
        };
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final CyclomaticComplexityCheck cyclomaticComplexityCheckObj =
            new CyclomaticComplexityCheck();
        final int[] actual = cyclomaticComplexityCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testHighMax() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexitySwitchBlocks4.java"), expected);
    }

    @Test
    public void testDefaultMax() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 12, 10),
        };

        verifyWithInlineConfigParser(
                getPath("InputCyclomaticComplexitySwitchBlocks5.java"), expected);
    }

    @Test
    public void testWhenExpression() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_KEY, 5, 0),
            "22:4: " + getCheckMessage(MSG_KEY, 5, 0),
            "31:4: " + getCheckMessage(MSG_KEY, 7, 0),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCyclomaticComplexityWhenExpression.java"), expected);
    }

    @Test
    public void testWhenExpressionSwitchAsSinglePoint() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "22:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "31:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "41:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputCyclomaticComplexityWhenSwitchAsSinglePoint.java"), expected);
    }

    @Test
    public void testSwitchBlockAsSingleDecisionPointWithNestedSwitch() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "26:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "41:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputCyclomaticComplexitySwitchBlocks6.java"), expected);
    }

}
