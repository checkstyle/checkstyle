////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

public class NoWhitespaceBeforeCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(NoWhitespaceBeforeCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "30:14: '++' is preceded with whitespace.",
            "30:21: '--' is preceded with whitespace.",
            "176:18: ';' is preceded with whitespace.",
            "178:23: ';' is preceded with whitespace.",
            "185:18: ';' is preceded with whitespace.",
            "187:27: ';' is preceded with whitespace.",
            "195:26: ';' is preceded with whitespace.",
            "211:15: ';' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDot() throws Exception
    {
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:12: '.' is preceded with whitespace.",
            "6:4: '.' is preceded with whitespace.",
            "129:17: '.' is preceded with whitespace.",
            "135:12: '.' is preceded with whitespace.",
            "136:10: '.' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception
    {
        checkConfig.addAttribute("tokens", "DOT");
        checkConfig.addAttribute("allowLineBreaks", "yes");
        final String[] expected = {
            "5:12: '.' is preceded with whitespace.",
            "129:17: '.' is preceded with whitespace.",
            "136:10: '.' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
}
