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

public class WhitespaceAroundCheckTest extends AbstractModuleTestSupport {

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
            "42:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "42:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "42:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "42:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "48:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "48:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "57:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "57:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "75:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "76:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "77:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "77:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "82:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "83:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "84:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "84:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "113:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "assert"),
            "116:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "116:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
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
            "107:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "108:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "109:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "110:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "111:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "112:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
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
            "27:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundStartOfTheLine.java"), expected);
    }

    @Test
    public void testBracesPart1()
            throws Exception {
        final String[] expected = {
            "55:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "72:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBracesPart1.java"), expected);
    }

    @Test
    public void testBracesPart2()
            throws Exception {
        final String[] expected = {
            "38:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "44:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "44:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "51:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "51:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "51:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "51:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBracesPart2.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructorsPart1()
            throws Exception {
        final String[] expected = {
            "55:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "72:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces2Part1.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructorsPart2()
            throws Exception {
        final String[] expected = {
            "88:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "88:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "88:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "88:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
    public void testEnforceWhitespaceAroundArrayInit()
            throws Exception {
        final String[] expected = {
            "21:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "24:44: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:44: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:46: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "41:43: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "50:52: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "55:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "55:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "55:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:46: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "55:51: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:52: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "72:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "76:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "87:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEnforceArrayInit.java"), expected);
    }

    @Test
    public void testEnforceWhitespaceAroundArrayInitDisabled()
            throws Exception {
        final String[] expected = {
            "29:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEnforceArrayInitDisabled.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
            throws Exception {
        final String[] expected = {
            "29:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "29:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "&"),
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
            "33:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "39:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "39:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundDoubleBraceInitialization.java"),
                expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception {
        final String[] expected = {
            "41:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAround2.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception {
        final String[] expected = {
            "47:94: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:95: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "51:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "51:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "55:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception {
        final String[] expected = {
            "58:65: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "58:66: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "63:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "63:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "68:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "68:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "77:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "77:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "90:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "90:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles2.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception {
        final String[] expected = {
            "28:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
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
            "31:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
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
            "33:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "43:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "43:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "49:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "51:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "51:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "56:68: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "56:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "61:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "64:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "64:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "71:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses.java"),
            expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToTrueAndNonEmptyClasses() throws Exception {
        final String[] expected = {
            "32:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "36:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "42:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "48:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "54:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "57:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "57:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "64:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses2.java"),
            expected);
    }

    @Test
    public void testNotAllowEmptyLambdaExpressionsByDefault() throws Exception {
        final String[] expected = {
            "29:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "29:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "41:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testAllowEmptyLambdaExpressionsWithAllowEmptyLambdaParameter() throws Exception {
        final String[] expected = {
            "34:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions2.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundLambda() throws Exception {
        final String[] expected = {
            "30:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "->"),
            "30:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
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
            "28:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "39:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "57:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "58:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "58:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "70:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "71:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "71:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "82:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "83:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "83:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "93:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "97:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "97:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundRecords.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAllowEmptyCompactCtors()
            throws Exception {
        final String[] expected = {
            "28:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "39:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "43:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "57:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "58:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "58:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "70:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "71:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "71:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "87:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "100:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "104:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "104:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "104:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "113:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "23:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "25:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "27:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "32:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "32:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
            "36:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "36:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
            "55:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "66:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "69:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "when"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAroundLiteralWhen.java"),
            expected);
    }

    @Test
    public void testWhitespaceAroundAfterPermitsList() throws Exception {
        final String[] expected = {
            "27:53: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:53: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "27:54: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "32:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "37:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "34:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "48:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "48:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "52:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "52:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "69:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "69:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "77:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "77:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "82:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "82:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "90:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "90:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "90:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "90:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "97:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "97:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "101:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "101:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "101:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "101:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "101:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "101:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "109:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "109:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "116:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
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
            "50:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "69:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "69:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "74:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "74:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "85:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "85:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "89:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "89:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "89:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "89:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "97:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "97:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath(fileName),
                expected);
    }

    @Test
    public void testWhitespaceAroundCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "24:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "24:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "30:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "30:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "42:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "46:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "50:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "50:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "54:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "54:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "65:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "65:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputWhitespaceAroundCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "33:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "33:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "49:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "49:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputWhitespaceAroundCompactSourceFileProperties.java"),
                expected);
    }

}
