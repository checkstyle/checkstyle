////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PatternVariableNameCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/patternvariablename";
    }

    @Test
    public void testGetAcceptableTokens() {
        final PatternVariableNameCheck patternVariableNameCheck = new PatternVariableNameCheck();
        final int[] expected = {TokenTypes.PATTERN_VARIABLE_DEF};

        assertArrayEquals(expected, patternVariableNameCheck.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(PatternVariableNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "15:39: " + getCheckMessage(MSG_INVALID_PATTERN, "OTHER", pattern),
            "25:34: " + getCheckMessage(MSG_INVALID_PATTERN, "Count", pattern),
            "40:36: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
            "41:42: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "44:34: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "45:43: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "57:37: " + getCheckMessage(MSG_INVALID_PATTERN, "INTEGER", pattern),
            "63:43: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing1", pattern),
            "67:41: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing2", pattern),
        };
        verify(checkConfig,
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofTestDefault.java"),
                expected);
    }

    @Test
    public void testPatternVariableNameNoSingleChar() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(PatternVariableNameCheck.class);
        checkConfig.addProperty("format", "^[a-z][a-zA-Z0-9]+$");

        final String pattern = "^[a-z][a-zA-Z0-9]+$";

        final String[] expected = {
            "15:39: " + getCheckMessage(MSG_INVALID_PATTERN, "OTHER", pattern),
            "20:33: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "25:34: " + getCheckMessage(MSG_INVALID_PATTERN, "Count", pattern),
            "40:36: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
            "41:42: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "43:34: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "44:43: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "46:57: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "53:48: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "54:39: " + getCheckMessage(MSG_INVALID_PATTERN, "x", pattern),
            "55:43: " + getCheckMessage(MSG_INVALID_PATTERN, "y", pattern),
            "57:37: " + getCheckMessage(MSG_INVALID_PATTERN, "INTEGER", pattern),
            "62:43: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing1", pattern),
            "66:41: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing2", pattern),
            "71:38: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "72:36: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "73:37: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "80:41: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
        };

        verify(checkConfig,
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofNoSingleChar.java"),
                expected);
    }
}
