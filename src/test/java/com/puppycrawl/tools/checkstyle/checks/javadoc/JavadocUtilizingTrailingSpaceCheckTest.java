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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtilizingTrailingSpaceCheck.MSG_TOO_LONG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtilizingTrailingSpaceCheck.MSG_TOO_SHORT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocUtilizingTrailingSpaceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocutilizingtrailingspace";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocUtilizingTrailingSpaceCheck checkObj =
                new JavadocUtilizingTrailingSpaceCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "22: " + getCheckMessage(MSG_TOO_SHORT, 80, 39),
            "36: " + getCheckMessage(MSG_TOO_SHORT, 80, 59),
            "57: " + getCheckMessage(MSG_TOO_SHORT, 80, 20),
            "58: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "70: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpace.java"), expected);
    }

    @Test
    public void testBlankLines() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceBlankLines.java"), expected);
    }

    @Test
    public void testBlockTags() throws Exception {
        final String[] expected = {
            "31: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "44: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceBlockTags.java"), expected);
    }

    @Test
    public void testClassLevel() throws Exception {
        final String[] expected = {
            "30: " + getCheckMessage(MSG_TOO_SHORT, 80, 31),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceClassLevel.java"), expected);
    }

    @Test
    public void testCustomLimit() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_LONG, 50, 58),
            "28: " + getCheckMessage(MSG_TOO_SHORT, 50, 12),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceCustomLimit.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_SHORT, 80, 8),
            "81: " + getCheckMessage(MSG_TOO_LONG, 80, 81),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceEdgeCases.java"), expected);
    }

    @Test
    public void testHtmlInMiddle() throws Exception {
        final String[] expected = {
            "37: " + getCheckMessage(MSG_TOO_LONG, 80, 102),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlInMiddle.java"), expected);
    }

    @Test
    public void testHtmlTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlTags.java"), expected);
    }

    @Test
    public void testIndentation() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "25: " + getCheckMessage(MSG_TOO_SHORT, 80, 35),
            "26: " + getCheckMessage(MSG_TOO_SHORT, 80, 55),
            "33: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "40: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "51: " + getCheckMessage(MSG_TOO_SHORT, 80, 33),
            "52: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "53: " + getCheckMessage(MSG_TOO_SHORT, 80, 21),
            "60: " + getCheckMessage(MSG_TOO_SHORT, 80, 48),
            "69: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "70: " + getCheckMessage(MSG_TOO_SHORT, 80, 31),
            "71: " + getCheckMessage(MSG_TOO_SHORT, 80, 34),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIndentation.java"), expected);
    }

    @Test
    public void testInlineCode() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineCode.java"), expected);
    }

    @Test
    public void testInlineLink() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineLink.java"), expected);
    }

    @Test
    public void testMixed() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_SHORT, 80, 74),
            "40: " + getCheckMessage(MSG_TOO_SHORT, 80, 45),
            "41: " + getCheckMessage(MSG_TOO_SHORT, 80, 33),
            "71: " + getCheckMessage(MSG_TOO_SHORT, 80, 61),
            "72: " + getCheckMessage(MSG_TOO_SHORT, 80, 17),
            "73: " + getCheckMessage(MSG_TOO_SHORT, 80, 73),
            "74: " + getCheckMessage(MSG_TOO_LONG, 80, 101),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceMixed.java"), expected);
    }

    @Test
    public void testMultiParagraph() throws Exception {
        final String[] expected = {
            "67: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceMultiParagraph.java"), expected);
    }

    @Test
    public void testNoViolations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceNoViolations.java"), expected);
    }

    @Test
    public void testPreBlock() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpacePreBlock.java"), expected);
    }

    @Test
    public void testReferences() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceReferences.java"), expected);
    }

    @Test
    public void testSingleLine() throws Exception {
        final String[] expected = {
            "51: " + getCheckMessage(MSG_TOO_LONG, 80, 97),
            "55: " + getCheckMessage(MSG_TOO_LONG, 80, 118),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceSingleLine.java"), expected);
    }

    @Test
    public void testTagHeaderOnly() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "26: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "35: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "54: " + getCheckMessage(MSG_TOO_SHORT, 80, 11),
            "65: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "67: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "69: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "78: " + getCheckMessage(MSG_TOO_SHORT, 80, 18),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTagHeaderOnly.java"), expected);
    }

    @Test
    public void testTooLong() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
            "23: " + getCheckMessage(MSG_TOO_LONG, 80, 116),
            "30: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "31: " + getCheckMessage(MSG_TOO_LONG, 80, 121),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooLong.java"), expected);
    }

    @Test
    public void testTooShort() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "24: " + getCheckMessage(MSG_TOO_SHORT, 80, 20),
            "31: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "38: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "45: " + getCheckMessage(MSG_TOO_SHORT, 80, 12),
            "54: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "55: " + getCheckMessage(MSG_TOO_SHORT, 80, 34),
            "63: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "64: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooShort.java"), expected);
    }

    @Test
    public void testUrl() throws Exception {
        final String[] expected = {
            "32: " + getCheckMessage(MSG_TOO_LONG, 80, 88),
            "38: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
            "65: " + getCheckMessage(MSG_TOO_SHORT, 80, 40),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceUrl.java"), expected);
    }
}
