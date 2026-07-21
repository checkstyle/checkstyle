///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck.MSG_WS_NOT_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAroundCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacearound";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAroundCheck checkObj = new WhitespaceAroundCheck();
        assertWithMessage(
                "WhitespaceAroundCheck#getRequiredTokens should return empty array by default")
                        .that(checkObj.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    public void testKeywordsAndOperators()
            throws Exception {
        final String[] expected = {
            "33:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "33:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "38:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "46:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "47:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "47:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "51:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "51:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "55:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "63:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "65:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "65:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "70:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "90:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "108:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundKeywordsAndOperators.java"), expected);
    }

    @Test
    public void testOperators() throws Exception {
        final String[] expected = {
            "41:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "41:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "41:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "41:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "47:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "47:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "56:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "56:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "74:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "75:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "76:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "76:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "81:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "82:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "83:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "83:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "112:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "assert"),
            "115:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "115:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundOperators.java"), expected);
    }

    @Test
    public void testAnonymousClasses() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAnonymousClasses.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testForLoopsAndArrays() throws Exception {
        final String[] expected = {
            "71:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "100:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "100:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "100:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "100:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundForLoopsAndArrays.java"), expected);
    }

    @Test
    public void testSimpleInputPart1()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSimplePart1.java"), expected);
    }

    @Test
    public void testSimpleInputPart2()
            throws Exception {
        final String[] expected = {
            "106:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "107:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "108:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "109:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "110:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "111:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSimplePart2.java"), expected);
    }

    @Test
    public void testSimpleInputPart3()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSimplePart3.java"), expected);
    }

    @Test
    public void testStartOfTheLine()
            throws Exception {
        final String[] expected = {
            "26:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundStartOfTheLine.java"), expected);
    }

    @Test
    public void testBracesPart1()
            throws Exception {
        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "71:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBracesPart1.java"), expected);
    }

    @Test
    public void testBracesPart2()
            throws Exception {
        final String[] expected = {
            "37:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "50:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "50:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "50:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBracesPart2.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructorsPart1()
            throws Exception {
        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "71:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces2Part1.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructorsPart2()
            throws Exception {
        final String[] expected = {
            "87:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "87:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "87:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "87:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces2Part2.java"), expected);
    }

    @Test
    public void testArrayInitialization()
            throws Exception {
        final String[] expected = {
            "22:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "29:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "50:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundArrayInitialization.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
            throws Exception {
        final String[] expected = {
            "28:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "28:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "&"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundGenerics.java"), expected);
    }

    @Test
    public void test1322879And1649038() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAround1.java"),
               expected);
    }

    @Test
    public void testAllowDoubleBraceInitialization() throws Exception {
        final String[] expected = {
            "32:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "33:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "41:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "41:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundDoubleBraceInitialization.java"),
                expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception {
        final String[] expected = {
            "40:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAround2.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception {
        final String[] expected = {
            "46:94: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:95: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "50:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "54:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "54:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception {
        final String[] expected = {
            "57:65: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "57:66: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "62:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "62:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "67:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "67:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "76:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "76:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "89:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles2.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSwitch.java"), expected);
    }

    @Test
    public void testSwitchExpressionWhitespaceAround() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSwitchExpressions.java"), expected);
    }

    @Test
    public void testDoWhileWhitespaceAround() throws Exception {
        final String[] expected = {
            "30:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundDoWhile.java"), expected);
    }

    @Test
    public void allowEmptyMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAround3.java"), expected);
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
            TokenTypes.LITERAL_WHEN,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToFalseAndNonEmptyClasses() throws Exception {
        final String[] expected = {
            "32:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "36:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "42:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "48:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "50:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:68: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "55:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "63:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "63:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "70:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses.java"),
            expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToTrueAndNonEmptyClasses() throws Exception {
        final String[] expected = {
            "31:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "35:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "39:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "53:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "56:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "56:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "63:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses2.java"),
            expected);
    }

    @Test
    public void testNotAllowEmptyLambdaExpressionsByDefault() throws Exception {
        final String[] expected = {
            "28:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "40:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "40:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testAllowEmptyLambdaExpressionsWithAllowEmptyLambdaParameter() throws Exception {
        final String[] expected = {
            "33:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions2.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundLambda() throws Exception {
        final String[] expected = {
            "29:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "->"),
            "29:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundLambda.java"), expected);
    }

    @Test
    public void testWhitespaceAroundEmptyCatchBlock() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundCatch.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundVarargs() throws Exception {
        final String[] expected = {
            "20:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "21:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "22:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "22:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "27:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "27:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "31:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "31:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundVarargs.java"), expected);
    }

    @Test
    public void testWhitespaceAroundRecords()
            throws Exception {
        final String[] expected = {
            "27:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "46:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "54:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "56:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "57:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "57:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "69:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "70:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "70:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "81:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "82:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "82:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "92:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "96:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "96:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundRecords.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAllowEmptyCompactCtors()
            throws Exception {
        final String[] expected = {
            "27:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "46:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "54:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "56:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "57:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "57:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "69:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "70:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "70:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "86:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "99:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "103:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "103:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "103:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "112:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyCompactCtors.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundRecordsAllowEmptyTypes()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundRecordsAllowEmptyTypes.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundAllTokens() throws Exception {
        final String[] expected = {
            "28:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "28:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "28:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "28:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "28:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "28:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "28:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">"),
            "28:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
            "28:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllTokens.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAfterEmoji() throws Exception {
        final String[] expected = {
            "26:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "27:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "28:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "28:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "33:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "33:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAfterEmoji.java"), expected);
    }

    @Test
    public void testLiteralWhen() throws Exception {
        final String[] expected = {
            "22:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "24:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "26:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "31:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "31:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
            "35:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "35:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
            "54:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "65:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "68:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAroundLiteralWhen.java"),
            expected);
    }

    @Test
    public void testWhitespaceAroundAfterPermitsList() throws Exception {
        final String[] expected = {
            "26:53: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:53: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:54: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "31:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "31:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "31:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "36:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAfterPermitsList.java"), expected);
    }

    @Test
    public void testWhitespaceAroundUnnamedPatterns() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundUnnamedPattern.java"), expected);
    }

    @Test
    public void testSwitchCasesParens() throws Exception {
        final String[] expected = {
            "33:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "51:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "51:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "59:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "59:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "68:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "68:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "76:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "76:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "81:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "81:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "89:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "89:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "96:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "96:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "100:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "100:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "100:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "100:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "100:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "100:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "108:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "108:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "115:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSwitchCasesParens.java"),
                expected);
    }

    @Test
    public void testSwitchCasesParensWithAllowEmptySwitchBlockStatements() throws Exception {
        final String fileName =
                "InputWhitespaceAroundSwitchCasesParensWithAllowEmptySwitchBlockStatements.java";

        final String[] expected = {
            "49:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "49:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "59:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "59:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "68:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "68:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "73:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "73:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "84:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "84:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "88:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "88:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "88:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "88:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "88:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "88:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "96:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "96:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath(fileName),
                expected);
    }

}
