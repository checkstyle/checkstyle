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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Rob Worth
 * @author Lars Kühne
 */
public class AnonInnerLengthCheckTest extends BaseCheckTestSupport {
    @Test
    public void testGetAcceptableTokens() {
        AnonInnerLengthCheck anonInnerLengthCheckObj =
                new AnonInnerLengthCheck();
        int[] actual = anonInnerLengthCheckObj.getAcceptableTokens();
        int[] expected = new int[]{TokenTypes.LITERAL_NEW};

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AnonInnerLengthCheck.class);
        final String[] expected = {
            "50:35: " + getCheckMessage(MSG_KEY, 21, 20),
        };
        verify(checkConfig, getPath("InputAnonInnerLength.java"), expected);
    }

    @Test
    public void testNonDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AnonInnerLengthCheck.class);
        checkConfig.addAttribute("max", "6");
        final String[] expected = {
            "50:35: " + getCheckMessage(MSG_KEY, 21, 6),
            "75:35: " + getCheckMessage(MSG_KEY, 20, 6),
        };
        verify(checkConfig, getPath("InputAnonInnerLength.java"), expected);
    }
}
