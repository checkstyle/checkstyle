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
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "21:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "36:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "40:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "48:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "58:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "62:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "72:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),

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
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableChainedCalls.java"), expected);
    }
}
