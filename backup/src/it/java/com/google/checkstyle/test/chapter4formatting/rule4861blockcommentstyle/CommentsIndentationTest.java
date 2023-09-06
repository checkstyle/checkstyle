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

package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;

public class CommentsIndentationTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4861blockcommentstyle";
    }

    @Test
    public void testCommentIsAtTheEndOfBlock() throws Exception {
        final String[] expected = {
            "18:26: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 17, 25, 8),
            "33:6: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 35, 5, 4),
            "37:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 36, 0, 8),
            "47:16: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 46, 15, 12),
            "49:11: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 45, 10, 8),
            "54:14: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 53, 13, 8),
            "74:19: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 70, 18, 8),
            "88:32: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 85, 31, 8),
            "100:22: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 98, 21, 8),
            "115:30: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 116, 29, 12),
            "138:27: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 131, 26, 8),
            "164:34: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 161, 33, 8),
            "174:22: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 173, 21, 8),
            "186:35: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 184, 34, 8),
            "208:27: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 206, 26, 8),
            "214:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 213, 0, 8),
            "228:13: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 225, 12, 8),
            "234:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 233, 0, 8),
            "248:13: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 245, 12, 8),
            "255:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 252, 0, 8),
            "265:15: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 264, 14, 8),
            "271:10: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 269, 9, 8),
            "277:10: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 276, 9, 8),
            "316:10: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 315, 9, 8),
            "322:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 323, 0, 4),
            "336:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 333, 0, 8),
            "355:10: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 352, 9, 8),
            };

        final Configuration checkConfig = getModuleConfig("CommentsIndentation");
        final String filePath =
            getPath("InputCommentsIndentationCommentIsAtTheEndOfBlock.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 20, 12, 16),
            "25:20: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "24, 26", 19, "16, 12"),
            "31:20: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "30, 32", 19, "16, 12"),
            "48:7: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 49, 6, 16),
            "55:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 56, 8, 12),
            "59:23: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 58, 22, 16),
            "68:15: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "65, 69", 14, "12, 16"),
            "88:25: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 89, 24, 20),
            "113:16: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "112, 114", 15, "17, 12"),
            "125:9: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 126, 8, 12),
            "138:5: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 139, 4, 8),
            "157:19: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "156, 158", 18, "16, 12"),
            "200:5: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "199, 201", 4, "12, 12"),
            "203:23: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "202, 206", 22, "16, 12"),
            "204:21: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "202, 206", 20, "16, 12"),
            "205:18: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "202, 206", 17, "16, 12"),
            "229:7: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", "228, 230", 6, "12, 12"),
            };

        final Configuration checkConfig = getModuleConfig("CommentsIndentation");
        final String filePath =
            getPath("InputCommentsIndentationInSwitchBlock.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
        final String[] expected = {
            "9:20: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 12, 19, 31),
            "10:24: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 12, 23, 31),
            "33:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 34, 0, 8),
            "57:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 58, 0, 8),
            "71:1: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 72, 0, 8),
            };

        final Configuration checkConfig = getModuleConfig("CommentsIndentation");
        final String filePath =
            getPath("InputCommentsIndentationInEmptyBlock.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final String[] expected = {
            "13:15: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 14, 14, 12),
            "23:17: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 24, 16, 12),
            "25:17: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 27, 16, 12),
            "28:17: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 31, 16, 12),
            "50:28: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 53, 27, 36),
            "51:24: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.block", 53, 23, 36),
            "90:15: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 91, 14, 8),
            "98:14: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 100, 13, 8),
            "108:34: " + getCheckMessage(CommentsIndentationCheck.class,
                "comments.indentation.single", 109, 33, 8),
            };

        final Configuration checkConfig = getModuleConfig("CommentsIndentation");
        final String filePath =
            getPath("InputCommentsIndentationSurroundingCode.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
