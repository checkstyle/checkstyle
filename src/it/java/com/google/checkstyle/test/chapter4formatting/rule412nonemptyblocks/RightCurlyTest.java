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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_SAME;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RightCurlyTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "RightCurlySame", "RightCurlyAlone",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks";
    }

    @Test
    public void testRightCurly() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 17),
            "32:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "79:27: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
            "97:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "108:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
        };

        final Configuration checkConfig = createTreeWalkerConfig(getModuleConfigsByIds(MODULES));
        final String filePath = getPath("InputRightCurlyOther.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurly2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = createTreeWalkerConfig(getModuleConfigsByIds(MODULES));
        final String filePath = getPath("InputRightCurly2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyLiteralDoDefault() throws Exception {
        final String[] expected = {
            "62:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
            "67:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "83:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
        };

        final Configuration checkConfig = createTreeWalkerConfig(getModuleConfigsByIds(MODULES));
        final String filePath = getPath("InputRightCurlyDoWhile.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyOther() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 17),
            "32:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "79:27: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
            "97:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "108:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
        };

        final Configuration checkConfig = createTreeWalkerConfig(getModuleConfigsByIds(MODULES));
        final String filePath = getPath("InputRightCurlyOther2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlyLiteralDo() throws Exception {
        final String[] expected = {
            "62:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
            "67:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "83:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
        };

        final Configuration checkConfig = createTreeWalkerConfig(getModuleConfigsByIds(MODULES));
        final String filePath = getPath("InputRightCurlyDoWhile2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
