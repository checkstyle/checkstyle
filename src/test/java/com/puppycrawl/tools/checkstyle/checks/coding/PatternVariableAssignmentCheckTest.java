///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.PatternVariableAssignmentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PatternVariableAssignmentCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/patternvariableassignment";
    }

    @Test
    public void testGetAcceptableTokens() {
        final PatternVariableAssignmentCheck patternVariableAssignmentCheckObj =
            new PatternVariableAssignmentCheck();
        final int[] actual = patternVariableAssignmentCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.LITERAL_INSTANCEOF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testPatternVariableAssignmentCheck() throws Exception {

        final String[] expected = {
            "27:13: " + getCheckMessage(MSG_KEY, "s"),
            "31:13: " + getCheckMessage(MSG_KEY, "x"),
            "33:13: " + getCheckMessage(MSG_KEY, "y"),
            "37:13: " + getCheckMessage(MSG_KEY, "c"),
            "41:13: " + getCheckMessage(MSG_KEY, "c"),
            "45:13: " + getCheckMessage(MSG_KEY, "c"),
            "50:21: " + getCheckMessage(MSG_KEY, "d"),
            "67:14: " + getCheckMessage(MSG_KEY, "f"),
        };

        verifyWithInlineXmlConfig(getPath(
            "InputPatternVariableAssignmentCheck1.java"), expected);
    }

    @Test
    public void testPatternVariableAssignmentCheck2() throws Exception {

        final String[] expected = {
            "56:17: " + getCheckMessage(MSG_KEY, "parent"),
            "66:9: " + getCheckMessage(MSG_KEY, "r"),
            "81:22: " + getCheckMessage(MSG_KEY, "string"),
        };

        verifyWithInlineXmlConfig(getPath(
            "InputPatternVariableAssignmentCheck2.java"), expected);
    }

}
