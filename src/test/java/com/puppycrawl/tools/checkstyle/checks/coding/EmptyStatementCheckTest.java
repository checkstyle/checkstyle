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

public class EmptyStatementCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/emptystatement";
    }

    @Test
    public void testEmptyStatements() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_KEY),
            "20:7: " + getCheckMessage(MSG_KEY),
            "25:7: " + getCheckMessage(MSG_KEY),
            "30:19: " + getCheckMessage(MSG_KEY),
            "34:10: " + getCheckMessage(MSG_KEY),
            "37:16: " + getCheckMessage(MSG_KEY),
            "41:10: " + getCheckMessage(MSG_KEY),
            "51:10: " + getCheckMessage(MSG_KEY),
            "57:13: " + getCheckMessage(MSG_KEY),
            "59:13: " + getCheckMessage(MSG_KEY),
            "62:19: " + getCheckMessage(MSG_KEY),
            "66:10: " + getCheckMessage(MSG_KEY),
            "69:9: " + getCheckMessage(MSG_KEY),
            "74:10: " + getCheckMessage(MSG_KEY),
            "80:10: " + getCheckMessage(MSG_KEY),
            "84:10: " + getCheckMessage(MSG_KEY),
            "88:10: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
            getPath("InputEmptyStatement.java"), expected);
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

}
