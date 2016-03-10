////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_PRECEDED;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class TypecastParenPadCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        final String[] expected = {
            "89:14: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "89:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "87:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "87:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "88:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "88:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "90:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "90:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "241:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "241:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final TypecastParenPadCheck typecastParenPadCheckObj = new TypecastParenPadCheck();
        final int[] actual = typecastParenPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RPAREN,
            TokenTypes.TYPECAST,
        };
        Assert.assertArrayEquals(expected, actual);
    }
}
