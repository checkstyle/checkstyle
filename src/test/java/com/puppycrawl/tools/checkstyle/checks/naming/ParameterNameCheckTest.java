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

public class ParameterNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/parametername";
    }

    @Test
    public void testGetRequiredTokens() {
        final ParameterNameCheck checkObj = new ParameterNameCheck();
        final int[] expected = {TokenTypes.PARAMETER_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCatch()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^NO_WAY_MATEY$");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterNameCatchOnly.java"), expected);
    }

    @Test
    public void testSpecified()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^a[A-Z][a-zA-Z0-9]*$");

        final String pattern = "^a[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "71:19: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat1", pattern),
            "71:34: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat2", pattern),
            "72:25: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat3", pattern),
        };
        verify(checkConfig, getPath("InputParameterName.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterName.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterNameCheck parameterNameCheckObj = new ParameterNameCheck();
        final int[] actual = parameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PARAMETER_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testSkipMethodsWithOverrideAnnotationTrue()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^h$");
        checkConfig.addAttribute("ignoreOverridden", "true");

        final String pattern = "^h$";

        final String[] expected = {
            "11:28: " + getCheckMessage(MSG_INVALID_PATTERN, "object", pattern),
            "15:30: " + getCheckMessage(MSG_INVALID_PATTERN, "aaaa", pattern),
            "19:19: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "19:28: " + getCheckMessage(MSG_INVALID_PATTERN, "bd", pattern),
            "21:18: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "28:46: " + getCheckMessage(MSG_INVALID_PATTERN, "fie", pattern),
            "28:73: " + getCheckMessage(MSG_INVALID_PATTERN, "pkgNames", pattern),
            };
        verify(checkConfig, getPath("InputParameterNameOverrideAnnotation.java"), expected);
    }

    @Test
    public void testSkipMethodsWithOverrideAnnotationFalse()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^h$");
        checkConfig.addAttribute("ignoreOverridden", "false");

        final String pattern = "^h$";

        final String[] expected = {
            "6:34: " + getCheckMessage(MSG_INVALID_PATTERN, "o", pattern),
            "11:28: " + getCheckMessage(MSG_INVALID_PATTERN, "object", pattern),
            "15:30: " + getCheckMessage(MSG_INVALID_PATTERN, "aaaa", pattern),
            "19:19: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "19:28: " + getCheckMessage(MSG_INVALID_PATTERN, "bd", pattern),
            "21:18: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "28:46: " + getCheckMessage(MSG_INVALID_PATTERN, "fie", pattern),
            "28:73: " + getCheckMessage(MSG_INVALID_PATTERN, "pkgNames", pattern),
            };
        verify(checkConfig, getPath("InputParameterNameOverrideAnnotation.java"), expected);
    }

    @Test
    public void testPublicAccessModifier()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^h$");
        checkConfig.addAttribute("accessModifiers", AccessModifier.PUBLIC.toString());

        final String pattern = "^h$";

        final String[] expected = {
            "5:49: " + getCheckMessage(MSG_INVALID_PATTERN, "pubconstr", pattern),
            "9:31: " + getCheckMessage(MSG_INVALID_PATTERN, "inner", pattern),
            "19:24: " + getCheckMessage(MSG_INVALID_PATTERN, "pubpub", pattern),
            "30:21: " + getCheckMessage(MSG_INVALID_PATTERN, "pubifc", pattern),
            "44:24: " + getCheckMessage(MSG_INVALID_PATTERN, "packpub", pattern),
            "60:21: " + getCheckMessage(MSG_INVALID_PATTERN, "packifc", pattern),
            };
        verify(checkConfig, getPath("InputParameterNameAccessModifier.java"), expected);
    }

    @Test
    public void testIsOverriddenNoNullPointerException()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^[a-z][a-zA-Z0-9]*$");
        checkConfig.addAttribute("ignoreOverridden", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterNameOverrideAnnotationNoNPE.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParameterNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterNameReceiver.java"), expected);
    }

    @Test
    public void testLambdaParameterNoViolationAtAll() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParameterNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParameterNameLambda.java"), expected);
    }

}
