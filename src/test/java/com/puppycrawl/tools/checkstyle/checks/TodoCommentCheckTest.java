////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TodoCommentCheckTest
    extends BaseCheckTestSupport {

    @Test
    public void testGetRequiredTokens() {
        TodoCommentCheck checkObj = new TodoCommentCheck();
        int[] expected = new int[] {TokenTypes.COMMENT_CONTENT};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TodoCommentCheck.class);
        checkConfig.addAttribute("format", "FIXME:");
        final String[] expected = {
            "161: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "162: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "163: " + getCheckMessage(MSG_KEY, "FIXME:"),
            "167: " + getCheckMessage(MSG_KEY, "FIXME:"),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        int[] expected = {TokenTypes.COMMENT_CONTENT };
        TodoCommentCheck check = new TodoCommentCheck();
        int[] actual = check.getAcceptableTokens();
        assertTrue(actual.length == 1);
        assertArrayEquals(expected, actual);
    }
}
