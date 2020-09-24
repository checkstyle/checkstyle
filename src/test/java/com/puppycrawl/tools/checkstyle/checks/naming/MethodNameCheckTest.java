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
import static com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MethodNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/methodname";
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodNameCheck checkObj = new MethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "137:10: " + getCheckMessage(MSG_INVALID_PATTERN, "ALL_UPPERCASE_METHOD", pattern),
        };
        verify(checkConfig, getPath("InputMethodNameSimple.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                pattern),
            "23:20: " + getCheckMessage(MSG_KEY, "Inner"),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "37:24: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "47:9: " + getCheckMessage(MSG_KEY, "SweetInterface"),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_KEY, "Outer"),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addAttribute("allowClassName", "true");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                pattern),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName.java"), expected);
    }

    @Test
    public void testAccessTuning() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addAttribute("allowClassName", "true");

        // allow method names and class names to equal
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMethodNameExtra.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PUBLICfoo", pattern),
            "20:20: " + getCheckMessage(MSG_INVALID_PATTERN, "PROTECTEDfoo", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameOverriddenMethods.java"), expected);
    }

    @Test
    public void testInterfacesExcludePublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);
        checkConfig.addAttribute("applyToPublic", "false");
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "10:18: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod", pattern),
            "12:25: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod2", pattern),
        };

        verify(checkConfig, getNonCompilablePath("InputMethodNamePublicMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testInterfacesExcludePrivate() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);
        checkConfig.addAttribute("applyToPrivate", "false");
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "14:18: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod", pattern),
            "17:25: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod2", pattern),
            "20:10: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod", pattern),
            "22:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod2", pattern),
        };

        verify(checkConfig, getNonCompilablePath("InputMethodNamePrivateMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodNameCheck methodNameCheckObj = new MethodNameCheck();
        final int[] actual = methodNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}
