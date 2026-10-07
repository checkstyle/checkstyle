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

import java.util.Arrays;
import java.util.List;

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
            "12:13: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "16:9: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "26:9: " + getCheckMessage(MSG_KEY_FIELD, "a"),
            "27:9: " + getCheckMessage(MSG_KEY_FIELD, "b"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisBasic.java"), expected);
    }

    @Test
    public void testConstructorShadow() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisConstructorShadow.java"), expected);
    }

    @Test
    public void testLocalVariableScope() throws Exception {
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY_FIELD, "age"),
            "57:13: " + getCheckMessage(MSG_KEY_FIELD, "scanner"),
            "60:13: " + getCheckMessage(MSG_KEY_FIELD, "e"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLocalVariableScope.java"), expected);
    }

    @Test
    public void testMethodCall() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_METHOD, "helper"),
            "19:9: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "31:13: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "34:13: " + getCheckMessage(MSG_KEY_FIELD, "email"),
            "39:28: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "39:48: " + getCheckMessage(MSG_KEY_FIELD, "email"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisMethodCall.java"), expected);
    }

    @Test
    public void testNestedClass() throws Exception {
        final String[] expected = {
            "19:62: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "22:62: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "32:13: " + getCheckMessage(MSG_KEY_FIELD, "x"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisNestedClass.java"), expected);
    }

    @Test
    public void testRecord() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_KEY_FIELD, "name"),
            "13:34: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "20:16: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "24:16: " + getCheckMessage(MSG_KEY_METHOD, "describe"),
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
    public void testEnumAndInterface() throws Exception {
        final String[] expected = {
            "17:32: " + getCheckMessage(MSG_KEY_FIELD, "name"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisEnumAndInterface.java"), expected);
    }

    @Test
    public void testPatternVariables() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisPatternVariables.java"), expected);
    }

    @Test
    public void testAdjacent() throws Exception {
        final String[] expected = {
            "12:21: " + getCheckMessage(MSG_KEY_FIELD, "x"),
            "47:13: " + getCheckMessage(MSG_KEY_FIELD, "flag"),
            "61:26: " + getCheckMessage(MSG_KEY_FIELD, "z"),
            "67:26: " + getCheckMessage(MSG_KEY_FIELD, "z"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRedundantThisAdjacent.java"), expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final String filePath1 = getPath("InputRedundantThisMultipleInputsFirst.java");
        final String filePath2 = getPath("InputRedundantThisMultipleInputsSecond.java");

        final List<String> expectedFromFile1 = Arrays.asList(
            "14:16: " + getCheckMessage(MSG_KEY_FIELD, "a"));
        final List<String> expectedFromFile2 = Arrays.asList(
            "14:9: " + getCheckMessage(MSG_KEY_FIELD, "x"));

        verifyWithInlineConfigParser(filePath1, filePath2, expectedFromFile1, expectedFromFile2);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_FIELD, "a"),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputRedundantThisCompact.java"),
                expected);
    }

    @Test
    public void testCheckMethodsCompactSourceFile() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY_METHOD, "method"),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputRedundantThisCompactCheckMethodCall.java"),
                expected);
    }

}
