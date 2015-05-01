////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck.MSG_KEY;

public class IllegalThrowsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);

        String[] expected = {
            "9:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "14:45: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "14:73: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
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
            "5:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "14:73: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
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
            "9:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "18:35: " + getCheckMessage(MSG_KEY, "Throwable"),
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
            "5:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "18:35: " + getCheckMessage(MSG_KEY, "Throwable"),
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with <b>ignoreOverriddenMethods</b>
     * property.
     * @throws Exception
     */
    @Test
    public void testIgnoreOverriddenMethods() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);

        String[] expected = {

        };

        verify(checkConfig, getPath("coding" + File.separator
                + "InputIllegalThrowsCheckIgnoreOverriddenMethods.java"), expected);
    }
}
