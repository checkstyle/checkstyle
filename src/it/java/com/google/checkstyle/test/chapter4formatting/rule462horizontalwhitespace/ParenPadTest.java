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
        };
        final Configuration checkConfig = getCheckConfig("ParenPad");
        final String filePath = getPath("InputParenPad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
