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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.WS_NOT_PRECEDED;

public class WhitespaceAroundTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(WhitespaceAroundCheck.class);
    }

    @Test
    public void testIt()
        throws Exception
    {
        final String[] expected = {
            "16:22: " + getCheckMessage(WS_NOT_PRECEDED, "="),
            "16:23: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "18:24: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "26:14: " + getCheckMessage(WS_NOT_PRECEDED, "="),
            "27:10: " + getCheckMessage(WS_NOT_PRECEDED, "="),
            "27:11: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "28:10: " + getCheckMessage(WS_NOT_PRECEDED, "+="),
            "28:12: " + getCheckMessage(WS_NOT_FOLLOWED, "+="),
            "29:13: " + getCheckMessage(WS_NOT_FOLLOWED, "-="),
            "37:21: " + getCheckMessage(WS_NOT_FOLLOWED, "synchronized"),
            "39:12: " + getCheckMessage(WS_NOT_FOLLOWED, "try"),
            "39:12: " + getCheckMessage(WS_NOT_PRECEDED, "{"),
            "41:14: " + getCheckMessage(WS_NOT_FOLLOWED, "catch"),
            "41:34: " + getCheckMessage(WS_NOT_PRECEDED, "{"),
            "58:11: " + getCheckMessage(WS_NOT_FOLLOWED, "if"),
            "76:19: " + getCheckMessage(WS_NOT_FOLLOWED, "return"),
            "97:29: " + getCheckMessage(WS_NOT_PRECEDED, "?"),
            "97:30: " + getCheckMessage(WS_NOT_FOLLOWED, "?"),
            "97:34: " + getCheckMessage(WS_NOT_PRECEDED, ":"),
            "97:35: " + getCheckMessage(WS_NOT_FOLLOWED, ":"),
            "98:15: " + getCheckMessage(WS_NOT_PRECEDED, "=="),
            "98:17: " + getCheckMessage(WS_NOT_FOLLOWED, "=="),
            "104:20: " + getCheckMessage(WS_NOT_FOLLOWED, "*"),
            "104:21: " + getCheckMessage(WS_NOT_PRECEDED, "*"),
            "119:18: " + getCheckMessage(WS_NOT_PRECEDED, "%"),
            "120:20: " + getCheckMessage(WS_NOT_FOLLOWED, "%"),
            "121:18: " + getCheckMessage(WS_NOT_PRECEDED, "%"),
            "121:19: " + getCheckMessage(WS_NOT_FOLLOWED, "%"),
            "123:18: " + getCheckMessage(WS_NOT_PRECEDED, "/"),
            "124:20: " + getCheckMessage(WS_NOT_FOLLOWED, "/"),
            "125:18: " + getCheckMessage(WS_NOT_PRECEDED, "/"),
            "125:19: " + getCheckMessage(WS_NOT_FOLLOWED, "/"),
            "153:15: " + getCheckMessage(WS_NOT_FOLLOWED, "assert"),
            "156:20: " + getCheckMessage(WS_NOT_PRECEDED, ":"),
            "156:21: " + getCheckMessage(WS_NOT_FOLLOWED, ":"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testIt2()
        throws Exception
    {
        final String[] expected = {
            "153:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "154:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "155:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "156:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "157:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
            "158:27: " + getCheckMessage(WS_NOT_FOLLOWED, "="),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testIt3()
        throws Exception
    {
        final String[] expected = {
            "41:14: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
            "58:12: " + getCheckMessage(WS_NOT_FOLLOWED, "for"),
            // + ":58:23: ';' is not followed by whitespace.",
            //  + ":58:29: ';' is not followed by whitespace.",
            "115:27: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "115:27: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "118:40: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "118:40: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testIt4()
        throws Exception
    {
        checkConfig.addAttribute("allowEmptyMethods", "true");
        checkConfig.addAttribute("allowEmptyConstructors", "true");
        final String[] expected = {
            "41:14: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
            "58:12: " + getCheckMessage(WS_NOT_FOLLOWED, "for"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
        throws Exception
    {
        final String[] expected = {
            "6:67: " + getCheckMessage(WS_NOT_PRECEDED, "&"),
            "6:68: " + getCheckMessage(WS_NOT_FOLLOWED, "&"),
        };
        verify(checkConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879And1649038() throws Exception
    {
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception
    {
        checkConfig.addAttribute("ignoreEnhancedForColon", "false");
        final String[] expected = {
            "19:20: " + getCheckMessage(WS_NOT_PRECEDED, ":"),
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception
    {
        checkConfig.addAttribute("allowEmptyTypes", "true");
        final String[] expected = {
            "29:95: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "29:95: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "30:33: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "30:33: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "31:21: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "31:21: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("whitespace/InputEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception
    {
        checkConfig.addAttribute("allowEmptyLoops", "true");
        final String[] expected = {
            "40:65: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "40:65: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "42:17: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "42:17: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "44:20: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "44:20: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "50:44: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "50:44: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "60:18: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "60:18: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("whitespace/InputEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception
    {
        final String[] expected = {
            "6:15: " + getCheckMessage(WS_NOT_FOLLOWED, "switch"),
        };
        verify(checkConfig,
               getPath("whitespace/InputSwitchWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testDoWhileWhitespaceAround() throws Exception
    {
        final String[] expected = {
            "9:16: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
        };
        verify(checkConfig,
               getPath("whitespace/InputDoWhileWhitespaceAround.java"),
               expected);
    }

    @Test
    public void allowEmptyMethods() throws Exception
    {
        checkConfig.addAttribute("allowEmptyMethods", "true");
        final String[] expected = {};
        verify(checkConfig,
               getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}
