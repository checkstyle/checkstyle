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

import static com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "i"),
            "9:16: " + getCheckMessage(MSG_KEY, "j"),
            "10:18: " + getCheckMessage(MSG_KEY, "runnable"),
            "20:13: " + getCheckMessage(MSG_KEY, "i"),
            "24:13: " + getCheckMessage(MSG_KEY, "z"),
            "26:16: " + getCheckMessage(MSG_KEY, "obj"),
            "30:16: " + getCheckMessage(MSG_KEY, "x"),
            "36:18: " + getCheckMessage(MSG_KEY, "runnable"),
            "40:21: " + getCheckMessage(MSG_KEY, "q"),
            "56:13: " + getCheckMessage(MSG_KEY, "i"),
            "60:13: " + getCheckMessage(MSG_KEY, "z"),
            "62:16: " + getCheckMessage(MSG_KEY, "obj"),
            "66:16: " + getCheckMessage(MSG_KEY, "x"),
            "74:21: " + getCheckMessage(MSG_KEY, "w"),
            "75:26: " + getCheckMessage(MSG_KEY, "runnable"),
            "96:17: " + getCheckMessage(MSG_KEY, "weird"),
            "97:17: " + getCheckMessage(MSG_KEY, "j"),
            "98:17: " + getCheckMessage(MSG_KEY, "k"),
            "185:13: " + getCheckMessage(MSG_KEY, "x"),
            "190:17: " + getCheckMessage(MSG_KEY, "x"),
            "210:17: " + getCheckMessage(MSG_KEY, "n"),
            "216:13: " + getCheckMessage(MSG_KEY, "q"),
            "217:13: " + getCheckMessage(MSG_KEY, "w"),
            "226:21: " + getCheckMessage(MSG_KEY, "w"),
            "227:21: " + getCheckMessage(MSG_KEY, "e"),
            "247:17: " + getCheckMessage(MSG_KEY, "n"),
            "259:17: " + getCheckMessage(MSG_KEY, "t"),
            "269:21: " + getCheckMessage(MSG_KEY, "foo"),
            "288:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "300:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "344:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "357:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "360:21: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "375:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "386:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "418:13: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "421:21: " + getCheckMessage(MSG_KEY, "shouldBeFinal"),
            "441:33: " + getCheckMessage(MSG_KEY, "table"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testParameter() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");

        final String[] expected = {
            "45:28: " + getCheckMessage(MSG_KEY, "aArg"),
            "149:36: " + getCheckMessage(MSG_KEY, "_o"),
            "154:37: " + getCheckMessage(MSG_KEY, "_o1"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testNativeMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableNativeMethods.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableFalsePositive.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");
        checkConfig.addAttribute("validateEnhancedForLoopVariable", "true");
        final String[] expected = {
            "8:20: " + getCheckMessage(MSG_KEY, "a"),
            "15:13: " + getCheckMessage(MSG_KEY, "x"),
            "21:66: " + getCheckMessage(MSG_KEY, "snippets"),
            "22:32: " + getCheckMessage(MSG_KEY, "filteredSnippets"),
            "23:21: " + getCheckMessage(MSG_KEY, "snippet"),
            "38:20: " + getCheckMessage(MSG_KEY, "a"),
            "41:16: " + getCheckMessage(MSG_KEY, "a"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_KEY, "x"),
            "21:66: " + getCheckMessage(MSG_KEY, "snippets"),
            "22:32: " + getCheckMessage(MSG_KEY, "filteredSnippets"),
            "41:16: " + getCheckMessage(MSG_KEY, "a"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testLambda()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");
        final String[] expected = {
            "32:16: " + getCheckMessage(MSG_KEY, "result"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableNameLambda.java"),
            expected);
    }

    @Test
    public void testVariableNameShadowing()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");

        final String[] expected = {
            "4:28: " + getCheckMessage(MSG_KEY, "text"),
            "17:13: " + getCheckMessage(MSG_KEY, "x"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableNameShadowing.java"), expected);
    }

    @Test
    public void testImproperToken() {
        final FinalLocalVariableCheck check = new FinalLocalVariableCheck();

        final DetailAstImpl lambdaAst = new DetailAstImpl();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testVariableWhichIsAssignedMultipleTimes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expected = {
            "49:13: " + getCheckMessage(MSG_KEY, "i"),
            "122:16: " + getCheckMessage(MSG_KEY, "path"),
            "126:20: " + getCheckMessage(MSG_KEY, "relativePath"),
            "202:17: " + getCheckMessage(MSG_KEY, "kind"),
            "207:24: " + getCheckMessage(MSG_KEY, "m"),
            "409:17: " + getCheckMessage(MSG_KEY, "increment"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableAssignedMultipleTimes.java"), expected);
    }

    @Test
    public void testVariableIsAssignedInsideAndOutsideSwitchBlock() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = {
            "31:13: " + getCheckMessage(MSG_KEY, "b"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableAssignedInsideAndOutsideSwitch.java"),
            expected);
    }

    @Test
    public void testFinalLocalVariableFalsePositives() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = {
            "344:16: " + getCheckMessage(MSG_KEY, "c2"),
            "2187:16: " + getCheckMessage(MSG_KEY, "b"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableFalsePositives.java"), expected);
    }

    @Test
    public void testMultipleAndNestedConditions() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableMultipleAndNestedConditions.java"),
            expected);
    }

    @Test
    public void testMultiTypeCatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableMultiCatch.java"),
                expected);
    }

    @Test
    public void testLeavingSlistToken() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableLeavingSlistToken.java"), expected);
    }

    @Test
    public void testBreakOrReturn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = {
            "7:19: " + getCheckMessage(MSG_KEY, "e"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableBreak.java"), expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "testSupport"),
        };
        verify(checkConfig, getPath("InputFinalLocalVariableAnonymousClass.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableReceiverParameter.java"), expected);
    }

}
