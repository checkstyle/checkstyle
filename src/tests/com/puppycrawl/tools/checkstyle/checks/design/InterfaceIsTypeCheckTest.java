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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class InterfaceIsTypeCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceIsTypeCheck.class);
        final String[] expected = {
            "25: interfaces should describe a type and hence have methods.",
        };
        verify(checkConfig, getPath("InputInterfaceIsType.java"), expected);
    }

    @Test
    public void testAllowMarker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceIsTypeCheck.class);
        checkConfig.addAttribute("allowMarkerInterfaces", "false");
        final String[] expected = {
            "20: interfaces should describe a type and hence have methods.",
            "25: interfaces should describe a type and hence have methods.",
        };
        verify(checkConfig, getPath("InputInterfaceIsType.java"), expected);
    }

}
