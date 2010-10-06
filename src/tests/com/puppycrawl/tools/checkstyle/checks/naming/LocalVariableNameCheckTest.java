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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class LocalVariableNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final String[] expected = {
            "119:13: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "130:18: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "132:20: Name 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "207:21: Name 'O' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testCatchParameter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");
        checkConfig.addAttribute("format", "^e$");
        final String[] expected = {
            "74:24: Name 'ex' must match pattern '^e$'.",
        };
        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }
}

