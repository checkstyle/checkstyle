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

public class OperatorWrapCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(OperatorWrapCheck.class);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final String[] expected = {
            "15:19: '+' should be on a new line.",
            "16:15: '-' should be on a new line.",
            "24:18: '&&' should be on a new line.",

        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    @Test
    public void testOpWrapEOL()
        throws Exception
    {
        checkConfig.addAttribute("option", OperatorWrapOption.EOL.toString());
        final String[] expected = {
            "18:13: '-' should be on the previous line.",
            "22:13: '&&' should be on the previous line.",
            "27:13: '&&' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    @Test
    public void testAssignEOL()
        throws Exception
    {
        checkConfig.addAttribute("tokens", "ASSIGN");
        checkConfig.addAttribute("option", OperatorWrapOption.EOL.toString());
        final String[] expected = {
            "33:13: '=' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }
}
