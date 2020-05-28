////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
            "12:22: " + getCheckMessage(messages, msgPreceded, "="),
            "14:23: " + getCheckMessage(messages, msgFollowed, "="),
            "22:14: " + getCheckMessage(messages, msgPreceded, "="),
            "23:10: " + getCheckMessage(messages, msgPreceded, "="),
            "24:11: " + getCheckMessage(messages, msgFollowed, "+="),
            "25:11: " + getCheckMessage(messages, msgFollowed, "-="),
            "33:9: " + getCheckMessage(messages, msgFollowed, "synchronized"),
            "35:13: " + getCheckMessage(messages, msgFollowed, "{"),
            "37:36: " + getCheckMessage(messages, msgFollowed, "{"),
            "54:9: " + getCheckMessage(messages, msgFollowed, "if"),
            "72:13: " + getCheckMessage(messages, msgFollowed, "return"),
            "94:24: " + getCheckMessage(messages, msgFollowed, "=="),
            "100:22: " + getCheckMessage(messages, msgPreceded, "*"),
            "115:18: " + getCheckMessage(messages, msgPreceded, "%"),
            "116:19: " + getCheckMessage(messages, msgFollowed, "%"),
            "117:18: " + getCheckMessage(messages, msgPreceded, "%"),
            "119:18: " + getCheckMessage(messages, msgPreceded, "/"),
            "120:19: " + getCheckMessage(messages, msgFollowed, "/"),
            "149:9: " + getCheckMessage(messages, msgFollowed, "assert"),
            "152:20: " + getCheckMessage(messages, msgPreceded, ":"),
            "243:19: " + getCheckMessage(messages, msgFollowed, ":"),
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
