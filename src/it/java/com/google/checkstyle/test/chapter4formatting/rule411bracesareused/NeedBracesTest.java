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

package com.google.checkstyle.test.chapter4formatting.rule411bracesareused;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class NeedBracesTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule411bracesareused";
    }

    @Test
    public void testNeedBraces() throws Exception {
        final Class<NeedBracesCheck> clazz = NeedBracesCheck.class;
        final String messageKey = "needBraces";

        final String[] expected = {
            "29:9: " + getCheckMessage(clazz, messageKey, "do"),
            "41:9: " + getCheckMessage(clazz, messageKey, "while"),
            "42:9: " + getCheckMessage(clazz, messageKey, "while"),
            "44:9: " + getCheckMessage(clazz, messageKey, "while"),
            "45:13: " + getCheckMessage(clazz, messageKey, "if"),
            "58:9: " + getCheckMessage(clazz, messageKey, "for"),
            "59:9: " + getCheckMessage(clazz, messageKey, "for"),
            "61:9: " + getCheckMessage(clazz, messageKey, "for"),
            "63:13: " + getCheckMessage(clazz, messageKey, "if"),
            "82:9: " + getCheckMessage(clazz, messageKey, "if"),
            "83:9: " + getCheckMessage(clazz, messageKey, "if"),
            "85:9: " + getCheckMessage(clazz, messageKey, "if"),
            "87:9: " + getCheckMessage(clazz, messageKey, "else"),
            "89:9: " + getCheckMessage(clazz, messageKey, "if"),
            "97:9: " + getCheckMessage(clazz, messageKey, "else"),
            "99:9: " + getCheckMessage(clazz, messageKey, "if"),
            "100:13: " + getCheckMessage(clazz, messageKey, "if"),
            "126:9: " + getCheckMessage(clazz, messageKey, "while"),
            "129:9: " + getCheckMessage(clazz, messageKey, "do"),
            "135:9: " + getCheckMessage(clazz, messageKey, "if"),
            "138:9: " + getCheckMessage(clazz, messageKey, "if"),
            "139:9: " + getCheckMessage(clazz, messageKey, "else"),
            "144:9: " + getCheckMessage(clazz, messageKey, "for"),
            "147:9: " + getCheckMessage(clazz, messageKey, "for"),
            "157:13: " + getCheckMessage(clazz, messageKey, "while"),
            "160:13: " + getCheckMessage(clazz, messageKey, "do"),
            "166:13: " + getCheckMessage(clazz, messageKey, "if"),
            "169:13: " + getCheckMessage(clazz, messageKey, "if"),
            "170:13: " + getCheckMessage(clazz, messageKey, "else"),
            "175:13: " + getCheckMessage(clazz, messageKey, "for"),
            "178:13: " + getCheckMessage(clazz, messageKey, "for"),
            "189:13: " + getCheckMessage(clazz, messageKey, "while"),
            "192:13: " + getCheckMessage(clazz, messageKey, "do"),
            "198:13: " + getCheckMessage(clazz, messageKey, "if"),
            "201:13: " + getCheckMessage(clazz, messageKey, "if"),
            "202:13: " + getCheckMessage(clazz, messageKey, "else"),
            "207:13: " + getCheckMessage(clazz, messageKey, "for"),
            "210:13: " + getCheckMessage(clazz, messageKey, "for"),
        };

        final Configuration checkConfig = getModuleConfig("NeedBraces");
        final String filePath = getPath("InputNeedBraces.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
