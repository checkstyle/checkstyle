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
            "10: " + getCheckMessage(MSG_MISPLACED_TAG),
            "10: " + getCheckMessage(MSG_LINE_BEFORE),
            "11: " + getCheckMessage(MSG_MISPLACED_TAG),
            "11: " + getCheckMessage(MSG_LINE_BEFORE),
            "17: " + getCheckMessage(MSG_MISPLACED_TAG),
            "17: " + getCheckMessage(MSG_LINE_BEFORE),
            "19: " + getCheckMessage(MSG_MISPLACED_TAG),
            "26: " + getCheckMessage(MSG_LINE_BEFORE),
            "28: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "36: " + getCheckMessage(MSG_MISPLACED_TAG),
            "36: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_MISPLACED_TAG),
            "37: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_MISPLACED_TAG),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "42: " + getCheckMessage(MSG_MISPLACED_TAG),
            "42: " + getCheckMessage(MSG_LINE_BEFORE),
            "48: " + getCheckMessage(MSG_MISPLACED_TAG),
            "48: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "51: " + getCheckMessage(MSG_MISPLACED_TAG),
            "53: " + getCheckMessage(MSG_MISPLACED_TAG),
            "53: " + getCheckMessage(MSG_LINE_BEFORE),
            "54: " + getCheckMessage(MSG_MISPLACED_TAG),
            "54: " + getCheckMessage(MSG_LINE_BEFORE),
            "64: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "65: " + getCheckMessage(MSG_TAG_AFTER),
            "73: " + getCheckMessage(MSG_MISPLACED_TAG),
            "73: " + getCheckMessage(MSG_LINE_BEFORE),
            "75: " + getCheckMessage(MSG_MISPLACED_TAG),
            "78: " + getCheckMessage(MSG_MISPLACED_TAG),
            "78: " + getCheckMessage(MSG_LINE_BEFORE),
            "84: " + getCheckMessage(MSG_TAG_AFTER),
            "85: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        checkConfig.addProperty("allowNewlineParagraph", "false");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_LINE_BEFORE),
            "11: " + getCheckMessage(MSG_LINE_BEFORE),
            "17: " + getCheckMessage(MSG_LINE_BEFORE),
            "26: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "36: " + getCheckMessage(MSG_LINE_BEFORE),
            "37: " + getCheckMessage(MSG_LINE_BEFORE),
            "38: " + getCheckMessage(MSG_LINE_BEFORE),
            "42: " + getCheckMessage(MSG_LINE_BEFORE),
            "48: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "53: " + getCheckMessage(MSG_LINE_BEFORE),
            "54: " + getCheckMessage(MSG_LINE_BEFORE),
            "64: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "65: " + getCheckMessage(MSG_TAG_AFTER),
            "73: " + getCheckMessage(MSG_LINE_BEFORE),
            "78: " + getCheckMessage(MSG_LINE_BEFORE),
            "84: " + getCheckMessage(MSG_TAG_AFTER),
            "85: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect2.java"), expected);
    }

}
