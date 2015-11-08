////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;

public class LeftCurlyTest extends BaseCheckTestSupport {

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void leftCurlyBracesTest() throws Exception {

        final String[] expected = {
            "4:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "7:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "13:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "26:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "43:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "61:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "97:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
        };

        final Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        final String filePath = builder.getFilePath("InputLeftCurlyBraces");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void leftCurlyAnnotationsTest() throws Exception {

        final String[] expected = {
            "10:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "14:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "27:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "50:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
        };

        final Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        final String filePath = builder.getFilePath("InputLeftCurlyAnnotations");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void leftCurlyMethodsTest() throws Exception {

        final String[] expected = {
            "4:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "9:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "16:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "19:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "23:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "31:1: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 1),
            "33:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "38:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "41:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "45:9: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 9),
            "57:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "61:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "69:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "72:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
            "76:5: " + getCheckMessage(LeftCurlyCheck.class, MSG_KEY_LINE_PREVIOUS, "{", 5),
        };

        final Configuration checkConfig = builder.getCheckConfig("LeftCurly");
        final String filePath = builder.getFilePath("InputLeftCurlyMethod");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
