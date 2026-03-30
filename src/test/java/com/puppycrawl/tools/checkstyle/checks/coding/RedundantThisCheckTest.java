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
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_KEY_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_KEY_VARIABLE;

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
    public void testRedundantThisWithInstanceVariable() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY_VARIABLE, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThis1.java"), expected);
    }

    @Test
    public void testRedundantThisWithInstanceVariable2() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY_METHOD, "helper"),
            "21:9: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
            "23:28: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThis2.java"), expected);
    }

    @Test
    public void testRedundantThisInRecord() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
            "12:34: " + getCheckMessage(MSG_KEY_VARIABLE, "x"),
            "19:16: " + getCheckMessage(MSG_KEY_VARIABLE, "x"),
            "23:16: " + getCheckMessage(MSG_KEY_METHOD, "describe"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisRecord.java"), expected);
    }

    @Test
    public void testRedundantThisNested() throws Exception {
        final String[] expected = {
            "18:62: " + getCheckMessage(MSG_KEY_VARIABLE, "x"),
            "21:62: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
            "31:13: " + getCheckMessage(MSG_KEY_VARIABLE, "x"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisNestedClass.java"), expected);
    }

    @Test
    public void testRedundantThisChained() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
            "19:13: " + getCheckMessage(MSG_KEY_VARIABLE, "email"),
            "26:28: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
            "26:48: " + getCheckMessage(MSG_KEY_VARIABLE, "email"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisChained.java"), expected);
    }

    @Test
    public void testRedundantThisInstanceVariables() throws Exception {
        final String[] expected = {
            "11:20: " + getCheckMessage(MSG_KEY_VARIABLE, "name"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisInstantVariables.java"),
                expected
        );
    }

    @Test
    public void testRedundantThisValidThis() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisValidThis.java"), expected);
    }

    @Test
    public void testRedundantThisLocalVariables() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLocalVariables.java"), expected);
    }

    @Test
    public void testRedundantThisLocalVariablesScope() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY_VARIABLE, "age"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLocalVariablesScope.java"), expected);
    }

    @Test
    public void testRedundantThisLocalLoopsAndStreams() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_KEY_VARIABLE, "age"),
            "24:13: " + getCheckMessage(MSG_KEY_VARIABLE, "age"),
            "36:28: " + getCheckMessage(MSG_KEY_METHOD, "display"),
            "42:31: " + getCheckMessage(MSG_KEY_METHOD, "display"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLoopsAndStreams.java"), expected);
    }

    @Test
    public void testRedundantThisInConstructor() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY_VARIABLE, "a"),
            "21:9: " + getCheckMessage(MSG_KEY_VARIABLE, "b"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisInConstructor.java"), expected);
    }

    @Test
    public void testRedundantThisQualifiedConstructorParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisQualifiedConstructorParameter.java"),
                expected
        );
    }

}
