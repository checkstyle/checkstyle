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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.TrailingCommentCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Required tokens array is not empty");
    }

    @Test
    public void testGetAcceptableTokens() {
        final TrailingCommentCheck checkObj = new TrailingCommentCheck();
        final int[] expected = {TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN, };
        assertArrayEquals(expected, checkObj.getAcceptableTokens(),
                "Acceptable tokens array is not empty");
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        final String[] expected = {
            "4:12: " + getCheckMessage(MSG_KEY),
            "7:12: " + getCheckMessage(MSG_KEY),
            "8:22: " + getCheckMessage(MSG_KEY),
            "18:19: " + getCheckMessage(MSG_KEY),
            "19:21: " + getCheckMessage(MSG_KEY),
            "29:50: " + getCheckMessage(MSG_KEY),
            "30:51: " + getCheckMessage(MSG_KEY),
            "31:31: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testLegalComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        checkConfig.addProperty("legalComment", "^NOI18N$");
        final String[] expected = {
            "4:12: " + getCheckMessage(MSG_KEY),
            "7:12: " + getCheckMessage(MSG_KEY),
            "8:22: " + getCheckMessage(MSG_KEY),
            "18:19: " + getCheckMessage(MSG_KEY),
            "19:21: " + getCheckMessage(MSG_KEY),
            "31:31: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        checkConfig.addProperty("format", "NOT MATCH");
        final String[] expected = {
            "4:12: " + getCheckMessage(MSG_KEY),
            "5:5: " + getCheckMessage(MSG_KEY),
            "6:5: " + getCheckMessage(MSG_KEY),
            "7:12: " + getCheckMessage(MSG_KEY),
            "8:22: " + getCheckMessage(MSG_KEY),
            "13:17: " + getCheckMessage(MSG_KEY),
            "14:7: " + getCheckMessage(MSG_KEY),
            "15:5: " + getCheckMessage(MSG_KEY),
            "18:19: " + getCheckMessage(MSG_KEY),
            "19:21: " + getCheckMessage(MSG_KEY),
            "26:5: " + getCheckMessage(MSG_KEY),
            "29:50: " + getCheckMessage(MSG_KEY),
            "30:51: " + getCheckMessage(MSG_KEY),
            "31:31: " + getCheckMessage(MSG_KEY),
            "34:9: " + getCheckMessage(MSG_KEY),
            "35:9: " + getCheckMessage(MSG_KEY),
            "43:5: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }
}
