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
            "22: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
            "23: " + getCheckMessage(MSG_TOO_SHORT, 80, 39),
            "37: " + getCheckMessage(MSG_TOO_SHORT, 80, 59),
            "58: " + getCheckMessage(MSG_TOO_SHORT, 80, 20),
            "59: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "71: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
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

    // @Test
    // public void testEdgeCases() throws Exception {
    //     final String[] expected = {
    //         "17: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "81: " + getCheckMessage(MSG_TOO_LONG, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceEdgeCases.java"), expected);
    // }

    // @Test
    // public void testHtmlInMiddle() throws Exception {
    //     final String[] expected = {
    //         "37: " + getCheckMessage(MSG_TOO_LONG, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceHtmlInMiddle.java"), expected);
    // }

    // @Test
    // public void testHtmlTags() throws Exception {
    //     final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceHtmlTags.java"), expected);
    // }

    // @Test
    // public void testIndentation() throws Exception {
    //     final String[] expected = {
    //         "17: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "24: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "25: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "32: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "39: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "48: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "49: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "50: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "57: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "64: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "65: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "66: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceIndentation.java"), expected);
    // }

    // @Test
    // public void testInlineCode() throws Exception {
    //     final String[] expected = {
    //         "22: " + getCheckMessage(MSG_TOO_LONG, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceInlineCode.java"), expected);
    // }

    // @Test
    // public void testInlineLink() throws Exception {
    //     final String[] expected = {
    //         "22: " + getCheckMessage(MSG_TOO_LONG, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceInlineLink.java"), expected);
    // }

    // @Test
    // public void testMixed() throws Exception {
    //     final String[] expected = {
    //         "17: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "39: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "40: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "67: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "68: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "69: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "70: " + getCheckMessage(MSG_TOO_LONG, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceMixed.java"), expected);
    // }

    // @Test
    // public void testMultiParagraph() throws Exception {
    //     final String[] expected = {
    //         "66: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceMultiParagraph.java"), expected);
    // }

    // @Test
    // public void testNoViolations() throws Exception {
    //     final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceNoViolations.java"), expected);
    // }

    // @Test
    // public void testPreBlock() throws Exception {
    //     final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpacePreBlock.java"), expected);
    // }

    // @Test
    // public void testReferences() throws Exception {
    //     final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceReferences.java"), expected);
    // }

    // @Test
    // public void testSingleLine() throws Exception {
    //     final String[] expected = {
    //         "50: " + getCheckMessage(MSG_TOO_LONG, 80, 97),
    //         "53: " + getCheckMessage(MSG_TOO_LONG, 80, 120),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceSingleLine.java"), expected);
    // }

    // @Test
    // public void testTagHeaderOnly() throws Exception {
    //     final String[] expected = {
    //         "18: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "24: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "32: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "38: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "50: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "60: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "70: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceTagHeaderOnly.java"), expected);
    // }

    // @Test
    // public void testTooLong() throws Exception {
    //     final String[] expected = {
    //         "16: " + getCheckMessage(MSG_TOO_LONG, 80, 138),
    //         "21: " + getCheckMessage(MSG_TOO_LONG, 80, 146),
    //         "26: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "27: " + getCheckMessage(MSG_TOO_LONG, 80, 151),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceTooLong.java"), expected);
    // }

    // @Test
    // public void testTooShort() throws Exception {
    //     final String[] expected = {
    //         "16: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "22: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "28: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "34: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "40: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "46: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "47: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "53: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "54: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceTooShort.java"), expected);
    // }

    // @Test
    // public void testUrl() throws Exception {
    //     final String[] expected = {
    //         "31: " + getCheckMessage(MSG_TOO_LONG, 80, 118),
    //         "36: " + getCheckMessage(MSG_TOO_LONG, 80, 122),
    //         "62: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceUrl.java"), expected);
    // }

    // @Test
    // public void testNonTightHtml() throws Exception {
    //     final String[] expected = {
    //         "16: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "22: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "29: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "38: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "46: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "52: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "53: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceNonTightHtml.java"), expected);
    // }

    // @Test
    // public void testNonTightHtmlCustomLimit() throws Exception {
    //     final String[] expected = {
    //         "28: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "35: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "43: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "49: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //         "50: " + getCheckMessage(MSG_TOO_SHORT, 80),
    //     };
    //     verifyWithInlineConfigParser(
    //             getPath("InputJavadocUtilizingTrailingSpaceNonTightHtmlCustomLimit.java"), expected);
    // }
}

