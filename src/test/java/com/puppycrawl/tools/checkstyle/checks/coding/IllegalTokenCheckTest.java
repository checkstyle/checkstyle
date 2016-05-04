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

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalTokenCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testCheckWithDefaultSettings()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenCheck.class);
        final String[] expected = {
            "29:14: " + getCheckMessage(MSG_KEY, "label:"),
            "31:25: " + getCheckMessage(MSG_KEY, "anotherLabel:"),
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testPreviouslyIllegalTokens()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH,POST_INC,POST_DEC");
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_KEY, "switch"),
            "14:18: " + getCheckMessage(MSG_KEY, "--"),
            "15:18: " + getCheckMessage(MSG_KEY, "++"),
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testNative() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        final String[] expected = {
            "20:12: " + getCheckMessage(MSG_KEY, "native"),
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testCommentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalTokenCheck.class);
        checkConfig.addAttribute("tokens", "COMMENT_CONTENT");

        final String[] expected = {
                "3:3: " + getCheckMessage(MSG_KEY, "*\n" +
                        " * Test for illegal tokens\n" +
                        " "),
                "31:30: " + getCheckMessage(MSG_KEY, " some comment href"),
                "35:28: " + getCheckMessage(MSG_KEY, " some a href"),
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

}
