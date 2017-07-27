////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

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
            createCheckConfig(IllegalTokenTextCheck.class);
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
            createCheckConfig(IllegalTokenTextCheck.class);
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
            createCheckConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");

        final String customMessage = "My custom message";
        checkConfig.addAttribute("message", customMessage);
        final String[] expected = {
            "24:28: " + customMessage,
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testNullCustomMessage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenTextCheck.class);
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
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertNotNull("Required tokens should not be null", check.getRequiredTokens());
        Assert.assertTrue("Comments are also TokenType token", check.isCommentNodesRequired());
    }

    @Test
    public void testCommentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "COMMENT_CONTENT");
        checkConfig.addAttribute("format", "a href");

        checkConfig.addAttribute("message", null);
        final String[] expected = {
            "35:28: " + getCheckMessage(MSG_KEY, "a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokenTextTokens.java"), expected);
    }

    @Test
    public void testAcceptableTokensMakeSense() {
        final int expectedTokenTypesTotalNumber = 169;
        Assert.assertEquals("Total number of TokenTypes has changed, acceptable tokens in"
                + " IllegalTokenTextCheck need to be reconsidered.",
            expectedTokenTypesTotalNumber, TokenUtils.getTokenTypesTotalNumber());

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
            Assert.assertTrue(TokenUtils.getTokenName(tokenType) + " should not be allowed"
                + " in this check as its text is a constant (IllegalTokenCheck should be used for"
                + " such cases).", tokenTypesWithMutableText.contains(tokenType));
        }
    }
}
