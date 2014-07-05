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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class NoLineWrapCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testCaseWithoutLineWrapping() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("whitespace/NoLineWrapGoodInput.java"), expected);
    }

    @Test
    public void testDefaultTokensLineWrapping() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        final String[] expected = {
            "1: package statement should not be line-wrapped.",
            "6: import statement should not be line-wrapped.",
        };
        verify(checkConfig, getPath("whitespace/NoLineWrapBadInput.java"), expected);
    }

    @Test
    public void testCustomTokensLineWrapping()
        throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        checkConfig.addAttribute("tokens", "IMPORT, CLASS_DEF, METHOD_DEF, ENUM_DEF");
        final String[] expected = {
            "6: import statement should not be line-wrapped.",
            "10: CLASS_DEF statement should not be line-wrapped.",
            "13: METHOD_DEF statement should not be line-wrapped.",
            "20: ENUM_DEF statement should not be line-wrapped.",
        };
        verify(checkConfig, getPath("whitespace/NoLineWrapBadInput.java"), expected);
    }
}
