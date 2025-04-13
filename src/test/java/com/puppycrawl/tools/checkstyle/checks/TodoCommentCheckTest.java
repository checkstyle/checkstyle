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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TodoCommentCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/todocomment";
    }

    @Test
    public void testGetRequiredTokens() {
        final TodoCommentCheck checkObj = new TodoCommentCheck();
        final int[] expected = {TokenTypes.COMMENT_CONTENT};
        assertWithMessage("Required tokens differs from expected")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testItOne() throws Exception {
        final String[] expected = {
            "1:3: " + getCheckMessage(MSG_KEY, "FIXME:"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTodoCommentSimpleOne.java"), expected);
    }

    @Test
    public void testItTwo() throws Exception {
        final String[] expected = {
            "1:3: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "68:7: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "69:7: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "74:17: " + getCheckMessage(MSG_KEY, "FIXME:"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTodoCommentSimpleTwo.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expected = {TokenTypes.COMMENT_CONTENT };
        final TodoCommentCheck check = new TodoCommentCheck();
        final int[] actual = check.getAcceptableTokens();
        assertWithMessage("Amount of acceptable tokens differs from expected")
            .that(actual.length)
            .isEqualTo(1);
        assertWithMessage("Acceptable tokens differs from expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void test() throws Exception {
        final String[] expected = {
            "11:16: " + getCheckMessage(MSG_KEY, "TODO:"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTodoCommentDefault.java"), expected);
    }
}
