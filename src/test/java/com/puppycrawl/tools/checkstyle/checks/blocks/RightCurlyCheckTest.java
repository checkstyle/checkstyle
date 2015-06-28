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

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_SAME;

public class RightCurlyCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(RightCurlyCheck.class);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "40:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "44:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
            "97:54: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testSame() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "40:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "44:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
            "97:54: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testAlone() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testNewLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF");
        final String[] expected = {
            "111:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "122:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "136:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testShouldStartLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testMethodCtorNamedClassClosingBrace() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testForceLineBreakBefore() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "LITERAL_FOR,"
                + "LITERAL_WHILE, LITERAL_DO, STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
            "35:43: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "41:71: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "47:25: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
        };
        verify(checkConfig, getPath("InputRightCurlyLineBreakBefore.java"), expected);
    }

    @Test
    public void testForceLineBreakBefore2() throws Exception {
        final String[] expected = {
            "24:33: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
            "32:44: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
            "32:63: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
            "52:56: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}"),
        };
        verify(checkConfig, getPath("InputRightCurlyLineBreakBefore.java"), expected);
    }

    @Test
    public void testNPE() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputRightCurlyEmptyAbstractMethod.java"), expected);
    }

    @Test
    public void testWithAnnotations() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
            "9:57: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "16:41: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
        };
        verify(checkConfig, getPath("InputRightCurlyAnnotations.java"), expected);
    }
}
