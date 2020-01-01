////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EmptyStatementCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/emptystatement";
    }

    @Test
    public void testEmptyStatements()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(EmptyStatementCheck.class);
        final String[] expected = {
            "12:7: " + getCheckMessage(MSG_KEY),
            "17:7: " + getCheckMessage(MSG_KEY),
            "22:19: " + getCheckMessage(MSG_KEY),
            "26:10: " + getCheckMessage(MSG_KEY),
            "29:16: " + getCheckMessage(MSG_KEY),
            "33:10: " + getCheckMessage(MSG_KEY),
            "43:10: " + getCheckMessage(MSG_KEY),
            "49:13: " + getCheckMessage(MSG_KEY),
            "51:13: " + getCheckMessage(MSG_KEY),
            "54:19: " + getCheckMessage(MSG_KEY),
            "58:10: " + getCheckMessage(MSG_KEY),
            "61:9: " + getCheckMessage(MSG_KEY),
            "66:10: " + getCheckMessage(MSG_KEY),
            "72:10: " + getCheckMessage(MSG_KEY),
            "76:10: " + getCheckMessage(MSG_KEY),
            "80:10: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EmptyStatementCheck check = new EmptyStatementCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
