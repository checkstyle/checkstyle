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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.LINE_PREVIOUS;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SeparatorWrapCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(SeparatorWrapCheck.class);
    }

    @Test
    public void testDot()
        throws Exception {
        checkConfig.addAttribute("option", "NL");
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "31:10: " + getCheckMessage(LINE_NEW, "."),
        };
        verify(checkConfig, getPath("whitespace/InputSeparatorWrap.java"), expected);
    }

    @Test
    public void testComma() throws Exception {
        checkConfig.addAttribute("option", "EOL");
        checkConfig.addAttribute("tokens", "COMMA");
        final String[] expected = {
            "39:17: " + getCheckMessage(LINE_PREVIOUS, ","),
        };
        verify(checkConfig, getPath("whitespace/InputSeparatorWrap.java"), expected);
    }

    @Test
    public void testGetDefaultTokens() {
        SeparatorWrapCheck separatorWrapCheckObj = new SeparatorWrapCheck();
        int[] actual = separatorWrapCheckObj.getDefaultTokens();
        int[] expected = {
            TokenTypes.DOT,
            TokenTypes.COMMA,
        };
        Assert.assertArrayEquals(expected, actual);
    }
}
