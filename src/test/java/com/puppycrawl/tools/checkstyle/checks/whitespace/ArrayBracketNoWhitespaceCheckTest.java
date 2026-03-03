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
    public void testDefault() throws Exception {
        final String[] expected = {
            "11:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "14:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "16:33: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "17:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "17:34: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "25:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "26:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "28:23: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "29:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "29:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "37:13: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "37:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "41:20: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "41:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:24: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "46:26: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "66:2: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "73:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "79:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "80:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "82:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "84:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "86:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "88:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "93:25: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "94:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "99:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "101:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "103:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "105:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "107:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "111:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "112:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "116:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDefault.java"), expected);
    }

    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "11:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "14:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "15:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "23:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:28: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "32:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "37:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "38:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "41:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "43:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "48:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "50:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "55:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "56:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "56:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "65:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "66:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "67:27: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "72:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "77:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "84:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "87:49: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "95:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "["),
            "99:14: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "99:16: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }

    @Test
    public void testBranchCoverage() throws Exception {
        final String[] expected = {
            "113:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
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
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceIsTokenAfter.java"), expected);
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
