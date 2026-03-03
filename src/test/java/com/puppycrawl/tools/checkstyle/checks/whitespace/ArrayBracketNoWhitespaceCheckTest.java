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
            "71:24: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "77:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "78:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "80:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "82:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "84:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "86:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "91:25: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "92:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "97:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "99:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "102:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "104:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "105:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "109:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "113:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "117:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDefault.java"), expected);
    }

    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "13:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "18:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "19:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "19:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "30:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "35:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "37:28: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "42:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "44:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "48:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "51:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "53:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "59:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "60:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "62:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "70:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "70:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "81:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "82:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "83:27: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "88:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "95:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "102:31: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
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
