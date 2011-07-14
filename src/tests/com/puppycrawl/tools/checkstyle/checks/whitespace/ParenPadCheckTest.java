////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
            "58:12: '(' is followed by whitespace.",
            "58:36: ')' is preceded with whitespace.",
            "74:13: '(' is followed by whitespace.",
            "74:18: ')' is preceded with whitespace.",
            "232:27: ')' is preceded with whitespace.",
            "241:24: '(' is followed by whitespace.",
            "241:30: ')' is preceded with whitespace.",
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
            "29:20: '(' is not followed by whitespace.",
            "29:23: ')' is not preceded with whitespace.",
            "37:22: '(' is not followed by whitespace.",
            "37:26: ')' is not preceded with whitespace.",
            "41:15: '(' is not followed by whitespace.",
            "41:33: ')' is not preceded with whitespace.",
            "76:20: '(' is not followed by whitespace.",
            "76:21: ')' is not preceded with whitespace.",
            "97:22: '(' is not followed by whitespace.",
            "97:28: ')' is not preceded with whitespace.",
            "98:14: '(' is not followed by whitespace.",
            "98:18: ')' is not preceded with whitespace.",
            "150:28: '(' is not followed by whitespace.",
            "150:32: ')' is not preceded with whitespace.",
            "153:16: '(' is not followed by whitespace.",
            "153:20: ')' is not preceded with whitespace.",
            "160:21: '(' is not followed by whitespace.",
            "160:34: ')' is not preceded with whitespace.",
            "162:20: '(' is not followed by whitespace.",
            "165:10: ')' is not preceded with whitespace.",
            "178:14: '(' is not followed by whitespace.",
            "178:36: ')' is not preceded with whitespace.",
            "225:14: '(' is not followed by whitespace.",
            "235:14: '(' is not followed by whitespace.",
            "235:39: ')' is not preceded with whitespace.",
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
            "17:34: ')' is preceded with whitespace.",
            "20:35: ')' is preceded with whitespace.",
            "40:14: '(' is followed by whitespace.",
            "40:36: ')' is preceded with whitespace.",
            "43:14: '(' is followed by whitespace.",
            "48:27: ')' is preceded with whitespace.",
            "51:26: ')' is preceded with whitespace.",
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
            "11:14: '(' is not followed by whitespace.",
            "11:35: ')' is not preceded with whitespace.",
            "14:14: '(' is not followed by whitespace.",
            "14:34: ')' is not preceded with whitespace.",
            "17:14: '(' is not followed by whitespace.",
            "20:14: '(' is not followed by whitespace.",
            "23:14: '(' is not followed by whitespace.",
            "27:14: '(' is not followed by whitespace.",
            "32:14: '(' is not followed by whitespace.",
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
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}
