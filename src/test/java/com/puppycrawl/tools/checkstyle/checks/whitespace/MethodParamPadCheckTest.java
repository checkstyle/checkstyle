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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_LINE_PREVIOUS;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_WS_PRECEDED;
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

public class MethodParamPadCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(MethodParamPadCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator
                + "methodparampad" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodParamPadCheck checkObj = new MethodParamPadCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "11:32: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "13:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "17:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "20:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "27:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "32:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "36:39: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "38:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "42:16: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "44:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "50:21: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "52:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "56:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "58:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "61:36: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "63:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "74:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "79:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
        };
        verify(checkConfig, getPath("InputMethodParamPad.java"), expected);
    }

    @Test
    public void testAllowLineBreaks() throws Exception {
        checkConfig.addAttribute("allowLineBreaks", "true");
        final String[] expected = {
            "11:32: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "13:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "27:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "36:39: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "42:16: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "50:21: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "56:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "61:36: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "74:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verify(checkConfig, getPath("InputMethodParamPad.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        checkConfig.addAttribute("option", "space");
        final String[] expected = {
            "6:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "8:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "17:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "20:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "23:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "32:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "35:58: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "38:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "41:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "44:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "47:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "49:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "52:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "54:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "55:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "58:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "60:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "63:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "66:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "69:66: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "70:57: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "79:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
        };
        verify(checkConfig, getPath("InputMethodParamPad.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMethodParamPadWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodParamPadCheck methodParamPadCheckObj = new MethodParamPadCheck();
        final int[] actual = methodParamPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testInvalidOption() throws Exception {
        checkConfig.addAttribute("option", "invalid_option");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputMethodParamPad.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'option' to 'invalid_option' in module"));
        }
    }
}
