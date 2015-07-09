////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheck.MSG_KEY_BLOCK_NESTED;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidNestedBlocksCheckTest
        extends BaseCheckTestSupport {
    @Test
    public void testStrictSettings()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidNestedBlocksCheck.class);
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "44:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "50:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "58:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
        };
        verify(checkConfig, getPath("InputNestedBlocks.java"), expected);
    }

    @Test
    public void testAllowSwitchInCase()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidNestedBlocksCheck.class);
        checkConfig.addAttribute("allowInSwitchCase", Boolean.TRUE.toString());

        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "44:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
            "58:17: " + getCheckMessage(MSG_KEY_BLOCK_NESTED),
        };
        verify(checkConfig, getPath("InputNestedBlocks.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        AvoidNestedBlocksCheck constantNameCheckObj = new AvoidNestedBlocksCheck();
        int[] actual = constantNameCheckObj.getAcceptableTokens();
        int[] expected = new int[] {TokenTypes.SLIST };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }

}
