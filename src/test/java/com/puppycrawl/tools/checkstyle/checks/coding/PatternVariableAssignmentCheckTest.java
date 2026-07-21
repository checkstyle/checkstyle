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
            "55:17: " + getCheckMessage(MSG_KEY, "parent"),
            "65:9: " + getCheckMessage(MSG_KEY, "r"),
            "74:11: " + getCheckMessage(MSG_KEY, "r"),
            "90:22: " + getCheckMessage(MSG_KEY, "string"),
        };

        verifyWithInlineXmlConfig(getPath(
            "InputPatternVariableAssignmentCheck2.java"), expected);
    }

    @Test
    public void testExtendedScope1() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_KEY, "s"),
            "23:13: " + getCheckMessage(MSG_KEY, "s"),
            "24:13: " + getCheckMessage(MSG_KEY, "s"),
            "27:13: " + getCheckMessage(MSG_KEY, "s"),
            "32:17: " + getCheckMessage(MSG_KEY, "i"),
            "36:13: " + getCheckMessage(MSG_KEY, "i"),
            "43:17: " + getCheckMessage(MSG_KEY, "s"),
            "46:13: " + getCheckMessage(MSG_KEY, "s"),
            "51:21: " + getCheckMessage(MSG_KEY, "i"),
            "54:21: " + getCheckMessage(MSG_KEY, "i"),
            "62:17: " + getCheckMessage(MSG_KEY, "s"),
            "64:17: " + getCheckMessage(MSG_KEY, "s"),
            "68:13: " + getCheckMessage(MSG_KEY, "x"),
            "69:13: " + getCheckMessage(MSG_KEY, "y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputPatternVariableAssignmentExtendedScope.java"), expected);
    }

    @Test
    public void testExtendedScope2() throws Exception {
        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_KEY, "s"),
            "20:13: " + getCheckMessage(MSG_KEY, "s"),
            "23:13: " + getCheckMessage(MSG_KEY, "s"),
            "28:16: " + getCheckMessage(MSG_KEY, "s"),
            "43:16: " + getCheckMessage(MSG_KEY, "s"),
            "50:9: " + getCheckMessage(MSG_KEY, "s"),
            "51:9: " + getCheckMessage(MSG_KEY, "s"),
            "58:17: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputPatternVariableAssignmentExtendedScope2.java"), expected);
    }

    @Test
    public void testPatternVariableAssignmentCheck3() throws Exception {

        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "integer3"),
            "24:13: " + getCheckMessage(MSG_KEY, "arr2"),
            "33:13: " + getCheckMessage(MSG_KEY, "s"),
        };

        verifyWithInlineXmlConfig(getPath(
            "InputPatternVariableAssignmentCheck3.java"), expected);
    }

}
