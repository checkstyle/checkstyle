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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class MethodCountCheckTest extends BaseCheckTestSupport
{

    @Test
    public void testDefaults() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);

        final String[] expected = {
        };

        verify(checkConfig,
            getSrcPath("checks/sizes/MethodCountCheckInput.java"), expected);
    }

    @Test
    public void testThrees() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "3");
        checkConfig.addAttribute("maxPackage", "3");
        checkConfig.addAttribute("maxProtected", "3");
        checkConfig.addAttribute("maxPublic", "3");
        checkConfig.addAttribute("maxTotal", "3");

        final String[] expected = {
            "3: Number of package methods is 5 (max allowed is 3).",
            "3: Number of private methods is 5 (max allowed is 3).",
            "3: Number of protected methods is 5 (max allowed is 3).",
            "3: Number of public methods is 5 (max allowed is 3).",
            "3: Total number of methods is 20 (max allowed is 3).",
            "9: Number of public methods is 5 (max allowed is 3).",
            "9: Total number of methods is 5 (max allowed is 3).",
            "45: Number of public methods is 5 (max allowed is 3).",
            "45: Total number of methods is 5 (max allowed is 3).",
        };

        verify(checkConfig,
            getSrcPath("checks/sizes/MethodCountCheckInput.java"), expected);
    }

    @Test
    public void testEnum() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "0");
        checkConfig.addAttribute("maxTotal", "2");

        final String[] expected = {
            "9: Number of private methods is 1 (max allowed is 0).",
            "9: Total number of methods is 3 (max allowed is 2).",
        };

        verify(checkConfig,
            getSrcPath("checks/sizes/MethodCountCheckInput2.java"), expected);
    }
}
