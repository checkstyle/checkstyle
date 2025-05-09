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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_TYPECAST;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class WhitespaceAfterCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter";
    }

    @Test
    void getRequiredTokens() {
        final WhitespaceAfterCheck checkObj = new WhitespaceAfterCheck();
        assertWithMessage(
                "WhitespaceAfterCheck#getRequiredTokens should return empty array by default")
                        .that(checkObj.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    void testDefault() throws Exception {
        final String[] expected = {
            "45:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "74:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDefaultConfig.java"),
                expected);
    }

    @Test
    void cast() throws Exception {
        final String[] expected = {
            "91:20: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterTypeCast.java"),
                expected);
    }

    @Test
    void multilineCast() throws Exception {
        final String[] expected = {
            "14:23: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterMultilineCast.java"),
                expected);
    }

    @Test
    void semi() throws Exception {
        final String[] expected = {
            "57:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "57:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "106:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterBraces.java"),
                expected);
    }

    @Test
    void literalWhile() throws Exception {
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralWhile.java"),
                expected);
    }

    @Test
    void literalIf() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralIf.java"),
                expected);
    }

    @Test
    void literalElse() throws Exception {
        final String[] expected = {
            "34:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralElse.java"),
                expected);
    }

    @Test
    void literalFor() throws Exception {
        final String[] expected = {
            "58:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralFor.java"),
                expected);
    }

    @Test
    void literalFinally() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
            "17:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralFinally.java"),
            expected);
    }

    @Test
    void literalReturn() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "21:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "25:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "29:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralReturn.java"),
            expected);
    }

    @Test
    void literalDo() throws Exception {
        final String[] expected = {
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "do"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralDo.java"),
                expected);
    }

    @Test
    void literalYield() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "yield"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAfterLiteralYield.java"),
                expected);
    }

    @Test
    void literalSynchronized() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "31:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralSynchronized.java"),
                expected);
    }

    @Test
    void doWhile() throws Exception {
        final String[] expected = {
            "25:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDoWhile.java"),
                expected);
    }

    @Test
    void literalTry() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "24:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralTry.java"),
            expected);
    }

    @Test
    void literalCatch() throws Exception {
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCatch.java"),
            expected);
    }

    @Test
    void literalCase() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "case"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCase.java"),
            expected);
    }

    @Test
    void literalCase2() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "case"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCase2.java"),
            expected);
    }

    @Test
    void emptyForIterator() throws Exception {
        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "21:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterFor.java"),
                expected);
    }

    @Test
    void typeArgumentAndParameterCommas() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "20:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "20:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterGenerics.java"),
                expected);
    }

    @Test
    void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterAround.java"),
               expected);
    }

    @Test
    void countUnicodeCorrectly() throws Exception {
        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterCountUnicodeCorrectly.java"), expected);
    }

    @Test
    void varargs() throws Exception {
        final String[] expected = {
            "14:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "18:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "21:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "28:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
            "37:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "..."),
        };
        verifyWithInlineConfigParser(getPath("InputWhitespaceAfterVarargs.java"), expected);
    }

    @Test
    void switchStatements() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
            "31:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
            "33:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "40:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
            "41:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "42:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "49:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "switch"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputWhitespaceAfterSwitchStatements.java"),
                expected);
    }

    @Test
    void lambdaExpressions() throws Exception {
        final String[] expected = {
            "17:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "19:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "28:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
        };

        verifyWithInlineConfigParser(getPath("InputWhitespaceAfterLambdaExpressions.java"),
                expected);
    }

    @Test
    void whitespaceAfterWithEmoji() throws Exception {
        final String[] expected = {
            "13:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "13:52: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "29:32: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "38:23: " + getCheckMessage(MSG_WS_TYPECAST, ";"),
            "48:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "48:53: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterWithEmoji.java"), expected);
    }

    @Test
    void literalWhen() throws Exception {
        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "16:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "18:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "20:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "35:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "45:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputWhitespaceAfterLiteralWhen.java"),
            expected);
    }

    @Test
    void unnamedPattern() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputWhitespaceAfterUnnamedPattern.java"),
            expected);

    }
}
