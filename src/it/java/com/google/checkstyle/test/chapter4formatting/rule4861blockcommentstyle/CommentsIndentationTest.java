////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;

public class CommentsIndentationTest extends BaseCheckTestSupport {

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void testCommentIsAtTheEndOfBlock() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                17, 25, 8),
            "33: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                35, 5, 4),
            "37: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                36, 0, 8),
            "47: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                46, 15, 12),
            "49: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                48, 10, 8),
            "54: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                53, 13, 8),
            "74: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                70, 18, 8),
            "88: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                85, 31, 8),
            "100: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                98, 21, 8),
            "115: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                116, 29, 12),
            "138: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                131, 26, 8),
            "164: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                161, 33, 8),
            "174: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                173, 21, 8),
            "186: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                184, 34, 8),
            "208: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                206, 26, 8),
            "214: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                213, 0, 8),
            "228: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                225, 12, 8),
            "234: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                233, 0, 8),
            "248: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                245, 12, 8),
            "255: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                252, 0, 8),
            "265: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                264, 14, 8),
            "271: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                269, 9, 8),
            "277: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                276, 9, 8),
            "316: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                315, 9, 8),
            "322: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                323, 0, 4),
            "336: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                337, 0, 4),
            "355: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                352, 9, 8),
            };

        final Configuration checkConfig = builder.getCheckConfig("CommentsIndentation");
        final String filePath =
            builder.getFilePath("InputCommentsIndentationCommentIsAtTheEndOfBlock");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCommentIsInsideSwitchBlock() throws Exception {
        final String[] expected = {
            "25: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "24, 26", 19, "16, 12"),
            "31: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "30, 32", 19, "16, 12"),
            "48: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                49, 6, 16),
            "55: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                56, 8, 12),
            "59: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                58, 22, 16),
            "68: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "65, 69", 14, "12, 16"),
            "88: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                89, 24, 20),
            "113: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "112, 114", 15, "17, 12"),
            "125: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                126, 8, 12),
            "138: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                139, 4, 8),
            "157: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "156, 158", 18, "16, 12"),
            "200: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "199, 201", 4, "12, 12"),
            "203: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                204, 22, 20),
            "204: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                205, 20, 17),
            "205: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "202, 206", 17, "16, 12"),
            "229: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                "228, 230", 6, "12, 12"),
            };

        final Configuration checkConfig = builder.getCheckConfig("CommentsIndentation");
        final String filePath =
            builder.getFilePath("InputCommentsIndentationInSwitchBlock");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testCommentIsInsideEmptyBlock() throws Exception {
        final String[] expected = {
            "9: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                10, 19, 23),
            "10: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block",
                12, 23, 31),
            "33: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                34, 0, 8),
            "57: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                58, 0, 8),
            "71: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                72, 0, 8),
            };

        final Configuration checkConfig = builder.getCheckConfig("CommentsIndentation");
        final String filePath =
            builder.getFilePath("InputCommentsIndentationInEmptyBlock");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSurroundingCode() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                14, 14, 12),
            "23: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block",
                24, 16, 12),
            "25: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block",
                27, 16, 12),
            "28: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block",
                31, 16, 12),
            "50: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                51, 27, 23),
            "51: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.block",
                53, 23, 36),
            "90: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                91, 14, 8),
            "98: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                99, 13, 8),
            "108: " + getCheckMessage(CommentsIndentationCheck.class, "comments.indentation.single",
                109, 33, 8),
            };

        final Configuration checkConfig = builder.getCheckConfig("CommentsIndentation");
        final String filePath =
            builder.getFilePath("InputCommentsIndentationSurroundingCode");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
