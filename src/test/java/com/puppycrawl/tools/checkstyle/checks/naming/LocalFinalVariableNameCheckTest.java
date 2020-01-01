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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LocalFinalVariableNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/localfinalvariablename";
    }

    @Test
    public void testGetRequiredTokens() {
        final LocalFinalVariableNameCheck checkObj =
            new LocalFinalVariableNameCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
            "LocalFinalVariableNameCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LocalFinalVariableNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "123:19: " + getCheckMessage(MSG_INVALID_PATTERN, "CDE", pattern),
        };
        verify(checkConfig, getPath("InputLocalFinalVariableName.java"), expected);
    }

    @Test
    public void testSet()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LocalFinalVariableNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");

        final String pattern = "[A-Z]+";

        final String[] expected = {
            "122:19: " + getCheckMessage(MSG_INVALID_PATTERN, "cde", pattern),
        };
        verify(checkConfig, getPath("InputLocalFinalVariableName.java"), expected);
    }

    @Test
    public void testInnerClass()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LocalFinalVariableNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputLocalFinalVariableNameInnerClass.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final LocalFinalVariableNameCheck localFinalVariableNameCheckObj =
            new LocalFinalVariableNameCheck();
        final int[] actual = localFinalVariableNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.RESOURCE,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testTryWithResources() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(LocalFinalVariableNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");

        final String pattern = "[A-Z]+";

        final String[] expected = {
            "23:30: " + getCheckMessage(MSG_INVALID_PATTERN, "br", pattern),
            "33:29: " + getCheckMessage(MSG_INVALID_PATTERN, "br", pattern),
            "53:22: " + getCheckMessage(MSG_INVALID_PATTERN, "zf", pattern),
            "71:30: " + getCheckMessage(MSG_INVALID_PATTERN, "fis8859_1", pattern),
            "73:32: " + getCheckMessage(MSG_INVALID_PATTERN, "isrutf8", pattern),
        };
        verify(checkConfig, getPath("InputLocalFinalVariableNameTryResources.java"), expected);
    }

}
