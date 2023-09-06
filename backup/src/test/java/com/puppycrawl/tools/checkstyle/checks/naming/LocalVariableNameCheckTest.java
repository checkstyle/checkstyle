///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
    protected String getPackageLocation() {
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
    public void testDefault()
            throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "122:13: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
            "133:18: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "135:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InnerBlockVariable", pattern),
            "210:21: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableName.java"), expected);
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
            "19:29: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
            "22:17: " + getCheckMessage(MSG_INVALID_PATTERN, "A", pattern),
            "24:21: " + getCheckMessage(MSG_INVALID_PATTERN, "i", pattern),
            "30:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Index", pattern),
            "47:32: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "50:32: " + getCheckMessage(MSG_INVALID_PATTERN, "B", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputLocalVariableNameOneCharInitVarName.java"), expected);
    }

}
