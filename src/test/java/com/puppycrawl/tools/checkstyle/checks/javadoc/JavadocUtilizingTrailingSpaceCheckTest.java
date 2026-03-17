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
            "21:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 39),
            "43:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 59),
            "70:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "82:7: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
            "96:7: " + getCheckMessage(MSG_TOO_LONG, 80, 85),
            "97:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 12),
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceBlockTags.java"), expected);
    }

    @Test
    public void testClassLevel() throws Exception {
        final String[] expected = {
            "30:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 31),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceClassLevel.java"), expected);
    }

    @Test
    public void testCustomLimit() throws Exception {
        final String[] expected = {
            "17:7: " + getCheckMessage(MSG_TOO_LONG, 50, 58),
            "33:7: " + getCheckMessage(MSG_TOO_SHORT, 50, 12),
            "75:7: " + getCheckMessage(MSG_TOO_LONG, 50, 75),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceCustomLimit.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = {
            "17:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 8),
            "81:7: " + getCheckMessage(MSG_TOO_LONG, 80, 81),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceEdgeCases.java"), expected);
    }

    @Test
    public void testHtmlInMiddle() throws Exception {
        final String[] expected = {
            "37:7: " + getCheckMessage(MSG_TOO_LONG, 80, 102),
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
            "17:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIndentation.java"), expected);
    }

    @Test
    public void testInlineCode() throws Exception {
        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineCode.java"), expected);
    }

    @Test
    public void testInlineLink() throws Exception {
        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineLink.java"), expected);
    }

    @Test
    public void testMixed() throws Exception {
        final String[] expected = {
            "17:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 74),
            "60:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 45),
            "99:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 70),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceMixed.java"), expected);
    }

    @Test
    public void testMultiParagraph() throws Exception {
        final String[] expected = {
            "67:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
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
            "51:8: " + getCheckMessage(MSG_TOO_LONG, 80, 97),
            "61:8: " + getCheckMessage(MSG_TOO_LONG, 80, 118),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceSingleLine.java"), expected);
    }

    @Test
    public void testTagHeaderOnly() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTagHeaderOnly.java"), expected);
    }

    @Test
    public void testTooLong() throws Exception {
        final String[] expected = {
            "17:7: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
            "29:7: " + getCheckMessage(MSG_TOO_LONG, 80, 116),
            "42:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "43:7: " + getCheckMessage(MSG_TOO_LONG, 80, 121),
            "79:7: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooLong.java"), expected);
    }

    @Test
    public void testTooShort() throws Exception {
        final String[] expected = {
            "17:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "29:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 20),
            "41:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "53:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "65:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 12),
            "79:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 34),
            "93:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooShort.java"), expected);
    }

    @Test
    public void testUrl() throws Exception {
        final String[] expected = {
            "32:7: " + getCheckMessage(MSG_TOO_LONG, 80, 88),
            "44:7: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
            "77:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 40),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceUrl.java"), expected);
    }
}
