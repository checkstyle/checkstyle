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

public class WhitespaceAfterCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter";
    }

    @Test
    public void getRequiredTokens() {
        final WhitespaceAfterCheck checkObj = new WhitespaceAfterCheck();
        assertWithMessage(
                "WhitespaceAfterCheck#getRequiredTokens should return empty array by default")
                        .that(checkObj.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "45:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "74:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDefaultConfig.java"),
                expected);
    }

    @Test
    public void cast() throws Exception {
        final String[] expected = {
            "91:20: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterTypeCast.java"),
                expected);
    }

    @Test
    public void multilineCast() throws Exception {
        final String[] expected = {
            "14:23: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterMultilineCast.java"),
                expected);
    }

    @Test
    public void semi() throws Exception {
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
    public void literalWhile() throws Exception {
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralWhile.java"),
                expected);
    }

    @Test
    public void literalIf() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralIf.java"),
                expected);
    }

    @Test
    public void literalElse() throws Exception {
        final String[] expected = {
            "34:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralElse.java"),
                expected);
    }

    @Test
    public void literalFor() throws Exception {
        final String[] expected = {
            "58:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralFor.java"),
                expected);
    }

    @Test
    public void literalFinally() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
            "17:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralFinally.java"),
            expected);
    }

    @Test
    public void literalReturn() throws Exception {
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
    public void literalDo() throws Exception {
        final String[] expected = {
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "do"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralDo.java"),
                expected);
    }

    @Test
    public void literalYield() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "yield"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralYield.java"),
                expected);
    }

    @Test
    public void literalSynchronized() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "31:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralSynchronized.java"),
                expected);
    }

    @Test
    public void doWhile() throws Exception {
        final String[] expected = {
            "25:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDoWhile.java"),
                expected);
    }

    @Test
    public void literalTry() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "24:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralTry.java"),
            expected);
    }

    @Test
    public void literalCatch() throws Exception {
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCatch.java"),
            expected);
    }

    @Test
    public void literalCase() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "case"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCase.java"),
            expected);
    }

    @Test
    public void literalCase2() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "case"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralCase2.java"),
            expected);
    }

    @Test
    public void emptyForIterator() throws Exception {
        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "21:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterFor.java"),
                expected);
    }

    @Test
    public void typeArgumentAndParameterCommas() throws Exception {
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
    public void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterAround.java"),
               expected);
    }

    @Test
    public void countUnicodeCorrectly() throws Exception {
        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterCountUnicodeCorrectly.java"), expected);
    }

    @Test
    public void varargs() throws Exception {
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
    public void switchStatements() throws Exception {
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
                getPath("InputWhitespaceAfterSwitchStatements.java"),
                expected);
    }

    @Test
    public void lambdaExpressions() throws Exception {
        final String[] expected = {
            "17:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "19:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
            "28:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "->"),
        };

        verifyWithInlineConfigParser(getPath("InputWhitespaceAfterLambdaExpressions.java"),
                expected);
    }

    @Test
    public void whitespaceAfterWithEmoji() throws Exception {
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
    public void literalWhen() throws Exception {
        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "16:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "18:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "20:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "35:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
            "45:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "when"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterLiteralWhen.java"),
            expected);
    }

    @Test
    public void unnamedPattern() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterUnnamedPattern.java"),
            expected);

    }

    @Test
    public void whitespaceAfterAnnotation() throws Exception {
        final String[] expected = {
            "31:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnoType"),
            "31:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "NonNull2"),
            "37:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "NonNull"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "NonNull"),
            "47:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnoType"),
            "52:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "57:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "NonNull"),
            "57:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnoType"),
            "68:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnotationAfterTest"),
            "75:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnotationAfterTest"),
            "79:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnotationAfterTest"),
            "83:33: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnotationAfterTest"),
            "89:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "AnnotationAfterTest"), };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputWhitespaceAfterAnnotation.java"), expected);
    }

    @Test
    public void whitespaceAfterAnnotation2() throws Exception {
        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "18:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "19:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "32:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "TA"),
            "32:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "TB"),
            "37:43: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "TB"),
            "40:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "43:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "46:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "49:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "TA"),
            "53:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "54:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "55:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
            "62:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceAfterAnnotation2.java"), expected);
    }

    @Test
    public void whitespaceAfterAnnotationInPackageFile() throws Exception {
        final String[] expected = {
            "9:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
        };
        verifyWithInlineConfigParser(
            getPath("example1/package-info.java"), expected);
    }

    @Test
    public void whitespaceAfterAnnotationInPackageFile2() throws Exception {
        final String[] expected = {
            "10:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ")"),
        };
        verifyWithInlineConfigParser(
            getPath("example2/package-info.java"), expected);
    }
}
