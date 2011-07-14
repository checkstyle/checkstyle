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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class MethodNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);
        final String[] expected = {
            "137:10: Name 'ALL_UPPERCASE_METHOD' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String[] expected = {
            "12:16: Method Name 'InputMethNameEqualClsName' must not equal the enclosing class name.",
            "12:16: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "17:17: Name 'PRIVATEInputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:20: Method Name 'Inner' must not equal the enclosing class name.",
            "23:20: Name 'Inner' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "28:20: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "37:24: Method Name 'InputMethNameEqualClsName' must not equal the enclosing class name.",
            "37:24: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:9: Method Name 'SweetInterface' must not equal the enclosing class name.",
            "47:9: Name 'SweetInterface' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "53:17: Method Name 'Outter' must not equal the enclosing class name.",
            "53:17: Name 'Outter' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);
        checkConfig.addAttribute("allowClassName", "true"); //allow method names and class names to equal

        final String[] expected = {
            "12:16: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "17:17: Name 'PRIVATEInputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:20: Name 'Inner' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "28:20: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "37:24: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:9: Name 'SweetInterface' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "53:17: Name 'Outter' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testAccessTuning() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);
        checkConfig.addAttribute("allowClassName", "true"); //allow method names and class names to equal
        checkConfig.addAttribute("applyToPrivate", "false"); //allow method names and class names to equal
        final String[] expected = {
            "12:16: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "23:20: Name 'Inner' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "28:20: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "37:24: Name 'InputMethNameEqualClsName' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "47:9: Name 'SweetInterface' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "53:17: Name 'Outter' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        verify(checkConfig, getPath("InputMethNameEqualClsName.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodNameCheck.class);

        final String[] expected = {
        };

        verify(checkConfig, getPath("naming/InputMethodNameExtra.java"), expected);
    }
}
