///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_KEY_FIELD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_KEY_METHOD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RedundantThisCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/redundantthis";
    }

    @Test
    public void testTokensNotNull() {
        final RedundantThisCheck check = new RedundantThisCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testRedundantThisBasic() throws Exception {
        final String[] expected = {
            "11:13: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "15:9: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "25:9: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "26:9: " + getCheckMessage(MSG_KEY_FIELD, "b"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisBasic.java"), expected);
    }

    @Test
    public void testRedundantThisConstructorShadow() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisConstructorShadow.java"), expected);
    }

    @Test
    public void testRedundantThisLocalVariableScope() throws Exception {
        final String[] expected = {
            "33:9: " + getCheckMessage(MSG_KEY_FIELD, "age"),
            "57:13: " + getCheckMessage(MSG_KEY_FIELD, "e"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLocalVariableScope.java"), expected);
    }

    @Test
    public void testRedundantThisMethodCall() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_METHOD, "helper"),
            "19:9: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "21:28: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "33:13: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "36:13: " + getCheckMessage(MSG_KEY_FIELD, "email"),
            "41:28: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "41:48: " + getCheckMessage(MSG_KEY_FIELD, "email"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisMethodCall.java"), expected);
    }

    @Test
    public void testRedundantThisLoopsAndStreams() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_KEY_FIELD, "age"),
            "24:13: " + getCheckMessage(MSG_KEY_FIELD, "age"),
            "36:28: " + getCheckMessage(MSG_KEY_METHOD, "display"),
            "42:31: " + getCheckMessage(MSG_KEY_METHOD, "display"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLoopsAndStreams.java"), expected);
    }

    @Test
    public void testRedundantThisNestedClass() throws Exception {
        final String[] expected = {
            "18:62: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "21:62: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "31:13: " + getCheckMessage(MSG_KEY_FIELD, "x"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisNestedClass.java"), expected);
    }

    @Test
    public void testRedundantThisRecord() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "12:34: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "19:16: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "23:16: " + getCheckMessage(MSG_KEY_METHOD, "describe"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisRecord.java"), expected);
    }

    @Test
    public void testRedundantThisValidThis() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisValidThis.java"), expected);
    }

}
