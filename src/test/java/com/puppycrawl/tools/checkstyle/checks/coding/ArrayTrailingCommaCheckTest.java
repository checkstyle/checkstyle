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

import static com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ArrayTrailingCommaCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/arraytrailingcomma";
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ArrayTrailingCommaCheck.class);
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY),
            "36:9: " + getCheckMessage(MSG_KEY),
            "75:12: " + getCheckMessage(MSG_KEY),
            "73:9: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputArrayTrailingComma.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final ArrayTrailingCommaCheck check = new ArrayTrailingCommaCheck();
        assertNotNull(check.getAcceptableTokens(), "Invalid acceptable tokens");
        assertNotNull(check.getDefaultTokens(), "Invalid default tokens");
        assertNotNull(check.getRequiredTokens(), "Invalid required tokens");
    }

    @Test
    public void testAlwaysDemandTrailingComma() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ArrayTrailingCommaCheck.class);
        checkConfig.addProperty("alwaysDemandTrailingComma", "true");
        final String[] expected = {
            "12:26: " + getCheckMessage(MSG_KEY),
            "19:29: " + getCheckMessage(MSG_KEY),
            "24:14: " + getCheckMessage(MSG_KEY),
            "26:17: " + getCheckMessage(MSG_KEY),
            "29:20: " + getCheckMessage(MSG_KEY),
            "35:17: " + getCheckMessage(MSG_KEY),
            "44:13: " + getCheckMessage(MSG_KEY),
            "49:28: " + getCheckMessage(MSG_KEY),
            "51:17: " + getCheckMessage(MSG_KEY),
            "53:13: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
            getPath("InputArrayTrailingCommaAlwaysDemandTrailingComma.java"), expected);
    }

}
