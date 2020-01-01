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

public class StaticVariableNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/staticvariablename";
    }

    @Test
    public void testGetRequiredTokens() {
        final StaticVariableNameCheck checkObj = new StaticVariableNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testSpecified()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(StaticVariableNameCheck.class);
        checkConfig.addAttribute("format", "^s[A-Z][a-zA-Z0-9]*$");

        final String pattern = "^s[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "30:24: " + getCheckMessage(MSG_INVALID_PATTERN, "badStatic", pattern),
        };
        verify(checkConfig, getPath("InputStaticVariableName1.java"), expected);
    }

    @Test
    public void testAccessTuning()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(StaticVariableNameCheck.class);
        checkConfig.addAttribute("format", "^s[A-Z][a-zA-Z0-9]*$");

        // allow method names and class names to equal
        checkConfig.addAttribute("applyToPrivate", "false");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputStaticVariableName1.java"), expected);
    }

    @Test
    public void testInterfaceOrAnnotationBlock()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(StaticVariableNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputStaticVariableName.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final StaticVariableNameCheck staticVariableNameCheckObj = new StaticVariableNameCheck();
        final int[] actual = staticVariableNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}
