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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ExecutableStatementCountCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testMaxZero() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "4:5: Executable statement count is 3 (max allowed is 0).",
            "7:17: Executable statement count is 1 (max allowed is 0).",
            "17:5: Executable statement count is 2 (max allowed is 0).",
            "27:5: Executable statement count is 1 (max allowed is 0).",
            "34:5: Executable statement count is 3 (max allowed is 0).",
            "48:5: Executable statement count is 2 (max allowed is 0).",
            "58:5: Executable statement count is 2 (max allowed is 0).",
            "67:5: Executable statement count is 2 (max allowed is 0).",
            "76:5: Executable statement count is 2 (max allowed is 0).",
            "79:13: Executable statement count is 1 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testMethodDef() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expected = {
            "4:5: Executable statement count is 3 (max allowed is 0).",
            "7:17: Executable statement count is 1 (max allowed is 0).",
            "17:5: Executable statement count is 2 (max allowed is 0).",
            "27:5: Executable statement count is 1 (max allowed is 0).",
            "34:5: Executable statement count is 3 (max allowed is 0).",
            "79:13: Executable statement count is 1 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testCtorDef() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "CTOR_DEF");

        final String[] expected = {
            "48:5: Executable statement count is 2 (max allowed is 0).",
            "76:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testStaticInit() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "STATIC_INIT");

        final String[] expected = {
            "58:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    @Test
    public void testInstanceInit() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "INSTANCE_INIT");

        final String[] expected = {
            "67:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
}
