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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck.MSG_KEY;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class IllegalTokenTextCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltokentext";
    }

    @Test
    public void testCaseSensitive()
            throws Exception {
        final String[] expected = {
            "34:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testCaseInSensitive()
            throws Exception {
        final String[] expected = {
            "34:28: " + getCheckMessage(MSG_KEY, "a href"),
            "35:32: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokenTextTokens2.java"), expected);
    }

    @Test
    public void testCustomMessage()
            throws Exception {

        final String[] expected = {
            "34:28: " + "My custom message",
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokenTextTokens3.java"), expected);
    }

    @Test
    public void testNullCustomMessage()
            throws Exception {

        final String[] expected = {
            "34:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokenTextTokens4.java"), expected);
    }

    @Test
    public void testIllegalTokenTextTextBlocks() throws Exception {

        final String[] expected = {
            "16:28: " + getCheckMessage(MSG_KEY, "a href"),
            "19:32: " + getCheckMessage(MSG_KEY, "a href"),
            "31:37: " + getCheckMessage(MSG_KEY, "a href"),
            "36:37: " + getCheckMessage(MSG_KEY, "a href"),
            "42:54: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalTokenTextTextBlocks.java"), expected);
    }

    @Test
    public void testIllegalTokenTextTextBlocksQuotes() throws Exception {

        final String[] expected = {
            "16:28: " + getCheckMessage(MSG_KEY, "\""),
            "17:33: " + getCheckMessage(MSG_KEY, "\""),
            "19:32: " + getCheckMessage(MSG_KEY, "\""),
            "21:36: " + getCheckMessage(MSG_KEY, "\""),
            "31:37: " + getCheckMessage(MSG_KEY, "\""),
            "36:37: " + getCheckMessage(MSG_KEY, "\""),
            "42:42: " + getCheckMessage(MSG_KEY, "\""),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalTokenTextTextBlocksQuotes.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
        assertWithMessage("Comments are also TokenType token")
                .that(check.isCommentNodesRequired())
                .isTrue();
    }

    @Test
    public void testCommentToken()
            throws Exception {

        final String[] expected = {
            "1:3: " + getCheckMessage(MSG_KEY, "a href"),
            "45:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokenTextTokens5.java"), expected);
    }

    @Test
    public void testOrderOfProperties() {
        // pure class must be used as configuration doesn't guarantee order of
        // attributes
        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        check.setFormat("test");
        check.setIgnoreCase(true);
        final Pattern actual = TestUtil.getInternalState(check, "format");
        assertWithMessage("should match")
            .that(actual.flags())
            .isEqualTo(Pattern.CASE_INSENSITIVE);
        assertWithMessage("should match")
            .that(actual.pattern())
            .isEqualTo("test");
    }

    @Test
    public void testAcceptableTokensMakeSense() {
        final int expectedTokenTypesTotalNumber = 185;
        assertWithMessage("Total number of TokenTypes has changed, acceptable tokens in"
                + " IllegalTokenTextCheck need to be reconsidered.")
            .that(TokenUtil.getTokenTypesTotalNumber())
            .isEqualTo(expectedTokenTypesTotalNumber);

        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        final int[] allowedTokens = check.getAcceptableTokens();
        final List<Integer> tokenTypesWithMutableText = Arrays.asList(
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
            TokenTypes.IDENT,
            TokenTypes.COMMENT_CONTENT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT
        );
        for (int tokenType : allowedTokens) {
            assertWithMessage(TokenUtil.getTokenName(tokenType) + " should not be allowed"
                    + " in this check as its text is a constant"
                    + " (IllegalTokenCheck should be used for such cases).")
                            .that(tokenTypesWithMutableText.contains(tokenType))
                            .isTrue();
        }
    }

}
