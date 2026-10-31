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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MIN;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MIN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DescendantTokenCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/descendanttoken";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens.java"), expected);
    }

    @Test
    public void testMaximumNumber()
            throws Exception {
        final String[] expected = {
            "32:12: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_NATIVE", "LITERAL_NATIVE"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens2.java"), expected);
    }

    @Test
    public void testMessage()
            throws Exception {
        final String[] expected = {
            "32:12: Using 'native' is not allowed.",
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens3.java"), expected);
    }

    @Test
    public void testMinimumNumber()
            throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_KEY_MIN, 1, 2, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens4.java"), expected);
    }

    @Test
    public void testMinimumDepth()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens5.java"), expected);
    }

    @Test
    public void testMaximumDepth()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens6.java"), expected);
    }

    @Test
    public void testEmptyStatements()
            throws Exception {

        final String[] expected = {
            "22:7: Empty statement.",
            "27:7: Empty statement.",
            "32:19: Empty statement.",
            "36:10: Empty statement.",
            "39:16: Empty statement.",
            "43:10: Empty statement.",
            "53:10: Empty statement.",
            "59:13: Empty statement.",
            "61:13: Empty statement.",
            "64:19: Empty statement.",
            "68:10: Empty statement.",
            "71:9: Empty statement.",
            "76:10: Empty statement.",
            "82:10: Empty statement.",
            "86:10: Empty statement.",
            "90:10: Empty statement.",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenEmptyStatement.java"), expected);
    }

    @Test
    public void testMissingSwitchDefault() throws Exception {

        final String[] expected = {
            "32:9: switch without \"default\" clause.",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenMissingSwitchDefault.java"), expected);
    }

    @Test
    public void testStringLiteralEquality() throws Exception {
        final String[] expected = {
            "22:18: Literal Strings should be compared using equals(), not '=='.",
            "27:20: Literal Strings should be compared using equals(), not '=='.",
            "32:22: Literal Strings should be compared using equals(), not '=='.",
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenStringLiteralEquality.java"), expected);
    }

    @Test
    public void testIllegalTokenDefault() throws Exception {

        final String[] expected = {
            "23:9: Using 'LITERAL_SWITCH' is not allowed.",
            "26:18: Using 'POST_DEC' is not allowed.",
            "27:18: Using 'POST_INC' is not allowed.",
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens7.java"), expected);
    }

    @Test
    public void testIllegalTokenNative() throws Exception {

        final String[] expected = {
            "32:12: Using 'LITERAL_NATIVE' is not allowed.",
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenIllegalTokens8.java"), expected);
    }

    @Test
    public void testReturnFromCatch() throws Exception {

        final String[] expected = {
            "22:11: Return from catch is not allowed.",
            "30:11: Return from catch is not allowed.",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromCatch.java"), expected);
    }

    @Test
    public void testReturnFromFinally() throws Exception {

        final String[] expected = {
            "22:11: Return from finally is not allowed.",
            "30:11: Return from finally is not allowed.",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally.java"), expected);
    }

    @Test
    public void testNoSum() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally2.java"), expected);
    }

    @Test
    public void testWithSumCustomMsg() throws Exception {

        final String[] expected = {
            "37:32: this cannot be null.",
            "37:50: this cannot be null.",
            "41:33: this cannot be null.",
            "41:51: this cannot be null.",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally3.java"), expected);
    }

    @Test
    public void testWithSumDefaultMsg() throws Exception {

        final String[] expected = {
            "37:32: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
            "37:50: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
            "41:33: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "NOT_EQUAL"),
            "41:51: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "NOT_EQUAL"),
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally4.java"), expected);
    }

    @Test
    public void testWithSumLessThenMinDefMsg() throws Exception {

        final String[] expected = {
            "32:44: " + getCheckMessage(MSG_KEY_SUM_MIN, 0, 3, "EQUAL"),
            "38:32: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "38:50: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "42:33: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "NOT_EQUAL"),
            "42:51: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "NOT_EQUAL"),
            "47:13: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "47:36: " + getCheckMessage(MSG_KEY_SUM_MIN, 1, 3, "EQUAL"),
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally5.java"), expected);
    }

    @Test
    public void testWithSumLessThenMinCustomMsg() throws Exception {

        final String[] expected = {
            "31:44: custom message",
            "37:32: custom message",
            "37:50: custom message",
            "41:33: custom message",
            "41:51: custom message",
            "46:13: custom message",
            "46:36: custom message",
        };

        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenReturnFromFinally6.java"), expected);
    }

    @Test
    public void testMaxTokenType() throws Exception {
        final String[] expected = {
            "21:48: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "LCURLY"),
            "21:48: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "RCURLY"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenLastTokenType.java"), expected);
    }

    @Test
    public void testMaxTokenTypeReverseOrder() throws Exception {
        final String[] expected = {
            "21:49: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "LCURLY"),
            "21:49: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "RCURLY"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenLastTokenType2.java"), expected);
    }

    @Test
    public void testLimitedToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenTestLimitedToken.java"), expected);
    }

    @Test
    public void testNegativeMaximumDepth() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "CLASS_DEF", "CLASS_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputDescendantTokenNegativeMaximumDepth.java"), expected);
    }

    @Test
    public void testNoStackOverflowOnDeepStringConcat() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_MAX, "1799", 1, "VARIABLE_DEF", "PLUS"),
        };
        // Limited stack size ensures that a recursive traversal of the deeply
        // nested tree of string concatenations throws StackOverflowError
        final boolean completed = TestUtil.getResultWithLimitedResources(() -> {
            verifyWithInlineConfigParser(
                    getPath("InputDescendantTokenDeepStringConcat.java"), expected);
            return true;
        });
        assertWithMessage("Check should process deeply nested tree without failure")
                .that(completed)
                .isTrue();
    }

    @Test
    public void testDepthLimitsOnDeepStringConcat() throws Exception {
        // PLUS tokens of the concatenation are at depths 3..1801 below VARIABLE_DEF,
        // only those at depths 1000..1500 are counted
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_MAX, "501", 1, "VARIABLE_DEF", "PLUS"),
        };
        final boolean completed = TestUtil.getResultWithLimitedResources(() -> {
            verifyWithInlineConfigParser(
                    getPath("InputDescendantTokenDeepStringConcatDepthLimits.java"), expected);
            return true;
        });
        assertWithMessage("Check should respect depth limits on deeply nested tree")
                .that(completed)
                .isTrue();
    }

    @Test
    public void testCompactDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactDefault.java"),
                expected);
    }

    @Test
    public void testCompactCounts() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_FINALLY", "LITERAL_RETURN"),
            "54:7: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "LITERAL_FINALLY", "LITERAL_RETURN"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactCounts.java"),
                expected);
    }

    @Test
    public void testCompactDepth() throws Exception {
        final String[] expected = {
            "28:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_RETURN"),
            "55:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_RETURN"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactDepth.java"),
                expected);
    }

    @Test
    public void testCompactSum() throws Exception {
        final String[] expected = {
            "22:7: " + getCheckMessage(MSG_KEY_SUM_MIN, 0, 1, "LITERAL_FINALLY"),
            "45:7: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "LITERAL_FINALLY"),
            "56:7: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "LITERAL_FINALLY"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactSum.java"),
                expected);
    }

    @Test
    public void testCompactMessages() throws Exception {
        final String[] expected = {
            "23:7: Too few LITERAL_RETURN in LITERAL_FINALLY: 0 < 1.",
            "56:7: Too many LITERAL_RETURN in LITERAL_FINALLY: 2 > 1.",
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactMessages.java"),
                expected);
    }

    @Test
    public void testCompactRoot() throws Exception {
        final String[] expected = {
            "21:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_FINALLY"),
            "28:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_FINALLY"),
            "36:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_FINALLY"),
            "46:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_FINALLY"),
            "57:7: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_FINALLY"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputDescendantTokenCompactRoot.java"),
                expected);
    }

}
