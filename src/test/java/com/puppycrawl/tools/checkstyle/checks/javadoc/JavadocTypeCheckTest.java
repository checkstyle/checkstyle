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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNKNOWN_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNUSED_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();
        assertWithMessage("JavadocTypeCheck#getRequiredTokens should return empty array by default")
            .that(javadocTypeCheck.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();

        final int[] actual = javadocTypeCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypePublicOnly.java"), expected);
    }

    @Test
    public void testProtected() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypePublicOnly1.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerInterfaces.java"),
               expected);
    }

    @Test
    public void testProtest() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void testPkg() throws Exception {
        final String[] expected = {
            "53:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerClasses.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWhitespace.java"), expected);
    }

    @Test
    public void testAuthorRegularEx()
            throws Exception {
        final String[] expected = {
            "31:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "67:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "103:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeJavadoc.java"), expected);
    }

    @Test
    public void testAuthorRegularExError()
            throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "31:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "40:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "58:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "67:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "76:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "94:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "103:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "112:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeJavadoc_1.java"), expected);
    }

    @Test
    public void testVersionRequired()
            throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWhitespace_1.java"), expected);
    }

    @Test
    public void testVersionRegularEx()
            throws Exception {
        final String[] expected = {
            "31:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "67:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "103:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeJavadoc_3.java"), expected);
    }

    @Test
    public void testVersionRegularExError()
            throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "31:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "40:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "49:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "58:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "67:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "76:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "85:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "94:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "103:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "112:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "121:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeJavadoc_2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "137:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc.java"),
               expected);
    }

    @Test
    public void testLimitViolationsBySpecifyingTokens() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc_2.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final String[] expected = {
            "137:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc_1.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "25:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <C456>"),
            "59:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "62:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <B>"),
            "75:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTypeParamsTags_1.java"), expected);
    }

    @Test
    public void testAllowMissingTypeParameters() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "58:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "74:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTypeParamsTags.java"), expected);
    }

    @Test
    public void testDontAllowUnusedParameterTag() throws Exception {
        final String[] expected = {
            "20:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void testBadTag() throws Exception {
        final String[] expected = {
            "19:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeBadTag.java"),
               expected);
    }

    @Test
    public void testBadTagSuppress() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeBadTag_1.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsDefault() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsWithFullyQualifiedName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_1.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsAllowed() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_2.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsNotAllowed() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_3.java"),
            expected);
    }

    @Test
    public void testJavadocTypeRecords() throws Exception {
        final String[] expected = {
            "24:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "33:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "42:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "55:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "65:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocTypeRecords.java"), expected);
    }

    @Test
    public void testJavadocTypeRecordComponents() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocTypeRecordComponents.java"), expected);
    }

    @Test
    public void testJavadocTypeRecordComponents2() throws Exception {

        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <X>"),
            "48:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "59:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "notMyString"),
            "62:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myString"),
            "62:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myInt"),
            "66:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "68:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myList"),
            "75:1: " + getCheckMessage(MSG_MISSING_TAG, "@param X"),
            "78:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "notMyString"),
            "81:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "81:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myInt"),
            "81:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myString"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocTypeRecordComponents2.java"), expected);
    }

    @Test
    public void testJavadocTypeInterfaceMemberScopeIsPublic() throws Exception {

        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<T>"),
            "24:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeInterfaceMemberScopeIsPublic.java"), expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTestTrimProperty.java"), expected);
    }
}
