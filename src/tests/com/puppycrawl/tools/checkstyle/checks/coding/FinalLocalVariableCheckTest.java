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
import org.junit.Test;

public class FinalLocalVariableCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);

        final String[] expected = {
            "9:13: Variable 'i' should be declared final.",
            "9:16: Variable 'j' should be declared final.",
            "10:18: Variable 'runnable' should be declared final.",
            "20:13: Variable 'i' should be declared final.",
            "24:13: Variable 'z' should be declared final.",
            "26:16: Variable 'obj' should be declared final.",
            "30:16: Variable 'x' should be declared final.",
            "36:18: Variable 'runnable' should be declared final.",
            "40:21: Variable 'q' should be declared final.",
            "56:13: Variable 'i' should be declared final.",
            "60:13: Variable 'z' should be declared final.",
            "62:16: Variable 'obj' should be declared final.",
            "66:16: Variable 'x' should be declared final.",
            "74:21: Variable 'w' should be declared final.",
            "75:26: Variable 'runnable' should be declared final.",
            "88:18: Variable 'i' should be declared final.",
            "96:17: Variable 'weird' should be declared final.",
            "97:17: Variable 'j' should be declared final.",
            "98:17: Variable 'k' should be declared final.",
        };
        verify(checkConfig, getPath("coding/InputFinalLocalVariable.java"), expected);
    }

    @Test
    public void testParameter() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalLocalVariableCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");

        final String[] expected = {
            "45:28: Variable 'aArg' should be declared final.",
            "149:36: Variable '_o' should be declared final.",
            "154:37: Variable '_o1' should be declared final.",
        };
        verify(checkConfig, getPath("coding/InputFinalLocalVariable.java"), expected);
    }
}
