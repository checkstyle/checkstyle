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
import static com.puppycrawl.tools.checkstyle.checks.coding.DuplicateElseIfConditionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class DuplicateElseIfConditionCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/duplicateelseifcondition";
    }

    @Test
    public void testIfBasic() throws Exception {
        final String[] expected = {
            "13:22: " + getCheckMessage(MSG_KEY, 11),
            "21:22: " + getCheckMessage(MSG_KEY, 19),
            "23:22: " + getCheckMessage(MSG_KEY, 19),
            "32:26: " + getCheckMessage(MSG_KEY, 30),
            "41:22: " + getCheckMessage(MSG_KEY, 38),
            "49:20: " + getCheckMessage(MSG_KEY, 48),
            "57:31: " + getCheckMessage(MSG_KEY, 55),
        };

        verifyWithInlineConfigParser(
                getPath("InputDuplicateElseIfConditionIfBasic.java"), expected);
    }

    @Test
    public void testIfStructure() throws Exception {
        final String[] expected = {
            "12:38: " + getCheckMessage(MSG_KEY, 11),
            "20:15: " + getCheckMessage(MSG_KEY, 18),
            "29:20: " + getCheckMessage(MSG_KEY, 27),
        };

        verifyWithInlineConfigParser(
                getPath("InputDuplicateElseIfConditionIfStructure.java"), expected);
    }

    @Test
    public void testIfNested() throws Exception {
        final String[] expected = {
            "13:26: " + getCheckMessage(MSG_KEY, 12),
            "25:26: " + getCheckMessage(MSG_KEY, 23),
            "35:26: " + getCheckMessage(MSG_KEY, 34),
            "53:30: " + getCheckMessage(MSG_KEY, 52),
            "66:22: " + getCheckMessage(MSG_KEY, 62),
        };

        verifyWithInlineConfigParser(
                getPath("InputDuplicateElseIfConditionIfNested.java"), expected);
    }

    @Test
    public void testIfExpressions() throws Exception {
        final String[] expected = {
            "12:26: " + getCheckMessage(MSG_KEY, 11),
            "19:27: " + getCheckMessage(MSG_KEY, 18),
            "26:20: " + getCheckMessage(MSG_KEY, 25),
            "33:26: " + getCheckMessage(MSG_KEY, 32),
            "40:36: " + getCheckMessage(MSG_KEY, 39),
            "47:24: " + getCheckMessage(MSG_KEY, 46),
            "80:27: " + getCheckMessage(MSG_KEY, 78),
            "86:27: " + getCheckMessage(MSG_KEY, 85),
        };

        verifyWithInlineConfigParser(
                getPath("InputDuplicateElseIfConditionIfExpressions.java"), expected);
    }

    @Test
    public void testCompact() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_KEY, 11),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputDuplicateElseIfConditionCompact.java"), expected);
    }

    @Test
    public void testTokens() {
        final DuplicateElseIfConditionCheck check = new DuplicateElseIfConditionCheck();
        final int[] expected = {TokenTypes.LITERAL_IF};

        assertWithMessage("Default tokens are invalid")
            .that(check.getDefaultTokens())
            .isEqualTo(expected);
        assertWithMessage("Acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Required tokens are invalid")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }

}
