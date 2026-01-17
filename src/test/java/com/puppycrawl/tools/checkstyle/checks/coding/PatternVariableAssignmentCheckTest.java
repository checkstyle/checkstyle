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

    @Test
    public void testPatternVariableInElseIfBranch() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "i"),
            "19:13: " + getCheckMessage(MSG_KEY, "s"),
            "22:13: " + getCheckMessage(MSG_KEY, "d"),
            "29:17: " + getCheckMessage(MSG_KEY, "i"),
            "32:17: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineXmlConfig(getPath(
                "InputPatternVariableAssignmentElseIf.java"), expected);
    }

    @Test
    public void testPatternVariableSingleStatementNoBlock() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "s"),
            "25:17: " + getCheckMessage(MSG_KEY, "s"),
            "33:17: " + getCheckMessage(MSG_KEY, "i"),
            "41:17: " + getCheckMessage(MSG_KEY, "i"),
            "50:21: " + getCheckMessage(MSG_KEY, "i"),
            "53:21: " + getCheckMessage(MSG_KEY, "i"),
            "61:17: " + getCheckMessage(MSG_KEY, "s"),
            "64:17: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineXmlConfig(getPath(
                "InputPatternVariableAssignmentSingleStmt.java"), expected);
    }

    @Test
    public void testPatternVariableMultipleStatementsInBlock() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "s"),
            "18:13: " + getCheckMessage(MSG_KEY, "s"),
            "20:13: " + getCheckMessage(MSG_KEY, "s"),
            "29:13: " + getCheckMessage(MSG_KEY, "s"),
            "36:17: " + getCheckMessage(MSG_KEY, "s"),
            "39:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineXmlConfig(getPath(
                "InputPatternVariableAssignmentMultipleStmts.java"), expected);
    }

    @Test
    public void testPatternVariableWithReturn() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "s"),
            "25:17: " + getCheckMessage(MSG_KEY, "s"),
            "28:13: " + getCheckMessage(MSG_KEY, "s"),
            "35:17: " + getCheckMessage(MSG_KEY, "s"),
            "38:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineXmlConfig(getPath(
                "InputPatternVariableAssignmentAfterReturn.java"), expected);
    }

    @Test
    public void testPatternVariableWithThrow() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "s"),
            "24:17: " + getCheckMessage(MSG_KEY, "s"),
            "27:13: " + getCheckMessage(MSG_KEY, "s"),
            "33:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineXmlConfig(getPath(
                "InputPatternVariableAssignmentAfterThrow.java"), expected);
    }

    @Test
    public void testIfWithoutBraces() throws Exception {
        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_KEY, "i"),
            "24:17: " + getCheckMessage(MSG_KEY, "i"),
            "31:17: " + getCheckMessage(MSG_KEY, "s"),
            "40:13: " + getCheckMessage(MSG_KEY, "i"),
            "48:13: " + getCheckMessage(MSG_KEY, "x"),
            "54:13: " + getCheckMessage(MSG_KEY, "s"),
            "56:13: " + getCheckMessage(MSG_KEY, "s"),
            "63:17: " + getCheckMessage(MSG_KEY, "i"),
            "72:21: " + getCheckMessage(MSG_KEY, "i"),
            "75:21: " + getCheckMessage(MSG_KEY, "i"),
            "83:17: " + getCheckMessage(MSG_KEY, "s"),
            "86:17: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputPatternVariableAssignmentIfWithoutBraces.java"),
                expected);
    }

    @Test
    public void testExtendedScope() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "s"),
            "24:13: " + getCheckMessage(MSG_KEY, "s"),
            "26:13: " + getCheckMessage(MSG_KEY, "s"),
            "33:13: " + getCheckMessage(MSG_KEY, "i"),
            "41:17: " + getCheckMessage(MSG_KEY, "s"),
            "44:13: " + getCheckMessage(MSG_KEY, "s"),
            "50:13: " + getCheckMessage(MSG_KEY, "s"),
            "58:13: " + getCheckMessage(MSG_KEY, "i"),
            "61:13: " + getCheckMessage(MSG_KEY, "i"),
            "67:13: " + getCheckMessage(MSG_KEY, "i"),
            "68:13: " + getCheckMessage(MSG_KEY, "i"),
            "69:13: " + getCheckMessage(MSG_KEY, "i"),
            "76:17: " + getCheckMessage(MSG_KEY, "s"),
            "84:17: " + getCheckMessage(MSG_KEY, "i"),
            "92:17: " + getCheckMessage(MSG_KEY, "i"),
            "101:21: " + getCheckMessage(MSG_KEY, "i"),
            "104:21: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputPatternVariableAssignmentExtendedScope.java"), expected);
    }

    @Test
    public void testExtendedScopePart2() throws Exception {
        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_KEY, "s"),
            "20:17: " + getCheckMessage(MSG_KEY, "s"),
            "29:13: " + getCheckMessage(MSG_KEY, "x"),
            "30:13: " + getCheckMessage(MSG_KEY, "y"),
            "38:13: " + getCheckMessage(MSG_KEY, "s"),
            "40:13: " + getCheckMessage(MSG_KEY, "s"),
            "47:17: " + getCheckMessage(MSG_KEY, "s"),
            "49:13: " + getCheckMessage(MSG_KEY, "s"),
            "55:13: " + getCheckMessage(MSG_KEY, "i"),
            "64:17: " + getCheckMessage(MSG_KEY, "s"),
            "67:13: " + getCheckMessage(MSG_KEY, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputPatternVariableAssignmentExtendedScopePart2.java"), expected);
    }
}
