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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.MSG_LINE_PREVIOUS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SeparatorWrapCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/separatorwrap";
    }

    @Test
    public void testDot()
            throws Exception {
        final String[] expected = {
            "39:10: " + getCheckMessage(MSG_LINE_NEW, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForTestDot.java"), expected);
    }

    @Test
    public void testComma() throws Exception {
        final String[] expected = {
            "47:17: " + getCheckMessage(MSG_LINE_PREVIOUS, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForTestComma.java"), expected);
    }

    @Test
    public void testMethodRef() throws Exception {
        final String[] expected = {
            "25:56: " + getCheckMessage(MSG_LINE_NEW, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForTestMethodRef.java"), expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final SeparatorWrapCheck separatorWrapCheckObj = new SeparatorWrapCheck();
        final int[] actual = separatorWrapCheckObj.getDefaultTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.COMMA,
        };
        assertWithMessage("Invalid default tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testInvalidOption() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputSeparatorWrapForInvalidOption.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.SeparatorWrapCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void testEllipsis() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "..."),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForEllipsis.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "["),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForArrayDeclarator.java"), expected);
    }

    @Test
    public void testWithEmoji() throws Exception {
        final String[] expected = {
            "13:39: " + getCheckMessage(MSG_LINE_NEW, '['),
            "16:57: " + getCheckMessage(MSG_LINE_NEW, '['),
            "19:39: " + getCheckMessage(MSG_LINE_NEW, "..."),
            "26:19: " + getCheckMessage(MSG_LINE_NEW, '.'),
            "39:50: " + getCheckMessage(MSG_LINE_NEW, ','),
            "41:50: " + getCheckMessage(MSG_LINE_NEW, "::"),
        };
        verifyWithInlineConfigParser(
            getPath("InputSeparatorWrapWithEmoji.java"), expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "18:44: " + getCheckMessage(MSG_LINE_NEW, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapSetOptionTrim.java"), expected);
    }

    @Test
    public void testCommaOnNewLine() throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_LINE_NEW, ","),
            "21:26: " + getCheckMessage(MSG_LINE_NEW, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputSeparatorWrapForTestTrailingWhitespace.java"), expected);
    }
}
