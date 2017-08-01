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

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

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

        final String[] expected = {
            "12:16: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "12:18: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "12:36: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "12:38: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "12:47: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "12:49: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, ">"),
            "12:49: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "14:32: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "14:34: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "14:45: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "15:32: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "15:34: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "15:45: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "20:38: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "20:40: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "20:61: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
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

        final String[] expected = {
            "16:13: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "16:15: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "16:23: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "16:43: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "16:45: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "16:53: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "17:13: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "17:15: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "17:20: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "17:22: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "17:30: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "17:32: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, ">"),
            "17:32: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "17:52: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "17:54: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "17:59: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "17:61: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, "<"),
            "17:69: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "17:71: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, ">"),
            "17:71: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, ">"),
            "30:17: " + getCheckMessage(checkConfig.getMessages(), msgNotPreceded, "<"),
            "30:21: " + getCheckMessage(checkConfig.getMessages(), msgIllegalFollow, ">"),
            "42:21: " + getCheckMessage(checkConfig.getMessages(), msgPreceded, "<"),
            "42:30: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, ">"),
            "60:60: " + getCheckMessage(checkConfig.getMessages(), msgNotPreceded, "&"),
            "63:60: " + getCheckMessage(checkConfig.getMessages(), msgFollowed, ">"),
        };

        final String filePath = getPath("InputGenericWhitespace.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void genericEndsTheLine() throws Exception {
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputGenericWhitespaceEndsTheLine.java"),
                expected);
    }
}
