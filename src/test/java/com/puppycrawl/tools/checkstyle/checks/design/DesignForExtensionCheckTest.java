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

import static com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class DesignForExtensionCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DesignForExtensionCheck.class);
        final String[] expected = {
            "46:5: " + getCheckMessage(MSG_KEY, "doh"),
            "54:5: " + getCheckMessage(MSG_KEY, "aNativeMethod"),
            "105:9: " + getCheckMessage(MSG_KEY, "someMethod"),
        };
        verify(checkConfig, getPath("InputDesignForExtension.java"), expected);

    }

    @Test
    public void testGetAcceptableTokens() {
        DesignForExtensionCheck obj = new DesignForExtensionCheck();
        int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }
}
