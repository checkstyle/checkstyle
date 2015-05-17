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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_BREAK_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;

public class LeftCurlyCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(LeftCurlyCheck.class);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "30:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testNL() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "49:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "53:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "58:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "62:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "67:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "72:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testNLOW() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NLOW.toString());
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "30:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "49:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "53:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "58:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "62:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "67:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "72:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testDefault2() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "17:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "24:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "31:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "39:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "41:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "46:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "53:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "69:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "77:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "84:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testNL2() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "14:39: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "21:20: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "34:31: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "43:24: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "56:35: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "60:24: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "74:20: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "87:31: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testDefault3() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "15:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "19:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "21:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "23:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "30:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "34:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "42:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "46:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "52:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "54:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "63:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "76:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "83:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "89:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "97:19: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "106:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "109:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "118:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "120:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "129:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "131:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "133:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "148:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "157:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "164:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testNL3() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "26:33: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "91:19: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "97:19: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "142:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "158:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "165:16: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testMissingBraces() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "15:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "34:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "51:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "69:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "105:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testDefaultWithAnnotations() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "14:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyAnnotations.java"), expected);
    }

    @Test
    public void testNLWithAnnotations() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "35:34: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "38:41: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "44:27: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
            "58:32: " + getCheckMessage(MSG_KEY_LINE_NEW, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyAnnotations.java"), expected);
    }

    @Test
    public void testLineBreakAfter() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.EOL.toString());
        checkConfig.addAttribute("maxLineLength", "100");
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "16:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "18:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "20:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "26:22: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "35:33: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "36:21: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "39:29: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "39:34: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "45:37: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "51:12: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "54:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
            "56:27: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{"),
            "66:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{"),
        };
        verify(checkConfig, getPath("InputLeftCurlyLineBreakAfter.java"), expected);
    }
}
