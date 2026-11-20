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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_PRECEDED;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParenPadCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testDefaultExpressionOwners() throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionOwners.java"), expected);
    }

    @Test
    public void testSelectedExpressionOwners() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "12:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "19:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadSelectedOwners.java"), expected);
    }

    @Test
    public void testExpressionBoundary() throws Exception {
        final String[] expected = {
            "23:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "23:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionBoundary.java"), expected);
    }

    @Test
    public void testExpressionBoundarySpace() throws Exception {
        final String[] expected = {
            "23:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionBoundarySpace.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "26:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "41:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "82:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "91:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "91:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "106:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "106:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final String[] expected = {
            "29:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "29:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "39:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "39:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "46:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "46:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "57:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "57:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "79:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "79:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "83:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "83:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "98:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "98:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "104:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "104:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2.java"), expected);
    }

    @Test
    public void testSpaceForLoop()
            throws Exception {
        final String[] expected = {
            "23:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "28:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "31:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "55:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "65:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "65:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "78:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "78:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "86:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "86:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "91:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "91:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "95:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "95:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "109:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "109:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2ForLoop.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
            throws Exception {
        final String[] expected = {
            "29:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "32:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "58:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "66:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
            throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "29:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "35:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "38:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "50:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace2.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithSpace.java"),
               expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadToCheckTrimFunctionInOptionProperty.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInput() throws Exception {
        final String[] expected = {
            "60:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "64:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "67:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "74:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "83:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "84:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "85:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "91:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "92:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "93:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "95:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputSpaceRight() throws Exception {
        final String[] expected = {
            "26:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "42:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "42:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "48:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "49:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "50:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "57:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "58:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "74:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "80:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "80:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "80:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "80:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "96:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "96:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "103:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "103:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "109:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "109:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1SpaceRight.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputCasts() throws Exception {
        final String[] expected = {
            "20:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "44:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "44:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "55:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "56:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "57:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "57:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "64:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "64:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "68:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "68:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "76:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "76:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "84:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "85:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "85:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1Casts.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputCalls() throws Exception {
        final String[] expected = {
            "21:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "23:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "23:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "51:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "55:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "55:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "64:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "64:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "68:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "69:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "70:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "76:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "76:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "87:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "87:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "87:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "87:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "93:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "95:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "95:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "95:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "104:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "104:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "109:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1Calls.java"), expected);
    }

    @Test
    public void testConfigureTokens() throws Exception {
        final String[] expected = {
            "98:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace2.java"), expected);
    }

    @Test
    public void testConfigureTokensCasts() throws Exception {
        final String[] expected = {
            "15:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "48:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "72:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "72:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "72:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "72:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace2Casts.java"), expected);
    }

    @Test
    public void testConfigureTokensCalls() throws Exception {
        final String[] expected = {
            "41:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "43:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "49:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace2Calls.java"), expected);
    }

    @Test
    public void testInvalidOption() {

        final CheckstyleException exc =
            getExpectedThrowable(CheckstyleException.class, () -> {
                final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

                verifyWithInlineConfigParser(
                        getPath("InputParenPadLeftRightAndNoSpace3.java"), expected);
            });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker");
        assertWithMessage("Invalid exception message")
            .that(exc.getCause().getMessage())
            .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                + "whitespace.ParenPadCheck");
    }

    @Test
    public void testLambdaAssignment() throws Exception {
        final String[] expected = {
            "25:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "41:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "46:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final String[] expected = {
            "25:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "25:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "30:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "49:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "49:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabled() throws Exception {
        final String[] expected = {
            "27:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "33:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabledWithSpace() throws Exception {
        final String[] expected = {
            "30:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithSpaceAndDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckOnly() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "33:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnly.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "22:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "25:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "28:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "28:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "33:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnlyWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace1() throws Exception {
        final String[] expected = {
            "21:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = {
            "25:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadTryWithResources.java"), expected);
    }

    @Test
    public void testTryWithResourcesAndSuppression() throws Exception {
        final String[] expectedFiltered = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedUnfiltered = {
            "32:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyFilterWithInlineConfigParser(
                getPath("InputParenPadTryWithResourcesAndSuppression.java"),
                expectedUnfiltered,
                expectedFiltered);
    }

    @Test
    public void testNoStackoverflowError()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithLimitedResources(getPath("InputParenPadNoStackoverflowError.java"),
                expected);
    }

    @Test
    public void testParenPadCheckRecords() throws Exception {

        final String[] expected = {
            "25:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "45:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "67:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecords.java"), expected);
    }

    @Test
    public void testParenPadCheckRecordsWithSpace() throws Exception {

        final String[] expected = {
            "30:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "39:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "48:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "50:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "50:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "62:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "64:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "65:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "67:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecordsSpace.java"), expected);
    }

    @Test
    public void testParenPadCheckEmoji() throws Exception {

        final String[] expected = {
            "30:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "38:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckEmoji.java"), expected);
    }

    @Test
    public void testParenPadForSynchronized() throws Exception {

        final String[] expected = {
            "18:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForSynchronized.java"), expected);
    }

    @Test
    public void testParenPadForEnum() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadForEnum.java"), expected);
    }

    @Test
    public void testParenPadWithWhenExpression() throws Exception {
        final String[] expected = {
            "26:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "32:41: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckWhenExpression.java"), expected);
    }

    @Test
    public void testParenPadForRecordPattern() throws Exception {
        final String[] expected = {
            "23:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:73: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:74: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "65:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "70:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "70:64: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadForRecordPattern.java"), expected);
    }

    @Test
    public void testParenPadForRecordPatternWithSpaceOption() throws Exception {
        final String[] expected = {
            "14:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "14:58: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "18:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:46: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:61: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:72: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "33:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "45:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadForRecordPatternWithSpaceOption.java"),
                expected);
    }

    @Test
    public void testParenPadCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "25:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:18: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputParenPadCompactSourceFileDefault.java"),
            expected);
    }

    @Test
    public void testParenPadCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "25:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "26:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputParenPadCompactSourceFileProperties.java"),
            expected);
    }

}
