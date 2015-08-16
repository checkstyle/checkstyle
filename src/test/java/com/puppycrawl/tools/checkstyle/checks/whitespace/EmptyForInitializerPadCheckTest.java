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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck.MSG_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck.MSG_PRECEDED;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyForInitializerPadCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(EmptyForInitializerPadCheck.class);
    }

    @Test
    public void testGetRequiredTokens() {
        EmptyForInitializerPadCheck checkObj = new EmptyForInitializerPadCheck();
        int[] expected = new int[] {TokenTypes.FOR_INIT};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "48:14: " + getCheckMessage(MSG_PRECEDED, ";"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "51:13: " + getCheckMessage(MSG_NOT_PRECEDED, ";"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        EmptyForInitializerPadCheck emptyForInitializerPadCheckObj = new EmptyForInitializerPadCheck();
        int[] actual = emptyForInitializerPadCheckObj.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.FOR_INIT,
        };
        Assert.assertNotNull(actual);
        assertArrayEquals(expected, actual);
    }
}
