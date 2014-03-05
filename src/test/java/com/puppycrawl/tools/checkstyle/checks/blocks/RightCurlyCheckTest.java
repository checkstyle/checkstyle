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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RightCurlyCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RightCurlyCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testSame() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testAlone() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        final String[] expected = {
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testShouldStartLine() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        mCheckConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: '}' should be alone on a line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }
}
