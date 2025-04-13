///
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
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
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCatch()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNameCatchOnly.java"), expected);
    }

    @Test
    public void testSpecified()
            throws Exception {

        final String pattern = "^a[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "68:19: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat1", pattern),
            "68:34: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat2", pattern),
            "69:25: " + getCheckMessage(MSG_INVALID_PATTERN, "badFormat3", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNameOne.java"), expected);
    }

    @Test
    public void testWhitespaceInAccessModifierProperty() throws Exception {
        final String pattern = "^h$";
        final String[] expected = {
            "14:69: " + getCheckMessage(MSG_INVALID_PATTERN, "parameter1", pattern),
            "18:31: " + getCheckMessage(MSG_INVALID_PATTERN, "parameter2", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNameWhitespaceInAccessModifierProperty.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterName.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterNameCheck parameterNameCheckObj = new ParameterNameCheck();
        final int[] actual = parameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PARAMETER_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testSkipMethodsWithOverrideAnnotationTrue()
            throws Exception {

        final String pattern = "^h$";

        final String[] expected = {
            "20:28: " + getCheckMessage(MSG_INVALID_PATTERN, "object", pattern),
            "24:30: " + getCheckMessage(MSG_INVALID_PATTERN, "aaaa", pattern),
            "28:19: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "28:28: " + getCheckMessage(MSG_INVALID_PATTERN, "bd", pattern),
            "30:18: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "37:46: " + getCheckMessage(MSG_INVALID_PATTERN, "fie", pattern),
            "37:73: " + getCheckMessage(MSG_INVALID_PATTERN, "pkgNames", pattern),
            };
        verifyWithInlineConfigParser(
                getPath("InputParameterNameOverrideAnnotation.java"), expected);
    }

    @Test
    public void testSkipMethodsWithOverrideAnnotationFalse()
            throws Exception {

        final String pattern = "^h$";

        final String[] expected = {
            "15:34: " + getCheckMessage(MSG_INVALID_PATTERN, "o", pattern),
            "20:28: " + getCheckMessage(MSG_INVALID_PATTERN, "object", pattern),
            "24:30: " + getCheckMessage(MSG_INVALID_PATTERN, "aaaa", pattern),
            "28:19: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "28:28: " + getCheckMessage(MSG_INVALID_PATTERN, "bd", pattern),
            "30:18: " + getCheckMessage(MSG_INVALID_PATTERN, "abc", pattern),
            "37:49: " + getCheckMessage(MSG_INVALID_PATTERN, "fie", pattern),
            "37:76: " + getCheckMessage(MSG_INVALID_PATTERN, "pkgNames", pattern),
            };
        verifyWithInlineConfigParser(
                getPath("InputParameterNameOverrideAnnotationOne.java"), expected);
    }

    @Test
    public void testPublicAccessModifier()
            throws Exception {

        final String pattern = "^h$";

        final String[] expected = {
            "14:49: " + getCheckMessage(MSG_INVALID_PATTERN, "pubconstr", pattern),
            "18:31: " + getCheckMessage(MSG_INVALID_PATTERN, "inner", pattern),
            "28:24: " + getCheckMessage(MSG_INVALID_PATTERN, "pubpub", pattern),
            "39:21: " + getCheckMessage(MSG_INVALID_PATTERN, "pubifc", pattern),
            "53:24: " + getCheckMessage(MSG_INVALID_PATTERN, "packpub", pattern),
            "69:21: " + getCheckMessage(MSG_INVALID_PATTERN, "packifc", pattern),
            };
        verifyWithInlineConfigParser(
                getPath("InputParameterNameAccessModifier.java"), expected);
    }

    @Test
    public void testIsOverriddenNoNullPointerException()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNameOverrideAnnotationNoNPE.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNameReceiver.java"), expected);
    }

    @Test
    public void testLambdaParameterNoViolationAtAll() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNameLambda.java"), expected);
    }

    @Test
    public void testWhitespaceInConfig() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNameWhitespaceInConfig.java"), expected);
    }

    @Test
    public void testSetAccessModifiers() throws Exception {
        final AccessModifierOption[] input = {
            AccessModifierOption.PACKAGE,
        };
        final ParameterNameCheck check = new ParameterNameCheck();
        check.setAccessModifiers(input);

        assertWithMessage("check creates its own instance of access modifier array")
            .that(System.identityHashCode(
                TestUtil.getClassDeclaredField(ParameterNameCheck.class, "accessModifiers")
                        .get(check)))
            .isNotEqualTo(System.identityHashCode(input));
    }

}
