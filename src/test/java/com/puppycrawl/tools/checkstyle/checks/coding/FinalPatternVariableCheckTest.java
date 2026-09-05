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

import static com.puppycrawl.tools.checkstyle.checks.coding.FinalPatternVariableCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
        assertArrayEquals(expected, check.getRequiredTokens(),
                "Required tokens should match");
    }

    @Test
    public void testGetDefaultTokens() {
        final FinalPatternVariableCheck check = new FinalPatternVariableCheck();
        final int[] expected = {TokenTypes.LITERAL_INSTANCEOF};
        assertArrayEquals(expected, check.getDefaultTokens(),
                "Default tokens should match");
    }

    @Test
    public void testGetAcceptableTokens() {
        final FinalPatternVariableCheck check = new FinalPatternVariableCheck();
        final int[] expected = {TokenTypes.LITERAL_INSTANCEOF};
        assertArrayEquals(expected, check.getAcceptableTokens(),
                "Acceptable tokens should match");
    }

    @Test
    public void testFinalPatternVariableCheck() throws Exception {
        final String[] expected = {
            "15:33: " + getCheckMessage(MSG_KEY, "s1"),
            "31:33: " + getCheckMessage(MSG_KEY, "s4"),
            "42:36: " + getCheckMessage(MSG_KEY, "x"),
            "50:34: " + getCheckMessage(MSG_KEY, "i1"),
            "53:38: " + getCheckMessage(MSG_KEY, "s6"),
            "58:40: " + getCheckMessage(MSG_KEY, "s7"),
        };

        verifyWithInlineConfigParser(getPath(
            "InputFinalPatternVariableCheck.java"), expected);
    }

}
