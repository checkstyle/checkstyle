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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class JUnitTestCaseCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(JUnitTestCaseCheck.class);

        String[] expected = {
            "14:5: The method 'setUp' must be public or protected.",
            "15:5: The method 'tearDown' shouldn't be static.",
            "16:5: The method 'suite' must be declared static.",
            "21:5: The method 'SetUp' should be named 'setUp'.",
            "22:5: The method 'tearDown' must be declared with a void return type.",
            "23:5: The method 'suite' must be declared with a junit.framework.Test return type.",
            "28:5: The method 'setUp' must be declared with no parameters.",
            "30:5: The method 'suite' must be declared static.",
            "31:5: The method 'tearDown' must be declared with no parameters.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputJUnitTest.java"), expected);
    }
}
