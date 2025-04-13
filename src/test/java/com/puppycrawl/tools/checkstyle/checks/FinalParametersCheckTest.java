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
            "27:26: " + getCheckMessage(MSG_KEY, "s"),
            "42:26: " + getCheckMessage(MSG_KEY, "i"),
            "47:26: " + getCheckMessage(MSG_KEY, "s"),
            "57:17: " + getCheckMessage(MSG_KEY, "s"),
            "73:17: " + getCheckMessage(MSG_KEY, "s"),
            "79:17: " + getCheckMessage(MSG_KEY, "s"),
            "94:45: " + getCheckMessage(MSG_KEY, "e"),
            "97:36: " + getCheckMessage(MSG_KEY, "e"),
            "114:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "117:18: " + getCheckMessage(MSG_KEY, "args"),
            "120:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCtorToken() throws Exception {
        final String[] expected = {
            "28:27: " + getCheckMessage(MSG_KEY, "s"),
            "43:27: " + getCheckMessage(MSG_KEY, "i"),
            "48:27: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters2.java"), expected);
    }

    @Test
    public void testMethodToken() throws Exception {
        final String[] expected = {
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
                getPath("InputFinalParameters3.java"), expected);
    }

    @Test
    public void testCatchToken() throws Exception {
        final String[] expected = {
            "130:16: " + getCheckMessage(MSG_KEY, "npe"),
            "136:16: " + getCheckMessage(MSG_KEY, "e"),
            "139:16: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters4.java"), expected);
    }

    @Test
    public void testForEachClauseToken() throws Exception {
        final String[] expected = {
            "157:13: " + getCheckMessage(MSG_KEY, "s"),
            "165:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParameters5.java"), expected);
    }

    @Test
    public void testIgnorePrimitiveTypesParameters() throws Exception {
        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, "k"),
            "15:15: " + getCheckMessage(MSG_KEY, "s"),
            "15:25: " + getCheckMessage(MSG_KEY, "o"),
            "19:15: " + getCheckMessage(MSG_KEY, "array"),
            "20:31: " + getCheckMessage(MSG_KEY, "s"),
            "21:22: " + getCheckMessage(MSG_KEY, "l"),
            "21:32: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputFinalParametersPrimitiveTypes.java"), expected);
    }

    @Test
    public void testPrimitiveTypesParameters() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_KEY, "i"),
            "14:15: " + getCheckMessage(MSG_KEY, "i"),
            "14:22: " + getCheckMessage(MSG_KEY, "k"),
            "14:32: " + getCheckMessage(MSG_KEY, "s"),
            "19:15: " + getCheckMessage(MSG_KEY, "s"),
            "19:25: " + getCheckMessage(MSG_KEY, "o"),
            "19:35: " + getCheckMessage(MSG_KEY, "l"),
            "24:15: " + getCheckMessage(MSG_KEY, "array"),
            "25:15: " + getCheckMessage(MSG_KEY, "i"),
            "25:22: " + getCheckMessage(MSG_KEY, "x"),
            "25:31: " + getCheckMessage(MSG_KEY, "s"),
            "30:15: " + getCheckMessage(MSG_KEY, "x"),
            "30:22: " + getCheckMessage(MSG_KEY, "l"),
            "30:32: " + getCheckMessage(MSG_KEY, "s"),
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
