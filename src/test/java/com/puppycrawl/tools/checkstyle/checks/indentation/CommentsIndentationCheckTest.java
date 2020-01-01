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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_SINGLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CommentsIndentationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/commentsindentation";
    }

    @Test
    public void testCommentIsAtTheEndOfBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY_SINGLE, 17, 25, 8),
            "33: " + getCheckMessage(MSG_KEY_SINGLE, 35, 5, 4),
            "37: " + getCheckMessage(MSG_KEY_SINGLE, 36, 0, 8),
            "47: " + getCheckMessage(MSG_KEY_SINGLE, 46, 15, 12),
            "49: " + getCheckMessage(MSG_KEY_SINGLE, 45, 10, 8),
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
            "336: " + getCheckMessage(MSG_KEY_SINGLE, 333, 0, 8),
            "355: " + getCheckMessage(MSG_KEY_SINGLE, 352, 9, 8),
            "380: " + getCheckMessage(MSG_KEY_BLOCK, 381, 12, 8),
            "393: " + getCheckMessage(MSG_KEY_SINGLE, 392, 12, 8),
            "400: " + getCheckMessage(MSG_KEY_SINGLE, 401, 8, 10),
            "457: " + getCheckMessage(MSG_KEY_SINGLE, 455, 0, 8),
            "473: " + getCheckMessage(MSG_KEY_BLOCK, 469, 10, 8),
            "483: " + getCheckMessage(MSG_KEY_BLOCK, 477, 10, 8),
            "491: " + getCheckMessage(MSG_KEY_BLOCK, 487, 10, 8),
            "499: " + getCheckMessage(MSG_KEY_BLOCK, 495, 10, 8),
            "507: " + getCheckMessage(MSG_KEY_BLOCK, 503, 10, 8),
            "518: " + getCheckMessage(MSG_KEY_SINGLE, 511, 10, 8),
            "525: " + getCheckMessage(MSG_KEY_SINGLE, 522, 0, 8),
            "532: " + getCheckMessage(MSG_KEY_SINGLE, 529, 0, 8),
            "538: " + getCheckMessage(MSG_KEY_SINGLE, 536, 0, 8),
            "546: " + getCheckMessage(MSG_KEY_SINGLE, 542, 4, 8),
            "551: " + getCheckMessage(MSG_KEY_SINGLE, 550, 12, 8),
            "557: " + getCheckMessage(MSG_KEY_SINGLE, 555, 0, 8),
            "562: " + getCheckMessage(MSG_KEY_SINGLE, 561, 0, 8),
            "577: " + getCheckMessage(MSG_KEY_SINGLE, 574, 0, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "19: " + getCheckMessage(MSG_KEY_BLOCK, 20, 12, 16),
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
            "203: " + getCheckMessage(MSG_KEY_SINGLE, "202, 206", 22, "16, 12"),
            "204: " + getCheckMessage(MSG_KEY_SINGLE, "202, 206", 20, "16, 12"),
            "205: " + getCheckMessage(MSG_KEY_SINGLE, "202, 206", 17, "16, 12"),
            "229: " + getCheckMessage(MSG_KEY_SINGLE, "228, 230", 6, "12, 12"),
            "276: " + getCheckMessage(MSG_KEY_BLOCK, "275, 279", 11, "16, 12"),
            "281: " + getCheckMessage(MSG_KEY_SINGLE, "280, 282", 11, "16, 12"),
            "311: " + getCheckMessage(MSG_KEY_SINGLE, "312", 0, "8"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY_SINGLE, 12, 19, 31),
            "10: " + getCheckMessage(MSG_KEY_BLOCK, 12, 23, 31),
            "33: " + getCheckMessage(MSG_KEY_SINGLE, 34, 0, 8),
            "57: " + getCheckMessage(MSG_KEY_SINGLE, 58, 0, 8),
            "71: " + getCheckMessage(MSG_KEY_SINGLE, 72, 0, 8),
            "103: " + getCheckMessage(MSG_KEY_SINGLE, 104, 0, 8),
            "107: " + getCheckMessage(MSG_KEY_SINGLE, 108, 0, 8),
        };
        final String testInputFile = "InputCommentsIndentationInEmptyBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "23: " + getCheckMessage(MSG_KEY_BLOCK, 24, 16, 12),
            "25: " + getCheckMessage(MSG_KEY_BLOCK, 27, 16, 12),
            "28: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 53, 27, 36),
            "51: " + getCheckMessage(MSG_KEY_BLOCK, 53, 23, 36),
            "90: " + getCheckMessage(MSG_KEY_SINGLE, 91, 14, 8),
            "98: " + getCheckMessage(MSG_KEY_SINGLE, 100, 13, 8),
            "108: " + getCheckMessage(MSG_KEY_SINGLE, 109, 33, 8),
            "130: " + getCheckMessage(MSG_KEY_BLOCK, 131, 12, 8),
            "135: " + getCheckMessage(MSG_KEY_BLOCK, 136, 4, 8),
            "141: " + getCheckMessage(MSG_KEY_BLOCK, 140, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testNoNpeWhenBlockCommentEndsClassFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String testInputFile = "InputCommentsIndentationNoNpe.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlySingleLineComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        checkConfig.addAttribute("tokens", "SINGLE_LINE_COMMENT");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_KEY_SINGLE, 14, 14, 12),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 53, 27, 36),
            "90: " + getCheckMessage(MSG_KEY_SINGLE, 91, 14, 8),
            "98: " + getCheckMessage(MSG_KEY_SINGLE, 100, 13, 8),
            "108: " + getCheckMessage(MSG_KEY_SINGLE, 109, 33, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        checkConfig.addAttribute("tokens", "BLOCK_COMMENT_BEGIN");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY_BLOCK, 24, 16, 12),
            "25: " + getCheckMessage(MSG_KEY_BLOCK, 27, 16, 12),
            "28: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "51: " + getCheckMessage(MSG_KEY_BLOCK, 53, 23, 36),
            "130: " + getCheckMessage(MSG_KEY_BLOCK, 131, 12, 8),
            "135: " + getCheckMessage(MSG_KEY_BLOCK, 136, 4, 8),
            "141: " + getCheckMessage(MSG_KEY_BLOCK, 140, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testVisitToken() {
        final CommentsIndentationCheck check = new CommentsIndentationCheck();
        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("methodStub");
        try {
            check.visitToken(methodDef);
            fail("IllegalArgumentException should have been thrown!");
        }
        catch (IllegalArgumentException ex) {
            final String msg = ex.getMessage();
            assertEquals("Unexpected token type: methodStub", msg, "Invalid exception message");
        }
    }

    @Test
    public void testJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY_BLOCK, 6, 2, 0),
            "8: " + getCheckMessage(MSG_KEY_BLOCK, 9, 0, 4),
            "11: " + getCheckMessage(MSG_KEY_BLOCK, 14, 8, 4),
            "17: " + getCheckMessage(MSG_KEY_BLOCK, 18, 10, 8),
        };
        final String testInputFile = "InputCommentsIndentationJavadoc.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testMultiblockStructures() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "12: " + getCheckMessage(MSG_KEY_SINGLE, 11, 8, 12),
            "18: " + getCheckMessage(MSG_KEY_SINGLE, "17, 19", 16, "12, 8"),
            "23: " + getCheckMessage(MSG_KEY_SINGLE, "22, 24", 0, "12, 8"),
            "33: " + getCheckMessage(MSG_KEY_SINGLE, 32, 8, 12),
            "39: " + getCheckMessage(MSG_KEY_SINGLE, "38, 40", 0, "12, 8"),
            "44: " + getCheckMessage(MSG_KEY_SINGLE, "43, 45", 16, "12, 8"),
            "54: " + getCheckMessage(MSG_KEY_SINGLE, 53, 8, 12),
            "60: " + getCheckMessage(MSG_KEY_SINGLE, "59, 61", 0, "12, 8"),
            "65: " + getCheckMessage(MSG_KEY_SINGLE, "64, 66", 16, "12, 8"),
            "75: " + getCheckMessage(MSG_KEY_SINGLE, 74, 8, 12),
            "81: " + getCheckMessage(MSG_KEY_SINGLE, "80, 82", 0, "12, 8"),
            "86: " + getCheckMessage(MSG_KEY_SINGLE, "85, 87", 16, "12, 8"),
            "96: " + getCheckMessage(MSG_KEY_SINGLE, 95, 8, 12),
            "102: " + getCheckMessage(MSG_KEY_SINGLE, "101, 103", 0, "12, 8"),
            "107: " + getCheckMessage(MSG_KEY_SINGLE, "106, 108", 16, "12, 8"),
            "117: " + getCheckMessage(MSG_KEY_SINGLE, 116, 8, 12),
            "123: " + getCheckMessage(MSG_KEY_SINGLE, "122, 124", 16, "12, 8"),
            "128: " + getCheckMessage(MSG_KEY_SINGLE, "127, 129", 0, "12, 8"),
        };
        final String testInputFile = "InputCommentsIndentationInMultiblockStructures.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentsAfterAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_SINGLE, 15, 4, 0),
            "18: " + getCheckMessage(MSG_KEY_SINGLE, 19, 8, 4),
            "36: " + getCheckMessage(MSG_KEY_SINGLE, 37, 4, 0),
            "41: " + getCheckMessage(MSG_KEY_SINGLE, 42, 8, 4),
            "50: " + getCheckMessage(MSG_KEY_SINGLE, 51, 2, 4),
        };
        final String testInputFile = "InputCommentsIndentationAfterAnnotation.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

}
