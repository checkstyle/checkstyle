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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

public class ThrowsCountCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);

        String[] expected = {
            "14:20: " + getCheckMessage(MSG_KEY, 2, 1),
            "18:20: " + getCheckMessage(MSG_KEY, 2, 1),
            "22:20: " + getCheckMessage(MSG_KEY, 3, 1),
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }

    @Test
    public void testMax() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("max", "2");

        String[] expected = {
            "22:20: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens()
    {
        ThrowsCountCheck obj = new ThrowsCountCheck();
        int[] expected = {TokenTypes.LITERAL_THROWS};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens()
    {
        ThrowsCountCheck obj = new ThrowsCountCheck();
        int[] expected = {TokenTypes.LITERAL_THROWS};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }
}
