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
    protected String getPackageLocation() {
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
            "14:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "15:19: " + getCheckMessage(MSG_LINE_BEFORE),
            "22:21: " + getCheckMessage(MSG_LINE_BEFORE),
            "24:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "34:13: " + getCheckMessage(MSG_LINE_BEFORE),
            "36:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "47:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "47:24: " + getCheckMessage(MSG_LINE_BEFORE),
            "48:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "49:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "49:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "50:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "50:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "62:25: " + getCheckMessage(MSG_LINE_BEFORE),
            "69:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "75:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "86:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "87:11: " + getCheckMessage(MSG_TAG_AFTER),
            "97:27: " + getCheckMessage(MSG_LINE_BEFORE),
            "99:13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "102:36: " + getCheckMessage(MSG_MISPLACED_TAG),
            "102:36: " + getCheckMessage(MSG_LINE_BEFORE),
            "112:11: " + getCheckMessage(MSG_TAG_AFTER),
            "113:11: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "22:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "24:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "38:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "table"),
            "50:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ol"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect4.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "16:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "17:19: " + getCheckMessage(MSG_MISPLACED_TAG),
            "17:19: " + getCheckMessage(MSG_LINE_BEFORE),
            "28:21: " + getCheckMessage(MSG_MISPLACED_TAG),
            "28:21: " + getCheckMessage(MSG_LINE_BEFORE),
            "30:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39:13: " + getCheckMessage(MSG_LINE_BEFORE),
            "41:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "56:24: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56:24: " + getCheckMessage(MSG_LINE_BEFORE),
            "57:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "57:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "58:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "58:11: " + getCheckMessage(MSG_MISPLACED_TAG),
            "58:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "59:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "59:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "59:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "59:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "75:25: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75:25: " + getCheckMessage(MSG_LINE_BEFORE),
            "86:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "86:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "89:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "91:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "92:29: " + getCheckMessage(MSG_MISPLACED_TAG),
            "92:29: " + getCheckMessage(MSG_LINE_BEFORE),
            "103:12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "103:12: " + getCheckMessage(MSG_LINE_BEFORE),
            "103:31: " + getCheckMessage(MSG_MISPLACED_TAG),
            "103:31: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph2() throws Exception {
        final String[] expected = {
            "16:12: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "17:11: " + getCheckMessage(MSG_TAG_AFTER),
            "30:27: " + getCheckMessage(MSG_MISPLACED_TAG),
            "30:27: " + getCheckMessage(MSG_LINE_BEFORE),
            "32:13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35:36: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35:36: " + getCheckMessage(MSG_LINE_BEFORE),
            "45:11: " + getCheckMessage(MSG_TAG_AFTER),
            "46:11: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect3.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph3() throws Exception {
        final String[] expected = {
            "15:12: " + getCheckMessage(MSG_LINE_BEFORE),
            "17:15: " + getCheckMessage(MSG_LINE_BEFORE),
            "20:9: " + getCheckMessage(MSG_MISPLACED_TAG),
            "20:9: " + getCheckMessage(MSG_LINE_BEFORE),
            "34:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "38:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "44:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "48:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "67:11: " + getCheckMessage(MSG_LINE_BEFORE),
            "80:8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "80:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect5.java"), expected);
    }

    @Test
    public void testJavadocParagraph() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "30:7: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "33:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "52:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "67:8: " + getCheckMessage(MSG_LINE_BEFORE),
            "78:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "table"),
            "89:8: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "pre"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphCheck1.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "21:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "28:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "29:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "35:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "36:6: " + getCheckMessage(MSG_TAG_AFTER),
            "38:6: " + getCheckMessage(MSG_TAG_AFTER),
            "58:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "73:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "85:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag2() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "21:7: " + getCheckMessage(MSG_LINE_BEFORE),
            "30:20: " + getCheckMessage(MSG_MISPLACED_TAG),
            "30:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "31:20: " + getCheckMessage(MSG_LINE_BEFORE),
            "37:8: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "38:6: " + getCheckMessage(MSG_TAG_AFTER),
            "40:6: " + getCheckMessage(MSG_TAG_AFTER),
            "50:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "63:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "63:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "78:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "87:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "91:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "91:7: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
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
            "15:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23:7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "31:7: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect6.java"), expected);
    }
}
