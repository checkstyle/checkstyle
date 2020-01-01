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

import static com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ArrayTypeStyleCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/arraytypestyle";
    }

    @Test
    public void testGetRequiredTokens() {
        final ArrayTypeStyleCheck checkObj = new ArrayTypeStyleCheck();
        final int[] expected = {TokenTypes.ARRAY_DECLARATOR};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Required tokens differs from expected");
    }

    @Test
    public void testJavaStyleOn()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ArrayTypeStyleCheck.class);
        final String[] expected = {
            "14:23: " + getCheckMessage(MSG_KEY),
            "15:18: " + getCheckMessage(MSG_KEY),
            "21:44: " + getCheckMessage(MSG_KEY),
            "45:33: " + getCheckMessage(MSG_KEY),
            "50:36: " + getCheckMessage(MSG_KEY),
            "56:29: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    @Test
    public void testJavaStyleOff()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ArrayTypeStyleCheck.class);
        checkConfig.addAttribute("javaStyle", "false");
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_KEY),
            "17:39: " + getCheckMessage(MSG_KEY),
            "23:18: " + getCheckMessage(MSG_KEY),
            "31:20: " + getCheckMessage(MSG_KEY),
            "45:33: " + getCheckMessage(MSG_KEY),
            "50:36: " + getCheckMessage(MSG_KEY),
            "56:29: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expected = {TokenTypes.ARRAY_DECLARATOR };
        final ArrayTypeStyleCheck check = new ArrayTypeStyleCheck();
        final int[] actual = check.getAcceptableTokens();
        assertEquals(1, actual.length, "Amount of acceptable tokens differs from expected");
        assertArrayEquals(expected, actual, "Acceptable tokens differs from expected");
    }

}
