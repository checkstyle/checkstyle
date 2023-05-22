///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
    public void testCommentIsAtTheEndOfBlock() throws Exception {
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
            "388:13: " + getCheckMessage(MSG_KEY_BLOCK, 389, 12, 8),
            "391:13: " + getCheckMessage(MSG_KEY_SINGLE, 389, 12, 8),
            "401:13: " + getCheckMessage(MSG_KEY_SINGLE, 400, 12, 8),
            "408:9: " + getCheckMessage(MSG_KEY_SINGLE, 409, 8, 10),
            "465:1: " + getCheckMessage(MSG_KEY_SINGLE, 463, 0, 8),
            "481:11: " + getCheckMessage(MSG_KEY_BLOCK, 477, 10, 8),
            "491:11: " + getCheckMessage(MSG_KEY_BLOCK, 485, 10, 8),
            "499:11: " + getCheckMessage(MSG_KEY_BLOCK, 495, 10, 8),
            "507:11: " + getCheckMessage(MSG_KEY_BLOCK, 503, 10, 8),
            "515:11: " + getCheckMessage(MSG_KEY_BLOCK, 511, 10, 8),
            "526:11: " + getCheckMessage(MSG_KEY_SINGLE, 519, 10, 8),
            "533:1: " + getCheckMessage(MSG_KEY_SINGLE, 530, 0, 8),
            "540:1: " + getCheckMessage(MSG_KEY_SINGLE, 537, 0, 8),
            "546:1: " + getCheckMessage(MSG_KEY_SINGLE, 544, 0, 8),
            "554:5: " + getCheckMessage(MSG_KEY_SINGLE, 550, 4, 8),
            "559:13: " + getCheckMessage(MSG_KEY_SINGLE, 558, 12, 8),
            "565:1: " + getCheckMessage(MSG_KEY_SINGLE, 563, 0, 8),
            "570:1: " + getCheckMessage(MSG_KEY_SINGLE, 569, 0, 8),
            "585:1: " + getCheckMessage(MSG_KEY_SINGLE, 582, 0, 8),
            "597:13: " + getCheckMessage(MSG_KEY_SINGLE, 595, 12, 8),
        };
        final String testInputFile = "InputCommentsIndentationCommentIsAtTheEndOfBlock.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final String[] expected = {
            "27:13: " + getCheckMessage(MSG_KEY_BLOCK, 28, 12, 16),
            "33:20: " + getCheckMessage(MSG_KEY_SINGLE, "32, 34", 19, "16, 12"),
            "39:20: " + getCheckMessage(MSG_KEY_SINGLE, "38, 40", 19, "16, 12"),
            "56:7: " + getCheckMessage(MSG_KEY_SINGLE, 57, 6, 16),
            "63:9: " + getCheckMessage(MSG_KEY_SINGLE, 64, 8, 12),
            "67:23: " + getCheckMessage(MSG_KEY_SINGLE, 66, 22, 16),
            "76:15: " + getCheckMessage(MSG_KEY_SINGLE, "73, 77", 14, "12, 16"),
            "96:25: " + getCheckMessage(MSG_KEY_SINGLE, 97, 24, 20),
            "121:16: " + getCheckMessage(MSG_KEY_SINGLE, "120, 122", 15, "17, 12"),
            "133:9: " + getCheckMessage(MSG_KEY_SINGLE, 134, 8, 12),
            "146:5: " + getCheckMessage(MSG_KEY_SINGLE, 147, 4, 8),
            "165:19: " + getCheckMessage(MSG_KEY_SINGLE, "164, 166", 18, "16, 12"),
            "208:5: " + getCheckMessage(MSG_KEY_SINGLE, "207, 209", 4, "12, 12"),
            "211:23: " + getCheckMessage(MSG_KEY_SINGLE, "210, 214", 22, "16, 12"),
            "212:21: " + getCheckMessage(MSG_KEY_SINGLE, "210, 214", 20, "16, 12"),
            "213:18: " + getCheckMessage(MSG_KEY_SINGLE, "210, 214", 17, "16, 12"),
            "237:7: " + getCheckMessage(MSG_KEY_SINGLE, "236, 238", 6, "12, 12"),
            "284:12: " + getCheckMessage(MSG_KEY_BLOCK, "283, 287", 11, "16, 12"),
            "289:12: " + getCheckMessage(MSG_KEY_SINGLE, "288, 290", 11, "16, 12"),
            "319:1: " + getCheckMessage(MSG_KEY_SINGLE, "320", 0, "8"),
        };
        final String testInputFile = "InputCommentsIndentationInSwitchBlock.java";
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
    public void testSurroundingCode() throws Exception {
        final String[] expected = {
            "20:15: " + getCheckMessage(MSG_KEY_SINGLE, 21, 14, 12),
            "31:17: " + getCheckMessage(MSG_KEY_BLOCK, 32, 16, 12),
            "33:17: " + getCheckMessage(MSG_KEY_BLOCK, 35, 16, 12),
            "36:17: " + getCheckMessage(MSG_KEY_BLOCK, 39, 16, 12),
            "58:28: " + getCheckMessage(MSG_KEY_SINGLE, 61, 27, 36),
            "59:24: " + getCheckMessage(MSG_KEY_BLOCK, 61, 23, 36),
            "98:15: " + getCheckMessage(MSG_KEY_SINGLE, 99, 14, 8),
            "106:14: " + getCheckMessage(MSG_KEY_SINGLE, 108, 13, 8),
            "116:34: " + getCheckMessage(MSG_KEY_SINGLE, 117, 33, 8),
            "138:13: " + getCheckMessage(MSG_KEY_BLOCK, 139, 12, 8),
            "144:5: " + getCheckMessage(MSG_KEY_BLOCK, 145, 4, 8),
            "151:5: " + getCheckMessage(MSG_KEY_BLOCK, 149, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode.java";
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
        final String testInputFile = "InputCommentsIndentationSurroundingCode2.java";
        verifyWithInlineConfigParser(getPath(testInputFile), expected);
    }

    @Test
    public void testCheckOnlyBlockComments() throws Exception {
        final String[] expected = {
            "30:17: " + getCheckMessage(MSG_KEY_BLOCK, 31, 16, 12),
            "32:17: " + getCheckMessage(MSG_KEY_BLOCK, 34, 16, 12),
            "35:17: " + getCheckMessage(MSG_KEY_BLOCK, 38, 16, 12),
            "57:24: " + getCheckMessage(MSG_KEY_BLOCK, 59, 23, 36),
            "133:13: " + getCheckMessage(MSG_KEY_BLOCK, 134, 12, 8),
            "139:5: " + getCheckMessage(MSG_KEY_BLOCK, 140, 4, 8),
            "146:5: " + getCheckMessage(MSG_KEY_BLOCK, 144, 4, 8),
        };
        final String testInputFile = "InputCommentsIndentationSurroundingCode3.java";
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
    public void testMultiblockStructures() throws Exception {
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
