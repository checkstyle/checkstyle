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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_DUPLICATE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG_GENERAL;

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
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
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
    public void testThrowsDetectionOne() throws Exception {
        final String[] expected = {
            "26:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "38:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "42:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "56:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "61:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "73:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "77:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "89:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "93:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodThrowsDetectionOne.java"), expected);
    }

    @Test
    public void testThrowsDetectionTwo() throws Exception {
        final String[] expected = {
            "27:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "42:31: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "55:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "71:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
            "85:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodThrowsDetectionTwo.java"), expected);
    }

    @Test
    public void testExtraThrowsOne() throws Exception {
        final String[] expected = {
            "51:56: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "67:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "80:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "93:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodExtraThrowsOne.java"), expected);
    }

    @Test
    public void testExtraThrowsTwo() throws Exception {
        final String[] expected = {
            "46:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "FileNotFoundException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodExtraThrowsTwo.java"), expected);
    }

    @Test
    public void testIgnoreThrowsOne() throws Exception {
        final String[] expected = {
            "35:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "38:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "55:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodIgnoreThrowsOne.java"), expected);
    }

    @Test
    public void testIgnoreThrowsTwo() throws Exception {
        final String[] expected = {
            "52:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "109:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodIgnoreThrowsTwo.java"), expected);
    }

    @Test
    public void testTagsOne() throws Exception {
        final String[] expected = {
            "50:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "89:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testTagsTwo() throws Exception {
        final String[] expected = {
            "28:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "35: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "44: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "51:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "60:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "66:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "66:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "71:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "79:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "83:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "85:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "85:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "91:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "92: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "101:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),

        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTags1One.java"), expected);
    }

    @Test
    public void testTagsThree() throws Exception {
        final String[] expected = {
            "66:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "72:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "115: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "115:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTags1Three.java"), expected);
    }

    @Test
    public void testStrictJavadocOne() throws Exception {
        final String[] expected = {
            "78:29: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "83:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "88:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "93:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnlyOne.java"), expected);
    }

    @Test
    public void testStrictJavadocTwo() throws Exception {
        final String[] expected = {
            "22:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnlyTwo.java"), expected);
    }

    @Test
    public void testNoJavadocOne() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnly1One.java"), expected);
    }

    @Test
    public void testNoJavadocTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodPublicOnly1Two.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final String[] expected = {
            "48:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "53:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodProtectedScopeJavadocTwo.java"), expected);
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
            "28: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "30: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "32: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "34: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodNoJavadocDefault.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final String[] expected = {
            "28: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "30: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodNoJavadocProtectedScope.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final String[] expected = {
            "28: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "32: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "34: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
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
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "35:26: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "number"),
            "66: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "76: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "88: " + getCheckMessage(MSG_RETURN_EXPECTED),
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
            "38:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "41:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
            "66:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "69:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowUndocumentedParamsTags() throws Exception {
        final String[] expected = {
            "34:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam"),
            "35:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam2"),
            "37:13: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam3"),
            "38:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam4"),
            "66:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "t"),
            "69:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "w"),
            "78:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "80:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "y"),
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
            "30:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "47:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "58:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "59:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
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
            "19:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "24:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "44:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "49:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "55:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "60:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodInheritDoc.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final String[] expected = {
            "20:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "31:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
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
            "22:49: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "25:50: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testJavadocMethodRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "30:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "44:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "56:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "64:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "74:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "79:9: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "myString"),
            "83:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "93:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "98:35: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "myInt"),
            "103:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodRecordsAndCompactCtors.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final int[] actual = check.getRequiredTokens();
        assertWithMessage("Required tokens differ from expected")
            .that(actual)
            .isEqualTo(expected);
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
            "22:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "a"),
            "27:43: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodDefaultAccessModifier.java"), expected);
    }

    @Test
    public void testAccessModifierEnum() throws Exception {
        final String[] expected = {
            "28:17: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "i"),
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
            "21: " + msgReturnExpectedCustom,
            "25:9: " + msgUnusedTagCustom,
            "32:22: " + msgExpectedTagCustom,
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocMethodCustomMessage.java"), expected);
    }

    @Test
    public void test1() throws Exception {
        final String[] expected = {
            "29: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethod1.java"), expected);
    }

    @Test
    public void test2() throws Exception {
        final String[] expected = {
            "21:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<"),
            "25:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<X>"),
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

    @Test
    public void testJavadocMethodRecords() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodRecords.java"), expected);
    }

    @Test
    public void testJavadocMethodRecords2() throws Exception {
        final String[] expected = {
            "38:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "lastName"),
            "53:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "lastName"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodRecords2.java"), expected);
    }

    @Test
    public void testJavadocMethodRecords3() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocMethodRecords3.java"), expected);
    }

    @Test
    public void testJavadocMethodAboveComments() throws Exception {
        final String[] expected = {
            "22:29: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "31:30: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "58:30: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "69:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "80:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "90:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "100:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodAboveComments.java"), expected);
    }

    @Test
    public void testJavadocMethodAllowInlineReturn() throws Exception {
        final String[] expected = {
            "32: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "39: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodAllowInlineReturn.java"), expected);
    }

    @Test
    public void testJavadocMethodDoNotAllowInlineReturn() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMethodDoNotAllowInlineReturn.java"), expected);
    }
}
