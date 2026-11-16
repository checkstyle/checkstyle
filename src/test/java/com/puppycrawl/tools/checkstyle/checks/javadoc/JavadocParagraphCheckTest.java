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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_MISPLACED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_PRECEDED_BLOCK_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_REDUNDANT_PARAGRAPH;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_TAG_AFTER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParagraphCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocParagraphCheck checkObj = new JavadocParagraphCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "16:19: " + getCheckMessage(MSG_LINE_BEFORE),
            "23:21: " + getCheckMessage(MSG_LINE_BEFORE),
            "25:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "34:13: " + getCheckMessage(MSG_LINE_BEFORE),
            "36:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "55:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "55:24: " + getCheckMessage(MSG_LINE_BEFORE),
            "56:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "57:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "57:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "58:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "58:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "58:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "58:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "62:25: " + getCheckMessage(MSG_LINE_BEFORE),
            "70:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "76:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "87:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "88:11: " + getCheckMessage(MSG_TAG_AFTER),
            "97:27: " + getCheckMessage(MSG_LINE_BEFORE),
            "99:13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "110:11: " + getCheckMessage(MSG_TAG_AFTER),
            "111:11: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "22:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "37:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "table"),
            "52:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "54:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "54:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ol"),
            "64:63: " + getCheckMessage(MSG_LINE_BEFORE),
            "65:7: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect4.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final String[] expected = {
            "19:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "19:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "20:19: " + getCheckMessage(MSG_MISPLACED_TAG),
            "20:19: " + getCheckMessage(MSG_LINE_BEFORE),
            "29:21: " + getCheckMessage(MSG_MISPLACED_TAG),
            "29:21: " + getCheckMessage(MSG_LINE_BEFORE),
            "31:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "40:13: " + getCheckMessage(MSG_LINE_BEFORE),
            "42:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "65:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "65:24: " + getCheckMessage(MSG_MISPLACED_TAG),
            "65:24: " + getCheckMessage(MSG_LINE_BEFORE),
            "66:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "66:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "67:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "67:11: " + getCheckMessage(MSG_MISPLACED_TAG),
            "67:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "68:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "68:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "68:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "68:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "75:25: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75:25: " + getCheckMessage(MSG_LINE_BEFORE),
            "90:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "90:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "93:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "95:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "96:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "96:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "108:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "108:12: " + getCheckMessage(MSG_LINE_BEFORE),
            "108:31: " + getCheckMessage(MSG_MISPLACED_TAG),
            "108:31: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph2() throws Exception {
        final String[] expected = {
            "17:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "18:11: " + getCheckMessage(MSG_TAG_AFTER),
            "30:27: " + getCheckMessage(MSG_MISPLACED_TAG),
            "30:27: " + getCheckMessage(MSG_LINE_BEFORE),
            "32:13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "43:11: " + getCheckMessage(MSG_TAG_AFTER),
            "44:11: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect3.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph3() throws Exception {
        final String[] expected = {
            "46:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "73:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "73:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect5.java"), expected);
    }

    @Test
    public void testJavadocParagraph() throws Exception {
        final String[] expected = {
            "23:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "32:7: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "35:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "53:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "79:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "table"),
            "90:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "pre"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphCheck1.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "22:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "29:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "30:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "37:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "38:6: " + getCheckMessage(MSG_TAG_AFTER),
            "40:6: " + getCheckMessage(MSG_TAG_AFTER),
            "58:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "74:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "86:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag2() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "22:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "31:20: " + getCheckMessage(MSG_MISPLACED_TAG),
            "31:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "32:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "39:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "40:6: " + getCheckMessage(MSG_TAG_AFTER),
            "42:6: " + getCheckMessage(MSG_TAG_AFTER),
            "51:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "63:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "63:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "79:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "90:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "94:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "94:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag2.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag3() throws Exception {
        final String[] expected = {
            "15:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "31:7: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag3.java"), expected);
    }

    @Test
    public void testIncorrect3() throws Exception {
        final String[] expected = {
            "16:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "24:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "32:7: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect6.java"), expected);
    }

    @Test
    public void testJavadocParagraphNested() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphNested.java"), expected);
    }

    @Test
    public void testJavadocParagraphBlockTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphBlockTag.java"), expected);
    }

    @Test
    public void testJavadocParagraphCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputJavadocParagraphCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testJavadocParagraphCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "16:4: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputJavadocParagraphCompactSourceFileProperties.java"),
                expected);
    }

}
