////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_SINGLE;

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
            "25:26: " + getCheckMessage(MSG_KEY_SINGLE, 24, 25, 8),
            "40:6: " + getCheckMessage(MSG_KEY_SINGLE, 42, 5, 4),
            "44:1: " + getCheckMessage(MSG_KEY_SINGLE, 43, 0, 8),
            "54:16: " + getCheckMessage(MSG_KEY_SINGLE, 53, 15, 12),
            "56:11: " + getCheckMessage(MSG_KEY_SINGLE, 52, 10, 8),
            "61:14: " + getCheckMessage(MSG_KEY_SINGLE, 60, 13, 8),
            "81:19: " + getCheckMessage(MSG_KEY_SINGLE, 77, 18, 8),
            "95:32: " + getCheckMessage(MSG_KEY_SINGLE, 92, 31, 8),
            "107:22: " + getCheckMessage(MSG_KEY_SINGLE, 105, 21, 8),
            "122:30: " + getCheckMessage(MSG_KEY_SINGLE, 123, 29, 12),
            "145:27: " + getCheckMessage(MSG_KEY_SINGLE, 138, 26, 8),
            "171:34: " + getCheckMessage(MSG_KEY_SINGLE, 168, 33, 8),
            "181:22: " + getCheckMessage(MSG_KEY_SINGLE, 180, 21, 8),
            "193:35: " + getCheckMessage(MSG_KEY_SINGLE, 191, 34, 8),
            "215:27: " + getCheckMessage(MSG_KEY_SINGLE, 213, 26, 8),
            "221:1: " + getCheckMessage(MSG_KEY_SINGLE, 220, 0, 8),
            "235:13: " + getCheckMessage(MSG_KEY_SINGLE, 232, 12, 8),
            "241:1: " + getCheckMessage(MSG_KEY_SINGLE, 240, 0, 8),
            "255:13: " + getCheckMessage(MSG_KEY_SINGLE, 252, 12, 8),
            "262:1: " + getCheckMessage(MSG_KEY_SINGLE, 259, 0, 8),
            "272:15: " + getCheckMessage(MSG_KEY_SINGLE, 271, 14, 8),
            "278:10: " + getCheckMessage(MSG_KEY_SINGLE, 276, 9, 8),
            "284:10: " + getCheckMessage(MSG_KEY_SINGLE, 283, 9, 8),
            "323:10: " + getCheckMessage(MSG_KEY_SINGLE, 322, 9, 8),
            "329:1: " + getCheckMessage(MSG_KEY_SINGLE, 330, 0, 4),
            "343:1: " + getCheckMessage(MSG_KEY_SINGLE, 340, 0, 8),
            "362:10: " + getCheckMessage(MSG_KEY_SINGLE, 359, 9, 8),
            "387:13: " + getCheckMessage(MSG_KEY_BLOCK, 388, 12, 8),
            "390:13: " + getCheckMessage(MSG_KEY_SINGLE, 388, 12, 8),
            "400:13: " + getCheckMessage(MSG_KEY_SINGLE, 399, 12, 8),
            "407:9: " + getCheckMessage(MSG_KEY_SINGLE, 408, 8, 10),
            "464:1: " + getCheckMessage(MSG_KEY_SINGLE, 462, 0, 8),
            "480:11: " + getCheckMessage(MSG_KEY_BLOCK, 476, 10, 8),
            "490:11: " + getCheckMessage(MSG_KEY_BLOCK, 484, 10, 8),
            "498:11: " + getCheckMessage(MSG_KEY_BLOCK, 494, 10, 8),
            "506:11: " + getCheckMessage(MSG_KEY_BLOCK, 502, 10, 8),
            "514:11: " + getCheckMessage(MSG_KEY_BLOCK, 510, 10, 8),
            "525:11: " + getCheckMessage(MSG_KEY_SINGLE, 518, 10, 8),
            "532:1: " + getCheckMessage(MSG_KEY_SINGLE, 529, 0, 8),
            "539:1: " + getCheckMessage(MSG_KEY_SINGLE, 536, 0, 8),
            "545:1: " + getCheckMessage(MSG_KEY_SINGLE, 543, 0, 8),
            "553:5: " + getCheckMessage(MSG_KEY_SINGLE, 549, 4, 8),
            "558:13: " + getCheckMessage(MSG_KEY_SINGLE, 557, 12, 8),
            "564:1: " + getCheckMessage(MSG_KEY_SINGLE, 562, 0, 8),
            "569:1: " + getCheckMessage(MSG_KEY_SINGLE, 568, 0, 8),
            "584:1: " + getCheckMessage(MSG_KEY_SINGLE, 581, 0, 8),
            "596:13: " + getCheckMessage(MSG_KEY_SINGLE, 594, 12, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "26:13: " + getCheckMessage(MSG_KEY_BLOCK, 27, 12, 16),
            "32:20: " + getCheckMessage(MSG_KEY_SINGLE, "31, 33", 19, "16, 12"),
            "38:20: " + getCheckMessage(MSG_KEY_SINGLE, "37, 39", 19, "16, 12"),
            "55:7: " + getCheckMessage(MSG_KEY_SINGLE, 56, 6, 16),
            "62:9: " + getCheckMessage(MSG_KEY_SINGLE, 63, 8, 12),
            "66:23: " + getCheckMessage(MSG_KEY_SINGLE, 65, 22, 16),
            "75:15: " + getCheckMessage(MSG_KEY_SINGLE, "72, 76", 14, "12, 16"),
            "95:25: " + getCheckMessage(MSG_KEY_SINGLE, 96, 24, 20),
            "120:16: " + getCheckMessage(MSG_KEY_SINGLE, "119, 121", 15, "17, 12"),
            "132:9: " + getCheckMessage(MSG_KEY_SINGLE, 133, 8, 12),
            "145:5: " + getCheckMessage(MSG_KEY_SINGLE, 146, 4, 8),
            "164:19: " + getCheckMessage(MSG_KEY_SINGLE, "163, 165", 18, "16, 12"),
            "207:5: " + getCheckMessage(MSG_KEY_SINGLE, "206, 208", 4, "12, 12"),
            "210:23: " + getCheckMessage(MSG_KEY_SINGLE, "209, 213", 22, "16, 12"),
            "211:21: " + getCheckMessage(MSG_KEY_SINGLE, "209, 213", 20, "16, 12"),
            "212:18: " + getCheckMessage(MSG_KEY_SINGLE, "209, 213", 17, "16, 12"),
            "236:7: " + getCheckMessage(MSG_KEY_SINGLE, "235, 237", 6, "12, 12"),
            "283:12: " + getCheckMessage(MSG_KEY_BLOCK, "282, 286", 11, "16, 12"),
            "288:12: " + getCheckMessage(MSG_KEY_SINGLE, "287, 289", 11, "16, 12"),
            "318:1: " + getCheckMessage(MSG_KEY_SINGLE, "319", 0, "8"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_KEY_SINGLE, 19, 19, 31),
            "17:24: " + getCheckMessage(MSG_KEY_BLOCK, 19, 23, 31),
            "40:1: " + getCheckMessage(MSG_KEY_SINGLE, 41, 0, 8),
            "64:1: " + getCheckMessage(MSG_KEY_SINGLE, 65, 0, 8),
            "78:1: " + getCheckMessage(MSG_KEY_SINGLE, 79, 0, 8),
            "110:1: " + getCheckMessage(MSG_KEY_SINGLE, 111, 0, 8),
            "114:1: " + getCheckMessage(MSG_KEY_SINGLE, 115, 0, 8),
        };
        final String testInputFile = "InputCommentsIndentationInEmptyBlock.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "20:15: " + getCheckMessage(MSG_KEY_SINGLE, 21, 14, 12),
            "30:17: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "32:17: " + getCheckMessage(MSG_KEY_BLOCK, 34, 16, 12),
            "35:17: " + getCheckMessage(MSG_KEY_BLOCK, 38, 16, 12),
            "57:28: " + getCheckMessage(MSG_KEY_SINGLE, 60, 27, 36),
            "58:24: " + getCheckMessage(MSG_KEY_BLOCK, 60, 23, 36),
            "97:15: " + getCheckMessage(MSG_KEY_SINGLE, 98, 14, 8),
            "105:14: " + getCheckMessage(MSG_KEY_SINGLE, 107, 13, 8),
            "115:34: " + getCheckMessage(MSG_KEY_SINGLE, 116, 33, 8),
            "137:13: " + getCheckMessage(MSG_KEY_BLOCK, 138, 12, 8),
            "142:5: " + getCheckMessage(MSG_KEY_BLOCK, 143, 4, 8),
            "148:5: " + getCheckMessage(MSG_KEY_BLOCK, 147, 4, 8),
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
        checkConfig.addProperty("tokens", "SINGLE_LINE_COMMENT");
        final String[] expected = {
            "20:15: " + getCheckMessage(MSG_KEY_SINGLE, 21, 14, 12),
            "57:28: " + getCheckMessage(MSG_KEY_SINGLE, 60, 27, 36),
            "97:15: " + getCheckMessage(MSG_KEY_SINGLE, 98, 14, 8),
            "105:14: " + getCheckMessage(MSG_KEY_SINGLE, 107, 13, 8),
            "115:34: " + getCheckMessage(MSG_KEY_SINGLE, 116, 33, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode2.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CommentsIndentationCheck.class);
        checkConfig.addProperty("tokens", "BLOCK_COMMENT_BEGIN");
        final String[] expected = {
            "30:17: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "32:17: " + getCheckMessage(MSG_KEY_BLOCK, 34, 16, 12),
            "35:17: " + getCheckMessage(MSG_KEY_BLOCK, 38, 16, 12),
            "58:24: " + getCheckMessage(MSG_KEY_BLOCK, 60, 23, 36),
            "137:13: " + getCheckMessage(MSG_KEY_BLOCK, 138, 12, 8),
            "142:5: " + getCheckMessage(MSG_KEY_BLOCK, 143, 4, 8),
            "148:5: " + getCheckMessage(MSG_KEY_BLOCK, 147, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode3.java";
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
            assertWithMessage("IllegalArgumentException should have been thrown!").fail();
        }
        catch (IllegalArgumentException ex) {
            final String msg = ex.getMessage();
            assertWithMessage("Invalid exception message")
                .that(msg)
                .isEqualTo("Unexpected token type: methodStub");
        }
    }

    @Test
    public void testJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "10:3: " + getCheckMessage(MSG_KEY_BLOCK, 13, 2, 0),
            "15:1: " + getCheckMessage(MSG_KEY_BLOCK, 16, 0, 4),
            "18:9: " + getCheckMessage(MSG_KEY_BLOCK, 21, 8, 4),
            "24:11: " + getCheckMessage(MSG_KEY_BLOCK, 25, 10, 8),
        };
        final String testInputFile = "InputCommentsIndentationJavadoc.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testMultiblockStructures() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY_SINGLE, 18, 8, 12),
            "25:17: " + getCheckMessage(MSG_KEY_SINGLE, "24, 26", 16, "12, 8"),
            "30:1: " + getCheckMessage(MSG_KEY_SINGLE, "29, 31", 0, "12, 8"),
            "40:9: " + getCheckMessage(MSG_KEY_SINGLE, 39, 8, 12),
            "46:1: " + getCheckMessage(MSG_KEY_SINGLE, "45, 47", 0, "12, 8"),
            "51:17: " + getCheckMessage(MSG_KEY_SINGLE, "50, 52", 16, "12, 8"),
            "61:9: " + getCheckMessage(MSG_KEY_SINGLE, 60, 8, 12),
            "67:1: " + getCheckMessage(MSG_KEY_SINGLE, "66, 68", 0, "12, 8"),
            "72:17: " + getCheckMessage(MSG_KEY_SINGLE, "71, 73", 16, "12, 8"),
            "82:9: " + getCheckMessage(MSG_KEY_SINGLE, 81, 8, 12),
            "88:1: " + getCheckMessage(MSG_KEY_SINGLE, "87, 89", 0, "12, 8"),
            "93:17: " + getCheckMessage(MSG_KEY_SINGLE, "92, 94", 16, "12, 8"),
            "103:9: " + getCheckMessage(MSG_KEY_SINGLE, 102, 8, 12),
            "109:1: " + getCheckMessage(MSG_KEY_SINGLE, "108, 110", 0, "12, 8"),
            "114:17: " + getCheckMessage(MSG_KEY_SINGLE, "113, 115", 16, "12, 8"),
            "124:9: " + getCheckMessage(MSG_KEY_SINGLE, 123, 8, 12),
            "130:17: " + getCheckMessage(MSG_KEY_SINGLE, "129, 131", 16, "12, 8"),
            "135:1: " + getCheckMessage(MSG_KEY_SINGLE, "134, 136", 0, "12, 8"),
        };
        final String testInputFile = "InputCommentsIndentationInMultiblockStructures.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentsAfterAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY_SINGLE, 22, 4, 0),
            "25:9: " + getCheckMessage(MSG_KEY_SINGLE, 26, 8, 4),
            "43:5: " + getCheckMessage(MSG_KEY_SINGLE, 44, 4, 0),
            "48:9: " + getCheckMessage(MSG_KEY_SINGLE, 49, 8, 4),
            "57:3: " + getCheckMessage(MSG_KEY_SINGLE, 58, 2, 4),
        };
        final String testInputFile = "InputCommentsIndentationAfterAnnotation.java";
        verify(checkConfig, getPath(testInputFile), expected);
    }

    @Test
    public void testCommentsInSameMethodCallWithSameIndent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "23:7: " + getCheckMessage(MSG_KEY_SINGLE, 24, 6, 4),
            "30:11: " + getCheckMessage(MSG_KEY_SINGLE, 31, 10, 4),
        };
        verify(checkConfig,
                getPath("InputCommentsIndentationWithInMethodCallWithSameIndent.java"),
                expected);
    }

    @Test
    public void testCommentIndentationWithEmoji() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "7:9: " + getCheckMessage(MSG_KEY_SINGLE, 8, 8, 16),
            "18:13: " + getCheckMessage(MSG_KEY_SINGLE, 17, 12, 8),
            "20:9: " + getCheckMessage(MSG_KEY_SINGLE, 22, 8, 4),
            "39:17: " + getCheckMessage(MSG_KEY_SINGLE, 38, 16, 24),
            "63:13: " + getCheckMessage(MSG_KEY_SINGLE, 65, 12, 8),
            "67:9: " + getCheckMessage(MSG_KEY_SINGLE, 66, 8, 12),
            "81:13: " + getCheckMessage(MSG_KEY_SINGLE, 83, 12, 8),
            "90:17: " + getCheckMessage(MSG_KEY_BLOCK, 91, 16, 12),
            "92:17: " + getCheckMessage(MSG_KEY_BLOCK, 94, 16, 12),
            "95:17: " + getCheckMessage(MSG_KEY_BLOCK, 108, 16, 12, 1),
        };
        verify(checkConfig,
                getPath("InputCommentsIndentationCheckWithEmoji.java"),
                expected);
    }

    @Test
    public void testCommentsBlockCommentBeforePackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_BLOCK, 11, 0, 1),
        };
        verify(checkConfig,
                getPath("InputCommentsIndentationBlockCommentBeforePackage.java"),
                expected);
    }

    @Test
    public void testCommentsAfterRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "15:17: " + getCheckMessage(MSG_KEY_SINGLE, 16, 16, 20),
            "28:1: " + getCheckMessage(MSG_KEY_SINGLE, 29, 0, 4),
            "31:9: " + getCheckMessage(MSG_KEY_SINGLE, 32, 8, 4),
            "37:9: " + getCheckMessage(MSG_KEY_SINGLE, 40, 8, 5),
            "42:9: " + getCheckMessage(MSG_KEY_SINGLE, 40, 8, 5),
        };
        verify(checkConfig,
            getNonCompilablePath("InputCommentsIndentationRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testCommentsAtTheEndOfMethodCall() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CommentsIndentationCheck.class);
        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_KEY_SINGLE, 20, 15, 8),
        };
        verify(checkConfig,
                getPath("InputCommentsIndentationCommentsAfterMethodCall.java"),
                expected);
    }

}
