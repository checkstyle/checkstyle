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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FinalParametersCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/finalparameters";
    }

    @Test
    public void testDefaultTokens() throws Exception {
        final String[] expected = {
            "28:26: " + getCheckMessage(MSG_KEY, "s"),
            "43:26: " + getCheckMessage(MSG_KEY, "i"),
            "48:26: " + getCheckMessage(MSG_KEY, "s"),
            "58:17: " + getCheckMessage(MSG_KEY, "s"),
            "74:17: " + getCheckMessage(MSG_KEY, "s"),
            "80:17: " + getCheckMessage(MSG_KEY, "s"),
            "95:45: " + getCheckMessage(MSG_KEY, "e"),
            "98:36: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCtorToken() throws Exception {
        final String[] expected = {
            "29:27: " + getCheckMessage(MSG_KEY, "s"),
            "44:27: " + getCheckMessage(MSG_KEY, "i"),
            "49:27: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters2.java"), expected);
    }

    @Test
    public void testMethodToken() throws Exception {
        final String[] expected = {
            "59:17: " + getCheckMessage(MSG_KEY, "s"),
            "75:17: " + getCheckMessage(MSG_KEY, "s"),
            "81:17: " + getCheckMessage(MSG_KEY, "s"),
            "96:45: " + getCheckMessage(MSG_KEY, "e"),
            "99:36: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters3.java"), expected);
    }

    @Test
    public void testCatchToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters4.java"), expected);
    }

    @Test
    public void testForEachClauseToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters5.java"), expected);
    }

    @Test
    public void testDefaultTokensSplit() throws Exception {
        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "21:18: " + getCheckMessage(MSG_KEY, "args"),
            "24:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters6.java"), expected);
    }

    @Test
    public void testCtorTokenSplit() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters7.java"), expected);
    }

    @Test
    public void testMethodTokenSplit() throws Exception {
        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "21:18: " + getCheckMessage(MSG_KEY, "args"),
            "24:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters8.java"), expected);
    }

    @Test
    public void testCatchTokenSplit() throws Exception {
        final String[] expected = {
            "33:16: " + getCheckMessage(MSG_KEY, "npe"),
            "39:16: " + getCheckMessage(MSG_KEY, "e"),
            "42:16: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters9.java"), expected);
    }

    @Test
    public void testForEachClauseTokenSplit() throws Exception {
        final String[] expected = {
            "60:13: " + getCheckMessage(MSG_KEY, "s"),
            "68:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters10.java"), expected);
    }

    @Test
    public void testIgnorePrimitiveTypesParameters() throws Exception {
        final String[] expected = {
            "15:22: " + getCheckMessage(MSG_KEY, "k"),
            "16:15: " + getCheckMessage(MSG_KEY, "s"),
            "16:25: " + getCheckMessage(MSG_KEY, "o"),
            "20:15: " + getCheckMessage(MSG_KEY, "array"),
            "21:31: " + getCheckMessage(MSG_KEY, "s"),
            "22:22: " + getCheckMessage(MSG_KEY, "l"),
            "22:32: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParametersPrimitiveTypes.java"), expected);
    }

    @Test
    public void testPrimitiveTypesParameters() throws Exception {
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_KEY, "i"),
            "15:15: " + getCheckMessage(MSG_KEY, "i"),
            "15:22: " + getCheckMessage(MSG_KEY, "k"),
            "15:32: " + getCheckMessage(MSG_KEY, "s"),
            "20:15: " + getCheckMessage(MSG_KEY, "s"),
            "20:25: " + getCheckMessage(MSG_KEY, "o"),
            "20:35: " + getCheckMessage(MSG_KEY, "l"),
            "25:15: " + getCheckMessage(MSG_KEY, "array"),
            "26:15: " + getCheckMessage(MSG_KEY, "i"),
            "26:22: " + getCheckMessage(MSG_KEY, "x"),
            "26:31: " + getCheckMessage(MSG_KEY, "s"),
            "31:15: " + getCheckMessage(MSG_KEY, "x"),
            "31:22: " + getCheckMessage(MSG_KEY, "l"),
            "31:32: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParametersPrimitiveTypes2.java"), expected);
    }

    @Test
    public void testReceiverParameters() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFinalParametersReceiver.java"), expected);
    }

    @Test
    public void testUnnamedParametersPropertyTrue() throws Exception {
        final String[] expected = {
            "25:18: " + getCheckMessage(MSG_KEY, "__"),
            "30:18: " + getCheckMessage(MSG_KEY, "_e"),
            "35:18: " + getCheckMessage(MSG_KEY, "e_"),
            "46:14: " + getCheckMessage(MSG_KEY, "__"),
            "49:14: " + getCheckMessage(MSG_KEY, "_i"),
            "52:14: " + getCheckMessage(MSG_KEY, "i_"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalParametersUnnamedPropertyTrue.java"), expected);
    }

    @Test
    public void testUnnamedParametersPropertyFalse() throws Exception {
        final String[] expected = {
            "20:18: " + getCheckMessage(MSG_KEY, "_"),
            "25:18: " + getCheckMessage(MSG_KEY, "__"),
            "30:18: " + getCheckMessage(MSG_KEY, "_e"),
            "35:18: " + getCheckMessage(MSG_KEY, "e_"),
            "46:14: " + getCheckMessage(MSG_KEY, "__"),
            "43:14: " + getCheckMessage(MSG_KEY, "_"),
            "49:14: " + getCheckMessage(MSG_KEY, "_i"),
            "52:14: " + getCheckMessage(MSG_KEY, "i_"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFinalParametersUnnamedPropertyFalse.java"), expected);
    }

    @Test
    public void testMethodTokenInInterface() throws Exception {
        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_KEY, "param1"),
            "22:27: " + getCheckMessage(MSG_KEY, "param1"),
            "28:27: " + getCheckMessage(MSG_KEY, "param1"),
        };
        verifyWithInlineConfigParser(getPath("InputFinalParametersInterfaceMethod.java"), expected);

    }

    @Test
    public void testPatternVariableDefinitions() throws Exception {
        final String[] expected = {
            "17:47: " + getCheckMessage(MSG_KEY, "s"),
            "19:26: " + getCheckMessage(MSG_KEY, "i"),
            "24:34: " + getCheckMessage(MSG_KEY, "name"),
            "30:18: " + getCheckMessage(MSG_KEY, "s"),
            "32:26: " + getCheckMessage(MSG_KEY, "name"),
        };
        verifyWithInlineConfigParser(
            getPath("InputFinalParametersPatternVariables.java"), expected);
    }

    @Test
    public void testRecordForLoopPatternVariableDefinitions() throws Exception {
        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_KEY, "name"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputFinalParametersRecordForLoopPatternVariables.java"),
            expected
        );
    }
}
