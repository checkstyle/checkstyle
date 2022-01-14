////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

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
            "13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "13: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_MISPLACED_TAG),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "20: " + getCheckMessage(MSG_MISPLACED_TAG),
            "20: " + getCheckMessage(MSG_LINE_BEFORE),
            "22: " + getCheckMessage(MSG_MISPLACED_TAG),
            "29: " + getCheckMessage(MSG_LINE_BEFORE),
            "31: " + getCheckMessage(MSG_MISPLACED_TAG),
            "38: " + getCheckMessage(MSG_MISPLACED_TAG),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "39: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "40: " + getCheckMessage(MSG_MISPLACED_TAG),
            "40: " + getCheckMessage(MSG_LINE_BEFORE),
            "41: " + getCheckMessage(MSG_MISPLACED_TAG),
            "41: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_MISPLACED_TAG),
            "45: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_MISPLACED_TAG),
            "51: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "54: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56: " + getCheckMessage(MSG_LINE_BEFORE),
            "57: " + getCheckMessage(MSG_MISPLACED_TAG),
            "57: " + getCheckMessage(MSG_LINE_BEFORE),
            "67: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "68: " + getCheckMessage(MSG_TAG_AFTER),
            "76: " + getCheckMessage(MSG_MISPLACED_TAG),
            "76: " + getCheckMessage(MSG_LINE_BEFORE),
            "78: " + getCheckMessage(MSG_MISPLACED_TAG),
            "81: " + getCheckMessage(MSG_MISPLACED_TAG),
            "81: " + getCheckMessage(MSG_LINE_BEFORE),
            "87: " + getCheckMessage(MSG_TAG_AFTER),
            "88: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "20: " + getCheckMessage(MSG_LINE_BEFORE),
            "29: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "40: " + getCheckMessage(MSG_LINE_BEFORE),
            "41: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "56: " + getCheckMessage(MSG_LINE_BEFORE),
            "57: " + getCheckMessage(MSG_LINE_BEFORE),
            "67: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "68: " + getCheckMessage(MSG_TAG_AFTER),
            "76: " + getCheckMessage(MSG_LINE_BEFORE),
            "81: " + getCheckMessage(MSG_LINE_BEFORE),
            "87: " + getCheckMessage(MSG_TAG_AFTER),
            "88: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

}
