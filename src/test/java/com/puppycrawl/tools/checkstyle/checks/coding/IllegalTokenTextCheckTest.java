////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalTokenTextCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
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
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
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
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
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
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
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
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTokenTextCheck check = new IllegalTokenTextCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
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
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

}
