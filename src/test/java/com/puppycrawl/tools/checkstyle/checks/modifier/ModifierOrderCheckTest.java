///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModifierOrderCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
    public void testDefaultModifierOrder() throws Exception {
        final String[] expected = {
            "32:11: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderDefault.java"), expected);
    }

    @Test
    public void testOpenjdkModifierOrder() throws Exception {
        final String[] expected = {
            "72:19: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
            "77:19: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderOpenjdk.java"), expected);
    }

    @Test
    public void testCustomModifierOrder() throws Exception {
        final String[] expected = {
            "68:19: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
            "73:19: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderCustom.java"), expected);
    }

    @Test
    public void testInvalidModifierOrderProperty() {
        final ModifierOrderCheck check = new ModifierOrderCheck();
        final IllegalArgumentException exception =
                getExpectedThrowable(IllegalArgumentException.class,
                        () -> check.setModifierOrder("invalid_style"));

        assertWithMessage("Invalid exception message")
            .that(exception.getMessage())
            .isEqualTo("Invalid modifier in custom order: invalid_style");
    }

    @Test
    public void testModifierOrderCaseInsensitive() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        check.setModifierOrder("DEFAULT");
        check.setModifierOrder("OpenJDK");

        check.setModifierOrder(" default ");
        check.setModifierOrder(" openjdk ");

        assertWithMessage("Setting case-insensitive style names should not throw")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testCustomModifierOrderWithWhitespace() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        check.setModifierOrder("public, static , final");

        assertWithMessage("Setting modifier order with whitespace should not throw")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testAllValidModifiers() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        check.setModifierOrder("public");
        check.setModifierOrder("protected");
        check.setModifierOrder("private");
        check.setModifierOrder("abstract");
        check.setModifierOrder("default");
        check.setModifierOrder("static");
        check.setModifierOrder("sealed");
        check.setModifierOrder("non-sealed");
        check.setModifierOrder("final");
        check.setModifierOrder("transient");
        check.setModifierOrder("volatile");
        check.setModifierOrder("synchronized");
        check.setModifierOrder("native");
        check.setModifierOrder("strictfp");

        assertWithMessage("All valid modifier keywords should be accepted without throwing")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testInvalidModifierInCustomOrder() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        final String[] invalidModifiers = {
            "publick", "privat", "statick", "finl", "abstractt",
            "transientt", "volatilee", "synchronize", "nativee", "strictfpp",
            "unknown", "modifier", "test", "",
        };

        for (String invalidModifier : invalidModifiers) {
            final IllegalArgumentException exception =
                    getExpectedThrowable(IllegalArgumentException.class,
                            () -> check.setModifierOrder(invalidModifier));

            assertWithMessage("Invalid exception message for: " + invalidModifier)
                .that(exception.getMessage())
                .isEqualTo("Invalid modifier in custom order: " + invalidModifier);
        }
    }

    @Test
    public void testModifierOrderPropertyActuallyChangesBehavior() throws Exception {
        final String[] expectedDefault = {
            "17:11: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderFinalStatic.java"), expectedDefault);
    }

    @Test
    public void testCustomModifierOrderWithMultipleModifiers() throws Exception {
        final ModifierOrderCheck check = new ModifierOrderCheck();
        check.setModifierOrder("final, public, static");

        assertWithMessage("Setting multiple custom modifiers should not throw")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testTrimmingOfWhitespaceInCustomModifierOrder() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputModifierOrderWhitespaceTrim.java"), expected);
    }

    @Test
    public void testTrimmingOfWhitespaceWithInvalidModifier() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        final IllegalArgumentException exception =
                getExpectedThrowable(IllegalArgumentException.class,
                        () -> check.setModifierOrder(" publick "));

        assertWithMessage("Invalid exception message should show trimmed modifier")
            .that(exception.getMessage())
            .isEqualTo("Invalid modifier in custom order: publick");
    }

    @Test
    public void testTrimmingMakesInvalidModifierValid() {
        final ModifierOrderCheck check = new ModifierOrderCheck();

        check.setModifierOrder(" public , static ");

        assertWithMessage("Whitespace-padded valid modifiers should be accepted without throwing")
            .that(check)
            .isNotNull();
    }

    @Test
    public void testCustomModifierOrderBehaviorDifferentFromDefault() throws Exception {
        final String[] expectedOpenJdk = {
            "18:21: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderDefaultStatic.java"), expectedOpenJdk);
    }

    @Test
    public void testItOne() throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "20:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "26:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "36:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "41:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation2"),
            "51:35: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MyAnnotation4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderItOne.java"), expected);
    }

    @Test
    public void testItTwo() throws Exception {
        final String[] expected = {

            "16:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "final"),
            "58:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "default"),
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
    public void testSkipTypeAnnotationsOne() throws Exception {
        final String[] expected = {
            "103:13: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@MethodAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderTypeAnnotationsOne.java"),
            expected);
    }

    @Test
    public void testSkipTypeAnnotationsTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderTypeAnnotationsTwo.java"),
            expected);
    }

    @Test
    public void testAnnotationOnAnnotationDeclaration() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_ANNOTATION_ORDER, "@InterfaceAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderAnnotationDeclaration.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealed() throws Exception {
        final String[] expected = {
            "12:8: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "29:12: " + getCheckMessage(MSG_MODIFIER_ORDER, "private"),
            "47:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "sealed"),
            "53:11: " + getCheckMessage(MSG_MODIFIER_ORDER, "public"),
            "56:14: " + getCheckMessage(MSG_MODIFIER_ORDER, "static"),
            "61:10: " + getCheckMessage(MSG_MODIFIER_ORDER, "non-sealed"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderSealedAndNonSealed.java"), expected);
    }

    @Test
    public void testModifierOrderSealedAndNonSealedNoViolation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputModifierOrderSealedAndNonSealedNoViolation.java"),
                expected);
    }

}
