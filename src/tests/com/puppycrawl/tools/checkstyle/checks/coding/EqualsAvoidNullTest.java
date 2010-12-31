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
import java.io.File;
import org.junit.Test;

public class EqualsAvoidNullTest extends BaseCheckTestSupport
{
    @Test
    public void testEqualsWithDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsAvoidNullCheck.class);

        final String[] expected = {
            "18:17: String literal expressions should be on the left side of an equals comparison.",
            "20:17: String literal expressions should be on the left side of an equals comparison.",
            "22:17: String literal expressions should be on the left side of an equals comparison.",
            "24:17: String literal expressions should be on the left side of an equals comparison.",
            "26:17: String literal expressions should be on the left side of an equals comparison.",
            "28:17: String literal expressions should be on the left side of an equals comparison.",
            "37:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "39:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "41:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "43:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "45:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "47:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "57:17: String literal expressions should be on the left side of an equals comparison.",
            "59:17: String literal expressions should be on the left side of an equals comparison.",
            "61:17: String literal expressions should be on the left side of an equals comparison.",
            "63:17: String literal expressions should be on the left side of an equals comparison.",
            "65:17: String literal expressions should be on the left side of an equals comparison.",
            "67:17: String literal expressions should be on the left side of an equals comparison.",
            "69:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "71:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "73:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "75:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "77:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison.",
            "79:27: String literal expressions should be on the left side of an equalsIgnoreCase comparison."
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EqualsAvoidNullCheck.class);
        checkConfig.addAttribute("performEqualsIgnoreCaseCheck", "false");

        final String[] expected = {
            "18:17: String literal expressions should be on the left side of an equals comparison.",
            "20:17: String literal expressions should be on the left side of an equals comparison.",
            "22:17: String literal expressions should be on the left side of an equals comparison.",
            "24:17: String literal expressions should be on the left side of an equals comparison.",
            "26:17: String literal expressions should be on the left side of an equals comparison.",
            "28:17: String literal expressions should be on the left side of an equals comparison.",
            "57:17: String literal expressions should be on the left side of an equals comparison.",
            "59:17: String literal expressions should be on the left side of an equals comparison.",
            "61:17: String literal expressions should be on the left side of an equals comparison.",
            "63:17: String literal expressions should be on the left side of an equals comparison.",
            "65:17: String literal expressions should be on the left side of an equals comparison.",
            "67:17: String literal expressions should be on the left side of an equals comparison.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputEqualsAvoidNull.java"), expected);
    }
}
