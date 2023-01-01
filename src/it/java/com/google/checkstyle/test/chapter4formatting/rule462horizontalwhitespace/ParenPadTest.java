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

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck;

public class ParenPadTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testMethodParen() throws Exception {
        final Class<ParenPadCheck> clazz = ParenPadCheck.class;
        final String messageKeyPreceded = "ws.preceded";
        final String messageKeyFollowed = "ws.followed";

        final String[] expected = {
            "44:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "44:28: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "45:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "48:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "49:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "49:20: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "52:26: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "53:20: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:51: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "54:53: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "57:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "58:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "59:23: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:50: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "60:56: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "61:28: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "62:42: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "63:40: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "65:42: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "78:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "78:29: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "79:20: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "82:34: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "83:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "83:20: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "86:30: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "87:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "88:50: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "88:52: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "88:54: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "90:39: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "91:33: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "92:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "93:31: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:61: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:63: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "94:70: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "95:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "96:48: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "97:43: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "99:45: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "112:16: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:22: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:24: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "113:32: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "114:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "114:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "114:35: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "114:51: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "115:27: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "115:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:54: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "115:56: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "119:16: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "119:23: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "123:29: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "123:45: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "126:21: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "126:23: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "130:18: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "130:20: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "139:9: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "139:21: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "145:32: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "145:47: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "153:33: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "154:49: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "155:35: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "155:47: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "159:25: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "159:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "160:12: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "160:28: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "160:49: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "160:51: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "163:31: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "163:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "163:47: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "163:61: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "166:40: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "167:24: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "167:51: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "173:37: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "174:49: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "175:20: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "175:49: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "185:16: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "185:36: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "186:19: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "186:39: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "190:29: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "190:45: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "191:12: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "191:39: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "192:22: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "192:40: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "200:80: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "200:84: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "201:20: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "202:24: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "203:20: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "203:25: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "206:13: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "206:23: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "206:31: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "207:17: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "207:47: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "210:36: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "210:73: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "210:81: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "210:83: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "211:36: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "212:48: " + getCheckMessage(clazz, messageKeyFollowed, "("),
            "212:52: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "212:54: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "220:37: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
            "221:61: " + getCheckMessage(clazz, messageKeyPreceded, ")"),
        };
        final Configuration checkConfig = getModuleConfig("ParenPad");
        final String filePath = getPath("InputParenPad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
