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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnFieldCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class InappropriateJavadocBlockTagsOnFieldCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc"
                + "/inappropriatejavadocblocktagsonfield";
    }

    @Test
    public void testGetRequiredTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG,
            JavadocCommentsTokenTypes.VERSION_BLOCK_TAG,
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.USES_BLOCK_TAG,
            JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG,
        };
        assertWithMessage("Default javadoc tokens are invalid")
                .that(checkObj.getDefaultJavadocTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG,
            JavadocCommentsTokenTypes.VERSION_BLOCK_TAG,
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.USES_BLOCK_TAG,
            JavadocCommentsTokenTypes.PROVIDES_BLOCK_TAG,
        };
        assertWithMessage("Default required javadoc tokens are invalid")
                .that(checkObj.getRequiredJavadocTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected1 = {
            "29:5: " + getCheckMessage(MSG_KEY, "return", "invalidReturn"),
            "37:5: " + getCheckMessage(MSG_KEY, "param", "invalidParam"),
            "45:5: " + getCheckMessage(MSG_KEY, "throws", "invalidThrows"),
            "53:5: " + getCheckMessage(MSG_KEY, "exception", "invalidException"),
            "61:5: " + getCheckMessage(MSG_KEY, "author", "invalidAuthor"),
            "69:5: " + getCheckMessage(MSG_KEY, "version", "invalidVersion"),
            "77:5: " + getCheckMessage(MSG_KEY, "uses", "invalidUses"),
            "85:5: " + getCheckMessage(MSG_KEY, "provides", "invalidProvides"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldDefault.java"),
                expected1);

        final String[] expected2 = {
            "28:13: " + getCheckMessage(MSG_KEY, "return", "localClassField"),
            "67:9: " + getCheckMessage(MSG_KEY, "param", "INTERFACE_CONST"),
            "67:9: " + getCheckMessage(MSG_KEY, "return", "INTERFACE_CONST"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldDefault2.java"),
                expected2);

        final String[] expected3 = {
            "21:9: " + getCheckMessage(MSG_KEY, "throws", "enumField"),
            "38:9: " + getCheckMessage(MSG_KEY, "return", "RECORD_STATIC_FIELD"),
            "55:9: " + getCheckMessage(MSG_KEY, "exception", "ANNOTATION_FIELD"),
            "65:9: " + getCheckMessage(MSG_KEY, "param", "nestedField"),
            "75:9: " + getCheckMessage(MSG_KEY, "return", "innerField"),
            "86:13: " + getCheckMessage(MSG_KEY, "return", "anonField"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldDefault3.java"),
                expected3);
    }

    @Test
    public void testNonTightHtml() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_KEY_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldTightHtml.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "param", "field"),
            "14:1: " + getCheckMessage(MSG_KEY, "return", "field"),
            "14:1: " + getCheckMessage(MSG_KEY, "throws", "field"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputInappropriateJavadocBlockTagsOnFieldCompactSourceFile.java"),
                expected);
    }

    /**
     * Verifies that the check throws on unsupported Javadoc token types.
     */
    @Test
    public void testImproperJavadocToken() {
        final InappropriateJavadocBlockTagsOnFieldCheck check =
                new InappropriateJavadocBlockTagsOnFieldCheck();

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
