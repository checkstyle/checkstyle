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
import static com.puppycrawl.tools.checkstyle.checks.coding.FinalPatternVariableCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FinalPatternVariableCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/finalpatternvariable";
    }

    @Test
    public void testGetRequiredTokens() {
        final FinalPatternVariableCheck check = new FinalPatternVariableCheck();
        final int[] expected = {TokenTypes.LITERAL_INSTANCEOF};
        assertWithMessage("Required tokens should match")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final FinalPatternVariableCheck check = new FinalPatternVariableCheck();
        final int[] expected = {TokenTypes.LITERAL_INSTANCEOF};
        assertWithMessage("Default tokens should match")
            .that(check.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final FinalPatternVariableCheck check = new FinalPatternVariableCheck();
        final int[] expected = {TokenTypes.LITERAL_INSTANCEOF};
        assertWithMessage("Acceptable tokens should match")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testFinalPatternVariableCheck() throws Exception {
        final String[] expected = {
            "19:33: " + getCheckMessage(MSG_KEY, "s1"),
            "29:33: " + getCheckMessage(MSG_KEY, "s4"),
            "36:36: " + getCheckMessage(MSG_KEY, "x"),
            "43:34: " + getCheckMessage(MSG_KEY, "i1"),
            "47:38: " + getCheckMessage(MSG_KEY, "s6"),
            "52:40: " + getCheckMessage(MSG_KEY, "s7"),
            "62:41: " + getCheckMessage(MSG_KEY, "s10"),
            "67:35: " + getCheckMessage(MSG_KEY, "s12"),
            "72:37: " + getCheckMessage(MSG_KEY, "s13"),
            "78:35: " + getCheckMessage(MSG_KEY, "s14"),
            "84:76: " + getCheckMessage(MSG_KEY, "s15"),
            "91:50: " + getCheckMessage(MSG_KEY, "value1"),
        };

        verifyWithInlineConfigParser(getPath(
            "InputFinalPatternVariableCheck.java"), expected);
    }

    @Test
    public void testFinalPatternVariableCheck2() throws Exception {
        final String[] expected = {
            "20:38: " + getCheckMessage(MSG_KEY, "s16"),
            "25:61: " + getCheckMessage(MSG_KEY, "s17"),
            "36:35: " + getCheckMessage(MSG_KEY, "s20"),
            "63:33: " + getCheckMessage(MSG_KEY, "s27"),
            "69:35: " + getCheckMessage(MSG_KEY, "s29"),
            "77:35: " + getCheckMessage(MSG_KEY, "s30"),
        };

        verifyWithInlineConfigParser(getPath(
            "InputFinalPatternVariableCheck2.java"), expected);
    }

    @Test
    public void testFinalPatternVariableCheck3() throws Exception {
        final String[] expected = {
            "15:35: " + getCheckMessage(MSG_KEY, "str"),
            "26:35: " + getCheckMessage(MSG_KEY, "str"),
            "37:35: " + getCheckMessage(MSG_KEY, "str"),
            "41:35: " + getCheckMessage(MSG_KEY, "str"),
            "48:35: " + getCheckMessage(MSG_KEY, "str"),
            "56:35: " + getCheckMessage(MSG_KEY, "str"),
            "76:36: " + getCheckMessage(MSG_KEY, "text"),
            "85:35: " + getCheckMessage(MSG_KEY, "str"),
            "98:35: " + getCheckMessage(MSG_KEY, "str"),
            "102:52: " + getCheckMessage(MSG_KEY, "other"),
        };

        verifyWithInlineConfigParser(getPath(
            "InputFinalPatternVariableCheck3.java"), expected);
    }

    @Test
    public void testFinalPatternVariableCheck5() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_KEY, "str"),
            "24:35: " + getCheckMessage(MSG_KEY, "str"),
            "35:35: " + getCheckMessage(MSG_KEY, "str"),
            "49:43: " + getCheckMessage(MSG_KEY, "str"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath(
            "InputFinalPatternVariableCheck5.java"), expected);
    }

    @Test
    public void testFinalPatternVariableCheck6() throws Exception {
        final String[] expected = {
            "14:40: " + getCheckMessage(MSG_KEY, "_"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath(
            "InputFinalPatternVariableCheck6.java"), expected);
    }

}
