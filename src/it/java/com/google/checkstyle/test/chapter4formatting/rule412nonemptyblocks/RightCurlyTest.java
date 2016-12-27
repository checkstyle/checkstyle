////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_SAME;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RightCurlyTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule412nonemptyblocks"
                + File.separator + fileName);
    }

    @Test
    public void rightCurlyTestAlone() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 17),
            "32:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "79:27: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
        };

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlySame");
        final String filePath = getPath("InputRightCurlyOther.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void rightCurlyTestSame() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlySame");
        final String filePath = getPath("InputRightCurlySame.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlySameAndLiteralDoDefault() throws Exception {
        final String[] expected = {
            "62:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
            "67:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "83:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
        };

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlySame");
        final String filePath = getPath("InputRightCurlyDoWhile.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyAloneOther() throws Exception {
        final String[] expected = {
            "97:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "97:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "108:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "108:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "122:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
        };

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlyAlone");
        final String filePath = getPath("InputRightCurlyOtherAlone.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void rightCurlyAloneTestSame() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlyAlone");
        final String filePath = getPath("InputRightCurlySame.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyAloneSameAndLiteralDo() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlyAlone");
        final String filePath = getPath("InputRightCurlyDoWhileAlone.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyAloneInLambda() throws Exception {
        final String[] expected = {
            "12:70: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 70),
            "15:46: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 46),
            "22:33: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 33),
        };

        final Configuration checkConfig = getCheckConfig("RightCurly", "RightCurlyAlone");
        final String filePath = getPath("InputRightCurlyAloneLambda.java");

        final Integer[] warnList = {12, 15, 22};
        verify(checkConfig, filePath, expected, warnList);
    }
}
