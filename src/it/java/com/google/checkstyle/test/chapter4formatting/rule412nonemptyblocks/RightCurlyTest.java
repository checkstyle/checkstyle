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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_SAME;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RightCurlyTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule412nonemptyblocks"
                + File.separator + fileName);
    }

    @Test
    public void rightCurlyTestAlone() throws Exception {
        final DefaultConfiguration newCheckConfig = createCheckConfig(RightCurlyCheck.class);
        newCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        newCheckConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF");

        final String[] expected = {
            "97:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "97:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "108:5: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_ALONE, "}", 5),
            "108:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
            "122:6: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_NEW, "}", 6),
        };

        final String filePath = getPath("InputRightCurlyOther.java");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }

    @Test
    public void rightCurlyTestSame() throws Exception {
        final DefaultConfiguration newCheckConfig = createCheckConfig(RightCurlyCheck.class);
        newCheckConfig.addAttribute("option", RightCurlyOption.SAME.toString());

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final String filePath = getPath("InputRightCurlySame.java");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }

    @Test
    public void testRightCurlySameAndLiteralDo() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RightCurlyCheck.class);
        checkConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        checkConfig.addAttribute("tokens", "LITERAL_DO");
        final String[] expected = {
            "62:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
            "67:13: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 13),
            "83:9: " + getCheckMessage(RightCurlyCheck.class, MSG_KEY_LINE_SAME, "}", 9),
        };
        final String filePath = getPath("InputRightCurlyDoWhile.java");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
