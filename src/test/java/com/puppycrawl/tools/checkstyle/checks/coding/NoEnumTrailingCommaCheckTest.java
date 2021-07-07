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

import static com.puppycrawl.tools.checkstyle.checks.coding.NoEnumTrailingCommaCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoEnumTrailingCommaCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/noenumtrailingcomma";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NoEnumTrailingCommaCheck.class);
        final String[] expected = {
            "23:12: " + getCheckMessage(MSG_KEY),
            "28:12: " + getCheckMessage(MSG_KEY),
            "34:12: " + getCheckMessage(MSG_KEY),
            "37:25: " + getCheckMessage(MSG_KEY),
            "39:25: " + getCheckMessage(MSG_KEY),
            "53:21: " + getCheckMessage(MSG_KEY),
            "58:12: " + getCheckMessage(MSG_KEY),
            "76:9: " + getCheckMessage(MSG_KEY),
            "106:55: " + getCheckMessage(MSG_KEY),
            "110:14: " + getCheckMessage(MSG_KEY),
            "114:14: " + getCheckMessage(MSG_KEY),
            "130:14: " + getCheckMessage(MSG_KEY),
            "134:14: " + getCheckMessage(MSG_KEY),
            "138:55: " + getCheckMessage(MSG_KEY),
            "144:33: " + getCheckMessage(MSG_KEY),
            "151:33: " + getCheckMessage(MSG_KEY),
            "168:13: " + getCheckMessage(MSG_KEY),
            "186:21: " + getCheckMessage(MSG_KEY),
            "206:10: " + getCheckMessage(MSG_KEY),
            "250:55: " + getCheckMessage(MSG_KEY),
            "255:9: " + getCheckMessage(MSG_KEY),
            "258:9: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputNoEnumTrailingComma.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NoEnumTrailingCommaCheck check = new NoEnumTrailingCommaCheck();
        assertNotNull(check.getAcceptableTokens(), "Invalid acceptable tokens");
        assertNotNull(check.getDefaultTokens(), "Invalid default tokens");
        assertNotNull(check.getRequiredTokens(), "Invalid required tokens");
    }

}
