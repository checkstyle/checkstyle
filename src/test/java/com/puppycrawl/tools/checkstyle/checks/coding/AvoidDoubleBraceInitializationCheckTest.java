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

import static com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidDoubleBraceInitializationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/avoiddoublebraceinitialization";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidDoubleBraceInitializationCheck.class);
        final String[] expected = {
            "11:53: " + getCheckMessage(MSG_KEY),
            "16:40: " + getCheckMessage(MSG_KEY),
            "25:40: " + getCheckMessage(MSG_KEY),
            "31:40: " + getCheckMessage(MSG_KEY),
            "36:40: " + getCheckMessage(MSG_KEY),
            "42:40: " + getCheckMessage(MSG_KEY),
            "47:40: " + getCheckMessage(MSG_KEY),
            "53:40: " + getCheckMessage(MSG_KEY),
            "58:40: " + getCheckMessage(MSG_KEY),
            "66:41: " + getCheckMessage(MSG_KEY),
            "69:33: " + getCheckMessage(MSG_KEY),
            "73:33: " + getCheckMessage(MSG_KEY),
            "74:33: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidDoubleBraceInitialization.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final AvoidDoubleBraceInitializationCheck check =
            new AvoidDoubleBraceInitializationCheck();
        final int[] expected = {
            TokenTypes.OBJBLOCK,
        };
        assertArrayEquals(expected, check.getAcceptableTokens(),
            "Acceptable required tokens are invalid");
        assertArrayEquals(expected, check.getDefaultTokens(),
            "Default required tokens are invalid");
        assertArrayEquals(expected, check.getRequiredTokens(),
            "Required required tokens are invalid");
    }

}
