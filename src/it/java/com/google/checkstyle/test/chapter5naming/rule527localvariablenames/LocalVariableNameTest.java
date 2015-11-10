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

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class LocalVariableNameTest extends BaseCheckTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";
    private static ConfigurationBuilder builder;
    private static Configuration checkConfig;
    private static String format;

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        builder = new ConfigurationBuilder(new File("src/it/"));
        checkConfig = builder.getCheckConfig("LocalVariableName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void localVariableNameTest() throws Exception {

        final String[] expected = {
            "26:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "a", format),
            "27:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aA", format),
            "28:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "a1_a", format),
            "29:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "A_A", format),
            "30:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aa2_a", format),
            "31:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_a", format),
            "32:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "_aa", format),
            "33:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aa_", format),
            "34:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aaa$aaa", format),
            "35:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$aaaaaa", format),
            "36:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "aaaaaa$", format),
        };

        final String filePath = builder.getFilePath("InputLocalVariableNameSimple");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void oneCharTest() throws Exception {

        final String[] expected = {
            "15:13: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "i", format),
            "21:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "I_ndex", format),
            "45:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "i_ndex", format),
            "49:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "ii_i1", format),
            "53:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "$index", format),
            "57:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "in$dex", format),
            "61:17: " + getCheckMessage(checkConfig.getMessages(), MSG_KEY, "index$", format),
        };

        final String filePath = builder.getFilePath("InputLocalVariableNameOneCharVarName");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
