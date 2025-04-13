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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_PREVIOUS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OperatorWrapCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/operatorwrap";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "23:19: " + getCheckMessage(MSG_LINE_NEW, "+"),
            "24:15: " + getCheckMessage(MSG_LINE_NEW, "-"),
            "32:18: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "54:30: " + getCheckMessage(MSG_LINE_NEW, "&"),
            "67:31: " + getCheckMessage(MSG_LINE_NEW, "&"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrap1.java"), expected);
    }

    @Test
    public void testOpWrapEol()
            throws Exception {
        final String[] expected = {
            "26:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "-"),
            "30:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
            "35:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrap2.java"), expected);
    }

    @Test
    public void testNonDefOpsDefault()
            throws Exception {
        final String[] expected = {
            "37:33: " + getCheckMessage(MSG_LINE_NEW, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrap3.java"), expected);
    }

    @Test
    public void testNonDefOpsWrapEol()
            throws Exception {
        final String[] expected = {
            "35:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "::"),
            "40:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrap4.java"), expected);
    }

    @Test
    public void testAssignEol()
            throws Exception {
        final String[] expected = {
            "46:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrap5.java"), expected);
    }

    @Test
    public void testEol() throws Exception {
        final String[] expected = {
            "21:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
            "22:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "*"),
            "33:18: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
            "38:21: " + getCheckMessage(MSG_LINE_PREVIOUS, ":"),
            "39:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "?"),
            "44:17: " + getCheckMessage(MSG_LINE_PREVIOUS, ":"),
            "49:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
            "61:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "||"),
            "62:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "||"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrapEol.java"), expected);
    }

    @Test
    public void testNl() throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_LINE_NEW, "="),
            "21:19: " + getCheckMessage(MSG_LINE_NEW, "*"),
            "32:23: " + getCheckMessage(MSG_LINE_NEW, "="),
            "35:25: " + getCheckMessage(MSG_LINE_NEW, "?"),
            "43:24: " + getCheckMessage(MSG_LINE_NEW, ":"),
            "48:18: " + getCheckMessage(MSG_LINE_NEW, "="),
            "60:27: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "61:31: " + getCheckMessage(MSG_LINE_NEW, "&&"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrapNl.java"), expected);
    }

    @Test
    public void testArraysAssign() throws Exception {
        final String[] expected = {
            "18:22: " + getCheckMessage(MSG_LINE_NEW, "="),
            "36:28: " + getCheckMessage(MSG_LINE_NEW, "="),
        };

        verifyWithInlineConfigParser(
                getPath("InputOperatorWrapArrayAssign.java"), expected);
    }

    @Test
    public void testGuardedPattern() throws Exception {
        final String[] expected = {
            "52:30: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "54:31: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "62:30: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "64:31: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "71:23: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "72:43: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "75:30: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "76:44: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "77:34: " + getCheckMessage(MSG_LINE_NEW, "&&"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOperatorWrapGuardedPatterns.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrapTryWithResources.java"), expected);
    }

    @Test
    public void testInvalidOption() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputOperatorWrap6.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.OperatorWrapCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "18:21: " + getCheckMessage(MSG_LINE_PREVIOUS, ":"),
            "19:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "?"),
        };
        verifyWithInlineConfigParser(
                getPath("InputOperatorWrapWithTrimOptionProperty.java"), expected);
    }

    @Test
    public void testInstanceOfOperator() throws Exception {
        final String[] expected = {
            "17:15: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "23:15: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "35:23: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "39:23: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "49:33: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "59:33: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "72:15: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
            "82:38: " + getCheckMessage(MSG_LINE_NEW, "instanceof"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOperatorWrapInstanceOfOperator.java"), expected);
    }

    @Test
    public void testInstanceOfOperatorEndOfLine() throws Exception {
        final String[] expected = {
            "28:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "instanceof"),
            "43:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "instanceof"),
            "65:20: " + getCheckMessage(MSG_LINE_PREVIOUS, "instanceof"),
            "78:17: " + getCheckMessage(MSG_LINE_PREVIOUS, "instanceof"),
            "88:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "instanceof"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputOperatorWrapInstanceOfOperatorEndOfLine.java"), expected);
    }

}
