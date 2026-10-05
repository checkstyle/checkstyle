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
import static com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class EmptyStatementCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/emptystatement";
    }

    @Test
    public void testEmptyStatements()
            throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_KEY),
            "17:4: " + getCheckMessage(MSG_KEY),
            "21:7: " + getCheckMessage(MSG_KEY),
            "24:4: " + getCheckMessage(MSG_KEY),
            "28:7: " + getCheckMessage(MSG_KEY),
            "33:19: " + getCheckMessage(MSG_KEY),
            "37:10: " + getCheckMessage(MSG_KEY),
            "40:16: " + getCheckMessage(MSG_KEY),
            "44:10: " + getCheckMessage(MSG_KEY),
            "54:10: " + getCheckMessage(MSG_KEY),
            "60:13: " + getCheckMessage(MSG_KEY),
            "62:13: " + getCheckMessage(MSG_KEY),
            "65:19: " + getCheckMessage(MSG_KEY),
            "69:10: " + getCheckMessage(MSG_KEY),
            "72:9: " + getCheckMessage(MSG_KEY),
            "77:10: " + getCheckMessage(MSG_KEY),
            "83:10: " + getCheckMessage(MSG_KEY),
            "87:10: " + getCheckMessage(MSG_KEY),
            "91:10: " + getCheckMessage(MSG_KEY),
            "95:22: " + getCheckMessage(MSG_KEY),
            "96:13: " + getCheckMessage(MSG_KEY),
            "97:6: " + getCheckMessage(MSG_KEY),
            "98:27: " + getCheckMessage(MSG_KEY),
            "99:19: " + getCheckMessage(MSG_KEY),
            "100:34: " + getCheckMessage(MSG_KEY),
            "104:29: " + getCheckMessage(MSG_KEY),
            "106:38: " + getCheckMessage(MSG_KEY),
            "109:52: " + getCheckMessage(MSG_KEY),
            "113:39: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatement.java"), expected);
    }

    @Test
    public void testTopLevelSemicolons() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY),
            "13:2: " + getCheckMessage(MSG_KEY),
            "14:1: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatementTopLevel.java"), expected);
    }

    @Test
    public void testEnumSemicolons() throws Exception {
        final String[] expected = {
            "14:6: " + getCheckMessage(MSG_KEY),
            "19:10: " + getCheckMessage(MSG_KEY),
            "29:10: " + getCheckMessage(MSG_KEY),
            "34:9: " + getCheckMessage(MSG_KEY),
            "39:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatementEnum.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EmptyStatementCheck check = new EmptyStatementCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Default tokens should not be null")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Required tokens should not be null")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
            "12:5: " + getCheckMessage(MSG_KEY),
            "15:18: " + getCheckMessage(MSG_KEY),
            "17:14: " + getCheckMessage(MSG_KEY),
            "21:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputEmptyStatementCompactSourceFile.java"),
                expected);
    }

}
