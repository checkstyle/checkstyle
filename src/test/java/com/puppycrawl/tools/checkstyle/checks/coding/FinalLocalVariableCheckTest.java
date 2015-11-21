////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FinalLocalVariableCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);

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
        };
        verify(checkConfig, getPath("InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testParameter() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
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
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableNativeMethods.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalLocalVariableFalsePositive.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");
        checkConfig.addAttribute("validateEnhancedForLoopVariable", "true");
        final String[] expected = {
            "8:20: " + "Variable 'a' should be declared final.",
            "15:13: " + "Variable 'x' should be declared final.",
        };
        verify(checkConfig, getPath("InputFinalLocalVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "VARIABLE_DEF, PARAMETER_DEF");
        final String[] expected = {
            "15:13: " + "Variable 'x' should be declared final.",
        };
        verify(checkConfig, getPath("InputFinalLocalVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testLambda()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputFinalLocalVariableNameLambda.java"),
            expected);
    }

    @Test
    public void testVariableNameShadowing()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF,VARIABLE_DEF");

        final String[] expected = {
            "4:28: " + "Variable 'text' should be declared final.",
            "17:13: " + "Variable 'x' should be declared final.",
        };
        verify(checkConfig, getPath("InputFinalLocalVariableNameShadowing.java"), expected);
    }

    @Test
    public void testImproperToken() throws Exception {
        final FinalLocalVariableCheck check = new FinalLocalVariableCheck();

        final DetailAST lambdaAst = new DetailAST();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            Assert.fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }
}
