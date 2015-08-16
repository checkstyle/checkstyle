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

package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ArrayTypeStyleCheckTest
    extends BaseCheckTestSupport {

    @Test
    public void testGetRequiredTokens() {
        ArrayTypeStyleCheck checkObj = new ArrayTypeStyleCheck();
        int[] expected = new int[] {TokenTypes.ARRAY_DECLARATOR};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testJavaStyle()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ArrayTypeStyleCheck.class);
        final String[] expected = {
            "14:23: Array brackets at illegal position.",
            "20:44: Array brackets at illegal position.",
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    @Test
    public void testCStyle()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ArrayTypeStyleCheck.class);
        checkConfig.addAttribute("javaStyle", "false");
        final String[] expected = {
            "13:16: Array brackets at illegal position.",
            "16:39: Array brackets at illegal position.",
            "22:18: Array brackets at illegal position.",
            "30:20: Array brackets at illegal position.",
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    @Test
    public void testGetAcceptableTockens() {
        int[] expected = {TokenTypes.ARRAY_DECLARATOR };
        ArrayTypeStyleCheck check = new ArrayTypeStyleCheck();
        int[] actual = check.getAcceptableTokens();
        assertTrue(actual.length == 1);
        assertArrayEquals(expected, actual);
    }
}
