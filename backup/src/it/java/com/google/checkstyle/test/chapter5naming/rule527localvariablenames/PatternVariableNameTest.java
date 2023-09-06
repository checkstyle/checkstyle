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

public class PatternVariableNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule527localvariablenames";
    }

    @Test
    public void testPatternVariableName() throws Exception {
        final Configuration checkConfig = getModuleConfig("PatternVariableName");
        final String format = checkConfig.getProperty("format");
        final Map<String, String> messages = checkConfig.getMessages();
        final String[] expected = {
            "11:39: " + getCheckMessage(messages, MSG_KEY, "OTHER", format),
            "21:34: " + getCheckMessage(messages, MSG_KEY, "Count", format),
            "36:36: " + getCheckMessage(messages, MSG_KEY, "aA", format),
            "37:42: " + getCheckMessage(messages, MSG_KEY, "a1_a", format),
            "40:34: " + getCheckMessage(messages, MSG_KEY, "A_A", format),
            "41:43: " + getCheckMessage(messages, MSG_KEY, "aa2_a", format),
            "53:37: " + getCheckMessage(messages, MSG_KEY, "_a", format),
            "59:43: " + getCheckMessage(messages, MSG_KEY, "_aa", format),
            "63:41: " + getCheckMessage(messages, MSG_KEY, "aa_", format),
            "68:38: " + getCheckMessage(messages, MSG_KEY, "aaa$aaa", format),
            "69:36: " + getCheckMessage(messages, MSG_KEY, "$aaaaaa", format),
            "70:37: " + getCheckMessage(messages, MSG_KEY, "aaaaaa$", format),
            "77:41: " + getCheckMessage(messages, MSG_KEY, "_A_aa_B", format),

        };

        final String filePath =
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofTestDefault.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
