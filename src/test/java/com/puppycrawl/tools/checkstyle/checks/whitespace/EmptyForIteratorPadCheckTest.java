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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.WS_NOT_FOLLOWED;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyForIteratorPadCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(EmptyForIteratorPadCheck.class);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "27:31: " + getCheckMessage(WS_FOLLOWED, ";"),
            "43:32: " + getCheckMessage(WS_FOLLOWED, ";"),
            "55:11: " + getCheckMessage(WS_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "23:31: " + getCheckMessage(WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        EmptyForIteratorPadCheck emptyForIteratorPadCheckObj = new EmptyForIteratorPadCheck();
        int[] actual = emptyForIteratorPadCheckObj.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.FOR_ITERATOR,
        };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }
}
