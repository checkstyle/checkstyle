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
import static com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FinalLocalVariableCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_KEY, "i"),
            "17:16: " + getCheckMessage(MSG_KEY, "j"),
            "18:18: " + getCheckMessage(MSG_KEY, "runnable"),
            "28:13: " + getCheckMessage(MSG_KEY, "i"),
            "32:13: " + getCheckMessage(MSG_KEY, "z"),
            "34:16: " + getCheckMessage(MSG_KEY, "obj"),
            "38:16: " + getCheckMessage(MSG_KEY, "x"),
            "44:18: " + getCheckMessage(MSG_KEY, "runnable"),
            "48:21: " + getCheckMessage(MSG_KEY, "q"),
            "64:13: " + getCheckMessage(MSG_KEY, "i"),
            "68:13: " + getCheckMessage(MSG_KEY, "z"),
            "70:16: " + getCheckMessage(MSG_KEY, "obj"),
            "74:16: " + getCheckMessage(MSG_KEY, "x"),
            "82:21: " + getCheckMessage(MSG_KEY, "w"),
            "83:26: " + getCheckMessage(MSG_KEY, "runnable"),
            "104:17: " + getCheckMessage(MSG_KEY, "weird"),
            "105:17: " + getCheckMessage(MSG_KEY, "j"),
            "106:17: " + getCheckMessage(MSG_KEY, "k"),
            "193:13: " + getCheckMessage(MSG_KEY, "x"),
            "198:17: " + getCheckMessage(MSG_KEY, "x"),
            "218:17: " + getCheckMessage(MSG_KEY, "n"),
            "224:13: " + getCheckMessage(MSG_KEY, "q"),
            "225:13: " + getCheckMessage(MSG_KEY, "w"),
            "234:21: " + getCheckMessage(MSG_KEY, "w"),
            "235:21: " + getCheckMessage(MSG_KEY, "e"),
            "255:17: " + getCheckMessage(MSG_KEY, "n"),
            "267:17: " + getCheckMessage(MSG_KEY, "t"),
            "277:21: " + getCheckMessage(MSG_KEY, "foo"),
            "296:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "308:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "352:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "365:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "368:21: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "383:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "394:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "426:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "429:21: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "449:33: " + getCheckMessage(MSG_KEY, "table"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testRecordsInput() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, "b"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalLocalVariableCheckRecords.java"), expected);
    }

    @Test
    public void testParameter() throws Exception {

        final String[] expected = {
            "53:28: " + getCheckMessage(MSG_KEY, "aArg"),
            "157:36: " + getCheckMessage(MSG_KEY, "_o"),
            "162:37: " + getCheckMessage(MSG_KEY, "_o1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2.java"), expected);
    }

    @Test
    public void testNativeMethods() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableNativeMethods.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableFalsePositive.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {
        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_KEY, "a"),
            "23:13: " + getCheckMessage(MSG_KEY, "x"),
            "29:66: " + getCheckMessage(MSG_KEY, "snippets"),
            "30:32: " + getCheckMessage(MSG_KEY, "filteredSnippets"),
            "31:21: " + getCheckMessage(MSG_KEY, "snippet"),
            "46:20: " + getCheckMessage(MSG_KEY, "a"),
            "49:16: " + getCheckMessage(MSG_KEY, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_KEY, "x"),
            "29:66: " + getCheckMessage(MSG_KEY, "snippets"),
            "30:32: " + getCheckMessage(MSG_KEY, "filteredSnippets"),
            "49:16: " + getCheckMessage(MSG_KEY, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableEnhancedForLoopVariable2.java"),
            expected);
    }

    @Test
    public void testLambda()
            throws Exception {
        final String[] expected = {
            "40:16: " + getCheckMessage(MSG_KEY, "result"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableNameLambda.java"),
            expected);
    }

    @Test
    public void testVariableNameShadowing()
            throws Exception {

        final String[] expected = {
            "12:28: " + getCheckMessage(MSG_KEY, "text"),
            "25:13: " + getCheckMessage(MSG_KEY, "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableNameShadowing.java"), expected);
    }

    @Test
    public void testImproperToken() {
        final FinalLocalVariableCheck check = new FinalLocalVariableCheck();

        final DetailAstImpl lambdaAst = new DetailAstImpl();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testVariableWhichIsAssignedMultipleTimes() throws Exception {

        final String[] expected = {
            "57:13: " + getCheckMessage(MSG_KEY, "i"),
            "130:16: " + getCheckMessage(MSG_KEY, "path"),
            "134:20: " + getCheckMessage(MSG_KEY, "relativePath"),
            "210:17: " + getCheckMessage(MSG_KEY, "kind"),
            "215:24: " + getCheckMessage(MSG_KEY, "m"),
            "417:17: " + getCheckMessage(MSG_KEY, "increment"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableAssignedMultipleTimes.java"), expected);
    }

    @Test
    public void testVariableIsAssignedInsideAndOutsideSwitchBlock() throws Exception {
        final String[] expected = {
            "39:13: " + getCheckMessage(MSG_KEY, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableAssignedInsideAndOutsideSwitch.java"),
            expected);
    }

    @Test
    public void testFinalLocalVariableFalsePositives() throws Exception {
        final String[] expected = {
            "352:16: " + getCheckMessage(MSG_KEY, "c2"),
            "2195:16: " + getCheckMessage(MSG_KEY, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableFalsePositives.java"), expected);
    }

    @Test
    public void testMultipleAndNestedConditions() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableMultipleAndNestedConditions.java"),
            expected);
    }

    @Test
    public void testMultiTypeCatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableMultiCatch.java"),
                expected);
    }

    @Test
    public void testLeavingSlistToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableLeavingSlistToken.java"), expected);
    }

    @Test
    public void testBreakOrReturn() throws Exception {
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableBreak.java"), expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_KEY, "testSupport"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableAnonymousClass.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableReceiverParameter.java"), expected);
    }

    @Test
    public void testFinalLocalVariableSwitchExpressions() throws Exception {
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY, "e"),
            "53:19: " + getCheckMessage(MSG_KEY, "e"),
            "91:19: " + getCheckMessage(MSG_KEY, "e"),
            "125:19: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalLocalVariableCheckSwitchExpressions.java"),
            expected);
    }

    @Test
    public void testFinalLocalVariableSwitchAssignment() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_KEY, "a"),
            "44:13: " + getCheckMessage(MSG_KEY, "b"),
            "46:21: " + getCheckMessage(MSG_KEY, "x"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalLocalVariableCheckSwitchAssignment.java"),
            expected);
    }

}
