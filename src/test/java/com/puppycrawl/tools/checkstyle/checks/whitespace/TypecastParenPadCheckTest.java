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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_PRECEDED;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TypecastParenPadCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        final String[] expected = {
            "89:14: " + getCheckMessage(WS_FOLLOWED, "("),
            "89:21: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "87:21: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "87:27: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "88:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "88:20: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "90:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "90:20: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "241:18: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "241:21: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        TypecastParenPadCheck typecastParenPadCheckObj = new TypecastParenPadCheck();
        int[] actual = typecastParenPadCheckObj.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.RPAREN,
            TokenTypes.TYPECAST,
        };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }
}
