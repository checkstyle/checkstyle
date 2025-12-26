///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class IllegalTokenCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltoken";
    }

    @Test
    void illegalTokensCheckDefaultTokenLabel() throws Exception {
        final String[] expected = {
            "36:14: " + getCheckMessage(MSG_KEY, "label:"),
            "38:25: " + getCheckMessage(MSG_KEY, "anotherLabel:"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckDefaultTokenLabel.java"), expected);
    }

    @Test
    void illegalTokensCheckSwitchAndPostIncDec() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "switch"),
            "21:18: " + getCheckMessage(MSG_KEY, "--"),
            "22:18: " + getCheckMessage(MSG_KEY, "++"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckSwitchAndPostIncDec.java"), expected);
    }

    @Test
    void illegalTokensCheckTokenNative() throws Exception {
        final String[] expected = {
            "27:12: " + getCheckMessage(MSG_KEY, "native"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckTokenNative.java"), expected);
    }

    @Test
    void illegalTokensCheckCommentsContent() throws Exception {

        final String path = getPath("InputIllegalTokensCheckCommentsContent.java");
        final String[] expected = {
            "1:3: " + getCheckMessage(MSG_KEY,
                        "\\nIllegalToken\\ntokens = COMMENT_CONTENT\\n\\n\\n"),
            "10:3: " + getCheckMessage(MSG_KEY,
                        "*\\n * // violation 10"
                            + " lines above 'is not allowed'\\n"
                            + " * // violation 2 lines above 'is not allowed'\\n"
                            + " * Test for illegal tokens\\n "),
            "40:29: " + getCheckMessage(MSG_KEY,
                        " some comment href // violation, 'is not allowed'\\n"),
            "44:28: " + getCheckMessage(MSG_KEY,
                        " some a href // violation, 'is not allowed'\\n"),
        };
        verifyWithInlineConfigParser(path, expected);
    }

    @Test
    void illegalTokensCheckBlockCommentBegin() throws Exception {

        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_KEY, "/*"),
            "10:1: " + getCheckMessage(MSG_KEY, "/*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckBlockCommentBegin.java"), expected);
    }

    @Test
    void illegalTokensCheckBlockCommentEnd() throws Exception {

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, "*/"),
            "12:2: " + getCheckMessage(MSG_KEY, "*/"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckBlockCommentEnd.java"), expected);
    }

    @Test
    void illegalTokensCheckSingleLineComment() throws Exception {

        final String[] expected = {
            "38:27: " + getCheckMessage(MSG_KEY, "//"),
            "42:26: " + getCheckMessage(MSG_KEY, "//"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTokensCheckSingleLineComment.java"), expected);
    }

}
