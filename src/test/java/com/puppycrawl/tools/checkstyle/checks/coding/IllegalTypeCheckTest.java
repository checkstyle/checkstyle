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
import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class IllegalTypeCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(IllegalTypeCheck.class);
    }

    @Test
    public void testDefaults() throws Exception
    {
        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception
    {
        mCheckConfig.addAttribute("ignoredMethodNames", "table2");

        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testFormat() throws Exception
    {
        mCheckConfig.addAttribute("format", "^$");

        String[] expected = {
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception
    {
        mCheckConfig.addAttribute("legalAbstractClassNames", "AbstractClass");

        String[] expected = {
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    @Test
    public void testSameFileNameFalsePositive() throws Exception
    {
        mCheckConfig.addAttribute("illegalClassNames", "java.util.GregorianCalendar, SubCalendar, "
                + "java.util.List");

        String[] expected = {
            "12:5: Declaring variables, return values or parameters of type 'SubCalendar' is not allowed.",
            "27:5: Declaring variables, return values or parameters of type 'java.util.List' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator
                + "InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testSameFileNameGeneral() throws Exception
    {
        mCheckConfig.addAttribute("illegalClassNames", "List, GregorianCalendar, java.io.File, ArrayList");
        String[] expected = {
            "10:5: Declaring variables, return values or parameters of type 'GregorianCalendar' is not allowed.",
            "16:23: Declaring variables, return values or parameters of type 'GregorianCalendar' is not allowed.",
            "24:9: Declaring variables, return values or parameters of type 'List' is not allowed.",
            "25:9: Declaring variables, return values or parameters of type 'java.io.File' is not allowed.",
            "27:5: Declaring variables, return values or parameters of type 'java.util.List' is not allowed.",
            "28:13: Declaring variables, return values or parameters of type 'ArrayList' is not allowed.",
        };
        verify(mCheckConfig, getPath("coding" + File.separator
                + "InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testStarImports() throws Exception
    {
        mCheckConfig.addAttribute("illegalClassNames", "List");

        String[] expected = {
            "10:5: Declaring variables, return values or parameters of type 'List' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator
                + "InputIllegalTypeStarImports.java"), expected);
    }

    @Test
    public void testStaticImports() throws Exception
    {
        mCheckConfig.addAttribute("illegalClassNames", "SomeStaticClass");
        mCheckConfig.addAttribute("ignoredMethodNames", "foo1");

        String[] expected = {
            "13:6: Declaring variables, return values or parameters of type 'SomeStaticClass' is not allowed.",
            "15:31: Declaring variables, return values or parameters of type 'SomeStaticClass' is not allowed.",
        };

        verify(mCheckConfig, getPath("coding" + File.separator
                + "InputIllegalTypeStaticImports.java"), expected);
    }
}
