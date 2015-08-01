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
            "25:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "40:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 13),
            "44:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 13),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
            "97:54: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 54),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testSame() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "40:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 13),
            "44:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 13),
            "93:27: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
            "97:54: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 54),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testAlone() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testNewLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF, METHOD_DEF, CTOR_DEF");
        checkConfig.addAttribute("shouldStartLine", "true");
        final String[] expected = {
            "111:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "111:6: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 6),
            "122:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "122:6: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 6),
            "136:6: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 6),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testShouldStartLine() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testMethodCtorNamedClassClosingBrace() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testForceLineBreakBefore() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "LITERAL_FOR,"
                + "LITERAL_WHILE, LITERAL_DO, STATIC_INIT, INSTANCE_INIT");
        final String[] expected = {
            "35:43: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 43),
            "41:71: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 71),
            "47:25: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 25),
        };
        verify(checkConfig, getPath("InputRightCurlyLineBreakBefore.java"), expected);
    }

    @Test
    public void testForceLineBreakBefore2() throws Exception {
        final String[] expected = {
            "24:33: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 33),
            "32:44: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 44),
            "32:63: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 63),
            "52:48: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 48),
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
            "9:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 77),
            "12:65: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 65),
            "23:46: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 46),
            "27:31: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 31),
            "30:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 35),
            "33:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 36),
            "39:73: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 73),
            "41:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 37),
            "46:58: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 58),
            "48:97: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 97),
            "51:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "54:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "61:38: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 38),
            "68:62: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 62),
            "77:28: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 28),
            "79:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "81:20: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 20),
            "83:14: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 14),
            "94:26: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 26),
            "104:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "108:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "112:52: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 52),
            "112:112: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 112),
            "115:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 18),
            "119:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 23),
            "122:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 37),
            "124:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "128:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 77),
            "137:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "139:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "149:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "151:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 75),
            "152:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 77),
            "152:93: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 93),
            "153:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 77),
            "154:77: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 77),
            "154:93: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 93),
            "160:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 37),
            "167:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 37),
            "182:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "189:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "189:13: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 13),
            "198:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "198:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 10),
            "202:49: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 49),
            "202:50: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 50),
            "205:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 75),
            "205:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 76),
            "205:77: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 77),
            "209:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 76),
            "217:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),

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
            "60:26: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 26),
            "69:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "74:52: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 52),
            "77:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 18),
            "85:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "97:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "99:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "119:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 37),
            "126:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 37),
            "148:13: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 13),
            "157:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "157:10: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 10),
            "161:49: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 49),
            "161:50: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 50),
            "164:75: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 75),
            "164:76: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 76),
            "164:77: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 77),
            "176:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),

        };
        verify(checkConfig, getPath("InputRightCurlyAloneOrSingleline.java"), expected);
    }

    @Test
    public void testCatchWithoutFinally() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 13),
        };
        verify(checkConfig, getPath("InputRightCurly.java"), expected);
    }

    @Test
    public void testSingleLineClass() throws Exception {
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        checkConfig.addAttribute("tokens", "CLASS_DEF");
        final String[] expected = {
            "24:37: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 37),
        };
        verify(checkConfig, getPath("InputRightCurly.java"), expected);
    }
}
