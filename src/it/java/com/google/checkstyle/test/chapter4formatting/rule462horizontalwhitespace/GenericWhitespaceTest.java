///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GenericWhitespaceTest extends AbstractGoogleModuleTestSupport {

    public static final String MSG_PRECEDED =
            "GenericWhitespace ''{0}'' is preceded with whitespace.";
    public static final String MSG_FOLLOWED =
            "GenericWhitespace ''{0}'' is followed by whitespace.";
    public static final String MSG_ILLEGAL_FOLLOW =
            "GenericWhitespace ''{0}'' should followed by whitespace.";
    public static final String MSG_NOT_PRECEDED =
            "GenericWhitespace ''{0}'' is not preceded with whitespace.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundGenerics() throws Exception {
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");

        final String[] expected = {
            "12:17: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "12:17: " + getCheckMessage(MSG_PRECEDED, "<"),
            "12:37: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "12:37: " + getCheckMessage(MSG_PRECEDED, "<"),
            "12:48: " + getCheckMessage(MSG_FOLLOWED, ">"),
            "12:48: " + getCheckMessage(MSG_PRECEDED, ">"),
            "12:50: " + getCheckMessage(MSG_PRECEDED, ">"),
            "14:33: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "14:33: " + getCheckMessage(MSG_PRECEDED, "<"),
            "14:46: " + getCheckMessage(MSG_PRECEDED, ">"),
            "15:33: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "15:33: " + getCheckMessage(MSG_PRECEDED, "<"),
            "15:46: " + getCheckMessage(MSG_PRECEDED, ">"),
            "20:39: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "20:39: " + getCheckMessage(MSG_PRECEDED, "<"),
            "20:62: " + getCheckMessage(MSG_PRECEDED, ">"),
        };

        final String filePath = getPath("InputWhitespaceAroundGenerics.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testGenericWhitespace() throws Exception {
        final Configuration checkConfig = getModuleConfig("GenericWhitespace");

        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "16:14: " + getCheckMessage(MSG_PRECEDED, "<"),
            "16:24: " + getCheckMessage(MSG_PRECEDED, ">"),
            "16:44: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "16:44: " + getCheckMessage(MSG_PRECEDED, "<"),
            "16:54: " + getCheckMessage(MSG_PRECEDED, ">"),
            "17:14: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "17:14: " + getCheckMessage(MSG_PRECEDED, "<"),
            "17:21: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "17:21: " + getCheckMessage(MSG_PRECEDED, "<"),
            "17:31: " + getCheckMessage(MSG_FOLLOWED, ">"),
            "17:31: " + getCheckMessage(MSG_PRECEDED, ">"),
            "17:33: " + getCheckMessage(MSG_PRECEDED, ">"),
            "17:53: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "17:53: " + getCheckMessage(MSG_PRECEDED, "<"),
            "17:60: " + getCheckMessage(MSG_FOLLOWED, "<"),
            "17:60: " + getCheckMessage(MSG_PRECEDED, "<"),
            "17:70: " + getCheckMessage(MSG_FOLLOWED, ">"),
            "17:70: " + getCheckMessage(MSG_PRECEDED, ">"),
            "17:72: " + getCheckMessage(MSG_PRECEDED, ">"),
            "30:18: " + getCheckMessage(MSG_NOT_PRECEDED, "<"),
            "30:20: " + getCheckMessage(MSG_ILLEGAL_FOLLOW, ">"),
            "42:22: " + getCheckMessage(MSG_PRECEDED, "<"),
            "42:29: " + getCheckMessage(MSG_FOLLOWED, ">"),
            "60:59: " + getCheckMessage(MSG_NOT_PRECEDED, "&"),
            "63:59: " + getCheckMessage(MSG_FOLLOWED, ">"),
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
