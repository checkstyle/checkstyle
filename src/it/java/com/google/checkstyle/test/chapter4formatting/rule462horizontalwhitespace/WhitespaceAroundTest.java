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

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAroundTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundBasic() throws Exception {
        final Configuration checkConfig = getModuleConfig("WhitespaceAround");
        final String msgPreceded = "ws.notPreceded";
        final String msgFollowed = "ws.notFollowed";
        final Map<String, String> messages = checkConfig.getMessages();

        final String[] expected = {
            "10:22: " + getCheckMessage(messages, msgPreceded, "="),
            "12:23: " + getCheckMessage(messages, msgFollowed, "="),
            "20:14: " + getCheckMessage(messages, msgPreceded, "="),
            "21:10: " + getCheckMessage(messages, msgPreceded, "="),
            "22:11: " + getCheckMessage(messages, msgFollowed, "+="),
            "23:11: " + getCheckMessage(messages, msgFollowed, "-="),
            "31:9: " + getCheckMessage(messages, msgFollowed, "synchronized"),
            "33:13: " + getCheckMessage(messages, msgFollowed, "{"),
            "35:36: " + getCheckMessage(messages, msgFollowed, "{"),
            "52:9: " + getCheckMessage(messages, msgFollowed, "if"),
            "70:13: " + getCheckMessage(messages, msgFollowed, "return"),
            "92:24: " + getCheckMessage(messages, msgFollowed, "=="),
            "98:22: " + getCheckMessage(messages, msgPreceded, "*"),
            "113:18: " + getCheckMessage(messages, msgPreceded, "%"),
            "114:19: " + getCheckMessage(messages, msgFollowed, "%"),
            "115:18: " + getCheckMessage(messages, msgPreceded, "%"),
            "117:18: " + getCheckMessage(messages, msgPreceded, "/"),
            "118:19: " + getCheckMessage(messages, msgFollowed, "/"),
            "147:9: " + getCheckMessage(messages, msgFollowed, "assert"),
            "150:20: " + getCheckMessage(messages, msgPreceded, ":"),
            "241:19: " + getCheckMessage(messages, msgFollowed, ":"),
            "241:19: " + getCheckMessage(messages, msgPreceded, ":"),
            "242:20: " + getCheckMessage(messages, msgFollowed, ":"),
            "243:19: " + getCheckMessage(messages, msgPreceded, ":"),
            "257:14: " + getCheckMessage(messages, msgPreceded, "->"),
            "258:15: " + getCheckMessage(messages, msgFollowed, "->"),
        };

        final String filePath = getPath("InputWhitespaceAroundBasic.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testWhitespaceAroundEmptyTypesCycles() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = getModuleConfig("WhitespaceAround");
        final String filePath = getPath("InputWhitespaceAroundEmptyTypesAndCycles.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
