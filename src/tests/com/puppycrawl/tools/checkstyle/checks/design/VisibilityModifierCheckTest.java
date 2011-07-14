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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class VisibilityModifierCheckTest
    extends BaseCheckTestSupport
{
    private Checker getChecker() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^f[A-Z][a-zA-Z0-9]*$");
        return createChecker(checkConfig);
    }

    @Test
    public void testInner()
        throws Exception
    {
        final String[] expected = {
            "30:24: Variable 'rData' must be private and have accessor methods.",
            "33:27: Variable 'protectedVariable' must be private and have accessor methods.",
            "36:17: Variable 'packageVariable' must be private and have accessor methods.",
            "41:29: Variable 'sWeird' must be private and have accessor methods.",
            "43:19: Variable 'sWeird2' must be private and have accessor methods.",
            "77:20: Variable 'someValue' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputInner.java"), expected);
    }

    @Test
    public void testIgnoreAccess()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^r[A-Z]");
        checkConfig.addAttribute("protectedAllowed", "true");
        checkConfig.addAttribute("packageAllowed", "true");
        final String[] expected = {
            "17:20: Variable 'fData' must be private and have accessor methods.",
            "77:20: Variable 'someValue' must be private and have accessor methods.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testSimple() throws Exception
    {
        final String[] expected = {
            "39:19: Variable 'mNumCreated2' must be private and have accessor methods.",
            "49:23: Variable 'sTest1' must be private and have accessor methods.",
            "51:26: Variable 'sTest3' must be private and have accessor methods.",
            "53:16: Variable 'sTest2' must be private and have accessor methods.",
            "56:9: Variable 'mTest1' must be private and have accessor methods.",
            "58:16: Variable 'mTest2' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputSimple.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception
    {
        final String[] expected = {
            "44:9: Variable 'mLen' must be private and have accessor methods.",
            "45:19: Variable 'mDeer' must be private and have accessor methods.",
            "46:16: Variable 'aFreddo' must be private and have accessor methods.",
        };
        verify(getChecker(), getPath("InputPublicOnly.java"), expected);
    }
}
