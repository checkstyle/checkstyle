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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheck.MSG_WS_NOT_FOLLOWED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyForIteratorPadCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptyforiteratorpad";
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyForIteratorPadCheck checkObj = new EmptyForIteratorPadCheck();
        final int[] expected = {TokenTypes.FOR_ITERATOR};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "30:32: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
            "46:33: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
            "58:12: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyForIteratorPad.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception {
        final String[] expected = {
            "26:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyForIteratorPad1.java"), expected);
    }

    @Test
    public void testWithEmoji() throws Exception {
        final String[] expected = {
            "24:40: " + getCheckMessage(MSG_WS_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyForIteratorPadWithEmoji.java"), expected);

    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyForIteratorPadCheck emptyForIteratorPadCheckObj = new EmptyForIteratorPadCheck();
        final int[] actual = emptyForIteratorPadCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.FOR_ITERATOR,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testInvalidOption() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(getPath("InputEmptyForIteratorPad2.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.EmptyForIteratorPadCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "20:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyForIteratorPadToCheckTrimFunctionInOptionProperty.java"),
                expected);

    }

    @Test
    public void testUppercaseOptionProperty() throws Exception {
        final String[] expected = {
            "20:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyForIteratorPadToCheckUppercaseFunctionInOptionProperty.java"),
                expected);

    }
}
