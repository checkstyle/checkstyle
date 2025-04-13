///
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_LINE_PREVIOUS;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.MethodParamPadCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MethodParamPadCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/methodparampad";
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodParamPadCheck checkObj = new MethodParamPadCheck();
        assertWithMessage("MethodParamPadCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "21:32: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "23:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "27:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "30:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "37:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "42:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "46:39: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "48:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "52:16: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "54:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "60:21: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "62:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "66:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "68:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "71:36: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "73:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "84:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "89:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPad.java"), expected);
    }

    @Test
    public void testAllowLineBreaks() throws Exception {
        final String[] expected = {
            "21:33: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "23:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "37:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "46:39: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "52:16: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "60:21: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "66:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "71:36: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "84:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPad2.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        final String[] expected = {
            "16:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "18:14: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "27:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "30:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "33:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "42:9: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "46:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "49:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "52:15: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "55:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "58:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "60:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "63:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "66:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "67:17: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "70:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "72:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "75:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "78:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "82:45: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "84:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "93:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPad3.java"), expected);
    }

    @Test
    public void testMethodParamPadRecords() throws Exception {
        final String[] expected = {
            "19:16: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "20:34: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "31:17: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "32:14: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "33:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "37:17: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "38:33: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "44:17: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "45:18: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "51:25: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "57:25: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodParamPadRecords.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPadWhitespaceAround.java"),
               expected);
    }

    @Test
    public void testMethodParamPadCheckWithEmoji() throws Exception {
        final String[] expected = {
            "19:31: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "21:30: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "25:28: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "32:36: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "36:70: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "38:31: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "41:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "47:24: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "50:23: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPadCheckWithEmoji.java"), expected);
    }

    @Test
    public void testMethodParamPadCheckConstructors() throws Exception {
        final String[] expected = {
            "21:15: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "25:14: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "29:14: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPadCheckConstructors.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodParamPadCheck methodParamPadCheckObj = new MethodParamPadCheck();
        final int[] actual = methodParamPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_PATTERN_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testInvalidOption() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(getPath("InputMethodParamPad4.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.MethodParamPadCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "15:24: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "26:27: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodParamPadSetOptionTrim.java"), expected);
    }

    @Test
    public void testRecordPattern() throws Exception {
        final String[] expected = {
            "19:41: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "22:42: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "22:50: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "31:31: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "31:39: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "36:31: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "57:41: " + getCheckMessage(MSG_WS_PRECEDED, "("),
            "57:49: " + getCheckMessage(MSG_WS_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodParamPadCheckRecordPattern.java"), expected);
    }

    @Test
    public void testRecordPattern2() throws Exception {
        final String[] expected = {
            "23:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "23:46: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "31:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "36:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "39:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "40:26: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "44:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "46:22: " + getCheckMessage(MSG_LINE_PREVIOUS, "("),
            "50:40: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
            "50:47: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodParamPadCheckRecordPattern2.java"), expected);
    }
}
