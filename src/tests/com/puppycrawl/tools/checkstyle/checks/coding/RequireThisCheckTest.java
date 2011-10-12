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
import java.io.File;
import org.junit.Test;

public class RequireThisCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        final String[] expected = {
            "8:9: Reference to instance variable 'i' needs \"this.\".",
            "14:9: Method call to 'method1' needs \"this.\".",
            "28:9: Reference to instance variable 'i' needs \"this.\".",
            "53:9: Reference to instance variable 'z' needs \"this.\".",
//              "13:9: Unable find where 'j' is declared.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }

    @Test
    public void testMethodsOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkFields", "false");
        final String[] expected = {
            "14:9: Method call to 'method1' needs \"this.\".",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }

    @Test
    public void testFieldsOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkMethods", "false");
        final String[] expected = {
            "8:9: Reference to instance variable 'i' needs \"this.\".",
            "28:9: Reference to instance variable 'i' needs \"this.\".",
            "53:9: Reference to instance variable 'z' needs \"this.\".",
//              "13:9: Unable find where 'j' is declared.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }

    @Test
    public void testGenerics() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }
}
