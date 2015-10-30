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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.WS_NOT_PRECEDED;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class WhitespaceAroundCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(WhitespaceAroundCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAroundCheck checkObj = new WhitespaceAroundCheck();
        assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testIt()
        throws Exception {
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
            "262:14: " + getCheckMessage(WS_NOT_FOLLOWED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testIt2()
        throws Exception {
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
        throws Exception {
        final String[] expected = {
            "37:14: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
            "54:12: " + getCheckMessage(WS_NOT_FOLLOWED, "for"),
            // + ":58:23: ';' is not followed by whitespace.",
            //  + ":58:29: ';' is not followed by whitespace.",
            "111:27: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "111:27: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "114:40: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "114:40: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testIt4()
        throws Exception {
        checkConfig.addAttribute("allowEmptyMethods", "true");
        checkConfig.addAttribute("allowEmptyConstructors", "true");
        final String[] expected = {
            "37:14: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
            "54:12: " + getCheckMessage(WS_NOT_FOLLOWED, "for"),
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
        throws Exception {
        final String[] expected = {
            "6:67: " + getCheckMessage(WS_NOT_PRECEDED, "&"),
            "6:68: " + getCheckMessage(WS_NOT_FOLLOWED, "&"),
        };
        verify(checkConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879And1649038() throws Exception {
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception {
        checkConfig.addAttribute("ignoreEnhancedForColon", "false");
        final String[] expected = {
            "19:20: " + getCheckMessage(WS_NOT_PRECEDED, ":"),
        };
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception {
        checkConfig.addAttribute("allowEmptyTypes", "true");
        final String[] expected = {
            "29:95: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "29:95: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "30:33: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "30:33: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
            "31:21: " + getCheckMessage(WS_NOT_FOLLOWED, "{"),
            "31:21: " + getCheckMessage(WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception {
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
        verify(checkConfig, getPath("InputEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception {
        final String[] expected = {
            "6:15: " + getCheckMessage(WS_NOT_FOLLOWED, "switch"),
        };
        verify(checkConfig, getPath("InputSwitchWhitespaceAround.java"), expected);
    }

    @Test
    public void testDoWhileWhitespaceAround() throws Exception {
        final String[] expected = {
            "9:16: " + getCheckMessage(WS_NOT_FOLLOWED, "while"),
        };
        verify(checkConfig, getPath("InputDoWhileWhitespaceAround.java"), expected);
    }

    @Test
    public void allowEmptyMethods() throws Exception {
        checkConfig.addAttribute("allowEmptyMethods", "true");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final WhitespaceAroundCheck whitespaceAroundCheckObj = new WhitespaceAroundCheck();
        final int[] actual = whitespaceAroundCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.ASSIGN,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.DO_WHILE,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.WILDCARD_TYPE,
            TokenTypes.GENERIC_START,
            TokenTypes.GENERIC_END,
        };
        assertArrayEquals(expected, actual);
    }
}
