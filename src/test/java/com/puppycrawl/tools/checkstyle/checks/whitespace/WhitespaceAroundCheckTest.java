////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_PRECEDED;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAroundCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearound";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAroundCheck checkObj = new WhitespaceAroundCheck();
        assertArrayEquals(
            "WhitespaceAroundCheck#getRequiredTokens should return empty array by default",
            CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testKeywordsAndOperators()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "16:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "16:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "18:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "26:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "27:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "27:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "28:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "28:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "29:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "37:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "39:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "39:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "41:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "58:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "76:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "97:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "97:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "97:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "97:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "98:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "98:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "104:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "104:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "119:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "120:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "121:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "121:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "123:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "124:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "125:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "125:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "153:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "assert"),
            "156:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "156:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "262:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundKeywordsAndOperators.java"), expected);
    }

    @Test
    public void testSimpleInput()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "153:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "154:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "155:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "156:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "157:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "158:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundSimple.java"), expected);
    }

    @Test
    public void testStartOfTheLine()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "5:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundStartOfTheLine.java"), expected);
    }

    @Test
    public void testBraces()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "37:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "54:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            // + ":58:23: ';' is not followed by whitespace.",
            //  + ":58:29: ';' is not followed by whitespace.",
            "111:43: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "111:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "114:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "114:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundBraces.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructors()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyMethods", "true");
        checkConfig.addAttribute("allowEmptyConstructors", "true");
        final String[] expected = {
            "37:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "54:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundBraces.java"), expected);
    }

    @Test
    public void testArrayInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_INIT");
        final String[] expected = {
            "7:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "11:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "13:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "21:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "21:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "23:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "27:46: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundArrayInitialization.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "7:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "7:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "&"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundGenerics.java"), expected);
    }

    @Test
    public void test1322879And1649038() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testAllowDoubleBraceInitialization() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "11:73: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "12:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "14:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "14:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "17:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "17:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundDoubleBraceInitialization.java"),
                expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("ignoreEnhancedForColon", "false");
        final String[] expected = {
            "19:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };
        verify(checkConfig, getPath("InputWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyTypes", "true");
        final String[] expected = {
            "29:95: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "29:95: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "31:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "31:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyLoops", "true");
        final String[] expected = {
            "40:65: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "40:65: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "42:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "44:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "44:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "50:44: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "6:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundSwitch.java"), expected);
    }

    @Test
    public void testDoWhileWhitespaceAround() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "9:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundDoWhile.java"), expected);
    }

    @Test
    public void allowEmptyMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyMethods", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAround.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final WhitespaceAroundCheck whitespaceAroundCheckObj = new WhitespaceAroundCheck();
        final int[] actual = whitespaceAroundCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.ASSIGN,
            TokenTypes.ARRAY_INIT,
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
            TokenTypes.LAMBDA,
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
            TokenTypes.ELLIPSIS,
        };
        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testAllowEmptyTypesIsSetToFalseAndNonEmptyClasses() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyTypes", "false");
        final String[] expected = {
            "6:68: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "10:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "14:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "18:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "20:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "20:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "20:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "22:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "24:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "24:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:69: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "28:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "31:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "31:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses.java"),
            expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToTrueAndNonEmptyClasses() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyTypes", "true");
        final String[] expected = {
            "6:68: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "10:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "14:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "18:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "20:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "20:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "20:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "22:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "28:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "31:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "31:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            };
        verify(checkConfig, getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses.java"),
            expected);
    }

    @Test
    public void testNotAllowEmptyLambdaExpressionsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "7:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "7:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "12:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "12:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "13:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "13:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testAllowEmptyLambdaExpressionsWithAllowEmptyLambdaParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyLambdas", "true");
        final String[] expected = {
            "12:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "12:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "13:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "13:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundLambda() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        final String[] expected = {
            "8:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "->"),
            "8:50: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundLambda.java"), expected);
    }

    @Test
    public void testWhitespaceAroundEmptyCatchBlock() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("allowEmptyCatches", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAroundCatch.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundVarargs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAroundCheck.class);
        checkConfig.addAttribute("tokens", "ELLIPSIS");
        final String[] expected = {
            "9:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "10:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "11:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "11:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "13:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "13:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "14:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "14:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
        };
        verify(checkConfig, getPath("InputWhitespaceAroundVarargs.java"), expected);
    }

}
