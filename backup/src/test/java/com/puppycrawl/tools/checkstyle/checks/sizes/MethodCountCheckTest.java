///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_MANY_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PACKAGE_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PRIVATE_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PROTECTED_METHODS;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck.MSG_PUBLIC_METHODS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MethodCountCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/methodcount";
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodCountCheck checkObj = new MethodCountCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodCountCheck methodCountCheckObj =
            new MethodCountCheck();
        final int[] actual = methodCountCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefaults() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMethodCount.java"), expected);
    }

    @Test
    public void testThrees() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_PACKAGE_METHODS, 5, 3),
            "15:1: " + getCheckMessage(MSG_PRIVATE_METHODS, 5, 3),
            "15:1: " + getCheckMessage(MSG_PROTECTED_METHODS, 5, 3),
            "15:1: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "15:1: " + getCheckMessage(MSG_MANY_METHODS, 20, 3),
            "21:3: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "21:3: " + getCheckMessage(MSG_MANY_METHODS, 5, 3),
            "57:3: " + getCheckMessage(MSG_PUBLIC_METHODS, 5, 3),
            "57:3: " + getCheckMessage(MSG_MANY_METHODS, 5, 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCount1.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {

        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_PRIVATE_METHODS, 1, 0),
            "21:5: " + getCheckMessage(MSG_MANY_METHODS, 3, 2),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCount2.java"), expected);
    }

    @Test
    public void testWithPackageModifier() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_MANY_METHODS, 5, 2),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCount3.java"), expected);
    }

    @Test
    public void testOnInterfaceDefinitionWithField() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMethodCount4.java"), expected);
    }

    @Test
    public void testWithInterfaceDefinitionInClass() throws Exception {

        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_MANY_METHODS, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCount5.java"), expected);
    }

    @Test
    public void testPartialTokens() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMethodCount6.java"), expected);
    }

    @Test
    public void testCountMethodToCorrectDefinition() throws Exception {

        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_MANY_METHODS, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCount7.java"), expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {

        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_PUBLIC_METHODS, 2, 1),
            "27:5: " + getCheckMessage(MSG_PUBLIC_METHODS, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputMethodCountInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testMethodCountRecords() throws Exception {
        final int max = 2;

        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_MANY_METHODS, 3, max),
            "50:13: " + getCheckMessage(MSG_MANY_METHODS, 3, max),
            "68:5: " + getCheckMessage(MSG_MANY_METHODS, 3, max),
            "78:9: " + getCheckMessage(MSG_MANY_METHODS, 3, max),
            "87:13: " + getCheckMessage(MSG_MANY_METHODS, 4, max),
            "99:21: " + getCheckMessage(MSG_MANY_METHODS, 3, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodCountRecords.java"), expected);
    }

}
