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

package com.google.checkstyle.test.chapter4formatting.rule411bracesareused;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck;

public class NeedBracesTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule411bracesareused"
                + File.separator + fileName);
    }

    @Test
    public void needBracesTest() throws Exception {

        final Class<NeedBracesCheck> clazz = NeedBracesCheck.class;
        final String messageKey = "needBraces";

        final String[] expected = {
            "29: " + getCheckMessage(clazz, messageKey, "do"),
            "41: " + getCheckMessage(clazz, messageKey, "while"),
            "42: " + getCheckMessage(clazz, messageKey, "while"),
            "44: " + getCheckMessage(clazz, messageKey, "while"),
            "45: " + getCheckMessage(clazz, messageKey, "if"),
            "58: " + getCheckMessage(clazz, messageKey, "for"),
            "59: " + getCheckMessage(clazz, messageKey, "for"),
            "61: " + getCheckMessage(clazz, messageKey, "for"),
            "63: " + getCheckMessage(clazz, messageKey, "if"),
            "82: " + getCheckMessage(clazz, messageKey, "if"),
            "83: " + getCheckMessage(clazz, messageKey, "if"),
            "85: " + getCheckMessage(clazz, messageKey, "if"),
            "87: " + getCheckMessage(clazz, messageKey, "else"),
            "89: " + getCheckMessage(clazz, messageKey, "if"),
            "97: " + getCheckMessage(clazz, messageKey, "else"),
            "99: " + getCheckMessage(clazz, messageKey, "if"),
            "100: " + getCheckMessage(clazz, messageKey, "if"),
            "126: " + getCheckMessage(clazz, messageKey, "while"),
            "129: " + getCheckMessage(clazz, messageKey, "do"),
            "135: " + getCheckMessage(clazz, messageKey, "if"),
            "138: " + getCheckMessage(clazz, messageKey, "if"),
            "139: " + getCheckMessage(clazz, messageKey, "else"),
            "144: " + getCheckMessage(clazz, messageKey, "for"),
            "147: " + getCheckMessage(clazz, messageKey, "for"),
            "157: " + getCheckMessage(clazz, messageKey, "while"),
            "160: " + getCheckMessage(clazz, messageKey, "do"),
            "166: " + getCheckMessage(clazz, messageKey, "if"),
            "169: " + getCheckMessage(clazz, messageKey, "if"),
            "170: " + getCheckMessage(clazz, messageKey, "else"),
            "175: " + getCheckMessage(clazz, messageKey, "for"),
            "178: " + getCheckMessage(clazz, messageKey, "for"),
            "189: " + getCheckMessage(clazz, messageKey, "while"),
            "192: " + getCheckMessage(clazz, messageKey, "do"),
            "198: " + getCheckMessage(clazz, messageKey, "if"),
            "201: " + getCheckMessage(clazz, messageKey, "if"),
            "202: " + getCheckMessage(clazz, messageKey, "else"),
            "207: " + getCheckMessage(clazz, messageKey, "for"),
            "210: " + getCheckMessage(clazz, messageKey, "for"),
        };

        final Configuration checkConfig = getCheckConfig("NeedBraces");
        final String filePath = getPath("InputNeedBraces.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
