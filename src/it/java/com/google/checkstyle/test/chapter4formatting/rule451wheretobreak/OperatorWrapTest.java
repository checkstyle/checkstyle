////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WrapOption;

public class OperatorWrapTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule451wheretobreak"
                + File.separator + fileName);
    }

    @Test
    public void operatorWrapTest() throws Exception {

        final Class<OperatorWrapCheck> clazz = OperatorWrapCheck.class;
        final String messageKey = "line.new";

        final String[] expected = {
            "11:27: " + getCheckMessage(clazz, messageKey, "+"),
            "12:28: " + getCheckMessage(clazz, messageKey, "-"),
            "20:27: " + getCheckMessage(clazz, messageKey, "&&"),
            "62:42: " + getCheckMessage(clazz, messageKey, "?"),
            "66:27: " + getCheckMessage(clazz, messageKey, "!="),
            "72:30: " + getCheckMessage(clazz, messageKey, "=="),
            "78:27: " + getCheckMessage(clazz, messageKey, ">"),
            "84:35: " + getCheckMessage(clazz, messageKey, "||"),
            "107:46: " + getCheckMessage(clazz, messageKey, "?"),
            "111:31: " + getCheckMessage(clazz, messageKey, "!="),
            "117:34: " + getCheckMessage(clazz, messageKey, "=="),
            "123:31: " + getCheckMessage(clazz, messageKey, ">"),
            "129:39: " + getCheckMessage(clazz, messageKey, "||"),
            "153:46: " + getCheckMessage(clazz, messageKey, "?"),
            "157:31: " + getCheckMessage(clazz, messageKey, "!="),
            "163:34: " + getCheckMessage(clazz, messageKey, "=="),
            "169:31: " + getCheckMessage(clazz, messageKey, ">"),
            "175:39: " + getCheckMessage(clazz, messageKey, "||"),
            "194:38: " + getCheckMessage(clazz, messageKey, "?"),
        };

        final Configuration checkConfig = getCheckConfig("OperatorWrap");
        final String filePath = getPath("InputOperatorWrap.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void operatorWrapTestAssign() throws Exception {
        final DefaultConfiguration newCheckConfig = createCheckConfig(OperatorWrapCheck.class);
        newCheckConfig.addAttribute("option", WrapOption.EOL.toString());
        newCheckConfig.addAttribute("tokens", "ASSIGN, DIV_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN,"
                + "STAR_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, SL_ASSIGN, BXOR_ASSIGN,"
                + "BOR_ASSIGN, BAND_ASSIGN");
        final String messageKey = "line.previous";
        final Class<OperatorWrapCheck> clazz = OperatorWrapCheck.class;

        final String[] expected = {
            "28:13: " + getCheckMessage(clazz, messageKey, "="),
            "177:9: " + getCheckMessage(clazz, messageKey, "="),
            "207:14: " + getCheckMessage(clazz, messageKey, "+="),
            "211:14: " + getCheckMessage(clazz, messageKey, "-="),
            "215:14: " + getCheckMessage(clazz, messageKey, "/="),
            "219:14: " + getCheckMessage(clazz, messageKey, "*="),
            "223:14: " + getCheckMessage(clazz, messageKey, "%="),
            "227:14: " + getCheckMessage(clazz, messageKey, "^="),
            "231:14: " + getCheckMessage(clazz, messageKey, "|="),
            "235:14: " + getCheckMessage(clazz, messageKey, "&="),
            "239:13: " + getCheckMessage(clazz, messageKey, ">>="),
            "243:13: " + getCheckMessage(clazz, messageKey, ">>>="),
            "247:13: " + getCheckMessage(clazz, messageKey, "<<="),
            "257:18: " + getCheckMessage(clazz, messageKey, "+="),
            "261:18: " + getCheckMessage(clazz, messageKey, "-="),
            "265:18: " + getCheckMessage(clazz, messageKey, "/="),
            "269:18: " + getCheckMessage(clazz, messageKey, "*="),
            "273:18: " + getCheckMessage(clazz, messageKey, "%="),
            "277:18: " + getCheckMessage(clazz, messageKey, "^="),
            "281:18: " + getCheckMessage(clazz, messageKey, "|="),
            "285:18: " + getCheckMessage(clazz, messageKey, "&="),
            "289:17: " + getCheckMessage(clazz, messageKey, ">>="),
            "293:17: " + getCheckMessage(clazz, messageKey, ">>>="),
            "297:17: " + getCheckMessage(clazz, messageKey, "<<="),
            "308:18: " + getCheckMessage(clazz, messageKey, "+="),
            "312:18: " + getCheckMessage(clazz, messageKey, "-="),
            "316:18: " + getCheckMessage(clazz, messageKey, "/="),
            "320:18: " + getCheckMessage(clazz, messageKey, "*="),
            "324:18: " + getCheckMessage(clazz, messageKey, "%="),
            "328:18: " + getCheckMessage(clazz, messageKey, "^="),
            "332:18: " + getCheckMessage(clazz, messageKey, "|="),
            "336:18: " + getCheckMessage(clazz, messageKey, "&="),
            "340:17: " + getCheckMessage(clazz, messageKey, ">>="),
            "344:17: " + getCheckMessage(clazz, messageKey, ">>>="),
            "348:17: " + getCheckMessage(clazz, messageKey, "<<="),
        };

        final String filePath = getPath("InputOperatorWrapAssign.java");
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(newCheckConfig, filePath, expected, warnList);
    }
}
