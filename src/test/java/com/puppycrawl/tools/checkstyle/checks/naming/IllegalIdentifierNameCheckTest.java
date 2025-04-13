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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalIdentifierNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/illegalidentifiername";
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalIdentifierNameCheck illegalIdentifierNameCheck =
            new IllegalIdentifierNameCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
            TokenTypes.LAMBDA,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(illegalIdentifierNameCheck.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final IllegalIdentifierNameCheck illegalIdentifierNameCheck =
            new IllegalIdentifierNameCheck();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;

        assertWithMessage("Default required tokens are invalid")
            .that(illegalIdentifierNameCheck.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testIllegalIdentifierNameDefault() throws Exception {

        final String format = "^(?!var$|\\S*\\$)\\S+$";

        final String[] expected = {
            "57:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "59:13: " + getCheckMessage(MSG_INVALID_PATTERN, "$amt", format),
            "74:52: " + getCheckMessage(MSG_INVALID_PATTERN, "yield$text", format),
            "74:74: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierName.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameOpenTransitive() throws Exception {
        final String format = "(?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$";

        final String[] expected = {
            "21:25: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "22:24: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "28:13: " + getCheckMessage(MSG_INVALID_PATTERN, "open", format),
            "30:21: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "45:9: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "57:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "59:13: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "61:16: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "63:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Record", format),
            "64:25: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "73:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Transitive", format),
            "76:37: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "76:56: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "76:72: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameOpenTransitive.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameParameterReceiver() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameParameterReceiver.java"),
            expected);
    }

    @Test
    public void testIllegalIdentifierNameUnderscore() throws Exception {
        final String format = "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$";

        final String[] expected = {
            "18:12: " + getCheckMessage(MSG_INVALID_PATTERN, "_", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameUnderscore.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameLambda() throws Exception {
        final String format = "^(?!var$|\\S*\\$)\\S+$";

        final String[] expected = {
            "19:39: " + getCheckMessage(MSG_INVALID_PATTERN, "param$", format),
            "20:40: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "32:9: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "42:47: " + getCheckMessage(MSG_INVALID_PATTERN, "te$t", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameLambda.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameUnnamedVariable() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameUnnamedVariables.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameRecordPattern() throws Exception {
        final String format = "^(?!var$|\\S*\\$)\\S+$";

        final String[] expected = {
            "16:36: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "23:39: " + getCheckMessage(MSG_INVALID_PATTERN, "permit$", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameRecordPattern.java"), expected);
    }
}
