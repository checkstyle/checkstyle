////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TrailingCommentCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/trailingcomment";
    }

    @Test
    public void testGetRequiredTokens() {
        final TrailingCommentCheck checkObj = new TrailingCommentCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "Required tokens array is not empty");
    }

    @Test
    public void testGetAcceptableTokens() {
        final TrailingCommentCheck checkObj = new TrailingCommentCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getAcceptableTokens(),
                "Acceptable tokens array is not empty");
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY),
            "7: " + getCheckMessage(MSG_KEY),
            "8: " + getCheckMessage(MSG_KEY),
            "18: " + getCheckMessage(MSG_KEY),
            "19: " + getCheckMessage(MSG_KEY),
            "29: " + getCheckMessage(MSG_KEY),
            "30: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testLegalComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        checkConfig.addAttribute("legalComment", "^NOI18N$");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY),
            "7: " + getCheckMessage(MSG_KEY),
            "8: " + getCheckMessage(MSG_KEY),
            "18: " + getCheckMessage(MSG_KEY),
            "19: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(TrailingCommentCheck.class);
        checkConfig.addAttribute("format", "NOT MATCH");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY),
            "5: " + getCheckMessage(MSG_KEY),
            "6: " + getCheckMessage(MSG_KEY),
            "7: " + getCheckMessage(MSG_KEY),
            "8: " + getCheckMessage(MSG_KEY),
            "13: " + getCheckMessage(MSG_KEY),
            "14: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "18: " + getCheckMessage(MSG_KEY),
            "19: " + getCheckMessage(MSG_KEY),
            "26: " + getCheckMessage(MSG_KEY),
            "29: " + getCheckMessage(MSG_KEY),
            "30: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testCallVisitToken() {
        final TrailingCommentCheck check = new TrailingCommentCheck();
        try {
            check.visitToken(new DetailAstImpl());
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("visitToken() shouldn't be called.", ex.getMessage(),
                    "Error message is unexpected");
        }
    }

}
