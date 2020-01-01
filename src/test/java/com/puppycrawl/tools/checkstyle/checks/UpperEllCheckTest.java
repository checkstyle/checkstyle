////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.UpperEllCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UpperEllCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/upperell";
    }

    @Test
    public void testGetRequiredTokens() {
        final UpperEllCheck checkObj = new UpperEllCheck();
        final int[] expected = {TokenTypes.NUM_LONG};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testWithChecker()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UpperEllCheck.class);
        final String[] expected = {
            "94:40: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUpperEllSemantic.java"), expected);
    }

    @Test
    public void testAcceptableTokens() {
        final int[] expected = {TokenTypes.NUM_LONG };
        final UpperEllCheck check = new UpperEllCheck();
        final int[] actual = check.getAcceptableTokens();
        assertEquals(1, actual.length, "Invalid size of tokens");
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}
