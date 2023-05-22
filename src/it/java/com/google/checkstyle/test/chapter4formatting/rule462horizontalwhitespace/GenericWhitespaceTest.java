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

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GenericWhitespaceTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundGenerics() throws Exception {
        final String msgPreceded = "ws.preceded";
        final String msgFollowed = "ws.followed";
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");
        final Map<String, String> messages = checkConfig.getMessages();

        final String[] expected = {
            "12:17: " + getCheckMessage(messages, msgFollowed, "<"),
            "12:17: " + getCheckMessage(messages, msgPreceded, "<"),
            "12:37: " + getCheckMessage(messages, msgFollowed, "<"),
            "12:37: " + getCheckMessage(messages, msgPreceded, "<"),
            "12:48: " + getCheckMessage(messages, msgFollowed, ">"),
            "12:48: " + getCheckMessage(messages, msgPreceded, ">"),
            "12:50: " + getCheckMessage(messages, msgPreceded, ">"),
            "14:33: " + getCheckMessage(messages, msgFollowed, "<"),
            "14:33: " + getCheckMessage(messages, msgPreceded, "<"),
            "14:46: " + getCheckMessage(messages, msgPreceded, ">"),
            "15:33: " + getCheckMessage(messages, msgFollowed, "<"),
            "15:33: " + getCheckMessage(messages, msgPreceded, "<"),
            "15:46: " + getCheckMessage(messages, msgPreceded, ">"),
            "20:39: " + getCheckMessage(messages, msgFollowed, "<"),
            "20:39: " + getCheckMessage(messages, msgPreceded, "<"),
            "20:62: " + getCheckMessage(messages, msgPreceded, ">"),
        };

        final String filePath = getPath("InputWhitespaceAroundGenerics.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testGenericWhitespace() throws Exception {
        final String msgPreceded = "ws.preceded";
        final String msgFollowed = "ws.followed";
        final String msgNotPreceded = "ws.notPreceded";
        final String msgIllegalFollow = "ws.illegalFollow";
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");
        final Map<String, String> messages = checkConfig.getMessages();

        final String[] expected = {
            "16:14: " + getCheckMessage(messages, msgFollowed, "<"),
            "16:14: " + getCheckMessage(messages, msgPreceded, "<"),
            "16:24: " + getCheckMessage(messages, msgPreceded, ">"),
            "16:44: " + getCheckMessage(messages, msgFollowed, "<"),
            "16:44: " + getCheckMessage(messages, msgPreceded, "<"),
            "16:54: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:14: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:14: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:21: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:21: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:31: " + getCheckMessage(messages, msgFollowed, ">"),
            "17:31: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:33: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:53: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:53: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:60: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:60: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:70: " + getCheckMessage(messages, msgFollowed, ">"),
            "17:70: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:72: " + getCheckMessage(messages, msgPreceded, ">"),
            "30:18: " + getCheckMessage(messages, msgNotPreceded, "<"),
            "30:20: " + getCheckMessage(messages, msgIllegalFollow, ">"),
            "42:22: " + getCheckMessage(messages, msgPreceded, "<"),
            "42:29: " + getCheckMessage(messages, msgFollowed, ">"),
            "60:59: " + getCheckMessage(messages, msgNotPreceded, "&"),
            "63:59: " + getCheckMessage(messages, msgFollowed, ">"),
        };

        final String filePath = getPath("InputGenericWhitespace.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void genericEndsTheLine() throws Exception {
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputGenericWhitespaceEndsTheLine.java"),
                expected);
    }

}
