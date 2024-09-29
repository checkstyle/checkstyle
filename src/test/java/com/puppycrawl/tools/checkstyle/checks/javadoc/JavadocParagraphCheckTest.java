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
            "23: " + getCheckMessage(MSG_LINE_BEFORE),
            "25: " + getCheckMessage(MSG_MISPLACED_TAG),
            "33: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_MISPLACED_TAG),
            "46: " + getCheckMessage(MSG_LINE_BEFORE),
            "46: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "47: " + getCheckMessage(MSG_LINE_BEFORE),
            "48: " + getCheckMessage(MSG_LINE_BEFORE),
            "49: " + getCheckMessage(MSG_MISPLACED_TAG),
            "49: " + getCheckMessage(MSG_LINE_BEFORE),
            "58: " + getCheckMessage(MSG_LINE_BEFORE),
            "65: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "68: " + getCheckMessage(MSG_MISPLACED_TAG),
            "70: " + getCheckMessage(MSG_LINE_BEFORE),
            "71: " + getCheckMessage(MSG_LINE_BEFORE),
            "84: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "85: " + getCheckMessage(MSG_TAG_AFTER),
            "95: " + getCheckMessage(MSG_LINE_BEFORE),
            "97: " + getCheckMessage(MSG_MISPLACED_TAG),
            "100: " + getCheckMessage(MSG_MISPLACED_TAG),
            "100: " + getCheckMessage(MSG_LINE_BEFORE),
            "110: " + getCheckMessage(MSG_TAG_AFTER),
            "111: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect.java"), expected);
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
            "88: " + getCheckMessage(MSG_LINE_BEFORE),
            "89: " + getCheckMessage(MSG_MISPLACED_TAG),
            "89: " + getCheckMessage(MSG_LINE_BEFORE),
            "106: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "107: " + getCheckMessage(MSG_TAG_AFTER),
            "120: " + getCheckMessage(MSG_MISPLACED_TAG),
            "120: " + getCheckMessage(MSG_LINE_BEFORE),
            "122: " + getCheckMessage(MSG_MISPLACED_TAG),
            "125: " + getCheckMessage(MSG_MISPLACED_TAG),
            "125: " + getCheckMessage(MSG_LINE_BEFORE),
            "135: " + getCheckMessage(MSG_TAG_AFTER),
            "136: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

    @Test
    public void testJavadocParagraph() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_LINE_BEFORE),
            "30: " + getCheckMessage(MSG_LINE_BEFORE),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphCheck1.java"), expected);
    }
}
