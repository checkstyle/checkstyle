////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = {
            "24:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testCaseInSensitive()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "24:28: " + getCheckMessage(MSG_KEY, "a href"),
            "25:32: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testCustomMessage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");

        checkConfig.addAttribute("message", "My custom message");
        final String[] expected = {
            "24:28: " + "My custom message",
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testNullCustomMessage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");

        checkConfig.addAttribute("message", null);
        final String[] expected = {
            "24:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
        assertTrue(check.isCommentNodesRequired(), "Comments are also TokenType token");
    }

    @Test
    public void testCommentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "COMMENT_CONTENT");
        checkConfig.addAttribute("format", "a href");

        checkConfig.addAttribute("message", null);
        final String[] expected = {
            "35:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testOrderOfProperties() throws Exception {
        // pure class must be used as configuration doesn't guarantee order of
        // attributes
        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        check.setFormat("test");
        check.setIgnoreCase(true);
        final Pattern actual = (Pattern) TestUtil.getClassDeclaredField(
                IllegalTokenTextCheck.class, "format").get(check);
        assertEquals(Pattern.CASE_INSENSITIVE, actual.flags(), "should match");
        assertEquals("test", actual.pattern(), "should match");
    }

    @Test
    public void testAcceptableTokensMakeSense() {
        final int expectedTokenTypesTotalNumber = 169;
        assertEquals(expectedTokenTypesTotalNumber, TokenUtil.getTokenTypesTotalNumber(),
                "Total number of TokenTypes has changed, acceptable tokens in"
                + " IllegalTokenTextCheck need to be reconsidered.");

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
            TokenTypes.CHAR_LITERAL
        );
        for (int tokenType : allowedTokens) {
            assertTrue(tokenTypesWithMutableText.contains(tokenType),
                TokenUtil.getTokenName(tokenType) + " should not be allowed"
                + " in this check as its text is a constant (IllegalTokenCheck should be used for"
                + " such cases).");
        }
    }

}
