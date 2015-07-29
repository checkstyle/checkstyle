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

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck.MSG_KEY_LINE_SAME;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

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
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testNewLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF");
        final String[] expected = {
            "111:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "111:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "122:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
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
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, "
            + "LITERAL_ELSE, CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, "
            + "STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
            "9:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "12:65: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "23:46: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "27:31: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "30:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "33:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "39:73: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "41:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "46:58: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "48:97: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "51:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "54:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "61:38: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "68:62: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "77:28: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "79:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "81:20: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "83:14: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "94:26: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "104:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "108:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "112:52: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "112:112: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "115:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "119:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "122:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "124:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "128:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "137:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "139:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "149:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "151:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "152:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "152:93: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "153:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "154:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "154:93: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "160:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "167:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "182:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "189:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "189:13: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "198:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "198:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "202:49: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "202:50: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "205:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "205:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "205:77: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "209:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "217:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),

        };
        verify(checkConfig, getPath("InputRightCurlyAnnotations.java"), expected);
    }

    @Test
    public void testAloneOrSingleLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE_OR_SINGLELINE.toString());
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, "
            + "LITERAL_ELSE, CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, "
            + "STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
            "60:26: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "69:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "74:52: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "77:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "85:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "97:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "99:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "119:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "126:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "148:13: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "157:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "157:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "161:49: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "161:50: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "164:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "164:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
            "164:77: " + getCheckMessage(MSG_KEY_LINE_NEW, "}"),
            "176:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),

        };
        verify(checkConfig, getPath("InputRightCurlyAloneOrSingleline.java"), expected);
    }

    @Test
    public void testCatchWithoutFinally() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}"),
        };
        verify(checkConfig, getPath("InputRightCurly.java"), expected);
    }

    @Test
    public void testSingleLineClass() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        final String[] expected = {
            "24:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}"),
        };
        verify(checkConfig, getPath("InputRightCurly.java"), expected);
    }
}
