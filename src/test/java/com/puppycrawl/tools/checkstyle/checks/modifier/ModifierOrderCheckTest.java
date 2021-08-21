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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_ANNOTATION_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck.MSG_MODIFIER_ORDER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "19:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "25:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "35:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "40:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "50:35: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation4"),
            "158:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "default"),
        };
        verifyWithInlineConfigParser(checkConfig,
            getPath("InputModifierOrderIt.java"), expected);
    }

    @Test
    public void testDefaultMethods()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(checkConfig,
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
        assertArrayEquals(expected, actual, "Default tokens are invalid");
        final int[] unexpectedEmptyArray = CommonUtil.EMPTY_INT_ARRAY;
        assertNotSame(unexpectedEmptyArray, actual, "Default tokens should not be empty array");
        assertNotSame(unexpectedArray, actual, "Invalid default tokens");
        assertNotNull(actual, "Default tokens should not be null");
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
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
        final int[] unexpectedEmptyArray = CommonUtil.EMPTY_INT_ARRAY;
        assertNotSame(
                unexpectedEmptyArray, actual, "Default tokens should not be empty array");
        assertNotSame(unexpectedArray, actual, "Invalid acceptable tokens");
        assertNotNull(actual, "Acceptable tokens should not be null");
    }

    @Test
    public void testSkipTypeAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "110:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MethodAnnotation"),
        };
        verifyWithInlineConfigParser(checkConfig,
            getPath("InputModifierOrderTypeAnnotations.java"),
            expected);
    }

    @Test
    public void testAnnotationOnAnnotationDeclaration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "9:8: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@InterfaceAnnotation"),
        };
        verifyWithInlineConfigParser(checkConfig,
            getPath("InputModifierOrderAnnotationDeclaration.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "26:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "44:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "sealed"),
            "50:11: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "53:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
            "58:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "non-sealed"),
        };
        verifyWithInlineConfigParser(checkConfig,
            getNonCompilablePath("InputModifierOrderSealedAndNonSealed.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealedNoViolation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ModifierOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(checkConfig,
            getNonCompilablePath("InputModifierOrderSealedAndNonSealedNoViolation.java"), expected);
    }

}
