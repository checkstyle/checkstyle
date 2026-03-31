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

public class LocalVariableNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/localvariablename";
    }

    @Test
    public void testGetAcceptableTokens() {
        final LocalVariableNameCheck localVariableNameCheck = new LocalVariableNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};

        assertWithMessage("Default acceptable tokens are invalid")
            .that(localVariableNameCheck.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultOne()
            throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "40:13: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
            "51:18: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "53:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InnerBlockVariable", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableName1two.java"), expected);
    }

    @Test
    public void testDefaultTwo()
            throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "28:21: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableName2one.java"), expected);
    }

    @Test
    public void testInnerClass()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableNameInnerClass.java"), expected);
    }

    @Test
    public void testLoopVariables()
            throws Exception {

        final String pattern = "^[a-z]{2,}[a-zA-Z0-9]*$";

        final String[] expected = {
            "20:29: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "23:17: " + getCheckMessage(MSG_INVALID_PATTERN, "A", pattern),
            "25:21: " + getCheckMessage(MSG_INVALID_PATTERN, "i", pattern),
            "31:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Index", pattern),
            "48:32: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "51:32: " + getCheckMessage(MSG_INVALID_PATTERN, "B", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableNameOneCharInitVarName.java"), expected);
    }

    @Test
    public void testUnnamedVariables() throws Exception {
        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
            "21:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_result", pattern),
            "37:22: " + getCheckMessage(MSG_INVALID_PATTERN, "__", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputLocalVariableNameUnnamedVariables.java"), expected);

    }

}
