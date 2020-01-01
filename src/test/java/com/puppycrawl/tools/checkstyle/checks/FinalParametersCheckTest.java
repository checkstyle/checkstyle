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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FinalParametersCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/finalparameters";
    }

    @Test
    public void testDefaultTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        final String[] expected = {
            "23:26: " + getCheckMessage(MSG_KEY, "s"),
            "38:26: " + getCheckMessage(MSG_KEY, "i"),
            "43:26: " + getCheckMessage(MSG_KEY, "s"),
            "53:17: " + getCheckMessage(MSG_KEY, "s"),
            "69:17: " + getCheckMessage(MSG_KEY, "s"),
            "75:17: " + getCheckMessage(MSG_KEY, "s"),
            "90:45: " + getCheckMessage(MSG_KEY, "e"),
            "93:36: " + getCheckMessage(MSG_KEY, "e"),
            "110:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "113:18: " + getCheckMessage(MSG_KEY, "args"),
            "116:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCtorToken() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "CTOR_DEF");
        final String[] expected = {
            "23:26: " + getCheckMessage(MSG_KEY, "s"),
            "38:26: " + getCheckMessage(MSG_KEY, "i"),
            "43:26: " + getCheckMessage(MSG_KEY, "s"),
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testMethodToken() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        final String[] expected = {
            "53:17: " + getCheckMessage(MSG_KEY, "s"),
            "69:17: " + getCheckMessage(MSG_KEY, "s"),
            "75:17: " + getCheckMessage(MSG_KEY, "s"),
            "90:45: " + getCheckMessage(MSG_KEY, "e"),
            "93:36: " + getCheckMessage(MSG_KEY, "e"),
            "110:18: " + getCheckMessage(MSG_KEY, "aParam"),
            "113:18: " + getCheckMessage(MSG_KEY, "args"),
            "116:18: " + getCheckMessage(MSG_KEY, "args"),
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCatchToken() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CATCH");
        final String[] expected = {
            "125:16: " + getCheckMessage(MSG_KEY, "npe"),
            "131:16: " + getCheckMessage(MSG_KEY, "e"),
            "134:16: " + getCheckMessage(MSG_KEY, "e"),
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testForEachClauseToken() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "FOR_EACH_CLAUSE");
        final String[] expected = {
            "152:13: " + getCheckMessage(MSG_KEY, "s"),
            "160:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testIgnorePrimitiveTypesParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("ignorePrimitiveTypes", "true");
        final String[] expected = {
            "6:22: " + getCheckMessage(MSG_KEY, "k"),
            "7:15: " + getCheckMessage(MSG_KEY, "s"),
            "7:25: " + getCheckMessage(MSG_KEY, "o"),
            "8:15: " + getCheckMessage(MSG_KEY, "array"),
            "9:31: " + getCheckMessage(MSG_KEY, "s"),
            "10:22: " + getCheckMessage(MSG_KEY, "l"),
            "10:32: " + getCheckMessage(MSG_KEY, "s"),
        };
        verify(checkConfig, getPath("InputFinalParametersPrimitiveTypes.java"), expected);
    }

    @Test
    public void testPrimitiveTypesParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        final String[] expected = {
            "5:14: " + getCheckMessage(MSG_KEY, "i"),
            "6:15: " + getCheckMessage(MSG_KEY, "i"),
            "6:22: " + getCheckMessage(MSG_KEY, "k"),
            "6:32: " + getCheckMessage(MSG_KEY, "s"),
            "7:15: " + getCheckMessage(MSG_KEY, "s"),
            "7:25: " + getCheckMessage(MSG_KEY, "o"),
            "7:35: " + getCheckMessage(MSG_KEY, "l"),
            "8:15: " + getCheckMessage(MSG_KEY, "array"),
            "9:15: " + getCheckMessage(MSG_KEY, "i"),
            "9:22: " + getCheckMessage(MSG_KEY, "x"),
            "9:31: " + getCheckMessage(MSG_KEY, "s"),
            "10:15: " + getCheckMessage(MSG_KEY, "x"),
            "10:22: " + getCheckMessage(MSG_KEY, "l"),
            "10:32: " + getCheckMessage(MSG_KEY, "s"),
        };
        verify(checkConfig, getPath("InputFinalParametersPrimitiveTypes.java"), expected);
    }

    @Test
    public void testReceiverParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalParametersCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFinalParametersReceiver.java"), expected);
    }

}
