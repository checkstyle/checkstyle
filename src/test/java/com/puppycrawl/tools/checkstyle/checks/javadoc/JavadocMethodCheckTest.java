///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_DUPLICATE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG_GENERAL;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocMethodCheck javadocMethodCheck = new JavadocMethodCheck();

        final int[] actual = javadocMethodCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testThrowsDetection() throws Exception {
        final String[] expected = {
            "25:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "37:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "41:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "55:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "60:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "72:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "76:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "88:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "92:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "105:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "120:31: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "133:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "149:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
            "163:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodThrowsDetection.java"), expected);
    }

    @Test
    public void testExtraThrows() throws Exception {
        final String[] expected = {
            "54:56: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "70:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "83:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "96:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "137:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "FileNotFoundException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodExtraThrows.java"), expected);
    }

    @Test
    public void testIgnoreThrows() throws Exception {
        final String[] expected = {
            "40:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "43:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "60:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "141:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "198:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodIgnoreThrows.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "37: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "46: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "53:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "62:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "68:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "68:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "73:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "81:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "85:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "87:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "87:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "93:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "94: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "103:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "273:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "279:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "322: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "322:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "361:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "400:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final String[] expected = {
            "77:29: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "82:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "87:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "92:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "102:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnly1.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final String[] expected = {
            "87:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "92:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodProtectedScopeJavadoc.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "29: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "31: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "33: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodNoJavadocDefault.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "29: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodNoJavadocProtectedScope.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "31: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "33: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodNoJavadocOnlyPrivateScope.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodMissingJavadocNoMissingTags.java"),
                expected);
    }

    @Test
    public void testSurroundingAccessModifier() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodSurroundingAccessModifier.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "34:26: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "number"),
            "65: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "75: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "87: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodMissingJavadocTagsDefault.java"), expected);
    }

    @Test
    public void testSetterGetter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testTypeParamsTags() throws Exception {
        final String[] expected = {
            "37:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "40:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
            "65:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "68:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowUndocumentedParamsTags() throws Exception {
        final String[] expected = {
            "33:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam"),
            "34:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam2"),
            "36:13: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam3"),
            "37:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam4"),
            "65:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "t"),
            "68:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "w"),
            "77:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "79:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodParamsTags.java"), expected);
    }

    @Test
    public void test11684081() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod_02.java"), expected);
    }

    @Test
    public void test11684083() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod_03.java"), expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final String[] expected = {
            "29:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "46:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "57:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "58:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodGenerics.java"), expected);
    }

    @Test
    public void test1379666() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod_1379666.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "23:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "43:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "48:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "54:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "59:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodInheritDoc.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "30:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testJava8ReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodReceiverParameter.java"), expected);
    }

    @Test
    public void testJavadocInMethod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodJavadocInMethod.java"), expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final String[] expected = {
            "21:49: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "24:50: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testJavadocMethodRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "29:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "43:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "55: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "63:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "73: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "81:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "91:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "96:35: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "myInt"),
            "101:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodRecordsAndCompactCtors.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final int[] actual = check.getRequiredTokens();
        assertWithMessage("Required tokens differ from expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTokenToString() throws Exception {
        final Class<?> tokenType = Class.forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                + "JavadocMethodCheck$Token");
        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        tokenConstructor.setAccessible(true);
        final Object token = tokenConstructor.newInstance("tokenName", 1, 1);
        final Method toString = token.getClass().getDeclaredMethod("toString");
        final String result = (String) toString.invoke(token);
        assertWithMessage("Invalid toString result")
            .that(result)
            .isEqualTo("Token[tokenName(1x1)]");
    }

    @Test
    public void testWithoutLogErrors() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodLoadErrors.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testCompilationUnit() throws Exception {
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodCompilationUnit.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testDefaultAccessModifier() throws Exception {
        final String[] expected = {
            "21:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "a"),
            "26:43: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodDefaultAccessModifier.java"), expected);
    }

    @Test
    public void testAccessModifierEnum() throws Exception {
        final String[] expected = {
            "27:17: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodEnum.java"), expected);
    }

    @Test
    public void testCustomMessages() throws Exception {
        final String msgReturnExpectedCustom =
            "@return tag should be present and have description :)";
        final String msgUnusedTagCustom = "Unused @param tag for 'unused' :)";
        final String msgExpectedTagCustom = "Expected @param tag for 'a' :)";
        final String[] expected = {
            "20: " + msgReturnExpectedCustom,
            "24:9: " + msgUnusedTagCustom,
            "31:22: " + msgExpectedTagCustom,
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocMethodCustomMessage.java"), expected);
    }

    @Test
    public void test1() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod1.java"), expected);
    }

    @Test
    public void test2() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<"),
            "19:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<X>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod2.java"), expected);
    }

    @Test
    public void test3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod3.java"), expected);
    }
}
