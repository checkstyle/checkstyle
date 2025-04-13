///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ArrayTrailingCommaCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/arraytrailingcomma";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY),
            "43:9: " + getCheckMessage(MSG_KEY),
            "80:9: " + getCheckMessage(MSG_KEY),
            "82:12: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayTrailingComma.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final ArrayTrailingCommaCheck check = new ArrayTrailingCommaCheck();
        assertWithMessage("Invalid acceptable tokens")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Invalid default tokens")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Invalid required tokens")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

    @Test
    public void testAlwaysDemandTrailingComma() throws Exception {
        final String[] expected = {
            "15:26: " + getCheckMessage(MSG_KEY),
            "22:29: " + getCheckMessage(MSG_KEY),
            "27:14: " + getCheckMessage(MSG_KEY),
            "29:17: " + getCheckMessage(MSG_KEY),
            "32:20: " + getCheckMessage(MSG_KEY),
            "38:17: " + getCheckMessage(MSG_KEY),
            "47:13: " + getCheckMessage(MSG_KEY),
            "52:28: " + getCheckMessage(MSG_KEY),
            "54:17: " + getCheckMessage(MSG_KEY),
            "56:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayTrailingCommaAlwaysDemandTrailingComma.java"), expected);
    }

}
