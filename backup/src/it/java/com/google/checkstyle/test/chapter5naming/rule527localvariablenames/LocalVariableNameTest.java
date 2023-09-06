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

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class LocalVariableNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule527localvariablenames";
    }

    @Test
    public void testLocalVariableName() throws Exception {
        final Configuration checkConfig = getModuleConfig("LocalVariableName");
        final String format = checkConfig.getProperty("format");
        final Map<String, String> messages = checkConfig.getMessages();
        final String[] expected = {
            "27:13: " + getCheckMessage(messages, MSG_KEY, "aA", format),
            "28:13: " + getCheckMessage(messages, MSG_KEY, "a1_a", format),
            "29:13: " + getCheckMessage(messages, MSG_KEY, "A_A", format),
            "30:13: " + getCheckMessage(messages, MSG_KEY, "aa2_a", format),
            "31:13: " + getCheckMessage(messages, MSG_KEY, "_a", format),
            "32:13: " + getCheckMessage(messages, MSG_KEY, "_aa", format),
            "33:13: " + getCheckMessage(messages, MSG_KEY, "aa_", format),
            "34:13: " + getCheckMessage(messages, MSG_KEY, "aaa$aaa", format),
            "35:13: " + getCheckMessage(messages, MSG_KEY, "$aaaaaa", format),
            "36:13: " + getCheckMessage(messages, MSG_KEY, "aaaaaa$", format),
        };

        final String filePath = getPath("InputLocalVariableNameSimple.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testOneChar() throws Exception {
        final Configuration checkConfig = getModuleConfig("LocalVariableName");
        final String format = checkConfig.getProperty("format");
        final Map<String, String> messages = checkConfig.getMessages();
        final String[] expected = {
            "21:17: " + getCheckMessage(messages, MSG_KEY, "I_ndex", format),
            "45:17: " + getCheckMessage(messages, MSG_KEY, "i_ndex", format),
            "49:17: " + getCheckMessage(messages, MSG_KEY, "ii_i1", format),
            "53:17: " + getCheckMessage(messages, MSG_KEY, "$index", format),
            "57:17: " + getCheckMessage(messages, MSG_KEY, "in$dex", format),
            "61:17: " + getCheckMessage(messages, MSG_KEY, "index$", format),
        };

        final String filePath = getPath("InputLocalVariableNameOneCharVarName.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
