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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RedundantModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/redundantmodifier";
    }

    @Test
    public void testClassesInsideOfInterfaces() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, "static"),
            "25:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierClassesInsideOfInterfaces.java"),
            expected);
    }

    @Test
    public void testItOne() throws Exception {
        final String[] expected = {
            "57:12: " + getCheckMessage(MSG_KEY, "static"),
            "60:9: " + getCheckMessage(MSG_KEY, "public"),
            "66:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "69:9: " + getCheckMessage(MSG_KEY, "public"),
            // "72:9: Redundant 'abstract' modifier.",
            "75:9: " + getCheckMessage(MSG_KEY, "final"),
            "82:13: " + getCheckMessage(MSG_KEY, "final"),
            "91:12: " + getCheckMessage(MSG_KEY, "final"),
            "102:1: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierItOne.java"), expected);
    }

    @Test
    public void testItTwo() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "public"),
            "23:5: " + getCheckMessage(MSG_KEY, "final"),
            "24:5: " + getCheckMessage(MSG_KEY, "static"),
            "26:5: " + getCheckMessage(MSG_KEY, "public"),
            "27:5: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierItTwo.java"), expected);
    }

    @Test
    public void testStaticMethodInInterface()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStaticMethodInInterface.java"),
            expected);
    }

    @Test
    public void testFinalInInterface()
            throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInInterface.java"), expected);
    }

    @Test
    public void testEnumConstructorIsImplicitlyPrivate() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierConstructorModifier.java"), expected);
    }

    @Test
    public void testInnerTypeInInterfaceIsImplicitlyStatic() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "static"),
            "16:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStaticInInnerTypeOfInterface.java"),
            expected);
    }

    @Test
    public void testNotPublicClassConstructorHasNotPublicModifier() throws Exception {

        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierPublicModifierInNotPublicClass.java"),
            expected);
    }

    @Test
    public void testNestedClassConsInPublicInterfaceHasValidPublicModifier() throws Exception {

        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_KEY, "public"),
            "24:13: " + getCheckMessage(MSG_KEY, "public"),
            "26:21: " + getCheckMessage(MSG_KEY, "public"),
            "37:12: " + getCheckMessage(MSG_KEY, "public"),
            "45:17: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(
            getPath("InputRedundantModifierNestedClassInInt.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        final int[] actual = redundantModifierCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RESOURCE,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LAMBDA,
        };
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testWrongTokenType() {
        final RedundantModifierCheck obj = new RedundantModifierCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(TokenTypes.LITERAL_NULL, "null");

        final IllegalStateException exception =
                getExpectedThrowable(IllegalStateException.class,
                        () -> obj.visitToken(ast), "IllegalStateException was expected");

        assertWithMessage("Expected and actual violation messages do not match")
                .that(exception.getMessage())
                .isEqualTo("Unexpected token type: " + ast.getType());
    }

    @Test
    public void testGetRequiredTokens() {
        final RedundantModifierCheck redundantModifierCheckObj = new RedundantModifierCheck();
        final int[] actual = redundantModifierCheckObj.getRequiredTokens();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testNestedStaticEnum() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "static"),
            "16:9: " + getCheckMessage(MSG_KEY, "static"),
            "20:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStaticModifierInNestedEnum.java"),
            expected);
    }

    @Test
    public void testFinalInAnonymousClass()
            throws Exception {
        final String[] expected = {
            "22:20: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInAnonymousClass.java"),
            expected);
    }

    @Test
    public void testFinalInTryWithResource() throws Exception {
        final String[] expected = {
            "38:14: " + getCheckMessage(MSG_KEY, "final"),
            "43:14: " + getCheckMessage(MSG_KEY, "final"),
            "44:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInTryWithResource.java"),
            expected);
    }

    @Test
    public void testFinalInAbstractMethods() throws Exception {
        final String[] expected = {
            "12:33: " + getCheckMessage(MSG_KEY, "final"),
            "16:49: " + getCheckMessage(MSG_KEY, "final"),
            "19:17: " + getCheckMessage(MSG_KEY, "final"),
            "24:24: " + getCheckMessage(MSG_KEY, "final"),
            "33:33: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInAbstractMethods.java"),
            expected);
    }

    @Test
    public void testEnumMethods() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_KEY, "final"),
            "30:16: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInEnumMethods.java"), expected);
    }

    @Test
    public void testEnumStaticMethodsInPublicClass() throws Exception {
        final String[] expected = {
            "20:23: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInEnumStaticMethods.java"), expected);
    }

    @Test
    public void testAnnotationOnEnumConstructor() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierAnnotationOnEnumConstructor.java"),
                expected);
    }

    @Test
    public void testPrivateMethodInPrivateClass() throws Exception {
        final String[] expected = {
            "13:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierPrivateMethodInPrivateClass.java"),
                expected);
    }

    @Test
    public void testTryWithResourcesBlock() throws Exception {
        final String[] expected = {
            "18:19: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierTryWithResources.java"),
                expected);
    }

    @Test
    public void testNestedDef() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, "public"),
            "11:5: " + getCheckMessage(MSG_KEY, "static"),
            "12:5: " + getCheckMessage(MSG_KEY, "public"),
            "12:12: " + getCheckMessage(MSG_KEY, "static"),
            "13:5: " + getCheckMessage(MSG_KEY, "static"),
            "13:12: " + getCheckMessage(MSG_KEY, "public"),
            "16:9: " + getCheckMessage(MSG_KEY, "public"),
            "19:5: " + getCheckMessage(MSG_KEY, "public"),
            "19:12: " + getCheckMessage(MSG_KEY, "static"),
            "22:5: " + getCheckMessage(MSG_KEY, "public"),
            "22:12: " + getCheckMessage(MSG_KEY, "abstract"),
            "22:21: " + getCheckMessage(MSG_KEY, "static"),
            "26:1: " + getCheckMessage(MSG_KEY, "abstract"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:12: " + getCheckMessage(MSG_KEY, "static"),
            "32:9: " + getCheckMessage(MSG_KEY, "public"),
            "32:16: " + getCheckMessage(MSG_KEY, "static"),
            "34:13: " + getCheckMessage(MSG_KEY, "public"),
            "34:20: " + getCheckMessage(MSG_KEY, "static"),
            "37:13: " + getCheckMessage(MSG_KEY, "public"),
            "37:20: " + getCheckMessage(MSG_KEY, "static"),
            "40:13: " + getCheckMessage(MSG_KEY, "public"),
            "40:20: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputRedundantModifierNestedDef.java"), expected);
    }

    @Test
    public void testRecords() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "static"),
            "16:9: " + getCheckMessage(MSG_KEY, "final"),
            "16:15: " + getCheckMessage(MSG_KEY, "static"),
            "21:9: " + getCheckMessage(MSG_KEY, "static"),
            "27:9: " + getCheckMessage(MSG_KEY, "final"),
            "27:15: " + getCheckMessage(MSG_KEY, "static"),
            "32:13: " + getCheckMessage(MSG_KEY, "static"),
            "38:1: " + getCheckMessage(MSG_KEY, "final"),
            "40:5: " + getCheckMessage(MSG_KEY, "final"),
            "43:5: " + getCheckMessage(MSG_KEY, "static"),
            "47:9: " + getCheckMessage(MSG_KEY, "final"),
            "47:15: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierRecords.java"), expected);
    }

    @Test
    public void testSealedClasses() throws Exception {
        final String[] expected = {
            "11:4: " + getCheckMessage(MSG_KEY, "final"),
            "11:10: " + getCheckMessage(MSG_KEY, "public"),
            "11:17: " + getCheckMessage(MSG_KEY, "static"),
            "16:4: " + getCheckMessage(MSG_KEY, "abstract"),
            "16:13: " + getCheckMessage(MSG_KEY, "public"),
            "20:4: " + getCheckMessage(MSG_KEY, "public"),
            "20:12: " + getCheckMessage(MSG_KEY, "static"),
            "24:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "24:18: " + getCheckMessage(MSG_KEY, "public"),
            "29:4: " + getCheckMessage(MSG_KEY, "public"),
            "29:11: " + getCheckMessage(MSG_KEY, "static"),
            "33:4: " + getCheckMessage(MSG_KEY, "public"),
            "33:11: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierSealedClasses.java"), expected);
    }

    @Test
    public void testStrictfpWithVersionBeforeJava9() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "27:9: " + getCheckMessage(MSG_KEY, "public"),
            "27:16: " + getCheckMessage(MSG_KEY, "static"),
            "34:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierStrictfpWithVersionBeforeJava9.java"),
                expected);
    }

    @Test
    public void testStrictfpWithOldVersion() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "27:9: " + getCheckMessage(MSG_KEY, "public"),
            "27:16: " + getCheckMessage(MSG_KEY, "static"),
            "34:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierStrictfpWithOldVersion.java"),
                expected);
    }

    @Test
    public void testStrictfpWithJava17() throws Exception {
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY, "strictfp"),
            "18:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "21:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "24:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "27:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "30:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "30:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "34:9: " + getCheckMessage(MSG_KEY, "public"),
            "34:16: " + getCheckMessage(MSG_KEY, "static"),
            "34:23: " + getCheckMessage(MSG_KEY, "strictfp"),
            "42:9: " + getCheckMessage(MSG_KEY, "final"),
            "42:15: " + getCheckMessage(MSG_KEY, "strictfp"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierStrictfpWithJava17.java"),
                expected);
    }

    @Test
    public void testStrictfpWithDefaultVersion() throws Exception {
        final String[] expected = {
            "14:19: " + getCheckMessage(MSG_KEY, "strictfp"),
            "17:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "20:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "23:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "26:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "29:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "29:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "33:9: " + getCheckMessage(MSG_KEY, "public"),
            "33:16: " + getCheckMessage(MSG_KEY, "static"),
            "33:23: " + getCheckMessage(MSG_KEY, "strictfp"),
            "41:9: " + getCheckMessage(MSG_KEY, "final"),
            "41:15: " + getCheckMessage(MSG_KEY, "strictfp"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierStrictfpWithDefaultVersion.java"),
                expected);
    }

    @Test
    public void testFinalUnnamedVariablesWithDefaultVersion() throws Exception {
        final String[] expected = {
            "18:26: " + getCheckMessage(MSG_KEY, "final"),
            "24:9: " + getCheckMessage(MSG_KEY, "final"),
            "34:18: " + getCheckMessage(MSG_KEY, "final"),
            "44:14: " + getCheckMessage(MSG_KEY, "final"),
            "51:14: " + getCheckMessage(MSG_KEY, "final"),
            "54:18: " + getCheckMessage(MSG_KEY, "final"),
            "65:53: " + getCheckMessage(MSG_KEY, "final"),
            "69:53: " + getCheckMessage(MSG_KEY, "final"),
            "69:70: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierFinalUnnamedVariables.java"),
                expected);
    }

    @Test
    public void testFinalUnnamedVariablesWithOldVersion() throws Exception {
        final String[] expected = {
            "40:14: " + getCheckMessage(MSG_KEY, "final"),
            "47:14: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputRedundantModifierFinalUnnamedVariablesWithOldVersion.java"),
                expected);
    }
}
