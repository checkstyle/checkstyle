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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class DeclarationOrderCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);

        final String[] expected = {
            "8:5: Variable access definition in wrong order.",
            "13:5: Variable access definition in wrong order.",
            "18:5: Variable access definition in wrong order.",
            "21:5: Variable access definition in wrong order.",
            "27:5: Static variable definition in wrong order.",
            "27:5: Variable access definition in wrong order.",
            "34:9: Variable access definition in wrong order.",
            "45:9: Static variable definition in wrong order.",
            "45:9: Variable access definition in wrong order.",
            "54:5: Constructor definition in wrong order.",
            "80:5: Instance variable definition in wrong order.",

            "92:9: Variable access definition in wrong order.",
            "100:9: Static variable definition in wrong order.",
            "100:9: Variable access definition in wrong order.",
            "106:5: Variable access definition in wrong order.",
            "111:5: Variable access definition in wrong order.",
            "116:5: Variable access definition in wrong order.",
            "119:5: Variable access definition in wrong order.",
            "125:5: Static variable definition in wrong order.",
            "125:5: Variable access definition in wrong order.",
            "132:9: Variable access definition in wrong order.",
            "143:9: Static variable definition in wrong order.",
            "143:9: Variable access definition in wrong order.",
            "152:5: Constructor definition in wrong order.",
            "178:5: Instance variable definition in wrong order.",
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyConstructors() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "false");
        checkConfig.addAttribute("ignoreMethods", "true");
        checkConfig.addAttribute("ignoreModifiers", "true");

        final String[] expected = {
            "54:5: Constructor definition in wrong order.",
            "152:5: Constructor definition in wrong order.",
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyModifiers() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "true");
        checkConfig.addAttribute("ignoreMethods", "true");
        checkConfig.addAttribute("ignoreModifiers", "false");

        final String[] expected = {
            "8:5: Variable access definition in wrong order.",
            "13:5: Variable access definition in wrong order.",
            "18:5: Variable access definition in wrong order.",
            "21:5: Variable access definition in wrong order.",
            "27:5: Static variable definition in wrong order.",
            "27:5: Variable access definition in wrong order.",
            "34:9: Variable access definition in wrong order.",
            "45:9: Static variable definition in wrong order.",
            "45:9: Variable access definition in wrong order.",
            "80:5: Instance variable definition in wrong order.",

            "92:9: Variable access definition in wrong order.",
            "100:9: Static variable definition in wrong order.",
            "100:9: Variable access definition in wrong order.",
            "106:5: Variable access definition in wrong order.",
            "111:5: Variable access definition in wrong order.",
            "116:5: Variable access definition in wrong order.",
            "119:5: Variable access definition in wrong order.",
            "125:5: Static variable definition in wrong order.",
            "125:5: Variable access definition in wrong order.",
            "132:9: Variable access definition in wrong order.",
            "143:9: Static variable definition in wrong order.",
            "143:9: Variable access definition in wrong order.",
            "178:5: Instance variable definition in wrong order.",
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }
}
