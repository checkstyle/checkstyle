///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule411optionalbracesusage;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class NeedBracesTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule411optionalbracesusage";
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
            "133:9: " + getCheckMessage(clazz, messageKey, "while"),
            "136:9: " + getCheckMessage(clazz, messageKey, "do"),
            "142:9: " + getCheckMessage(clazz, messageKey, "if"),
            "145:9: " + getCheckMessage(clazz, messageKey, "if"),
            "146:9: " + getCheckMessage(clazz, messageKey, "else"),
            "151:9: " + getCheckMessage(clazz, messageKey, "for"),
            "154:9: " + getCheckMessage(clazz, messageKey, "for"),
            "164:13: " + getCheckMessage(clazz, messageKey, "while"),
            "167:13: " + getCheckMessage(clazz, messageKey, "do"),
            "173:13: " + getCheckMessage(clazz, messageKey, "if"),
            "176:13: " + getCheckMessage(clazz, messageKey, "if"),
            "177:13: " + getCheckMessage(clazz, messageKey, "else"),
            "182:13: " + getCheckMessage(clazz, messageKey, "for"),
            "185:13: " + getCheckMessage(clazz, messageKey, "for"),
            "196:13: " + getCheckMessage(clazz, messageKey, "while"),
            "199:13: " + getCheckMessage(clazz, messageKey, "do"),
            "205:13: " + getCheckMessage(clazz, messageKey, "if"),
            "208:13: " + getCheckMessage(clazz, messageKey, "if"),
            "209:13: " + getCheckMessage(clazz, messageKey, "else"),
            "214:13: " + getCheckMessage(clazz, messageKey, "for"),
            "217:13: " + getCheckMessage(clazz, messageKey, "for"),
        };

        final Configuration checkConfig = getModuleConfig("NeedBraces");
        final String filePath = getPath("InputNeedBraces.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
