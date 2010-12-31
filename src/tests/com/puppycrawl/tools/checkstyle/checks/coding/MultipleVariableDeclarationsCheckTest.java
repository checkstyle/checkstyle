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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

import java.io.File;

public class MultipleVariableDeclarationsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expected = {
            "3:5: Each variable declaration must be in its own statement.",
            "4:5: Only one variable definition per line allowed.",
            "7:9: Each variable declaration must be in its own statement.",
            "8:9: Only one variable definition per line allowed.",
            "12:5: Only one variable definition per line allowed.",
            "15:5: Only one variable definition per line allowed.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleVariableDeclarations.java"),
               expected);
    }
}
