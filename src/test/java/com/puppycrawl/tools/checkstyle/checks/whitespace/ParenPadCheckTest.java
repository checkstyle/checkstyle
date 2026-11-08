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
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class ParenPadCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "21:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "21:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "77:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "101:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "101:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final String[] expected = {
            "24:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "24:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "34:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "34:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "52:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "52:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "74:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "74:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "78:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "78:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "93:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "93:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "99:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "99:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2.java"), expected);
    }

    @Test
    public void testSpaceForLoop()
            throws Exception {
        final String[] expected = {
            "18:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "18:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "26:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "50:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "60:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "60:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "73:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "73:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "81:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "81:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "86:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "86:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "90:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "90:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "104:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "104:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2ForLoop.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
            throws Exception {
        final String[] expected = {
            "24:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
            throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "18:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "24:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "24:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "30:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
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
            "55:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "55:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "69:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "78:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "79:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "80:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "87:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "88:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "90:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputSpaceRight() throws Exception {
        final String[] expected = {
            "21:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "21:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "29:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "37:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "37:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "37:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "43:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "44:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "45:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "54:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "56:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "69:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "75:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "75:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "81:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "81:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "81:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "91:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "91:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "98:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "98:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "104:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "104:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1SpaceRight.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputCasts() throws Exception {
        final String[] expected = {
            "15:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "39:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "39:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "50:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "51:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "59:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "63:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "63:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "71:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "71:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "79:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "80:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "80:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1Casts.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInputCalls() throws Exception {
        final String[] expected = {
            "16:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "18:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "18:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "42:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "46:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "50:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "50:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "59:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "63:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "64:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "65:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "71:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "71:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "82:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "82:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "82:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "82:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "88:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "90:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "90:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "90:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "99:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "99:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "104:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "20:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "41:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final String[] expected = {
            "20:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "20:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "25:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "28:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "31:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "31:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "44:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "44:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
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
            "16:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = {
            "20:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "21:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "23:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadTryWithResources.java"), expected);
    }

    @Test
    public void testTryWithResourcesAndSuppression() throws Exception {
        final String[] expectedFiltered = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedUnfiltered = {
            "27:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
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
            "20:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "37:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "57:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "62:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecords.java"), expected);
    }

    @Test
    public void testParenPadCheckRecordsWithSpace() throws Exception {

        final String[] expected = {
            "25:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "31:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "34:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "45:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "57:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "59:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "60:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "62:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecordsSpace.java"), expected);
    }

    @Test
    public void testParenPadCheckEmoji() throws Exception {

        final String[] expected = {
            "25:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "37:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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

    /**
     * Pitest requires us to specify more concrete lower bound for condition for
     * ParenPadCheck#isAcceptableToken as nodes of first several types like CTOR_DEF,
     * METHOD_DEF will never reach this method. It is hard to recreate conditions for
     * all tokens to go through this method. We do not want to change main code to have
     * this set ok tokens more exact, because it will not be ease to understand.
     * So we have to use reflection to be sure all acceptable tokens pass that check.
     */
    @Test
    public void testIsAcceptableToken() throws Exception {
        final ParenPadCheck check = new ParenPadCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        final String message = "Expected that all acceptable tokens will pass isAcceptableToken "
            + "method, but some token don't: ";

        for (int token : check.getAcceptableTokens()) {
            ast.setType(token);
            assertWithMessage("%s%s", message, TokenUtil.getTokenName(token))
                    .that(TestUtil.invokeMethod(check, "isAcceptableToken", Boolean.class, ast))
                    .isTrue();
        }
    }

    @Test
    public void testParenPadWithWhenExpression() throws Exception {
        final String[] expected = {
            "21:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:41: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "29:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckWhenExpression.java"), expected);
    }

    @Test
    public void testParenPadForRecordPattern() throws Exception {
        final String[] expected = {
            "19:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "21:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "21:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:73: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:74: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "57:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "57:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "66:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "66:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "66:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "66:64: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "20:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "21:18: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputParenPadCompactSourceFileDefault.java"),
            expected);
    }

    @Test
    public void testParenPadCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "20:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "21:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputParenPadCompactSourceFileProperties.java"),
            expected);
    }

}
