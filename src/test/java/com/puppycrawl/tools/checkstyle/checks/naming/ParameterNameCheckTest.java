////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ParameterNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testCatch()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^NO_WAY_MATEY$");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^a[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "71:19: Name 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "71:34: Name 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "72:25: Name 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
