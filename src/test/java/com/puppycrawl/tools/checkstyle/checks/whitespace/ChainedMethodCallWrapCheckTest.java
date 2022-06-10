///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ChainedMethodCallWrapCheck.MSG_TOO_MANY_SINGLE_LINE;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ChainedMethodCallWrapCheck.MSG_WRAP_METHOD_CALL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ChainedMethodCallWrapCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/chainedmethodcallwrap";
    }

    @Test
    public void testDefaultTokens() {
        final ChainedMethodCallWrapCheck check = new ChainedMethodCallWrapCheck();
        final int[] requiredTokens = {
            TokenTypes.METHOD_CALL,
        };
        assertWithMessage("Required tokens are invalid")
                .that(check.getRequiredTokens())
                .isEqualTo(requiredTokens);
    }

    @Test
    public void testChainedMethodCall() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputChainedMethodCallWrap.java"), expected);
    }

    @Test
    public void testChainedMethodCallWrapIdentifierPattern() throws Exception {
        final String[] expected = {
            "14:36: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "17:9: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 2, 1),
            "24:36: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "24:44: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "28:43: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "31:15: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputChainedMethodCallWrapIdentifierPattern.java"), expected);
    }

    @Test
    public void testChainedMethodCallWrapMaxSingleLineCalls() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 2),
        };
        verifyWithInlineConfigParser(
                getPath("InputChainedMethodCallWrapMaxSingleLineCalls.java"), expected);
    }

    @Test
    public void testMethodCallWrapNested() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 4, 3),
            "20:55: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "27:34: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 4, 3),
            "30:43: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "35:58: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "37:53: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
        };

        verifyWithInlineConfigParser(
                getPath("InputChainedMethodCallWrapNested.java"), expected);
    }

    @Test
    public void testTypecastMethodCalls() throws Exception {
        final String[] expected = {
            "15:48: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
            "19:32: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "27:22: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
            "30:23: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 5, 1),
            "33:26: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 5, 1),
            "36:25: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
            "40:22: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
            "42:37: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "42:59: " + getCheckMessage(MSG_WRAP_METHOD_CALL),
            "44:17: " + getCheckMessage(MSG_TOO_MANY_SINGLE_LINE, 3, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputChainedMethodCallWrapTypecast.java"), expected);
    }
}
