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
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "15: " + getCheckMessage(MSG_LINE_BEFORE),
            "22: " + getCheckMessage(MSG_LINE_BEFORE),
            "24: " + getCheckMessage(MSG_MISPLACED_TAG),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "36: " + getCheckMessage(MSG_MISPLACED_TAG),
            "47: " + getCheckMessage(MSG_LINE_BEFORE),
            "47: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "48: " + getCheckMessage(MSG_LINE_BEFORE),
            "49: " + getCheckMessage(MSG_LINE_BEFORE),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_LINE_BEFORE),
            "59: " + getCheckMessage(MSG_LINE_BEFORE),
            "66: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "72: " + getCheckMessage(MSG_LINE_BEFORE),
            "84: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "85: " + getCheckMessage(MSG_TAG_AFTER),
            "96: " + getCheckMessage(MSG_LINE_BEFORE),
            "98: " + getCheckMessage(MSG_MISPLACED_TAG),
            "101: " + getCheckMessage(MSG_MISPLACED_TAG),
            "101: " + getCheckMessage(MSG_LINE_BEFORE),
            "111: " + getCheckMessage(MSG_TAG_AFTER),
            "112: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "22: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
            "24: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "table"),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ol"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect4.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_MISPLACED_TAG),
            "16: " + getCheckMessage(MSG_LINE_BEFORE),
            "17: " + getCheckMessage(MSG_MISPLACED_TAG),
            "17: " + getCheckMessage(MSG_LINE_BEFORE),
            "28: " + getCheckMessage(MSG_MISPLACED_TAG),
            "28: " + getCheckMessage(MSG_LINE_BEFORE),
            "30: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "41: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56: " + getCheckMessage(MSG_LINE_BEFORE),
            "56: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "57: " + getCheckMessage(MSG_MISPLACED_TAG),
            "57: " + getCheckMessage(MSG_LINE_BEFORE),
            "58: " + getCheckMessage(MSG_MISPLACED_TAG),
            "58: " + getCheckMessage(MSG_LINE_BEFORE),
            "59: " + getCheckMessage(MSG_MISPLACED_TAG),
            "59: " + getCheckMessage(MSG_LINE_BEFORE),
            "72: " + getCheckMessage(MSG_MISPLACED_TAG),
            "72: " + getCheckMessage(MSG_LINE_BEFORE),
            "83: " + getCheckMessage(MSG_MISPLACED_TAG),
            "83: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "86: " + getCheckMessage(MSG_MISPLACED_TAG),
            "88: " + getCheckMessage(MSG_MISPLACED_TAG),
            "89: " + getCheckMessage(MSG_MISPLACED_TAG),
            "89: " + getCheckMessage(MSG_LINE_BEFORE),
            "100: " + getCheckMessage(MSG_MISPLACED_TAG),
            "100: " + getCheckMessage(MSG_LINE_BEFORE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph2() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "17: " + getCheckMessage(MSG_TAG_AFTER),
            "30: " + getCheckMessage(MSG_MISPLACED_TAG),
            "30: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_TAG_AFTER),
            "46: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect3.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph3() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_LINE_BEFORE),
            "17: " + getCheckMessage(MSG_LINE_BEFORE),
            "20: " + getCheckMessage(MSG_MISPLACED_TAG),
            "20: " + getCheckMessage(MSG_LINE_BEFORE),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "44: " + getCheckMessage(MSG_LINE_BEFORE),
            "48: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52: " + getCheckMessage(MSG_MISPLACED_TAG),
            "52: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "67: " + getCheckMessage(MSG_LINE_BEFORE),
            "80: " + getCheckMessage(MSG_MISPLACED_TAG),
            "80: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect5.java"), expected);
    }

    @Test
    public void testJavadocParagraph() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_LINE_BEFORE),
            "28: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "31: " + getCheckMessage(MSG_LINE_BEFORE),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphCheck1.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_MISPLACED_TAG),
            "21: " + getCheckMessage(MSG_LINE_BEFORE),
            "28: " + getCheckMessage(MSG_LINE_BEFORE),
            "29: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "36: " + getCheckMessage(MSG_TAG_AFTER),
            "38: " + getCheckMessage(MSG_TAG_AFTER),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag2() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_MISPLACED_TAG),
            "21: " + getCheckMessage(MSG_LINE_BEFORE),
            "30: " + getCheckMessage(MSG_MISPLACED_TAG),
            "30: " + getCheckMessage(MSG_LINE_BEFORE),
            "31: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "38: " + getCheckMessage(MSG_TAG_AFTER),
            "40: " + getCheckMessage(MSG_TAG_AFTER),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "61: " + getCheckMessage(MSG_MISPLACED_TAG),
            "84: " + getCheckMessage(MSG_MISPLACED_TAG),
            "88: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag2.java"), expected);
    }

    @Test
    public void testJavadocParagraphOpenClosedTag3() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23: " + getCheckMessage(MSG_MISPLACED_TAG),
            "31: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrectOpenClosedTag3.java"), expected);
    }
}
