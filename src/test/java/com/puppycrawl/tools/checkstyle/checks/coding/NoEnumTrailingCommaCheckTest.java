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
import static com.puppycrawl.tools.checkstyle.checks.coding.NoEnumTrailingCommaCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class NoEnumTrailingCommaCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/noenumtrailingcomma";
    }

    @Test
    public void testDefaultOne() throws Exception {
        final String[] expected = {
            "23:12: " + getCheckMessage(MSG_KEY),
            "28:12: " + getCheckMessage(MSG_KEY),
            "34:12: " + getCheckMessage(MSG_KEY),
            "37:25: " + getCheckMessage(MSG_KEY),
            "39:25: " + getCheckMessage(MSG_KEY),
            "53:21: " + getCheckMessage(MSG_KEY),
            "58:12: " + getCheckMessage(MSG_KEY),
            "76:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoEnumTrailingCommaOne.java"), expected);
    }

    @Test
    public void testDefaultTwo() throws Exception {
        final String[] expected = {
            "21:55: " + getCheckMessage(MSG_KEY),
            "25:14: " + getCheckMessage(MSG_KEY),
            "29:14: " + getCheckMessage(MSG_KEY),
            "45:14: " + getCheckMessage(MSG_KEY),
            "49:14: " + getCheckMessage(MSG_KEY),
            "54:55: " + getCheckMessage(MSG_KEY),
            "60:33: " + getCheckMessage(MSG_KEY),
            "67:33: " + getCheckMessage(MSG_KEY),
            "84:13: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoEnumTrailingCommaTwo.java"), expected);
    }

    @Test
    public void testDefaultThree() throws Exception {
        final String[] expected = {
            "13:21: " + getCheckMessage(MSG_KEY),
            "33:10: " + getCheckMessage(MSG_KEY),
            "78:55: " + getCheckMessage(MSG_KEY),
            "83:9: " + getCheckMessage(MSG_KEY),
            "86:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoEnumTrailingCommaThree.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NoEnumTrailingCommaCheck check = new NoEnumTrailingCommaCheck();
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
