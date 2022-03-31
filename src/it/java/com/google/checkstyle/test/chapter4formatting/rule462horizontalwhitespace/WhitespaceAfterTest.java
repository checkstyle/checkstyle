////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
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
////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAfterTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAfterBad() throws Exception {
        final Class<WhitespaceAfterCheck> clazz = WhitespaceAfterCheck.class;
        final String message = "ws.notFollowed";

        final String[] expected = {
            "7:29: " + getCheckMessage(clazz, message, ","),
            "8:9: " + getCheckMessage(clazz, message, "for"),
            "8:20: " + getCheckMessage(clazz, message, ","),
            "8:24: " + getCheckMessage(clazz, message, ";"),
            "8:28: " + getCheckMessage(clazz, message, ";"),
            "8:32: " + getCheckMessage(clazz, message, ","),
            "9:9: " + getCheckMessage(clazz, message, "while"),
            "10:20: " + getCheckMessage(clazz, message, ","),
            "12:9: " + getCheckMessage(clazz, message, "do"),
            "14:10: " + getCheckMessage(clazz, message, "while"),
            "16:35: " + getCheckMessage(clazz, message, ","),
            "17:9: " + getCheckMessage(clazz, message, "if"),
            "17:18: " + getCheckMessage(clazz, message, "typecast"),
            "20:9: " + getCheckMessage(clazz, message, "else"),
            "25:28: " + getCheckMessage(clazz, message, "..."),
            "26:26: " + getCheckMessage(clazz, message, "->"),
            "27:9: " + getCheckMessage(clazz, message, "switch"),
            "34:13: " + getCheckMessage(clazz, message, "try"),
        };
        final Configuration checkConfig = getModuleConfig("WhitespaceAfter");
        final String filePath = getPath("InputWhitespaceAfterBad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testWhitespaceAfterGood() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Configuration checkConfig = getModuleConfig("WhitespaceAfter");
        final String filePath = getPath("InputWhitespaceAfterGood.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
