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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnTypeCheck.MSG_INAPPROPRIATE_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class InappropriateJavadocBlockTagsOnTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/inappropriatejavadocblocktagsontype";
    }

    @Test
    public void testGetRequiredTokens() {
        final InappropriateJavadocBlockTagsOnTypeCheck check =
                new InappropriateJavadocBlockTagsOnTypeCheck();

        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };

        assertWithMessage(
                "Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InappropriateJavadocBlockTagsOnTypeCheck check =
                new InappropriateJavadocBlockTagsOnTypeCheck();

        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final InappropriateJavadocBlockTagsOnTypeCheck check =
                new InappropriateJavadocBlockTagsOnTypeCheck();

        final int[] actual = check.getRequiredJavadocTokens();
        final int[] expected = {
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
        };

        assertWithMessage("Required Javadoc tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testClassInterfaceEnumAnnotation() throws Exception {
        final String[] expected = {
            "14:1: "
                + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return",
                        "InputInappropriateJavadocBlockTagsOnTypeOnClass"),
            "14:1: "
                + getCheckMessage(MSG_INAPPROPRIATE_TAG, "throws",
                        "InputInappropriateJavadocBlockTagsOnTypeOnClass"),
            "24:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyInterface"),
            "24:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "throws", "MyInterface"),
            "34:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "exception", "MyEnum"),
            "34:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyEnum"),
            "46:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyAnnotation"),
            "46:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "throws", "MyAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnTypeOnClass.java"), expected);
    }

    @Test
    public void testRecord() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "return", "MyRecord"),
            "16:1: " + getCheckMessage(MSG_INAPPROPRIATE_TAG, "throws", "MyRecord"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnTypeOnRecord.java"), expected);
    }

    /**
     * Verifies that the check throws on unsupported Javadoc token types.
     *
     * <p>This case cannot be reproduced through real Javadoc parsing, so the AST
     * node is created manually instead of using {@code verifyWithInlineConfigParser}.</p>
     */
    @Test
    public void testImproperJavadocToken() {
        final InappropriateJavadocBlockTagsOnTypeCheck check =
                new InappropriateJavadocBlockTagsOnTypeCheck();

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
