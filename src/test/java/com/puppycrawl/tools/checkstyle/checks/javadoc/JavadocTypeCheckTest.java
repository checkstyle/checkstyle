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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNKNOWN_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNUSED_TAG_GENERAL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype";
    }

    @Test
    public void getRequiredTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();
        assertWithMessage("JavadocTypeCheck#getRequiredTokens should return empty array by default")
            .that(javadocTypeCheck.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void getAcceptableTokens() {
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
    public void tags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTags.java"), expected);
    }

    @Test
    public void inner() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeInner.java"), expected);
    }

    @Test
    public void strict() throws Exception {
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
    public void protectedTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypePublicOnly1Two.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerInterfaces.java"),
               expected);
    }

    @Test
    public void protest() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void pkg() throws Exception {
        final String[] expected = {
            "53:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerClasses.java"), expected);
    }

    @Test
    public void eclipse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void authorRequired() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWhitespace.java"), expected);
    }

    @Test
    public void authorRegularEx()
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
    public void authorRegularExError()
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
    public void versionRequired()
            throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWhitespace_1.java"), expected);
    }

    @Test
    public void versionRegularEx()
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
    public void versionRegularExError()
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
    public void scopes() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "137:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc.java"),
               expected);
    }

    @Test
    public void limitViolationsBySpecifyingTokens() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void scopes2() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc_2.java"),
               expected);
    }

    @Test
    public void excludeScope() throws Exception {
        final String[] expected = {
            "137:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeNoJavadoc_1.java"),
               expected);
    }

    @Test
    public void typeParameters() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "25:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <C456>"),
            "59:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "62:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <B>"),
            "75:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "79:5: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL, "@param"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTypeParamsTags_1.java"), expected);
    }

    @Test
    public void allowMissingTypeParameters() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "58:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "74:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTypeParamsTags.java"), expected);
    }

    @Test
    public void dontAllowUnusedParameterTag() throws Exception {
        final String[] expected = {
            "20:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
            "23:4: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL, "@param"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void badTag() throws Exception {
        final String[] expected = {
            "19:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
            "21:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
            "28:5: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeBadTag.java"),
               expected);
    }

    @Test
    public void badTagSuppress() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeBadTag_1.java"),
                expected);
    }

    @Test
    public void allowedAnnotationsDefault() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }

    @Test
    public void allowedAnnotationsWithFullyQualifiedName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_1.java"),
                expected);
    }

    @Test
    public void allowedAnnotationsAllowed() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_2.java"),
            expected);
    }

    @Test
    public void allowedAnnotationsNotAllowed() throws Exception {

        final String[] expected = {
            "38:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAllowedAnnotations_3.java"),
            expected);
    }

    @Test
    public void javadocTypeRecords() throws Exception {
        final String[] expected = {
            "24:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "33:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "42:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "55:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "65:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeRecords.java"), expected);
    }

    @Test
    public void javadocTypeRecordComponents() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeRecordComponents.java"), expected);
    }

    @Test
    public void javadocTypeParamDescriptionWithAngularTags() throws Exception {
        final String[] expected = {
            "44:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<P>"),
            "46:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <U>"),
            "50:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "region"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeParamDescriptionWithAngularTags.java"), expected);
    }

    @Test
    public void javadocTypeRecordParamDescriptionWithAngularTags() throws Exception {
        final String[] expected = {
            "51:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<P>"),
            "53:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <U>"),
            "57:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "region"),
            "60:1: " + getCheckMessage(MSG_MISSING_TAG, "@param a"),
            "73:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "e"),
            "76:1: " + getCheckMessage(MSG_MISSING_TAG, "@param c"),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputJavadocTypeRecordParamDescriptionWithAngularTags.java"),
                expected);
    }

    @Test
    public void javadocTypeRecordComponents2() throws Exception {

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
                getPath("InputJavadocTypeRecordComponents2.java"), expected);
    }

    @Test
    public void javadocTypeInterfaceMemberScopeIsPublic() throws Exception {

        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<T>"),
            "24:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeInterfaceMemberScopeIsPublic.java"), expected);
    }

    @Test
    public void trimOptionProperty() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeTestTrimProperty.java"), expected);
    }

    @Test
    public void authorFormat() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocType1.java"), expected);
    }

    @Test
    public void authorFormat2() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocType2.java"), expected);
    }

    @Test
    public void javadocType() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocType3.java"), expected);
    }

    @Test
    public void javadocType2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocType4.java"), expected);
    }

    @Test
    public void javadocTypeAboveComments() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "41:15: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeAboveComments.java"), expected);
    }

    @Test
    public void javadocWithNative() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWithNative.java"), expected);
    }

    @Test
    public void javadocTypeWithBlockComment() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<T>"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTypeWithBlockComment.java"), expected);
    }
}
