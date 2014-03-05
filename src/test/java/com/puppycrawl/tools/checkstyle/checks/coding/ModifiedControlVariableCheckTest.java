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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ModifiedControlVariableCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testModifiedControlVariable() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifiedControlVariableCheck.class);
        final String[] expected = {
            "14:14: Control variable 'i' is modified.",
            "17:15: Control variable 'i' is modified.",
            "20:37: Control variable 'i' is modified.",
            "21:17: Control variable 'i' is modified.",
            "25:14: Control variable 'j' is modified.",
            "49:15: Control variable 's' is modified.",
        };
        verify(checkConfig, getPath("coding/InputModifiedControl.java"), expected);
    }
}
