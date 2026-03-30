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
    public void testBasic() throws Exception {
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
    public void testConstructorShadow() throws Exception {
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY_FIELD, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisConstructorShadow.java"), expected);
    }

    @Test
    public void testLocalVariableScope() throws Exception {
        final String[] expected = {
            "33:9: " + getCheckMessage(MSG_KEY_FIELD, "age"),
            "57:13: " + getCheckMessage(MSG_KEY_FIELD, "e"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLocalVariableScope.java"), expected);
    }

    @Test
    public void testMethodCall() throws Exception {
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
    public void testLoopsAndStreams() throws Exception {
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
    public void testNestedClass() throws Exception {
        final String[] expected = {
            "18:62: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "21:62: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "31:13: " + getCheckMessage(MSG_KEY_FIELD, "x"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisNestedClass.java"), expected);
    }

    @Test
    public void testRecord() throws Exception {
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
    public void testValidThis() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisValidThis.java"), expected);
    }

    @Test
    public void testPatternVariables() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "20:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "39:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "44:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "51:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "56:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "61:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "64:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "67:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "69:36: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "72:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "74:39: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "93:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "101:17: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "107:31: " + getCheckMessage(MSG_KEY_FIELD, "s"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisPatternVariables.java"), expected);
    }

    @Test
    public void testPatternVariables2() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "20:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "29:9: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "50:17: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "58:17: " + getCheckMessage(MSG_KEY_FIELD, "s"),
            "67:13: " + getCheckMessage(MSG_KEY_FIELD, "s"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisPatternVariables2.java"), expected);
    }

    @Test
    public void testEnumAndInterface() throws Exception {
        final String[] expected = {
            "16:32: " + getCheckMessage(MSG_KEY_FIELD, "name"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisEnumAndInterface.java"), expected);
    }

}
