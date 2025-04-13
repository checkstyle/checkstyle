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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidNestedBlocksCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/avoidnestedblocks";
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidNestedBlocksCheck checkObj = new AvoidNestedBlocksCheck();
        final int[] expected = {TokenTypes.SLIST};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testStrictSettings()
            throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "47:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "53:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "61:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidNestedBlocksDefault.java"), expected);
    }

    @Test
    public void testAllowSwitchInCase()
            throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "43:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "57:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidNestedBlocksAllowInSwitchCase.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidNestedBlocksCheck constantNameCheckObj = new AvoidNestedBlocksCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.SLIST };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

}
