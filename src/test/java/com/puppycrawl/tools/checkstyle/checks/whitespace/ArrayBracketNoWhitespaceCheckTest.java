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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ArrayBracketNoWhitespaceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketnowhitespace";
    }

    @Test
    public void testCreation() throws Exception {
        final String[] expected = {
            "8:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "11:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "12:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "13:33: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "14:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "14:34: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "22:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "23:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "25:23: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "26:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "26:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "34:13: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "34:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "38:20: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "38:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "43:24: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "43:26: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceCreation.java"), expected);
    }

    @Test
    public void testMultiline() throws Exception {
        final String[] expected = {
            "23:2: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceMultiline.java"), expected);
    }

    @Test
    public void testMemberAndOperators() throws Exception {
        final String[] expected = {
            "16:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "22:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "23:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "25:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "27:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "29:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "31:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "36:25: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "37:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "42:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "44:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "46:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "48:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "50:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceMemberAndOperators.java"), expected);
    }

    @Test
    public void testMisc() throws Exception {
        final String[] expected = {
            "10:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "11:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceMisc.java"), expected);
    }

    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "11:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "14:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "15:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "25:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "28:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "29:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "31:51: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "36:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "42:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "43:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "43:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "52:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "53:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "58:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "63:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "76:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "["),
            "80:14: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "80:16: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }

    @Test
    public void testBranchCoverage() throws Exception {
        final String[] expected = {
            "72:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceBranchCoverage.java"), expected);
    }

    @Test
    public void testIsTokenAfter() throws Exception {
        final String[] expected = {
            "9:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "10:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "11:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "16:46: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "26:19: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "28:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "34:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceIsTokenAfter.java"), expected);
    }

    @Test
    public void testNullCurrentExit() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceNullCurrentExit.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {

        final ArrayBracketNoWhitespaceCheck check = new ArrayBracketNoWhitespaceCheck();
        assertWithMessage("Acceptable tokens and required tokens must match")
            .that(check.getRequiredTokens())
            .isEqualTo(check.getAcceptableTokens());
    }

    @Test
    public void testGetDefaultTokens() {
        final ArrayBracketNoWhitespaceCheck check = new ArrayBracketNoWhitespaceCheck();
        assertWithMessage("Default tokens and required tokens must match")
            .that(check.getRequiredTokens())
            .isEqualTo(check.getDefaultTokens());
    }
}
