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
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.EXPR,
            TokenTypes.ELIST,
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
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.EXPR,
            TokenTypes.ELIST,
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
            "21:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "22:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "37:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "41:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "49:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "64:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "68:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "78:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "94:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

    @Test
    public void testChainedUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "20:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "27:26: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "38:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "43:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "46:44: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "62:29: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "66:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "69:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "82:28: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "aA"),
            "83:34: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a1_a"),
            "85:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "x"),
            "86:51: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "89:34: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "w"),
            "91:72: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
            "92:51: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "99:29: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "k"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableChainedCalls.java"), expected);
    }
}
