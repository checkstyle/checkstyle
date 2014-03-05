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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class EmptyForIteratorPadCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(EmptyForIteratorPadCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "27:31: ';' is followed by whitespace.",
            "43:32: ';' is followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception
    {
        mCheckConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "23:31: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }
}
