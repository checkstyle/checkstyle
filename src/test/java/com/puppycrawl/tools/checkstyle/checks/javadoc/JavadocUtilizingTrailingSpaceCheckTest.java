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
            "23:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 39),
            "45:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 59),
            "72:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "99:7: " + getCheckMessage(MSG_TOO_LONG, 80, 85),
            "100:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 12),
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
    public void testBlockTagValues() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_TOO_LONG, 80, 131),
            "44:8: " + getCheckMessage(MSG_TOO_SHORT, 80, 29),
            "77:8: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceBlockTagValues.java"), expected);
    }

    @Test
    public void testClassLevel() throws Exception {
        final String[] expected = {
            "32:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 31),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceClassLevel.java"), expected);
    }

    @Test
    public void testCustomLimit() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_LONG, 50, 58),
            "35:7: " + getCheckMessage(MSG_TOO_SHORT, 50, 12),
            "47:8: " + getCheckMessage(MSG_TOO_LONG, 50, 60),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceCustomLimit.java"), expected);
    }

    @Test
    public void testDocsBlockTags() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_TOO_LONG, 80, 82),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceDocsBlockTags.java"), expected);
    }

    @Test
    public void testDocsLineLength() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 56),
            "32:7: " + getCheckMessage(MSG_TOO_LONG, 80, 88),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceDocsLineLength.java"), expected);
    }

    @Test
    public void testDocsSkipped() throws Exception {
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_TOO_LONG, 80, 93),
            "39:8: " + getCheckMessage(MSG_TOO_LONG, 80, 84),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceDocsSkipped.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 8),
            "83:7: " + getCheckMessage(MSG_TOO_LONG, 80, 81),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceEdgeCases.java"), expected);
    }

    @Test
    public void testHtmlCode() throws Exception {
        final String[] expected = {
            "52:7: " + getCheckMessage(MSG_TOO_LONG, 80, 88),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlCode.java"), expected);
    }

    @Test
    public void testHtmlInMiddle() throws Exception {
        final String[] expected = {
            "39:7: " + getCheckMessage(MSG_TOO_LONG, 80, 102),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlInMiddle.java"), expected);
    }

    @Test
    public void testHtmlLineStart() throws Exception {
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_TOO_LONG, 80, 143),
            "27:8: " + getCheckMessage(MSG_TOO_LONG, 80, 141),
            "71:7: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlLineStart.java"), expected);
    }

    @Test
    public void testHtmlLink() throws Exception {
        final String[] expected = {
            "46:8: " + getCheckMessage(MSG_TOO_LONG, 80, 95),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlLink.java"), expected);
    }

    @Test
    public void testHtmlTags() throws Exception {
        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_TOO_LONG, 80, 95),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceHtmlTags.java"), expected);
    }

    @Test
    public void testIgnorePatternCustom() throws Exception {
        final String[] expected = {
            "24:7: " + getCheckMessage(MSG_TOO_LONG, 80, 83),
            "30:8: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
            "36:8: " + getCheckMessage(MSG_TOO_SHORT, 80, 36),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnorePatternCustom.java"), expected);
    }

    @Test
    public void testIgnorePatternNone() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_LONG, 80, 83),
            "25:7: " + getCheckMessage(MSG_TOO_LONG, 80, 88),
            "31:8: " + getCheckMessage(MSG_TOO_LONG, 80, 110),
            "37:8: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnorePatternNone.java"), expected);
    }

    @Test
    public void testIgnoreTooShortPattern() throws Exception {
        final String[] expected = {
            "37:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "44:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 30),
            "52:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 27),
            "59:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "73:7: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnoreTooShortPattern.java"),
                expected);
    }

    @Test
    public void testIgnoreTooShortPatternCustom() throws Exception {
        final String[] expected = {
            "31:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
            "38:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 28),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternCustom.java"),
                expected);
    }

    @Test
    public void testIgnoreTooShortPatternNone() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
            "26:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 23),
            "33:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 30),
            "40:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "47:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 30),
            "54:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
            "62:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "70:8: " + getCheckMessage(MSG_TOO_SHORT, 80, 40),
            "77:7: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternNone.java"),
                expected);
    }

    @Test
    public void testIgnoreTooShortPatternSpaces() throws Exception {
        final String[] expected = {
            "31:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 26),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIgnoreTooShortPatternSpaces.java"),
                expected);
    }

    @Test
    public void testIndentation() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceIndentation.java"), expected);
    }

    @Test
    public void testInlineCode() throws Exception {
        final String[] expected = {
            "24:7: " + getCheckMessage(MSG_TOO_LONG, 80, 87),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineCode.java"), expected);
    }

    @Test
    public void testInlineLink() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_TOO_LONG, 80, 116),
            "46:8: " + getCheckMessage(MSG_TOO_LONG, 80, 96),
            "52:7: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceInlineLink.java"), expected);
    }

    @Test
    public void testMixed() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 74),
            "62:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 45),
            "101:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 70),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceMixed.java"), expected);
    }

    @Test
    public void testMultiParagraph() throws Exception {
        final String[] expected = {
            "69:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 14),
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
    public void testOpeningLine() throws Exception {
        final String[] expected = {
            "18:8: " + getCheckMessage(MSG_TOO_SHORT, 80, 33),
            "52:7: " + getCheckMessage(MSG_TOO_LONG, 80, 85),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceOpeningLine.java"), expected);
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
            "53:8: " + getCheckMessage(MSG_TOO_LONG, 80, 97),
            "63:8: " + getCheckMessage(MSG_TOO_LONG, 80, 118),
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
            "19:7: " + getCheckMessage(MSG_TOO_LONG, 80, 108),
            "31:7: " + getCheckMessage(MSG_TOO_LONG, 80, 116),
            "44:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 19),
            "45:7: " + getCheckMessage(MSG_TOO_LONG, 80, 121),
            "58:8: " + getCheckMessage(MSG_TOO_LONG, 80, 124),
            "64:8: " + getCheckMessage(MSG_TOO_LONG, 80, 129),
            "72:8: " + getCheckMessage(MSG_TOO_LONG, 80, 118),
            "84:7: " + getCheckMessage(MSG_TOO_LONG, 80, 92),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooLong.java"), expected);
    }

    @Test
    public void testTooShort() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 25),
            "31:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 20),
            "43:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 13),
            "55:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 44),
            "67:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 12),
            "81:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 34),
            "95:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 22),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceTooShort.java"), expected);
    }

    @Test
    public void testUrl() throws Exception {
        final String[] expected = {
            "79:7: " + getCheckMessage(MSG_TOO_SHORT, 80, 40),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocUtilizingTrailingSpaceUrl.java"), expected);
    }

}
