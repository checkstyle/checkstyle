///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
            "32:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "32:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "34:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "42:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "43:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "44:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+="),
            "44:10: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+="),
            "45:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "-="),
            "53:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "55:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "55:12: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "57:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "57:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "74:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "92:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "113:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "113:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "113:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
            "113:34: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ":"),
            "114:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "=="),
            "114:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "=="),
            "120:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "*"),
            "120:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "*"),
            "135:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "136:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "137:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "%"),
            "137:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "%"),
            "139:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "140:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
            "141:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "/"),
            "141:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "/"),
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
    public void testSimpleInput()
            throws Exception {
        final String[] expected = {
            "168:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "169:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "170:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "171:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "172:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "173:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSimple.java"), expected);
    }

    @Test
    public void testStartOfTheLine()
            throws Exception {
        final String[] expected = {
            "25:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundStartOfTheLine.java"), expected);
    }

    @Test
    public void testBraces()
            throws Exception {
        final String[] expected = {
            "53:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            // + ":74:23: ';' is not followed by whitespace.",
            // + ":74:29: ';' is not followed by whitespace.",
            "127:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "127:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "130:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "130:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "134:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "134:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "134:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "134:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces.java"), expected);
    }

    @Test
    public void testBracesInMethodsAndConstructors()
            throws Exception {
        final String[] expected = {
            "53:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            "134:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "134:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "134:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "134:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundBraces2.java"), expected);
    }

    @Test
    public void testArrayInitialization()
            throws Exception {
        final String[] expected = {
            "21:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "25:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "28:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "36:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "36:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "38:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "38:41: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "43:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundArrayInitialization.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
            throws Exception {
        final String[] expected = {
            "27:16: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "&"),
            "27:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "&"),
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
            "31:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:88: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "37:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "37:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundDoubleBraceInitialization.java"),
                expected);
    }

    @Test
    public void testIgnoreEnhancedForColon() throws Exception {
        final String[] expected = {
            "39:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ":"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAround2.java"),
               expected);
    }

    @Test
    public void testEmptyTypes() throws Exception {
        final String[] expected = {
            "45:94: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "45:95: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "46:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "46:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles.java"),
               expected);
    }

    @Test
    public void testEmptyLoops() throws Exception {
        final String[] expected = {
            "56:65: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "56:66: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "58:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "58:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "60:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "60:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "66:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "66:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "76:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "76:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundEmptyTypesAndCycles2.java"),
               expected);
    }

    @Test
    public void testSwitchWhitespaceAround() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundSwitch.java"), expected);
    }

    @Test
    public void testDoWhileWhitespaceAround() throws Exception {
        final String[] expected = {
            "29:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
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
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToFalseAndNonEmptyClasses() throws Exception {
        final String[] expected = {
            "31:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "35:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "39:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "41:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "41:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "43:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "45:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "45:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "47:68: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "47:69: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "49:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "52:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "52:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "56:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses.java"),
            expected);
    }

    @Test
    public void testAllowEmptyTypesIsSetToTrueAndNonEmptyClasses() throws Exception {
        final String[] expected = {
            "30:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "34:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "38:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "40:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "40:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "42:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "}"),
            "48:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "51:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "51:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "55:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyTypesAndNonEmptyClasses2.java"),
            expected);
    }

    @Test
    public void testNotAllowEmptyLambdaExpressionsByDefault() throws Exception {
        final String[] expected = {
            "27:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "27:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "32:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "33:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testAllowEmptyLambdaExpressionsWithAllowEmptyLambdaParameter() throws Exception {
        final String[] expected = {
            "32:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "32:30: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "33:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "33:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllowEmptyLambdaExpressions2.java"),
                expected);
    }

    @Test
    public void testWhitespaceAroundLambda() throws Exception {
        final String[] expected = {
            "28:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "->"),
            "28:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
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
            "19:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "20:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "21:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "21:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "23:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "23:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "24:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "..."),
            "24:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundVarargs.java"), expected);
    }

    @Test
    public void testWhitespaceAroundRecords()
            throws Exception {
        final String[] expected = {
            "26:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "35:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "41:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "43:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "44:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "44:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "53:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "54:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "54:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "62:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "63:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "63:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "70:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "74:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "74:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAroundRecords.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAllowEmptyCompactCtors()
            throws Exception {
        final String[] expected = {
            "26:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "34:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "34:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "35:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "35:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "36:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "36:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "41:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "43:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "44:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "44:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "53:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "54:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "="),
            "54:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "67:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "="),
            "80:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "84:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
            "84:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "{"),
            "84:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "}"),
            "89:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "{"),
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
            "27:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "27:29: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "27:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "<"),
            "27:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "27:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "?"),
            "27:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "?"),
            "27:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ">"),
            "27:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
            "27:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ">"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAllTokens.java"), expected);
    }

    @Test
    public void testWhitespaceAroundAfterEmoji() throws Exception {
        final String[] expected = {
            "25:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "26:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "27:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "27:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
            "29:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "+"),
            "29:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "+"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAroundAfterEmoji.java"), expected);
    }
}
