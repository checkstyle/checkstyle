////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
        final String format = checkConfig.getAttribute("format");
        final Map<String, String> messages = checkConfig.getMessages();
        final String[] expected = {
            "15:39: " + getCheckMessage(messages, MSG_KEY, "OTHER", format),
            "25:34: " + getCheckMessage(messages, MSG_KEY, "Count", format),
            "40:36: " + getCheckMessage(messages, MSG_KEY, "aA", format),
            "41:42: " + getCheckMessage(messages, MSG_KEY, "a1_a", format),
            "44:34: " + getCheckMessage(messages, MSG_KEY, "A_A", format),
            "45:43: " + getCheckMessage(messages, MSG_KEY, "aa2_a", format),
            "57:37: " + getCheckMessage(messages, MSG_KEY, "_a", format),
            "63:43: " + getCheckMessage(messages, MSG_KEY, "_aa", format),
            "67:41: " + getCheckMessage(messages, MSG_KEY, "aa_", format),
            "72:38: " + getCheckMessage(messages, MSG_KEY, "aaa$aaa", format),
            "73:36: " + getCheckMessage(messages, MSG_KEY, "$aaaaaa", format),
            "74:37: " + getCheckMessage(messages, MSG_KEY, "aaaaaa$", format),
            "81:41: " + getCheckMessage(messages, MSG_KEY, "_A_aa_B", format),

        };

        final String filePath =
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofTestDefault.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
