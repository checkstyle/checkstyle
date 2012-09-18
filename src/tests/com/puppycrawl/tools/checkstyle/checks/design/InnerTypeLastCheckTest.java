////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import java.io.File;
import org.junit.Test;

public class InnerTypeLastCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testMembersBeforeInner() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerTypeLastCheck.class);
        final String[] expected = {
            "15:17: Fields and methods should be before inner classes.",
            "25:17: Fields and methods should be before inner classes.",
            "26:17: Fields and methods should be before inner classes.",
            "39:25: Fields and methods should be before inner classes.",
            "40:25: Fields and methods should be before inner classes.",
            "44:9: Fields and methods should be before inner classes.",
            "60:25: Fields and methods should be before inner classes.",
            "61:25: Fields and methods should be before inner classes.",
            "65:9: Fields and methods should be before inner classes.",
            "69:9: Fields and methods should be before inner classes.",
            "78:5: Fields and methods should be before inner classes.",
        };
        verify(checkConfig, getPath("design" + File.separator
                                    + "InputInnerClassCheck.java"), expected);
    }
}
