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
import org.junit.Before;
import org.junit.Test;

public class NoWhitespaceAfterCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(NoWhitespaceAfterCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        checkConfig.addAttribute("allowLineBreaks", "false");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "6:12: '.' is followed by whitespace.",
            "29:14: '-' is followed by whitespace.",
            "29:21: '+' is followed by whitespace.",
            "31:15: '++' is followed by whitespace.",
            "31:22: '--' is followed by whitespace.",
            "111:22: '!' is followed by whitespace.",
            "112:23: '~' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "132:11: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception
    {
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception
    {
        checkConfig.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "87:28: ')' is followed by whitespace.",
            "89:23: ')' is followed by whitespace.",
            "241:22: ')' is followed by whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

}
