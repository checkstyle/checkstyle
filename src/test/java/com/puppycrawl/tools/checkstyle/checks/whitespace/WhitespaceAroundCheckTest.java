///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
    protected String getPackageLocation() {
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
            "35:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "43:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "44:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "44:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "45:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "45:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "46:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "54:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "56:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "56:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "58:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "58:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "75:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "93:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "114:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "114:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "114:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "114:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "115:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "115:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "121:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "121:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "136:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "137:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "138:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "138:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "140:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "141:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "142:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "142:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "168:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "assert"),
            "171:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "171:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "277:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "306:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "306:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "306:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "306:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundKeywordsAndOperators.java"), expected);
    }

    @Test
    public void testSimpleInput()
            throws Exception {
        final String[] expected = {
            "170:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "171:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "172:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "173:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "174:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "175:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSimple.java"), expected);
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
    public void testBraces()
            throws Exception {
        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "71:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            // + ":74:23: ';' is not followed by whitespace.",
            // + ":74:29: ';' is not followed by whitespace.",
            "128:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "128:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "131:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "131:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "135:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "135:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "135:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "135:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructors()
            throws Exception {
        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "71:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            "135:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "135:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "135:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "135:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces2.java"), expected);
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
            "39:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "39:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "44:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "35:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "38:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "38:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "47:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "48:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "48:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "59:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "59:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "61:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "61:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "67:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "67:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "77:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "77:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
                getNonCompilablePath("InputWhitespaceAroundSwitchExpressions.java"), expected);
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
            "44:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "46:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "48:68: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "48:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "50:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "53:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "53:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "57:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "43:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "49:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "52:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "52:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "56:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "33:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "34:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
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
            "24:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "24:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "25:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "25:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
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
            "35:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "44:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "45:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "45:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "54:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "55:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "63:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "64:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "64:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "71:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "75:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "75:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundRecords.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAllowEmptyCompactCtors()
            throws Exception {
        final String[] expected = {
            "27:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "35:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "37:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "44:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "45:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "45:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "54:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "55:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "55:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "68:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "81:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "85:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "85:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "85:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "90:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundAllowEmptyCompactCtors.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundRecordsAllowEmptyTypes()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundRecordsAllowEmptyTypes.java"),
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
            "30:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "30:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "30:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "30:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "30:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "30:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "30:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
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
            getNonCompilablePath("InputWhitespaceAroundLiteralWhen.java"),
            expected);
    }

    @Test
    public void testWhitespaceAroundAfterPermitsList() throws Exception {
        final String[] expected = {
            "26:53: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:53: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "26:54: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "27:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "27:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "28:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "28:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundAfterPermitsList.java"), expected);
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
                getNonCompilablePath("InputWhitespaceAroundSwitchCasesParens.java"),
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
                getNonCompilablePath(fileName),
                expected);
    }
}
