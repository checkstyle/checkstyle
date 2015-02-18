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
package com.puppycrawl.tools.checkstyle.checks.naming;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TypeParameterNameTest
    extends BaseCheckTestSupport
{
    @Test
    public void testClassDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        final String[] expected = {
            "5:38: Name 't' must match pattern '^[A-Z]$'.",
            "13:14: Name 'foo' must match pattern '^[A-Z]$'.",
            "27:24: Name 'foo' must match pattern '^[A-Z]$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);
        final String[] expected = {
            "7:13: Name 'TT' must match pattern '^[A-Z]$'.",
            "9:6: Name 'e_e' must match pattern '^[A-Z]$'.",
            "19:6: Name 'Tfo$o2T' must match pattern '^[A-Z]$'.",
            "23:6: Name 'foo' must match pattern '^[A-Z]$'.",
            "28:10: Name '_fo' must match pattern '^[A-Z]$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testInterfaceDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceTypeParameterNameCheck.class);
        final String[] expected = {
            "48:15: Name 'Input' must match pattern '^[A-Z]$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String[] expected = {
            "5:38: Name 't' must match pattern '^foo$'.",
            "33:18: Name 'T' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodFooName()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String[] expected = {
            "7:13: Name 'TT' must match pattern '^foo$'.",
            "9:6: Name 'e_e' must match pattern '^foo$'.",
            "19:6: Name 'Tfo$o2T' must match pattern '^foo$'.",
            "28:10: Name '_fo' must match pattern '^foo$'.",
            "35:6: Name 'E' must match pattern '^foo$'.",
            "37:14: Name 'T' must match pattern '^foo$'.",
            //"40:14: Name 'EE' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }

    @Test
    public void testInterfaceFooName()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InterfaceTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String[] expected = {
            "48:15: Name 'Input' must match pattern '^foo$'.",
            "52:24: Name 'T' must match pattern '^foo$'.",
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputTypeParameterName.java"), expected);
    }
}
