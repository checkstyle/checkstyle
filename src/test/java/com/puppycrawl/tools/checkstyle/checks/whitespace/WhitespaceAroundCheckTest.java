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
            "34:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "34:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "36:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "44:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "45:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "45:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "46:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "46:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "47:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "55:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "57:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "57:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "59:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "59:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "76:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "94:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "115:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "115:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "115:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "115:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "116:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "116:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "122:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "122:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "137:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "138:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "139:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "139:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "141:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "142:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "143:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "143:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "169:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "assert"),
            "172:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "172:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "278:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "307:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "307:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "307:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "307:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundKeywordsAndOperators.java"), expected);
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
            "41:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "45:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "45:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "45:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "45:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "23:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "27:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "30:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "38:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "38:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "45:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundArrayInitialization.java"), expected);
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
            "36:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "39:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "48:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "48:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "49:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "49:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "60:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "62:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "62:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "68:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "68:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "78:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "78:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "45:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "47:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "49:68: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "49:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "51:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "54:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "54:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "58:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "44:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "50:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "53:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "53:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "57:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "34:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "35:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "35:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "21:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "22:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "23:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "23:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "25:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "25:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "26:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "26:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
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
            "36:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "45:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "46:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "46:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "55:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "56:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "56:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "64:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "65:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "65:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "72:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "76:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "76:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "36:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "38:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "45:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "46:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "46:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "55:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "56:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "56:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "69:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "82:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "86:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "86:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "86:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "91:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "29:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "29:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "29:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "29:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "29:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "29:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "29:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">"),
            "29:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
            "29:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllTokens.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAfterEmoji() throws Exception {
        final String[] expected = {
            "27:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "28:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "31:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "31:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
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
            "28:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "29:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "29:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "29:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
    public void testInitializersWithAllowEmptyInitializers() throws Exception {
        final String[] expected = {
            "22:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "22:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "33:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyInitializersAllowed.java"), expected);
    }

    @Test
    public void testEmptyInitializers() throws Exception {
        final String[] expected = {
            "21:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "21:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "26:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "31:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "31:6: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "39:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "39:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "45:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "45:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyInitializers.java"), expected);
    }

}
