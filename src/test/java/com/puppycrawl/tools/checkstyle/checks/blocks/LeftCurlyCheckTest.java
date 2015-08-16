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

import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_BREAK_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck.MSG_KEY_LINE_PREVIOUS;
import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LeftCurlyCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(LeftCurlyCheck.class);
    }

    @Test
    public void testGetRequiredTokens() {
        LeftCurlyCheck checkObj = new LeftCurlyCheck();
        assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());

    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "30:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testNL() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "49:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 14),
            "53:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 14),
            "58:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "62:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "67:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 12),
            "72:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testNLOW() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NLOW.toString());
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "30:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "49:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 14),
            "53:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 14),
            "58:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "62:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "67:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 12),
            "72:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testDefault2() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "17:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "24:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "27:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "31:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "39:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "41:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "46:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "49:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "53:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "65:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "69:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "77:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "80:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "84:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        verify(checkConfig, getPath("InputLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testNL2() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "14:39: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 39),
            "21:20: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 20),
            "34:31: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 31),
            "43:24: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 24),
            "56:35: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 35),
            "60:24: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 24),
            "74:20: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 20),
            "87:31: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 31),
        };
        verify(checkConfig, getPath("InputLeftCurlyMethod.java"), expected);
    }

    @Test
    public void testDefault3() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "15:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "19:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "21:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 13),
            "23:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 17),
            "30:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 17),
            "34:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 17),
            "42:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 13),
            "46:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 13),
            "52:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "54:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 13),
            "63:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "76:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "83:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "89:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "97:19: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 19),
            "106:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "109:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "118:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "120:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "129:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "131:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "133:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "148:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "157:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "164:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testNL3() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "26:33: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 33),
            "91:19: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 19),
            "97:19: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 19),
            "142:37: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 37),
            "158:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 12),
            "165:16: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 16),
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testMissingBraces() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "15:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "34:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "51:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "69:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "105:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testDefaultWithAnnotations() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "14:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "27:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "50:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "58:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
        };
        verify(checkConfig, getPath("InputLeftCurlyAnnotations.java"), expected);
    }

    @Test
    public void testNLWithAnnotations() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "35:34: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 34),
            "38:41: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 41),
            "44:27: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 27),
            "66:32: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 32),
        };
        verify(checkConfig, getPath("InputLeftCurlyAnnotations.java"), expected);
    }

    @Test
    public void testLineBreakAfter() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.EOL.toString());
        checkConfig.addAttribute("maxLineLength", "100");
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "16:9: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 9),
            "18:13: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 13),
            "20:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 17),
            "26:22: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 22),
            "28:17: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 17),
            "35:33: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 33),
            "36:21: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 21),
            "39:29: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 29),
            "39:34: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 34),
            "45:37: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 37),
            "51:12: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 12),
            "54:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "56:19: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 19),
            "66:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 1),
        };
        verify(checkConfig, getPath("InputLeftCurlyLineBreakAfter.java"), expected);
    }

    @Test
    public void testIgnoreEnumsOptionTrue() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.EOL.toString());
        checkConfig.addAttribute("ignoreEnums", "true");
        final String[] expectedWhileTrue = {
        };
        verify(checkConfig, getPath("InputLeftCurlyEnums.java"), expectedWhileTrue);
    }

    @Test
    public void testIgnoreEnumsOptionFalse() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.EOL.toString());
        checkConfig.addAttribute("ignoreEnums", "false");
        final String[] expectedWhileFalse = {
            "4:17: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "{", 17),
        };
        verify(checkConfig, getPath("InputLeftCurlyEnums.java"), expectedWhileFalse);
    }

    @Test
    public void testGetAcceptableTokens() {
        LeftCurlyCheck check = new LeftCurlyCheck();
        int[] actual = check.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.STATIC_INIT, };
        Assert.assertNotNull(actual);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFirstLine() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.EOL.toString());
        checkConfig.addAttribute("maxLineLength", "100");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputLeftCurlyAllInOneLine.java"), expected);
    }

    @Test
    public void testCoverageIncrease() throws Exception {
        checkConfig.addAttribute("option", LeftCurlyOption.NLOW.toString());
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "21:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "30:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "{", 5),
            "53:14: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 14),
            "58:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "62:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
            "67:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 12),
            "72:18: " + getCheckMessage(MSG_KEY_LINE_NEW, "{", 18),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces2.java"), expected);
    }
}
