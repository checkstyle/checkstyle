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
package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RegexpMultilineCheckTest extends BaseFileSetCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RegexpMultilineCheck.class);
    }

    @Test
    public void testIt() throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
        throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "false");
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception
    {
        final String illegal = "^import";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "7: Line matches the illegal pattern '" + illegal + "'.",
            "8: Line matches the illegal pattern '" + illegal + "'.",
            "9: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    // Need to fix the line endings in the input file
    @Ignore @Test
    public void testCarriageReturn() throws Exception
    {
        final String illegal = "\\r";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "14: Line matches the illegal pattern '" + illegal + "'.",
            "16: Line matches the illegal pattern '" + illegal + "'.",
            "19: Line matches the illegal pattern '" + illegal + "'.",
            "21: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputLineBreaks.java"), expected);
    }

}
