////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
            "18:7: " + getCheckMessage(MSG_KEY),
            "23:7: " + getCheckMessage(MSG_KEY),
            "28:19: " + getCheckMessage(MSG_KEY),
            "32:10: " + getCheckMessage(MSG_KEY),
            "35:16: " + getCheckMessage(MSG_KEY),
            "39:10: " + getCheckMessage(MSG_KEY),
            "49:10: " + getCheckMessage(MSG_KEY),
            "55:13: " + getCheckMessage(MSG_KEY),
            "57:13: " + getCheckMessage(MSG_KEY),
            "60:19: " + getCheckMessage(MSG_KEY),
            "64:10: " + getCheckMessage(MSG_KEY),
            "67:9: " + getCheckMessage(MSG_KEY),
            "72:10: " + getCheckMessage(MSG_KEY),
            "78:10: " + getCheckMessage(MSG_KEY),
            "82:10: " + getCheckMessage(MSG_KEY),
            "86:10: " + getCheckMessage(MSG_KEY),
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
