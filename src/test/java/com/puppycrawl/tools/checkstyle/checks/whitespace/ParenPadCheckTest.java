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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck
.WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck
.WS_NOT_PRECEDED;

public class ParenPadCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "58:12: " + getCheckMessage(WS_FOLLOWED, "("),
            "58:36: " + getCheckMessage(WS_PRECEDED, ")"),
            "74:13: " + getCheckMessage(WS_FOLLOWED, "("),
            "74:18: " + getCheckMessage(WS_PRECEDED, ")"),
            "232:27: " + getCheckMessage(WS_PRECEDED, ")"),
            "241:24: " + getCheckMessage(WS_FOLLOWED, "("),
            "241:30: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "29:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "29:23: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "37:22: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "37:26: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "41:15: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "41:33: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "76:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "76:21: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "97:22: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "97:28: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "98:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "98:18: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "150:28: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "150:32: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "153:16: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "153:20: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "160:21: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "160:34: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "162:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "165:10: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "178:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "178:36: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "225:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "235:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "235:39: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "252:21: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "252:93: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "17:34: " + getCheckMessage(WS_PRECEDED, ")"),
            "20:35: " + getCheckMessage(WS_PRECEDED, ")"),
            "40:14: " + getCheckMessage(WS_FOLLOWED, "("),
            "40:36: " + getCheckMessage(WS_PRECEDED, ")"),
            "43:14: " + getCheckMessage(WS_FOLLOWED, "("),
            "48:27: " + getCheckMessage(WS_PRECEDED, ")"),
            "51:26: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "11:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "11:35: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "14:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "14:34: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "17:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "20:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "23:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "27:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "32:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/ParenPadWithSpace.java"),
               expected);
    }
}
