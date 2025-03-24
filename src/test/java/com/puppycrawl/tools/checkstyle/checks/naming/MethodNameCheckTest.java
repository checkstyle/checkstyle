///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultOne()
            throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMethodNameSimpleOne.java"), expected);
    }

    @Test
    public void testDefaultTwo()
            throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "61:10: " + getCheckMessage(MSG_INVALID_PATTERN, "ALL_UPPERCASE_METHOD", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodNameSimpleTwo.java"), expected);
    }

    @Test
    public void testDefaultThree()
            throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMethodNameSimpleThree.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "27:16: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "27:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "33:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                    pattern),
            "42:20: " + getCheckMessage(MSG_KEY, "Inner"),
            "42:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "48:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "60:24: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "60:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "73:9: " + getCheckMessage(MSG_KEY, "SweetInterface"),
            "73:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "82:17: " + getCheckMessage(MSG_KEY, "Outer"),
            "82:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNameEqualClassName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "25:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "31:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                    pattern),
            "38:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "44:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "54:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "64:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "70:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNameEqualClassName2.java"), expected);
    }

    @Test
    public void testAccessTuning() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "25:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "36:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "42:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "52:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "62:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "68:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNameEqualClassName3.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMethodNameExtra.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "29:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PUBLICfoo", pattern),
            "32:20: " + getCheckMessage(MSG_INVALID_PATTERN, "PROTECTEDfoo", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNameOverriddenMethods.java"), expected);
    }

    @Test
    public void testInterfacesExcludePublic() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod", pattern),
            "20:25: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod2", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNamePublicMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testInterfacesExcludePrivate() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "22:18: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod", pattern),
            "25:25: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod2", pattern),
            "28:10: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod", pattern),
            "30:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod2", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodNamePrivateMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodNameCheck methodNameCheckObj = new MethodNameCheck();
        final int[] actual = methodNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testRecordInInterfaceBody() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "24:14: " + getCheckMessage(MSG_INVALID_PATTERN, "VIOLATION", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodNameRecordInInterfaceBody.java"), expected);
    }

}
