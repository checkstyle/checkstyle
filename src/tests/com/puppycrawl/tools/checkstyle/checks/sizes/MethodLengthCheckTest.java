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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class MethodLengthCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodLengthCheck.class);
        checkConfig.addAttribute("max", "19");
        final String[] expected = {
            "79:5: Method length is 20 lines (max allowed is 19).",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testcountEmpty() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodLengthCheck.class);
        checkConfig.addAttribute("max", "19");
        checkConfig.addAttribute("countEmpty", "false");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testAbstract() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodLengthCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}
