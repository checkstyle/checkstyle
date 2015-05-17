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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck.MSG_KEY;

public class NoWhitespaceAfterCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(NoWhitespaceAfterCheck.class);
    }

    @Test
    public void testDefault() throws Exception {
        checkConfig.addAttribute("allowLineBreaks", "false");
        final String[] expected = {
            "5:14: " + getCheckMessage(MSG_KEY, "."),
            "6:12: " + getCheckMessage(MSG_KEY, "."),
            "29:14: " + getCheckMessage(MSG_KEY, "-"),
            "29:21: " + getCheckMessage(MSG_KEY, "+"),
            "31:15: " + getCheckMessage(MSG_KEY, "++"),
            "31:22: " + getCheckMessage(MSG_KEY, "--"),
            "111:22: " + getCheckMessage(MSG_KEY, "!"),
            "112:23: " + getCheckMessage(MSG_KEY, "~"),
            "129:24: " + getCheckMessage(MSG_KEY, "."),
            "132:11: " + getCheckMessage(MSG_KEY, "."),
            "136:12: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:14: " + getCheckMessage(MSG_KEY, "."),
            "129:24: " + getCheckMessage(MSG_KEY, "."),
            "136:12: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        checkConfig.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "87:28: " + getCheckMessage(MSG_KEY, ")"),
            "89:23: " + getCheckMessage(MSG_KEY, ")"),
            "241:22: " + getCheckMessage(MSG_KEY, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        final String[] expected = {
            "6:11: " + getCheckMessage(MSG_KEY, "Object"),
            "8:22: " + getCheckMessage(MSG_KEY, "someStuff3"),
            "9:8: " + getCheckMessage(MSG_KEY, "int"),
            "10:13: " + getCheckMessage(MSG_KEY, "s"),
            "11:13: " + getCheckMessage(MSG_KEY, "d"),
            "16:14: " + getCheckMessage(MSG_KEY, "get"),
            "18:8: " + getCheckMessage(MSG_KEY, "int"),
            "19:34: " + getCheckMessage(MSG_KEY, "get1"),
            "28:8: " + getCheckMessage(MSG_KEY, "int"),
            "29:12: " + getCheckMessage(MSG_KEY, "cba"),
            "31:26: " + getCheckMessage(MSG_KEY, "String"),
            "32:27: " + getCheckMessage(MSG_KEY, "String"),
            "39:11: " + getCheckMessage(MSG_KEY, "ar"),
            "39:24: " + getCheckMessage(MSG_KEY, "int"),
            "40:16: " + getCheckMessage(MSG_KEY, "int"),
            "43:63: " + getCheckMessage(MSG_KEY, "getLongMultArray"),
        };
        verify(checkConfig, getPath("whitespace/InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        final String[] expected = {

        };
        verify(checkConfig, getPath("whitespace/InputNoWhiteSpaceAfterCheckFormerNpe.java"),
                 expected);
    }
}
