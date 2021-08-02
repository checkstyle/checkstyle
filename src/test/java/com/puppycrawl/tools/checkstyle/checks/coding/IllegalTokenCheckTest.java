////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

public class IllegalTokenCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltoken";
    }

    @Test
    public void testCheckWithDefaultSettings()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenCheck.class);
        final String[] expected = {
            "36:14: " + getCheckMessage(MSG_KEY, "label:"),
            "38:25: " + getCheckMessage(MSG_KEY, "anotherLabel:"),
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testPreviouslyIllegalTokens()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH,POST_INC,POST_DEC");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "switch"),
            "21:18: " + getCheckMessage(MSG_KEY, "--"),
            "22:18: " + getCheckMessage(MSG_KEY, "++"),
        };
        verify(checkConfig, getPath("InputIllegalTokens2.java"), expected);
    }

    @Test
    public void testNative() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_NATIVE");
        final String[] expected = {
            "27:12: " + getCheckMessage(MSG_KEY, "native"),
        };
        verify(checkConfig, getPath("InputIllegalTokens3.java"), expected);
    }

    @Test
    public void testCommentContentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "COMMENT_CONTENT");

        final String path = getPath("InputIllegalTokens4.java");
        final String lineSeparator =
                CheckUtil.getLineSeparatorForFile(path, StandardCharsets.UTF_8);
        final String[] expected = {
            "1:3: " + getCheckMessage(MSG_KEY,
                        JavadocUtil.escapeAllControlChars(
                            lineSeparator
                            + "IllegalToken\\ntokens = COMMENT_CONTENT"
                            + lineSeparator
                            + lineSeparator
                            + lineSeparator)),
            "10:3: " + getCheckMessage(MSG_KEY,
                        JavadocUtil.escapeAllControlChars(
                            "* // violation" + lineSeparator
                            + " * Test for illegal tokens"
                            + lineSeparator + " ")),
            "38:29: " + getCheckMessage(MSG_KEY,
                        JavadocUtil.escapeAllControlChars(
                            " some comment href" + lineSeparator)),
            "42:28: " + getCheckMessage(MSG_KEY,
                        JavadocUtil.escapeAllControlChars(
                            " some a href" + lineSeparator)),
        };
        verify(checkConfig, path, expected);
    }

    @Test
    public void testBlockCommentBeginToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "BLOCK_COMMENT_BEGIN");

        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_KEY, "/*"),
            "10:1: " + getCheckMessage(MSG_KEY, "/*"),
        };
        verify(checkConfig, getPath("InputIllegalTokens5.java"), expected);
    }

    @Test
    public void testBlockCommentEndToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "BLOCK_COMMENT_END");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, "*/"),
            "12:2: " + getCheckMessage(MSG_KEY, "*/"),
        };
        verify(checkConfig, getPath("InputIllegalTokens6.java"), expected);
    }

    @Test
    public void testSingleLineCommentToken()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalTokenCheck.class);
        checkConfig.addProperty("tokens", "SINGLE_LINE_COMMENT");

        final String[] expected = {
            "38:27: " + getCheckMessage(MSG_KEY, "//"),
            "42:26: " + getCheckMessage(MSG_KEY, "//"),
        };
        verify(checkConfig, getPath("InputIllegalTokens7.java"), expected);
    }

}
