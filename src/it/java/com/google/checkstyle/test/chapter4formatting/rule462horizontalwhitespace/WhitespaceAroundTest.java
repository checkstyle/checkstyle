///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAroundTest extends AbstractGoogleModuleTestSupport {

    public static final String MSG_NOT_FOLLOWED =
            "WhitespaceAround: ''{0}'' is not followed by whitespace. Empty blocks"
                + "                may only be represented as '{}' when not part of a "
                + "multi-block statement (4.1.3)";
    public static final String MSG_NOT_PRECEDED =
            "WhitespaceAround: ''{0}'' is not preceded with whitespace.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundBasic() throws Exception {
        final Configuration checkConfig = getModuleConfig("WhitespaceAround");

        final String[] expected = {
            "10:22: " + getCheckMessage(MSG_NOT_PRECEDED, "="),
            "12:23: " + getCheckMessage(MSG_NOT_FOLLOWED, "="),
            "20:14: " + getCheckMessage(MSG_NOT_PRECEDED, "="),
            "21:10: " + getCheckMessage(MSG_NOT_PRECEDED, "="),
            "22:11: " + getCheckMessage(MSG_NOT_FOLLOWED, "+="),
            "23:11: " + getCheckMessage(MSG_NOT_FOLLOWED, "-="),
            "31:9: " + getCheckMessage(MSG_NOT_FOLLOWED, "synchronized"),
            "33:13: " + getCheckMessage(MSG_NOT_FOLLOWED, "{"),
            "35:36: " + getCheckMessage(MSG_NOT_FOLLOWED, "{"),
            "52:9: " + getCheckMessage(MSG_NOT_FOLLOWED, "if"),
            "70:13: " + getCheckMessage(MSG_NOT_FOLLOWED, "return"),
            "92:24: " + getCheckMessage(MSG_NOT_FOLLOWED, "=="),
            "98:22: " + getCheckMessage(MSG_NOT_PRECEDED, "*"),
            "113:18: " + getCheckMessage(MSG_NOT_PRECEDED, "%"),
            "114:19: " + getCheckMessage(MSG_NOT_FOLLOWED, "%"),
            "115:18: " + getCheckMessage(MSG_NOT_PRECEDED, "%"),
            "117:18: " + getCheckMessage(MSG_NOT_PRECEDED, "/"),
            "118:19: " + getCheckMessage(MSG_NOT_FOLLOWED, "/"),
            "147:9: " + getCheckMessage(MSG_NOT_FOLLOWED, "assert"),
            "150:20: " + getCheckMessage(MSG_NOT_PRECEDED, ":"),
            "241:19: " + getCheckMessage(MSG_NOT_FOLLOWED, ":"),
            "241:19: " + getCheckMessage(MSG_NOT_PRECEDED, ":"),
            "242:20: " + getCheckMessage(MSG_NOT_FOLLOWED, ":"),
            "243:19: " + getCheckMessage(MSG_NOT_PRECEDED, ":"),
            "257:14: " + getCheckMessage(MSG_NOT_PRECEDED, "->"),
            "258:15: " + getCheckMessage(MSG_NOT_FOLLOWED, "->"),
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
