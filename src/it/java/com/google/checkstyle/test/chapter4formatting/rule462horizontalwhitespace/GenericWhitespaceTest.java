////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.util.Map;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GenericWhitespaceTest extends AbstractModuleTestSupport {

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
            "12:16: " + getCheckMessage(messages, msgPreceded, "<"),
            "12:18: " + getCheckMessage(messages, msgFollowed, "<"),
            "12:36: " + getCheckMessage(messages, msgPreceded, "<"),
            "12:38: " + getCheckMessage(messages, msgFollowed, "<"),
            "12:47: " + getCheckMessage(messages, msgPreceded, ">"),
            "12:49: " + getCheckMessage(messages, msgFollowed, ">"),
            "12:49: " + getCheckMessage(messages, msgPreceded, ">"),
            "14:32: " + getCheckMessage(messages, msgPreceded, "<"),
            "14:34: " + getCheckMessage(messages, msgFollowed, "<"),
            "14:45: " + getCheckMessage(messages, msgPreceded, ">"),
            "15:32: " + getCheckMessage(messages, msgPreceded, "<"),
            "15:34: " + getCheckMessage(messages, msgFollowed, "<"),
            "15:45: " + getCheckMessage(messages, msgPreceded, ">"),
            "20:38: " + getCheckMessage(messages, msgPreceded, "<"),
            "20:40: " + getCheckMessage(messages, msgFollowed, "<"),
            "20:61: " + getCheckMessage(messages, msgPreceded, ">"),
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
            "16:13: " + getCheckMessage(messages, msgPreceded, "<"),
            "16:15: " + getCheckMessage(messages, msgFollowed, "<"),
            "16:23: " + getCheckMessage(messages, msgPreceded, ">"),
            "16:43: " + getCheckMessage(messages, msgPreceded, "<"),
            "16:45: " + getCheckMessage(messages, msgFollowed, "<"),
            "16:53: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:13: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:15: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:20: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:22: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:30: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:32: " + getCheckMessage(messages, msgFollowed, ">"),
            "17:32: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:52: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:54: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:59: " + getCheckMessage(messages, msgPreceded, "<"),
            "17:61: " + getCheckMessage(messages, msgFollowed, "<"),
            "17:69: " + getCheckMessage(messages, msgPreceded, ">"),
            "17:71: " + getCheckMessage(messages, msgFollowed, ">"),
            "17:71: " + getCheckMessage(messages, msgPreceded, ">"),
            "30:17: " + getCheckMessage(messages, msgNotPreceded, "<"),
            "30:21: " + getCheckMessage(messages, msgIllegalFollow, ">"),
            "42:21: " + getCheckMessage(messages, msgPreceded, "<"),
            "42:30: " + getCheckMessage(messages, msgFollowed, ">"),
            "60:60: " + getCheckMessage(messages, msgNotPreceded, "&"),
            "63:60: " + getCheckMessage(messages, msgFollowed, ">"),
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
