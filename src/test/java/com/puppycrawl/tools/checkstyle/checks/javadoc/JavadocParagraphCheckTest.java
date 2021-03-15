////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_MISPLACED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_REDUNDANT_PARAGRAPH;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_TAG_AFTER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocParagraphCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        final String[] expected = {
            "12: " + getCheckMessage(MSG_MISPLACED_TAG),
            "12: " + getCheckMessage(MSG_LINE_BEFORE),
            "13: " + getCheckMessage(MSG_MISPLACED_TAG),
            "13: " + getCheckMessage(MSG_LINE_BEFORE),
            "19: " + getCheckMessage(MSG_MISPLACED_TAG),
            "19: " + getCheckMessage(MSG_LINE_BEFORE),
            "21: " + getCheckMessage(MSG_MISPLACED_TAG),
            "28: " + getCheckMessage(MSG_LINE_BEFORE),
            "30: " + getCheckMessage(MSG_MISPLACED_TAG),
            "37: " + getCheckMessage(MSG_MISPLACED_TAG),
            "37: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "38: " + getCheckMessage(MSG_MISPLACED_TAG),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "40: " + getCheckMessage(MSG_MISPLACED_TAG),
            "40: " + getCheckMessage(MSG_LINE_BEFORE),
            "44: " + getCheckMessage(MSG_MISPLACED_TAG),
            "44: " + getCheckMessage(MSG_LINE_BEFORE),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "53: " + getCheckMessage(MSG_MISPLACED_TAG),
            "55: " + getCheckMessage(MSG_MISPLACED_TAG),
            "55: " + getCheckMessage(MSG_LINE_BEFORE),
            "56: " + getCheckMessage(MSG_MISPLACED_TAG),
            "56: " + getCheckMessage(MSG_LINE_BEFORE),
            "66: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "67: " + getCheckMessage(MSG_TAG_AFTER),
            "75: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
            "77: " + getCheckMessage(MSG_MISPLACED_TAG),
            "80: " + getCheckMessage(MSG_MISPLACED_TAG),
            "80: " + getCheckMessage(MSG_LINE_BEFORE),
            "86: " + getCheckMessage(MSG_TAG_AFTER),
            "87: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        checkConfig.addAttribute("allowNewlineParagraph", "false");
        final String[] expected = {
            "12: " + getCheckMessage(MSG_LINE_BEFORE),
            "13: " + getCheckMessage(MSG_LINE_BEFORE),
            "19: " + getCheckMessage(MSG_LINE_BEFORE),
            "28: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "40: " + getCheckMessage(MSG_LINE_BEFORE),
            "44: " + getCheckMessage(MSG_LINE_BEFORE),
            "50: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "55: " + getCheckMessage(MSG_LINE_BEFORE),
            "56: " + getCheckMessage(MSG_LINE_BEFORE),
            "66: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "67: " + getCheckMessage(MSG_TAG_AFTER),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
            "80: " + getCheckMessage(MSG_LINE_BEFORE),
            "86: " + getCheckMessage(MSG_TAG_AFTER),
            "87: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

}
