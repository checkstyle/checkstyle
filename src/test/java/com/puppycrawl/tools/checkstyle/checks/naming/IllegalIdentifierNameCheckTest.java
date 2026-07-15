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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalIdentifierNameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
            "58:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "61:13: " + getCheckMessage(MSG_INVALID_PATTERN, "$amt", format),
            "76:52: " + getCheckMessage(MSG_INVALID_PATTERN, "yield$text", format),
            "76:74: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalIdentifierName.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameOpenTransitive() throws Exception {
        final String format = "(?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$";

        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "24:24: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "31:13: " + getCheckMessage(MSG_INVALID_PATTERN, "open", format),
            "34:21: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "50:9: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "63:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "66:13: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "69:16: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "72:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Record", format),
            "74:25: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "84:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Transitive", format),
            "87:37: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "87:56: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "87:72: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalIdentifierNameOpenTransitive.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameParameterReceiver() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalIdentifierNameParameterReceiver.java"),
            expected);
    }

    @Test
    public void testIllegalIdentifierNameUnderscore() throws Exception {
        final String format = "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$";

        final String[] expected = {
            "19:12: " + getCheckMessage(MSG_INVALID_PATTERN, "_", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameUnderscore.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameLambda() throws Exception {
        final String format = "^(?!var$|\\S*\\$)\\S+$";

        final String[] expected = {
            "20:39: " + getCheckMessage(MSG_INVALID_PATTERN, "param$", format),
            "22:40: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "35:9: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "46:47: " + getCheckMessage(MSG_INVALID_PATTERN, "te$t", format),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalIdentifierNameLambda.java"), expected);
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
            "17:36: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "25:39: " + getCheckMessage(MSG_INVALID_PATTERN, "permit$", format),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalIdentifierNameRecordPattern.java"), expected);
    }

}
