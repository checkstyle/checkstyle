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

import static com.puppycrawl.tools.checkstyle.checks.coding.NoArrayTrailingCommaCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoArrayTrailingCommaCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/noarraytrailingcomma";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NoArrayTrailingCommaCheck.class);
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_KEY),
            "19:32: " + getCheckMessage(MSG_KEY),
            "23:53: " + getCheckMessage(MSG_KEY),
            "37:15: " + getCheckMessage(MSG_KEY),
            "41:17: " + getCheckMessage(MSG_KEY),
            "47:14: " + getCheckMessage(MSG_KEY),
            "61:29: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputNoArrayTrailingComma.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NoArrayTrailingCommaCheck check = new NoArrayTrailingCommaCheck();
        assertNotNull(check.getAcceptableTokens(), "Invalid acceptable tokens");
        assertNotNull(check.getDefaultTokens(), "Invalid default tokens");
        assertNotNull(check.getRequiredTokens(), "Invalid required tokens");
    }
}
