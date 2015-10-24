////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodNameCheck checkObj = new MethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "137:10: " + getCheckMessage(MSG_INVALID_PATTERN, "ALL_UPPERCASE_METHOD", pattern),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY, "InputMethNameEqualClsName"),
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethNameEqualClsName",
                pattern),
            "23:20: " + getCheckMessage(MSG_KEY, "Inner"),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "37:24: " + getCheckMessage(MSG_KEY, "InputMethNameEqualClsName"),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "47:9: " + getCheckMessage(MSG_KEY, "SweetInterface"),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_KEY, "Outter"),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outter", pattern),
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addAttribute("allowClassName", "true");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethNameEqualClsName",
                pattern),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outter", pattern),
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testAccessTuning() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addAttribute("allowClassName", "true");

        // allow method names and class names to equal
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "23:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "28:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "37:24: " + getCheckMessage(MSG_INVALID_PATTERN, "InputMethNameEqualClsName", pattern),
            "47:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "53:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outter", pattern),
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMethodNameExtra.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PUBLICfoo", pattern),
            "20:20: " + getCheckMessage(MSG_INVALID_PATTERN, "PROTECTEDfoo", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameOverridenMethods.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodNameCheck methodNameCheckObj = new MethodNameCheck();
        final int[] actual = methodNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
        };
        assertArrayEquals(expected, actual);
    }
}
