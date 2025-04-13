///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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

        assertWithMessage("Default acceptable tokens are invalid")
                .that(patternVariableNameCheck.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "18:39: " + getCheckMessage(MSG_INVALID_PATTERN, "OTHER", pattern),
            "28:34: " + getCheckMessage(MSG_INVALID_PATTERN, "Count", pattern),
            "43:36: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
            "44:42: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "47:34: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "48:43: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "60:37: " + getCheckMessage(MSG_INVALID_PATTERN, "INTEGER", pattern),
            "66:43: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing1", pattern),
            "70:41: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing2", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofTestDefault.java"),
                expected);
    }

    @Test
    public void testPatternVariableNameNoSingleChar() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]+$";

        final String[] expected = {
            "18:39: " + getCheckMessage(MSG_INVALID_PATTERN, "OTHER", pattern),
            "23:33: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "28:34: " + getCheckMessage(MSG_INVALID_PATTERN, "Count", pattern),
            "43:36: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
            "44:42: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "46:34: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "47:43: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "49:57: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "56:48: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "57:39: " + getCheckMessage(MSG_INVALID_PATTERN, "x", pattern),
            "58:43: " + getCheckMessage(MSG_INVALID_PATTERN, "y", pattern),
            "60:37: " + getCheckMessage(MSG_INVALID_PATTERN, "INTEGER", pattern),
            "65:43: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing1", pattern),
            "69:41: " + getCheckMessage(MSG_INVALID_PATTERN, "Thing2", pattern),
            "74:38: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "75:36: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "76:37: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "83:41: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputPatternVariableNameEnhancedInstanceofNoSingleChar.java"),
                expected);
    }

    @Test
    public void testPatternVariableNameUnnamed() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "17:33: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "19:33: " + getCheckMessage(MSG_INVALID_PATTERN, "_s", pattern),
            "22:33: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "29:25: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "32:25: " + getCheckMessage(MSG_INVALID_PATTERN, "_s", pattern),
            "40:67: " + getCheckMessage(MSG_INVALID_PATTERN, "_Color", pattern),
            "45:59: " + getCheckMessage(MSG_INVALID_PATTERN, "_Color", pattern),
            "51:76: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputPatternVariableNameUnnamed.java"),
                expected);
    }

    @Test
    public void testPatternVariableNameRecordPattern() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "15:36: " + getCheckMessage(MSG_INVALID_PATTERN, "XX", pattern),
            "15:44: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "20:49: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
            "25:28: " + getCheckMessage(MSG_INVALID_PATTERN, "XX", pattern),
            "25:36: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "31:41: " + getCheckMessage(MSG_INVALID_PATTERN, "S", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputPatternVariableNameRecordPattern.java"),
                expected);
    }
}
