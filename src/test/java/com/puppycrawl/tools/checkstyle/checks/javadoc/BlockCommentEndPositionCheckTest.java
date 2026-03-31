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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.BlockCommentEndPositionCheck.MSG_BLOCK_COMMENT_END;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class BlockCommentEndPositionCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/blockcommentendposition";
    }

    @Test
    public void testGetAcceptableTokens() {
        final BlockCommentEndPositionCheck blockObj = new BlockCommentEndPositionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Acceptable tokens are invalid")
            .that(blockObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final BlockCommentEndPositionCheck blockObj = new BlockCommentEndPositionCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default tokens are invalid")
            .that(blockObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "22:68: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "35:69: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
        };

        verifyWithInlineConfigParser(
                getPath("InputBlockCommentEndPositionDefault.java"), expected);
    }

    @Test
    public void testAloneProperty() throws Exception {
        final String[] expected = {
            "16:60: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "27:36: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "46:36: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "50:48: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "54:58: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
            "64:78: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
        };

        verifyWithInlineConfigParser(
                getPath("InputBlockCommentEndPositionAlone.java"), expected);
    }

    @Test
    public void testTrim() throws Exception {
        final String[] expected = {
            "13:17: " + getCheckMessage(MSG_BLOCK_COMMENT_END, "BLOCK_COMMENT_END"),
        };

        verifyWithInlineConfigParser(
                getPath("InputBlockCommentEndPositionTrim.java"), expected);
    }
}
