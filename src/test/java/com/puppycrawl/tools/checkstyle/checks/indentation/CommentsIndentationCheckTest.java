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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck.MSG_KEY_SINGLE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CommentsIndentationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/commentsindentation";
    }

    @Test
    public void testCommentIsAtTheEndOfBlockOne() throws Exception {
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
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockOne.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsAtTheEndOfBlockTwo() throws Exception {
        final String[] expected = {
            "22:30: " + getCheckMessage(MSG_KEY_SINGLE, 23, 29, 12),
            "45:27: " + getCheckMessage(MSG_KEY_SINGLE, 38, 26, 8),
            "71:34: " + getCheckMessage(MSG_KEY_SINGLE, 68, 33, 8),
            "81:22: " + getCheckMessage(MSG_KEY_SINGLE, 80, 21, 8),
            "93:35: " + getCheckMessage(MSG_KEY_SINGLE, 91, 34, 8),
            "115:27: " + getCheckMessage(MSG_KEY_SINGLE, 113, 26, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockTwo.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsAtTheEndOfBlockThree() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_KEY_SINGLE, 20, 0, 8),
            "35:13: " + getCheckMessage(MSG_KEY_SINGLE, 32, 12, 8),
            "42:1: " + getCheckMessage(MSG_KEY_SINGLE, 40, 0, 8),
            "56:13: " + getCheckMessage(MSG_KEY_SINGLE, 53, 12, 8),
            "63:1: " + getCheckMessage(MSG_KEY_SINGLE, 60, 0, 8),
            "73:15: " + getCheckMessage(MSG_KEY_SINGLE, 72, 14, 8),
            "79:10: " + getCheckMessage(MSG_KEY_SINGLE, 77, 9, 8),
            "85:10: " + getCheckMessage(MSG_KEY_SINGLE, 84, 9, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockThree.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsAtTheEndOfBlockFour() throws Exception {
        final String[] expected = {
            "22:10: " + getCheckMessage(MSG_KEY_SINGLE, 21, 9, 8),
            "28:1: " + getCheckMessage(MSG_KEY_SINGLE, 29, 0, 4),
            "41:1: " + getCheckMessage(MSG_KEY_SINGLE, 38, 0, 8),
            "60:10: " + getCheckMessage(MSG_KEY_SINGLE, 57, 9, 8),
            "86:13: " + getCheckMessage(MSG_KEY_BLOCK, 87, 12, 8),
            "89:13: " + getCheckMessage(MSG_KEY_SINGLE, 87, 12, 8),
            "99:13: " + getCheckMessage(MSG_KEY_SINGLE, 98, 12, 8),
            "106:9: " + getCheckMessage(MSG_KEY_SINGLE, 109, 8, 10),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockFour.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsAtTheEndOfBlockFive() throws Exception {
        final String[] expected = {
            "61:1: " + getCheckMessage(MSG_KEY_SINGLE, 59, 0, 8),
            "77:11: " + getCheckMessage(MSG_KEY_BLOCK, 73, 10, 8),
            "87:11: " + getCheckMessage(MSG_KEY_BLOCK, 81, 10, 8),
            "95:11: " + getCheckMessage(MSG_KEY_BLOCK, 91, 10, 8),
            "103:11: " + getCheckMessage(MSG_KEY_BLOCK, 99, 10, 8),
            "111:11: " + getCheckMessage(MSG_KEY_BLOCK, 107, 10, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockFive.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsAtTheEndOfBlockSix() throws Exception {
        final String[] expected = {
            "26:11: " + getCheckMessage(MSG_KEY_SINGLE, 19, 10, 8),
            "33:1: " + getCheckMessage(MSG_KEY_SINGLE, 30, 0, 8),
            "39:1: " + getCheckMessage(MSG_KEY_SINGLE, 36, 0, 8),
            "45:1: " + getCheckMessage(MSG_KEY_SINGLE, 43, 0, 8),
            "52:5: " + getCheckMessage(MSG_KEY_SINGLE, 48, 4, 8),
            "56:13: " + getCheckMessage(MSG_KEY_SINGLE, 55, 12, 8),
            "62:1: " + getCheckMessage(MSG_KEY_SINGLE, 60, 0, 8),
            "67:1: " + getCheckMessage(MSG_KEY_SINGLE, 66, 0, 8),
            "82:1: " + getCheckMessage(MSG_KEY_SINGLE, 79, 0, 8),
            "94:13: " + getCheckMessage(MSG_KEY_SINGLE, 92, 12, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlockSix.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlockOne() throws Exception {
        final String[] expected = {
            "27:13: " + getCheckMessage(MSG_KEY_BLOCK, 28, 12, 16),
            "33:20: " + getCheckMessage(MSG_KEY_SINGLE, "32, 34", 19, "16, 12"),
            "39:20: " + getCheckMessage(MSG_KEY_SINGLE, "38, 40", 19, "16, 12"),
            "56:7: " + getCheckMessage(MSG_KEY_SINGLE, 57, 6, 16),
            "63:9: " + getCheckMessage(MSG_KEY_SINGLE, 64, 8, 12),
            "67:23: " + getCheckMessage(MSG_KEY_SINGLE, 66, 22, 16),
            "76:15: " + getCheckMessage(MSG_KEY_SINGLE, "73, 77", 14, "12, 16"),
            "96:25: " + getCheckMessage(MSG_KEY_SINGLE, 97, 24, 20),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlockOne.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlockTwo() throws Exception {
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY_SINGLE, 19, 24, 20),
            "43:16: " + getCheckMessage(MSG_KEY_SINGLE, "42, 44", 15, "17, 12"),
            "55:9: " + getCheckMessage(MSG_KEY_SINGLE, 56, 8, 12),
            "68:5: " + getCheckMessage(MSG_KEY_SINGLE, 69, 4, 8),
            "87:19: " + getCheckMessage(MSG_KEY_SINGLE, "86, 88", 18, "16, 12"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlockTwo.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlockThree() throws Exception {
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY_SINGLE, 19, 24, 20),
            "45:5: " + getCheckMessage(MSG_KEY_SINGLE, "44, 46", 4, "12, 12"),
            "48:23: " + getCheckMessage(MSG_KEY_SINGLE, "47, 51", 22, "16, 12"),
            "49:21: " + getCheckMessage(MSG_KEY_SINGLE, "47, 51", 20, "16, 12"),
            "50:18: " + getCheckMessage(MSG_KEY_SINGLE, "47, 51", 17, "16, 12"),
            "74:7: " + getCheckMessage(MSG_KEY_SINGLE, "73, 75", 6, "12, 12"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlockThree.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlockFour() throws Exception {
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY_SINGLE, 19, 24, 20),
            "34:12: " + getCheckMessage(MSG_KEY_BLOCK, "33, 37", 11, "16, 12"),
            "39:12: " + getCheckMessage(MSG_KEY_SINGLE, "38, 40", 11, "16, 12"),
            "69:1: " + getCheckMessage(MSG_KEY_SINGLE, "70", 0, "8"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlockFour.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
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
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testSurroundingCodeOne() throws Exception {
        final String[] expected = {
            "20:15: " + getCheckMessage(MSG_KEY_SINGLE, 21, 14, 12),
            "31:17: " + getCheckMessage(MSG_KEY_BLOCK, 32, 16, 12),
            "33:17: " + getCheckMessage(MSG_KEY_BLOCK, 35, 16, 12),
            "36:17: " + getCheckMessage(MSG_KEY_BLOCK, 39, 16, 12),
            "58:28: " + getCheckMessage(MSG_KEY_SINGLE, 61, 27, 36),
            "59:24: " + getCheckMessage(MSG_KEY_BLOCK, 61, 23, 36),
            "98:15: " + getCheckMessage(MSG_KEY_SINGLE, 99, 14, 8),
            "106:14: " + getCheckMessage(MSG_KEY_SINGLE, 108, 13, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCodeOne.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testSurroundingCodeTwo() throws Exception {
        final String[] expected = {
            "20:34: " + getCheckMessage(MSG_KEY_SINGLE, 21, 33, 8),
            "42:13: " + getCheckMessage(MSG_KEY_BLOCK, 43, 12, 8),
            "48:5: " + getCheckMessage(MSG_KEY_BLOCK, 49, 4, 8),
            "55:5: " + getCheckMessage(MSG_KEY_BLOCK, 53, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCodeTwo.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testNoNpeWhenBlockCommentEndsClassFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String testInputFile = "InputCommentsIndentationNoNpe.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlySingleLineComments() throws Exception {
        final String[] expected = {
            "20:15: " + getCheckMessage(MSG_KEY_SINGLE, 21, 14, 12),
            "57:28: " + getCheckMessage(MSG_KEY_SINGLE, 60, 27, 36),
            "97:15: " + getCheckMessage(MSG_KEY_SINGLE, 98, 14, 8),
            "105:14: " + getCheckMessage(MSG_KEY_SINGLE, 107, 13, 8),
            "115:34: " + getCheckMessage(MSG_KEY_SINGLE, 116, 33, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode2One.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockCommentsOne() throws Exception {
        final String[] expected = {
            "30:17: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "32:17: " + getCheckMessage(MSG_KEY_BLOCK, 34, 16, 12),
            "35:17: " + getCheckMessage(MSG_KEY_BLOCK, 38, 16, 12),
            "57:24: " + getCheckMessage(MSG_KEY_BLOCK, 59, 23, 36),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode3One.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockCommentsTwo() throws Exception {
        final String[] expected = {
            "40:13: " + getCheckMessage(MSG_KEY_BLOCK, 41, 12, 8),
            "46:5: " + getCheckMessage(MSG_KEY_BLOCK, 47, 4, 8),
            "53:5: " + getCheckMessage(MSG_KEY_BLOCK, 51, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode3Two.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
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
        final String[] expected = {
            "10:3: " + getCheckMessage(MSG_KEY_BLOCK, 13, 2, 0),
            "16:1: " + getCheckMessage(MSG_KEY_BLOCK, 17, 0, 4),
            "19:9: " + getCheckMessage(MSG_KEY_BLOCK, 22, 8, 4),
            "26:11: " + getCheckMessage(MSG_KEY_BLOCK, 27, 10, 8),
        };
        final String testInputFile = "InputCommentsIndentationJavadoc.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testMultiblockStructuresOne() throws Exception {
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
        };
        final String testInputFile = "InputCommentsIndentationInMultiblockStructuresOne.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testMultiblockStructuresTwo() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY_SINGLE, 19, 8, 12),
            "26:17: " + getCheckMessage(MSG_KEY_SINGLE, "25, 27", 16, "12, 8"),
            "31:1: " + getCheckMessage(MSG_KEY_SINGLE, "30, 32", 0, "12, 8"),
        };
        final String testInputFile = "InputCommentsIndentationInMultiblockStructuresTwo.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentsAfterAnnotation() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY_SINGLE, 22, 4, 0),
            "25:9: " + getCheckMessage(MSG_KEY_SINGLE, 26, 8, 4),
            "43:5: " + getCheckMessage(MSG_KEY_SINGLE, 44, 4, 0),
            "48:9: " + getCheckMessage(MSG_KEY_SINGLE, 49, 8, 4),
            "57:3: " + getCheckMessage(MSG_KEY_SINGLE, 58, 2, 4),
        };
        final String testInputFile = "InputCommentsIndentationAfterAnnotation.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentsInSameMethodCallWithSameIndent() throws Exception {
        final String[] expected = {
            "23:7: " + getCheckMessage(MSG_KEY_SINGLE, 24, 6, 4),
            "30:11: " + getCheckMessage(MSG_KEY_SINGLE, 31, 10, 4),
        };
        verifyWithInlineConfigParser(
            getPath("InputCommentsIndentationWithInMethodCallWithSameIndent.java"),
            expected);
    }

    @Test
    public void testCommentIndentationWithEmoji() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY_SINGLE, 15, 8, 16),
            "25:13: " + getCheckMessage(MSG_KEY_SINGLE, 24, 12, 8),
            "27:9: " + getCheckMessage(MSG_KEY_SINGLE, 29, 8, 4),
            "46:17: " + getCheckMessage(MSG_KEY_SINGLE, 45, 16, 24),
            "70:13: " + getCheckMessage(MSG_KEY_SINGLE, 72, 12, 8),
            "74:9: " + getCheckMessage(MSG_KEY_SINGLE, 73, 8, 12),
            "88:13: " + getCheckMessage(MSG_KEY_SINGLE, 90, 12, 8),
            "98:17: " + getCheckMessage(MSG_KEY_BLOCK, 99, 16, 12),
            "100:17: " + getCheckMessage(MSG_KEY_BLOCK, 102, 16, 12),
            "103:17: " + getCheckMessage(MSG_KEY_BLOCK, 116, 16, 12, 1),
        };
        verifyWithInlineConfigParser(
            getPath("InputCommentsIndentationCheckWithEmoji.java"),
            expected);
    }

    @Test
    public void testCommentsBlockCommentBeforePackage() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_BLOCK, 11, 0, 1),
        };
        verifyWithInlineConfigParser(
            getPath("InputCommentsIndentationBlockCommentBeforePackage.java"),
            expected);
    }

    @Test
    public void testCommentsAfterRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "15:17: " + getCheckMessage(MSG_KEY_SINGLE, 16, 16, 20),
            "28:1: " + getCheckMessage(MSG_KEY_SINGLE, 29, 0, 4),
            "31:9: " + getCheckMessage(MSG_KEY_SINGLE, 32, 8, 4),
            "37:9: " + getCheckMessage(MSG_KEY_SINGLE, 40, 8, 5),
            "42:9: " + getCheckMessage(MSG_KEY_SINGLE, 40, 8, 5),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputCommentsIndentationRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testCommentsAtTheEndOfMethodCall() throws Exception {
        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_KEY_SINGLE, 20, 15, 8),
        };
        verifyWithInlineConfigParser(
            getPath("InputCommentsIndentationCommentsAfterMethodCall.java"),
            expected);
    }

}
