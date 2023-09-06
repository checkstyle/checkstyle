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

package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class IllegalTokenTextTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter2filebasic/rule232specialescape";
    }

    @Test
    public void testIllegalTokens() throws Exception {
        final String message = "Consider using special escape sequence instead of octal value or "
                + "Unicode escaped value.";

        final String[] expected = {
            "18:27: " + message,
            "19:27: " + message,
            "22:25: " + message,
            "23:25: " + message,
            "25:16: " + message,
            "43:21: " + message,
            "44:21: " + message,
            "45:21: " + message,
            "46:21: " + message,
            "47:21: " + message,
            "48:21: " + message,
            "49:21: " + message,
            "54:21: " + message,
            "55:21: " + message,
            "56:21: " + message,
            "57:21: " + message,
            "58:21: " + message,
            "59:21: " + message,
            "60:21: " + message,
            "61:21: " + message,
            "69:31: " + message,
            "70:31: " + message,
            "73:29: " + message,
            "74:29: " + message,
            "76:20: " + message,
            "94:25: " + message,
            "95:25: " + message,
            "96:25: " + message,
            "97:25: " + message,
            "98:25: " + message,
            "99:25: " + message,
            "100:25: " + message,
            "105:25: " + message,
            "106:25: " + message,
            "107:25: " + message,
            "108:25: " + message,
            "109:25: " + message,
            "110:25: " + message,
            "111:25: " + message,
            "112:25: " + message,
            "119:35: " + message,
            "120:35: " + message,
            "123:33: " + message,
            "124:33: " + message,
            "126:24: " + message,
            "144:29: " + message,
            "145:29: " + message,
            "146:29: " + message,
            "147:29: " + message,
            "148:29: " + message,
            "149:29: " + message,
            "150:29: " + message,
            "155:29: " + message,
            "156:29: " + message,
            "157:29: " + message,
            "158:29: " + message,
            "159:29: " + message,
            "160:29: " + message,
            "161:29: " + message,
            "162:29: " + message,
        };

        final Configuration checkConfig = getModuleConfig("IllegalTokenText");
        final String filePath = getPath("InputIllegalTokenText.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
