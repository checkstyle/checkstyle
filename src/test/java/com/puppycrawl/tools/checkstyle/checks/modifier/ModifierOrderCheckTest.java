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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_ANNOTATION_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_MODIFIER_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModifierOrderCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/modifierorder";
    }

    @Test
    public void testGetRequiredTokens() {
        final ModifierOrderCheck checkObj = new ModifierOrderCheck();
        final int[] expected = {TokenTypes.MODIFIERS};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testItOne() throws Exception {
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "19:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "25:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "35:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "40:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "50:35: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderItOne.java"), expected);
    }

    @Test
    public void testItTwo() throws Exception {
        final String[] expected = {

            "15:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "57:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "default"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderItTwo.java"), expected);
    }

    @Test
    public void testDefaultMethods()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderDefaultMethods.java"), expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final ModifierOrderCheck modifierOrderCheckObj = new ModifierOrderCheck();
        final int[] actual = modifierOrderCheckObj.getDefaultTokens();
        final int[] expected = {TokenTypes.MODIFIERS};
        final int[] unexpectedArray = {
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
        assertWithMessage("Default tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
        final int[] unexpectedEmptyArray = CommonUtil.EMPTY_INT_ARRAY;
        assertWithMessage("Default tokens should not be empty array")
            .that(actual)
            .isNotEqualTo(unexpectedEmptyArray);
        assertWithMessage("Invalid default tokens")
            .that(actual)
            .isNotEqualTo(unexpectedArray);
        assertWithMessage("Default tokens should not be null")
            .that(actual)
            .isNotNull();
    }

    @Test
    public void testGetAcceptableTokens() {
        final ModifierOrderCheck modifierOrderCheckObj = new ModifierOrderCheck();
        final int[] actual = modifierOrderCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.MODIFIERS};
        final int[] unexpectedArray = {
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
        final int[] unexpectedEmptyArray = CommonUtil.EMPTY_INT_ARRAY;
        assertWithMessage("Default tokens should not be empty array")
            .that(actual)
            .isNotEqualTo(unexpectedEmptyArray);
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isNotEqualTo(unexpectedArray);
        assertWithMessage("Acceptable tokens should not be null")
            .that(actual)
            .isNotNull();
    }

    @Test
    public void testSkipTypeAnnotations() throws Exception {
        final String[] expected = {
            "110:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MethodAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderTypeAnnotations.java"),
            expected);
    }

    @Test
    public void testAnnotationOnAnnotationDeclaration() throws Exception {
        final String[] expected = {
            "9:8: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@InterfaceAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderAnnotationDeclaration.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealed() throws Exception {
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "26:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "44:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "sealed"),
            "50:11: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "53:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
            "58:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "non-sealed"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModifierOrderSealedAndNonSealed.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealedNoViolation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModifierOrderSealedAndNonSealedNoViolation.java"),
                expected);
    }

}
