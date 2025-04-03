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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FinalParametersCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
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
            "115:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "118:18: " + getCheckMessage(MSG_KEY, "args"),
            "121:18: " + getCheckMessage(MSG_KEY, "args"),
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
            "116:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "119:18: " + getCheckMessage(MSG_KEY, "args"),
            "122:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters3.java"), expected);
    }

    @Test
    public void testCatchToken() throws Exception {
        final String[] expected = {
            "131:16: " + getCheckMessage(MSG_KEY, "npe"),
            "137:16: " + getCheckMessage(MSG_KEY, "e"),
            "140:16: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters4.java"), expected);
    }

    @Test
    public void testForEachClauseToken() throws Exception {
        final String[] expected = {
            "158:13: " + getCheckMessage(MSG_KEY, "s"),
            "166:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters5.java"), expected);
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

}
