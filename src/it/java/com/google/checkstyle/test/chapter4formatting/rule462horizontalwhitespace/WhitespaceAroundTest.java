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

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class WhitespaceAroundTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule462horizontalwhitespace"
                + File.separator + fileName);
    }

    @Test
    public void whitespaceAroundBasicTest() throws Exception {

        final Configuration checkConfig = getCheckConfig("WhitespaceAround");
        final String msgPreceded = "ws.notPreceded";
        final String msgFollowed = "ws.notFollowed";

        final String[] expected = {
            "10:22: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "="),
            "12:24: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "="),
            "20:14: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "="),
            "21:10: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "="),
            "22:13: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "+="),
            "23:13: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "-="),
            "31:21: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "synchronized"),
            "33:14: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "{"),
            "35:37: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "{"),
            "52:11: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "if"),
            "70:19: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "return"),
            "92:26: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "=="),
            "98:22: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "*"),
            "113:18: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "%"),
            "114:20: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "%"),
            "115:18: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "%"),
            "117:18: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "/"),
            "118:20: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "/"),
            "147:15: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "assert"),
            "150:20: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ":"),
            "249:14: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "->"),
            "250:17: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "->"),
            "250:17: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "{"),
        };

        final String filePath = getPath("InputWhitespaceAroundBasic.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void whitespaceAroundEmptyTypesCyclesTest() throws Exception {

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getCheckConfig("WhitespaceAround");
        final String filePath = getPath("InputWhitespaceAroundEmptyTypesAndCycles.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
