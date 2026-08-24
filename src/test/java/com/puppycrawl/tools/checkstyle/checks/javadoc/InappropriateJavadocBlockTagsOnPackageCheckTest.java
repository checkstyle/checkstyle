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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnPackageCheck.MSG_INAPPROPRIATE_TAG;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InappropriateJavadocBlockTagsOnPackageCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/"
                + "inappropriatejavadocblocktagsonpackage";
    }

    @Test
    public void testGetRequiredTokens() {
        final InappropriateJavadocBlockTagsOnPackageCheck check =
                new InappropriateJavadocBlockTagsOnPackageCheck();

        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
        };

        assertWithMessage(
                "Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InappropriateJavadocBlockTagsOnPackageCheck check =
                new InappropriateJavadocBlockTagsOnPackageCheck();

        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final InappropriateJavadocBlockTagsOnPackageCheck check =
                new InappropriateJavadocBlockTagsOnPackageCheck();

        final int[] actual = check.getRequiredJavadocTokens();
        final int[] expected = {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.USES_BLOCK_TAG,
            JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG,
            JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG,
            JavadocCommentsTokenTypes.HIDDEN_BLOCK_TAG,
            JavadocCommentsTokenTypes.INHERIT_DOC_INLINE_TAG,
            JavadocCommentsTokenTypes.SERIAL_DATA_BLOCK_TAG,
            JavadocCommentsTokenTypes.SERIAL_FIELD_BLOCK_TAG,
        };

        assertWithMessage("Required Javadoc tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAllInappropriateTags() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "exception", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "param", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "provides", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "serialData", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "serialField", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "throws", "package"),
            "19:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "uses", "package"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnPackageAllTags.java"), expected);
    }

    @Test
    public void testValidTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnPackageValidTags.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnPackageNoJavadoc.java"), expected);
    }

    @Test
    public void testSingleLineComment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputInappropriateJavadocBlockTagsOnPackageSingleLineComment.java"),
                expected);
    }

    @Test
    public void testWithAnnotation() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "package"),
        };
        verifyWithInlineConfigParser(
                getPath("annotation" + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("multipleannotations" + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotationNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("multipleannotationsnojavadoc"
                    + File.separator + "package-info.java"), expected);
    }

    @Test
    public void testWithAnnotationNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("annotationnojavadoc" + File.separator + "package-info.java"),
                expected);
    }

    @Test
    public void testWithAnnotationNonJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("annotationnonjavadoc"
                    + File.separator + "package-info.java"), expected);
    }

    /**
     * Verifies that the check throws on unsupported Javadoc token types.
     *
     * <p>This case cannot be reproduced through real Javadoc parsing, so the AST
     * node is created manually instead of using {@code verifyWithInlineConfigParser}.</p>
     */
    @Test
    public void testImproperJavadocToken() {
        final InappropriateJavadocBlockTagsOnPackageCheck check =
                new InappropriateJavadocBlockTagsOnPackageCheck();

        final JavadocNodeImpl ast = new JavadocNodeImpl();
        ast.setType(JavadocCommentsTokenTypes.EQUALS);
        ast.setText("EQUALS");

        final IllegalArgumentException exc = TestUtil.getExpectedThrowable(
                IllegalArgumentException.class,
                () -> check.visitJavadocToken(ast));

        assertWithMessage("Message must include token name")
                .that(exc.getMessage())
                .contains("EQUALS");
    }

}
