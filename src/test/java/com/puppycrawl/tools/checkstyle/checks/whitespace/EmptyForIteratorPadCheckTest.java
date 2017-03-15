////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.MSG_WS_NOT_FOLLOWED;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class EmptyForIteratorPadCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(EmptyForIteratorPadCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + "whitespace" + File.separator
            + "emptyforiteratorpad" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyForIteratorPadCheck checkObj = new EmptyForIteratorPadCheck();
        final int[] expected = {TokenTypes.FOR_ITERATOR};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "27:31: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
            "43:32: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
            "55:11: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputEmptyForIteratorPad.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "23:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputEmptyForIteratorPad.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyForIteratorPadCheck emptyForIteratorPadCheckObj = new EmptyForIteratorPadCheck();
        final int[] actual = emptyForIteratorPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.FOR_ITERATOR,
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testInvalidOption() throws Exception {
        checkConfig.addAttribute("option", "invalid_option");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputEmptyForIteratorPad.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'option' to 'invalid_option' in module"));
        }
    }
}
