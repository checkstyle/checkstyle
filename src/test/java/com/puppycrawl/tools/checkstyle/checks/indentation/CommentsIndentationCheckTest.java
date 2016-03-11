////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_SINGLE;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
*
* @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
* @author <a href="mailto:andreyselkin@gmail.com">Aleksey Nesterenko</a>
*
*/
public class CommentsIndentationCheckTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "indentation" + File.separator + filename);
    }

    @Test
    public void testCommentIsAtTheEndOfBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY_SINGLE, 17, 25, 8),
            "33: " + getCheckMessage(MSG_KEY_SINGLE, 35, 5, 4),
            "37: " + getCheckMessage(MSG_KEY_SINGLE, 36, 0, 8),
            "47: " + getCheckMessage(MSG_KEY_SINGLE, 46, 15, 12),
            "49: " + getCheckMessage(MSG_KEY_SINGLE, 48, 10, 8),
            "54: " + getCheckMessage(MSG_KEY_SINGLE, 53, 13, 8),
            "74: " + getCheckMessage(MSG_KEY_SINGLE, 70, 18, 8),
            "88: " + getCheckMessage(MSG_KEY_SINGLE, 85, 31, 8),
            "100: " + getCheckMessage(MSG_KEY_SINGLE, 98, 21, 8),
            "115: " + getCheckMessage(MSG_KEY_SINGLE, 116, 29, 12),
            "138: " + getCheckMessage(MSG_KEY_SINGLE, 131, 26, 8),
            "164: " + getCheckMessage(MSG_KEY_SINGLE, 161, 33, 8),
            "174: " + getCheckMessage(MSG_KEY_SINGLE, 173, 21, 8),
            "186: " + getCheckMessage(MSG_KEY_SINGLE, 184, 34, 8),
            "208: " + getCheckMessage(MSG_KEY_SINGLE, 206, 26, 8),
            "214: " + getCheckMessage(MSG_KEY_SINGLE, 213, 0, 8),
            "228: " + getCheckMessage(MSG_KEY_SINGLE, 225, 12, 8),
            "234: " + getCheckMessage(MSG_KEY_SINGLE, 233, 0, 8),
            "248: " + getCheckMessage(MSG_KEY_SINGLE, 245, 12, 8),
            "255: " + getCheckMessage(MSG_KEY_SINGLE, 252, 0, 8),
            "265: " + getCheckMessage(MSG_KEY_SINGLE, 264, 14, 8),
            "271: " + getCheckMessage(MSG_KEY_SINGLE, 269, 9, 8),
            "277: " + getCheckMessage(MSG_KEY_SINGLE, 276, 9, 8),
            "316: " + getCheckMessage(MSG_KEY_SINGLE, 315, 9, 8),
            "322: " + getCheckMessage(MSG_KEY_SINGLE, 323, 0, 4),
            "336: " + getCheckMessage(MSG_KEY_SINGLE, 337, 0, 4),
            "355: " + getCheckMessage(MSG_KEY_SINGLE, 352, 9, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "25: " + getCheckMessage(MSG_KEY_SINGLE, "24, 26", 19, "16, 12"),
            "31: " + getCheckMessage(MSG_KEY_SINGLE, "30, 32", 19, "16, 12"),
            "48: " + getCheckMessage(MSG_KEY_SINGLE, 49, 6, 16),
            "55: " + getCheckMessage(MSG_KEY_SINGLE, 56, 8, 12),
            "59: " + getCheckMessage(MSG_KEY_SINGLE, 58, 22, 16),
            "68: " + getCheckMessage(MSG_KEY_SINGLE, "65, 69", 14, "12, 16"),
            "88: " + getCheckMessage(MSG_KEY_SINGLE, 89, 24, 20),
            "113: " + getCheckMessage(MSG_KEY_SINGLE, "112, 114", 15, "17, 12"),
            "125: " + getCheckMessage(MSG_KEY_SINGLE, 126, 8, 12),
            "138: " + getCheckMessage(MSG_KEY_SINGLE, 139, 4, 8),
            "157: " + getCheckMessage(MSG_KEY_SINGLE, "156, 158", 18, "16, 12"),
            "200: " + getCheckMessage(MSG_KEY_SINGLE, "199, 201", 4, "12, 12"),
            "203: " + getCheckMessage(MSG_KEY_SINGLE, 204, 22, 20),
            "204: " + getCheckMessage(MSG_KEY_SINGLE, 205, 20, 17),
            "205: " + getCheckMessage(MSG_KEY_SINGLE, "202, 206", 17, "16, 12"),
            "229: " + getCheckMessage(MSG_KEY_SINGLE, "228, 230", 6, "12, 12"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY_SINGLE, 10, 19, 23),
            "10: " + getCheckMessage(MSG_KEY_BLOCK, 12, 23, 31),
            "33: " + getCheckMessage(MSG_KEY_SINGLE, 34, 0, 8),
            "57: " + getCheckMessage(MSG_KEY_SINGLE, 58, 0, 8),
            "71: " + getCheckMessage(MSG_KEY_SINGLE, 72, 0, 8),
        };
        final String testInputFile = "InputCommentsIndentationInEmptyBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "23: " + getCheckMessage(MSG_KEY_BLOCK, 24, 16, 12),
            "25: " + getCheckMessage(MSG_KEY_BLOCK, 27, 16, 12),
            "28: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 51, 27, 23),
            "51: " + getCheckMessage(MSG_KEY_BLOCK, 53, 23, 36),
            "90: " + getCheckMessage(MSG_KEY_SINGLE, 91, 14, 8),
            "98: " + getCheckMessage(MSG_KEY_SINGLE, 99, 13, 8),
            "108: " + getCheckMessage(MSG_KEY_SINGLE, 109, 33, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testNoNpeWhenBlockCommentEndsClassFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        final String testInputFile = "InputCommentsIndentationNoNpe.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlySingleLineComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        checkConfig.addAttribute("tokens", "SINGLE_LINE_COMMENT");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 51, 27, 23),
            "90: " + getCheckMessage(MSG_KEY_SINGLE, 91, 14, 8),
            "98: " + getCheckMessage(MSG_KEY_SINGLE, 99, 13, 8),
            "108: " + getCheckMessage(MSG_KEY_SINGLE, 109, 33, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CommentsIndentationCheck.class);
        checkConfig.addAttribute("tokens", "BLOCK_COMMENT_BEGIN");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY_BLOCK, 24, 16, 12),
            "25: " + getCheckMessage(MSG_KEY_BLOCK, 27, 16, 12),
            "28: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "51: " + getCheckMessage(MSG_KEY_BLOCK, 53, 23, 36),
            };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testVisitToken() {
        final CommentsIndentationCheck check = new CommentsIndentationCheck();
        final DetailAST methodDef = new DetailAST();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("methodStub");
        try {
            check.visitToken(methodDef);
            Assert.fail("IllegalArgumentException should have been thrown!");
        }
        catch (IllegalArgumentException ex) {
            final String msg = ex.getMessage();
            Assert.assertEquals("Unexpected token type: methodStub", msg);
        }
    }
}
