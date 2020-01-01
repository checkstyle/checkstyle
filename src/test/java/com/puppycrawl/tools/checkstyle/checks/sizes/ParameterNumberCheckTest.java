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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParameterNumberCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final ParameterNumberCheck checkObj = new ParameterNumberCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "ParameterNumberCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterNumberCheck paramNumberCheckObj =
            new ParameterNumberCheck();
        final int[] actual = paramNumberCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNumberCheck.class);
        final String[] expected = {
            "194:10: " + getCheckMessage(MSG_KEY, 7, 9),
        };
        verify(checkConfig, getPath("InputParameterNumberSimple.java"), expected);
    }

    @Test
    public void testNum()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addAttribute("max", "2");
        final String[] expected = {
            "71:9: " + getCheckMessage(MSG_KEY, 2, 3),
            "194:10: " + getCheckMessage(MSG_KEY, 2, 9),
        };
        verify(checkConfig, getPath("InputParameterNumberSimple.java"), expected);
    }

    @Test
    public void testMaxParam()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addAttribute("max", "9");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterNumberSimple.java"), expected);
    }

    @Test
    public void shouldLogActualParameterNumber()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addMessage("maxParam", "{0},{1}");
        final String[] expected = {
            "194:10: 7,9",
        };
        verify(checkConfig, getPath("InputParameterNumberSimple.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethods()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addAttribute("ignoreOverriddenMethods", "true");
        final String[] expected = {
            "6:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "11:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verify(checkConfig, getPath("InputParameterNumber.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethodsFalse()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        final String[] expected = {
            "6:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "11:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "19:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "24:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verify(checkConfig, getPath("InputParameterNumber.java"), expected);
    }

}
