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
import com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck;

public class ParenPadTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule462horizontalwhitespace"
                + File.separator + fileName);
    }

    @Test
    public void methodParenTest() throws Exception {

        final Class<ParenPadCheck> clazz = ParenPadCheck.class;
        final String messageKeyPreceded = "ws.preceded";
        final String messageKeyFollowed = "ws.followed";

        final String[] expected = {
            "44:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "44:27: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "45:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "48:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "49:19: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "49:19: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "52:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "53:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:52: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:52: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "57:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "58:22: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "59:24: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:51: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:57: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "61:29: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "62:43: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "63:41: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "65:43: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "78:28: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "78:28: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "79:19: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "82:33: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "83:19: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "83:19: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "86:29: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "87:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "88:51: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "88:51: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "88:53: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "90:38: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "91:32: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "92:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "93:30: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:60: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:62: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:69: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "95:34: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "96:47: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "97:42: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "99:44: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "112:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:23: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:31: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "114:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "114:28: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "114:34: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "114:50: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "115:28: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "115:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:53: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:55: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "119:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "119:22: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "123:30: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "123:44: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "126:22: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "126:22: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "130:19: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "130:19: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "139:10: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "139:20: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "145:33: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "145:46: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "153:34: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "154:48: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "155:36: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "155:46: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "159:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "159:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "160:13: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "160:29: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "160:48: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "160:50: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "163:32: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "163:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "163:48: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "163:60: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "166:39: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "167:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "167:50: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "173:38: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "174:48: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "175:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "175:48: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "185:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "185:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "186:20: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "186:38: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "190:30: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "190:44: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "191:13: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "191:38: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "192:23: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "192:39: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "200:81: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "200:83: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "201:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "202:23: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "203:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "203:24: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "206:14: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "206:22: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "206:32: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "207:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "207:46: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "210:37: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "210:74: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "210:80: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "210:82: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "211:37: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "212:49: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "212:51: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "212:53: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
        };
        final Configuration checkConfig = getCheckConfig("ParenPad");
        final String filePath = getPath("InputParenPad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
