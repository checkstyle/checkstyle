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

import java.io.File;

public class IllegalThrowsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);

        String[] expected = {
            "9:51: Throwing 'RuntimeException' is not allowed.",
            "14:45: Throwing 'java.lang.RuntimeException' is not allowed.",
            "14:73: Throwing 'java.lang.Error' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException");

        String[] expected = {
            "5:33: Throwing 'NullPointerException' is not allowed.",
            "14:73: Throwing 'java.lang.Error' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with ignoredMethodNames attribute
     * @throws Exception
     */
    @Test
    public void testIgnoreMethodNames() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("ignoredMethodNames", "methodTwo");

        String[] expected = {
            "9:51: Throwing 'RuntimeException' is not allowed.",
            "18:35: Throwing 'Throwable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with both the attributes specified
     * @throws Exception
     */
    @Test
    public void testIllegalClassNamesWithIgnoreMethodNames() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException, Throwable");
        checkConfig.addAttribute("ignoredMethodNames", "methodTwo");

        String[] expected = {
            "5:33: Throwing 'NullPointerException' is not allowed.",
            "18:35: Throwing 'Throwable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }
}
