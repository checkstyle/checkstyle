////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnusedLocalVariableCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedlocalvariable";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedLocalVariableCheck checkObj =
                new UnusedLocalVariableCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
        };
        assertWithMessage("Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnusedLocalVariableCheck typeParameterNameCheckObj =
                new UnusedLocalVariableCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "25:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "40:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "44:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "52:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "76:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "78:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "82:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "92:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "95:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "102:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "116:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "119:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "131:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "132:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "144:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "147:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "167:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "168:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Test"),
            "169:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }
}
