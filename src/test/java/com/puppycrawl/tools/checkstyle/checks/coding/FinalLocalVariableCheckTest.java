///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
    public void testInputFinalLocalVariableOne() throws Exception {

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
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableOne.java"), expected);
    }

    @Test
    public void testInputFinalLocalVariableTwo() throws Exception {
        final String[] expected = {
            "24:17: " + getCheckMessage(MSG_KEY, "weird"),
            "25:17: " + getCheckMessage(MSG_KEY, "j"),
            "26:17: " + getCheckMessage(MSG_KEY, "k"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableTwo.java"), expected);
    }

    @Test
    public void testInputFinalLocalVariableThree() throws Exception {
        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_KEY, "x"),
            "20:21: " + getCheckMessage(MSG_KEY, "x"),
            "41:21: " + getCheckMessage(MSG_KEY, "n"),
            "47:17: " + getCheckMessage(MSG_KEY, "q"),
            "48:17: " + getCheckMessage(MSG_KEY, "w"),
            "57:25: " + getCheckMessage(MSG_KEY, "w"),
            "58:25: " + getCheckMessage(MSG_KEY, "e"),
            "79:21: " + getCheckMessage(MSG_KEY, "n"),
            "92:21: " + getCheckMessage(MSG_KEY, "t"),
            "102:25: " + getCheckMessage(MSG_KEY, "foo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableThree.java"), expected);
    }

    @Test
    public void testInputFinalLocalVariableFour() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "28:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "72:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "85:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "88:25: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableFour.java"), expected);
    }

    @Test
    public void testFinalLocalVariableFive() throws Exception {
        final String[] expected = {
            "15:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "26:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "58:17: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "61:25: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "81:41: " + getCheckMessage(MSG_KEY, "table"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariableFive.java"), expected);
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
    public void testInputFinalLocalVariable2One() throws Exception {

        final String[] expected = {
            "52:28: " + getCheckMessage(MSG_KEY, "aArg"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2One.java"), expected);
    }

    @Test
    public void testInputFinalLocalVariable2Two() throws Exception {

        final String[] excepted = {
            "78:36: " + getCheckMessage(MSG_KEY, "_o"),
            "83:37: " + getCheckMessage(MSG_KEY, "_o1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2Two.java"), excepted);
    }

    @Test
    public void testInputFinalLocalVariable2Three() throws Exception {

        final String[] excepted = {

        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2Three.java"), excepted);
    }

    @Test
    public void testInputFinalLocalVariable2Four() throws Exception {

        final String[] excepted = {

        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2Four.java"), excepted);
    }

    @Test
    public void testInputFinalLocalVariable2Five() throws Exception {

        final String[] excepted = {

        };
        verifyWithInlineConfigParser(
                getPath("InputFinalLocalVariable2Five.java"), excepted);
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
            "72:16: " + getCheckMessage(MSG_KEY, "res"),
            "92:16: " + getCheckMessage(MSG_KEY, "res"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalLocalVariableCheckSwitchAssignment.java"),
            expected);
    }

    @Test
    public void testFinalLocalVariableSwitchStatement() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputFinalLocalVariableSwitchStatement.java"),
            expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final String[] expected = {
            "14:44: " + getCheckMessage(MSG_KEY, "a"),
            "18:44: " + getCheckMessage(MSG_KEY, "a"),
            "19:43: " + getCheckMessage(MSG_KEY, "b"),
            "22:47: " + getCheckMessage(MSG_KEY, "str"),
            "35:21: " + getCheckMessage(MSG_KEY, "str"),
        };
        verifyWithInlineConfigParser(
            getPath("InputFinalLocalVariableConstructor.java"),
            expected);
    }

    @Test
    public void test() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, "start"),
            "22:17: " + getCheckMessage(MSG_KEY, "end"),
            "40:38: " + getCheckMessage(MSG_KEY, "list"),
            "43:38: " + getCheckMessage(MSG_KEY, "forEach"),
            "45:38: " + getCheckMessage(MSG_KEY, "body"),
        };
        verifyWithInlineConfigParser(
            getPath("InputFinalLocalVariable3.java"),
            expected);
    }
}
