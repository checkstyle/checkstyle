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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck.MSG_KEY_CATCH_BLOCK_EMPTY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyCatchBlockCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/emptycatchblock";
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyCatchBlockCheck checkObj = new EmptyCatchBlockCheck();
        final int[] expected = {TokenTypes.LITERAL_CATCH};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "25:31: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "32:83: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockDefault.java"), expected);
    }

    @Test
    public void testWithUserSetValues() throws Exception {
        final String[] expected = {
            "26:31: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "54:78: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "88:29: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockDefault2.java"), expected);
    }

    @Test
    public void testLinesAreProperlySplitSystemIndependently() throws Exception {
        final String[] expected = {
            "25:31: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "53:78: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "87:29: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        final String originalLineSeparator = System.lineSeparator();
        try {
            System.setProperty("line.separator", "\r\n");
            verifyWithInlineConfigParser(
                    getPath("InputEmptyCatchBlockDefaultLF.java"), expected);
        }
        finally {
            System.setProperty("line.separator", originalLineSeparator);
        }
    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyCatchBlockCheck constantNameCheckObj = new EmptyCatchBlockCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.LITERAL_CATCH };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testNonEmptyCatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockNonEmptyCatch.java"), expected);
    }

    @Test
    public void testCommentRecognition() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockCommentRecognition.java"), expected);
    }

    @Test
    public void testNonEmptyCatch2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockNonEmptyCatch2.java"), expected);
    }

    @Test
    public void testCommentRecognition2() throws Exception {
        final String[] expected = {
            "17:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "26:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "45:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "61:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "70:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockCommentRecognition2.java"), expected);
    }

    @Test
    public void testNonEmptyCatchLf() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockNonEmptyCatchLF.java"), expected);
    }

    @Test
    public void testCommentRecognitionLf() throws Exception {
        final String[] expected = {
            "17:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "26:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "45:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "61:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
            "70:33: " + getCheckMessage(MSG_KEY_CATCH_BLOCK_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyCatchBlockCommentRecognitionLF.java"), expected);
    }

}
