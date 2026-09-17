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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/redundantmodifier";
    }

    @Test
    public void testClassesInsideOfInterfaces() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, "static"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "30:5: " + getCheckMessage(MSG_KEY, "public"),
            "36:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierClassesInsideOfInterfaces.java"),
            expected);
    }

    @Test
    public void testItOne() throws Exception {
        final String[] expected = {
            "59:12: " + getCheckMessage(MSG_KEY, "static"),
            "62:9: " + getCheckMessage(MSG_KEY, "public"),
            "68:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "72:9: " + getCheckMessage(MSG_KEY, "public"),
            // "75:9: Redundant 'abstract' modifier.",
            "79:9: " + getCheckMessage(MSG_KEY, "final"),
            "86:13: " + getCheckMessage(MSG_KEY, "final"),
            "95:12: " + getCheckMessage(MSG_KEY, "final"),
            "105:1: " + getCheckMessage(MSG_KEY, "abstract"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierItOne.java"), expected);
    }

    @Test
    public void testItTwo() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, "public"),
            "24:5: " + getCheckMessage(MSG_KEY, "final"),
            "25:5: " + getCheckMessage(MSG_KEY, "static"),
            "27:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "abstract"),
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
            "15:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInInterface.java"), expected);
    }

    @Test
    public void testEnumConstructorIsImplicitlyPrivate() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierConstructorModifier.java"), expected);
    }

    @Test
    public void testInnerTypeInInterfaceIsImplicitlyStatic() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "static"),
            "18:5: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStaticInInnerTypeOfInterface.java"),
            expected);
    }

    @Test
    public void testNotPublicClassConstructorHasNotPublicModifier() throws Exception {

        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierPublicModifierInNotPublicClass.java"),
            expected);
    }

    @Test
    public void testNestedClassConsInPublicInterfaceHasValidPublicModifier() throws Exception {

        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY, "public"),
            "28:13: " + getCheckMessage(MSG_KEY, "public"),
            "31:21: " + getCheckMessage(MSG_KEY, "public"),
            "43:12: " + getCheckMessage(MSG_KEY, "public"),
            "52:17: " + getCheckMessage(MSG_KEY, "public"),
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
            "14:5: " + getCheckMessage(MSG_KEY, "static"),
            "19:9: " + getCheckMessage(MSG_KEY, "static"),
            "24:9: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStaticModifierInNestedEnum.java"),
            expected);
    }

    @Test
    public void testFinalInAnonymousClass()
            throws Exception {
        final String[] expected = {
            "25:20: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInAnonymousClass.java"),
            expected);
    }

    @Test
    public void testFinalInTryWithResource() throws Exception {
        final String[] expected = {
            "40:14: " + getCheckMessage(MSG_KEY, "final"),
            "46:14: " + getCheckMessage(MSG_KEY, "final"),
            "48:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInTryWithResource.java"),
            expected);
    }

    @Test
    public void testFinalInAbstractMethods() throws Exception {
        final String[] expected = {
            "14:33: " + getCheckMessage(MSG_KEY, "final"),
            "19:49: " + getCheckMessage(MSG_KEY, "final"),
            "23:17: " + getCheckMessage(MSG_KEY, "final"),
            "29:24: " + getCheckMessage(MSG_KEY, "final"),
            "39:33: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInAbstractMethods.java"),
            expected);
    }

    @Test
    public void testEnumMethods() throws Exception {
        final String[] expected = {
            "17:16: " + getCheckMessage(MSG_KEY, "final"),
            "32:16: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInEnumMethods.java"), expected);
    }

    @Test
    public void testEnumStaticMethodsInPublicClass() throws Exception {
        final String[] expected = {
            "23:23: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierFinalInEnumStaticMethods.java"), expected);
    }

    @Test
    public void testAnnotationOnEnumConstructor() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "private"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierAnnotationOnEnumConstructor.java"),
                expected);
    }

    @Test
    public void testPrivateMethodInPrivateClass() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierPrivateMethodInPrivateClass.java"),
                expected);
    }

    @Test
    public void testTryWithResourcesBlock() throws Exception {
        final String[] expected = {
            "21:19: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierTryWithResources.java"),
                expected);
    }

    @Test
    public void testNestedDef() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, "public"),
            "14:5: " + getCheckMessage(MSG_KEY, "static"),
            "15:5: " + getCheckMessage(MSG_KEY, "public"),
            "15:12: " + getCheckMessage(MSG_KEY, "static"),
            "19:5: " + getCheckMessage(MSG_KEY, "static"),
            "19:12: " + getCheckMessage(MSG_KEY, "public"),
            "25:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:5: " + getCheckMessage(MSG_KEY, "public"),
            "28:12: " + getCheckMessage(MSG_KEY, "static"),
            "34:5: " + getCheckMessage(MSG_KEY, "public"),
            "34:12: " + getCheckMessage(MSG_KEY, "abstract"),
            "34:21: " + getCheckMessage(MSG_KEY, "static"),
            "42:1: " + getCheckMessage(MSG_KEY, "abstract"),
            "44:5: " + getCheckMessage(MSG_KEY, "public"),
            "44:12: " + getCheckMessage(MSG_KEY, "static"),
            "51:9: " + getCheckMessage(MSG_KEY, "public"),
            "51:16: " + getCheckMessage(MSG_KEY, "static"),
            "56:13: " + getCheckMessage(MSG_KEY, "public"),
            "56:20: " + getCheckMessage(MSG_KEY, "static"),
            "62:13: " + getCheckMessage(MSG_KEY, "public"),
            "62:20: " + getCheckMessage(MSG_KEY, "static"),
            "68:13: " + getCheckMessage(MSG_KEY, "public"),
            "68:20: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(getPath(
                "InputRedundantModifierNestedDef.java"), expected);
    }

    @Test
    public void testRecords() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, "static"),
            "20:9: " + getCheckMessage(MSG_KEY, "final"),
            "20:15: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
            "34:9: " + getCheckMessage(MSG_KEY, "final"),
            "34:15: " + getCheckMessage(MSG_KEY, "static"),
            "42:13: " + getCheckMessage(MSG_KEY, "static"),
            "48:1: " + getCheckMessage(MSG_KEY, "final"),
            "50:5: " + getCheckMessage(MSG_KEY, "final"),
            "53:5: " + getCheckMessage(MSG_KEY, "static"),
            "57:9: " + getCheckMessage(MSG_KEY, "final"),
            "57:15: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierRecords.java"), expected);
    }

    @Test
    public void testSealedClasses() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_KEY, "final"),
            "15:10: " + getCheckMessage(MSG_KEY, "public"),
            "15:17: " + getCheckMessage(MSG_KEY, "static"),
            "20:4: " + getCheckMessage(MSG_KEY, "abstract"),
            "20:13: " + getCheckMessage(MSG_KEY, "public"),
            "24:4: " + getCheckMessage(MSG_KEY, "public"),
            "24:12: " + getCheckMessage(MSG_KEY, "static"),
            "28:9: " + getCheckMessage(MSG_KEY, "abstract"),
            "28:18: " + getCheckMessage(MSG_KEY, "public"),
            "33:4: " + getCheckMessage(MSG_KEY, "public"),
            "33:11: " + getCheckMessage(MSG_KEY, "static"),
            "37:4: " + getCheckMessage(MSG_KEY, "public"),
            "37:11: " + getCheckMessage(MSG_KEY, "static"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierSealedClasses.java"), expected);
    }

    @Test
    public void testStrictfpWithVersionBeforeJava9() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:16: " + getCheckMessage(MSG_KEY, "static"),
            "35:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStrictfpWithVersionBeforeJava9.java"),
                expected);
    }

    @Test
    public void testStrictfpWithOldVersion() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "28:9: " + getCheckMessage(MSG_KEY, "public"),
            "28:16: " + getCheckMessage(MSG_KEY, "static"),
            "35:9: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStrictfpWithOldVersion.java"),
                expected);
    }

    @Test
    public void testStrictfpWithJava17() throws Exception {
        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY, "strictfp"),
            "19:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "22:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "25:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "28:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "31:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "31:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "35:9: " + getCheckMessage(MSG_KEY, "public"),
            "35:16: " + getCheckMessage(MSG_KEY, "static"),
            "35:23: " + getCheckMessage(MSG_KEY, "strictfp"),
            "43:9: " + getCheckMessage(MSG_KEY, "final"),
            "43:15: " + getCheckMessage(MSG_KEY, "strictfp"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStrictfpWithJava17.java"),
                expected);
    }

    @Test
    public void testStrictfpWithDefaultVersion() throws Exception {
        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_KEY, "strictfp"),
            "19:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "22:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "25:5: " + getCheckMessage(MSG_KEY, "strictfp"),
            "28:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "31:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "31:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "35:9: " + getCheckMessage(MSG_KEY, "public"),
            "35:16: " + getCheckMessage(MSG_KEY, "static"),
            "35:23: " + getCheckMessage(MSG_KEY, "strictfp"),
            "43:9: " + getCheckMessage(MSG_KEY, "final"),
            "43:15: " + getCheckMessage(MSG_KEY, "strictfp"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierStrictfpWithDefaultVersion.java"),
                expected);
    }

    @Test
    public void testFinalUnnamedVariablesWithDefaultVersion() throws Exception {
        final String[] expected = {
            "19:26: " + getCheckMessage(MSG_KEY, "final"),
            "25:9: " + getCheckMessage(MSG_KEY, "final"),
            "35:18: " + getCheckMessage(MSG_KEY, "final"),
            "45:14: " + getCheckMessage(MSG_KEY, "final"),
            "52:14: " + getCheckMessage(MSG_KEY, "final"),
            "55:18: " + getCheckMessage(MSG_KEY, "final"),
            "66:53: " + getCheckMessage(MSG_KEY, "final"),
            "70:53: " + getCheckMessage(MSG_KEY, "final"),
            "70:70: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierFinalUnnamedVariables.java"),
                expected);
    }

    @Test
    public void testFinalUnnamedVariablesWithOldVersion() throws Exception {
        final String[] expected = {
            "41:14: " + getCheckMessage(MSG_KEY, "final"),
            "48:14: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputRedundantModifierFinalUnnamedVariablesWithOldVersion.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierCompactSourceFile.java"),
                expected);
    }

}
