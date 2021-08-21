////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            "198:10: " + getCheckMessage(MSG_KEY, 7, 9),
        };
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumberSimple.java"), expected);
    }

    @Test
    public void testNum()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addProperty("max", "2");
        final String[] expected = {
            "75:9: " + getCheckMessage(MSG_KEY, 2, 3),
            "198:10: " + getCheckMessage(MSG_KEY, 2, 9),
        };
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumberSimple2.java"), expected);
    }

    @Test
    public void testMaxParam()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addProperty("max", "9");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumberSimple3.java"), expected);
    }

    @Test
    public void shouldLogActualParameterNumber()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addMessage("maxParam", "{0},{1}");
        final String[] expected = {
            "199:10: 7,9",
        };
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumberSimple4.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethods()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        checkConfig.addProperty("ignoreOverriddenMethods", "true");
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "20:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumber.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethodsFalse()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParameterNumberCheck.class);
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "20:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "28:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "33:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verifyWithInlineConfigParser(checkConfig,
                getPath("InputParameterNumber2.java"), expected);
    }

}
