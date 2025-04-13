///
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
///

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.TrailingCommentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TrailingCommentCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/trailingcomment";
    }

    @Test
    public void testGetRequiredTokens() {
        final TrailingCommentCheck checkObj = new TrailingCommentCheck();
        final int[] expected = {TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN, };
        assertWithMessage("Required tokens array is not empty")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final TrailingCommentCheck checkObj = new TrailingCommentCheck();
        final int[] expected = {TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN, };
        assertWithMessage("Acceptable tokens array is not empty")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(MSG_KEY),
            "17:12: " + getCheckMessage(MSG_KEY),
            "19:22: " + getCheckMessage(MSG_KEY),
            "30:19: " + getCheckMessage(MSG_KEY),
            "31:21: " + getCheckMessage(MSG_KEY),
            "42:50: " + getCheckMessage(MSG_KEY),
            "44:51: " + getCheckMessage(MSG_KEY),
            "46:31: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testLegalComment() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(MSG_KEY),
            "17:12: " + getCheckMessage(MSG_KEY),
            "19:22: " + getCheckMessage(MSG_KEY),
            "30:19: " + getCheckMessage(MSG_KEY),
            "32:21: " + getCheckMessage(MSG_KEY),
            "42:50: " + getCheckMessage(MSG_KEY),
            "45:31: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTrailingComment2.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_KEY),
            "12:12: " + getCheckMessage(MSG_KEY),
            "13:5: " + getCheckMessage(MSG_KEY),
            "14:33: " + getCheckMessage(MSG_KEY),
            "15:39: " + getCheckMessage(MSG_KEY),
            "16:22: " + getCheckMessage(MSG_KEY),
            "21:44: " + getCheckMessage(MSG_KEY),
            "22:7: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
            "26:19: " + getCheckMessage(MSG_KEY),
            "27:36: " + getCheckMessage(MSG_KEY),
            "34:5: " + getCheckMessage(MSG_KEY),
            "37:50: " + getCheckMessage(MSG_KEY),
            "38:62: " + getCheckMessage(MSG_KEY),
            "39:31: " + getCheckMessage(MSG_KEY),
            "42:39: " + getCheckMessage(MSG_KEY),
            "43:9: " + getCheckMessage(MSG_KEY),
            "51:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTrailingComment3.java"), expected);
    }

    @Test
    public void testLegalCommentWithNoPrecedingWhitespace() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputTrailingCommentWithNoPrecedingWhitespace.java"), expected);
    }

    @Test
    public void testWithEmoji() throws Exception {
        final String[] expected = {
            "13:24: " + getCheckMessage(MSG_KEY),
            "15:27: " + getCheckMessage(MSG_KEY),
            "21:33: " + getCheckMessage(MSG_KEY),
            "25:13: " + getCheckMessage(MSG_KEY),
            "27:16: " + getCheckMessage(MSG_KEY),
            "28:24: " + getCheckMessage(MSG_KEY),
            "33:37: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputTrailingCommentWithEmoji.java"), expected);
    }
}
