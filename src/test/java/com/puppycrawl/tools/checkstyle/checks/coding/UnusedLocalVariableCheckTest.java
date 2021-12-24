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

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
            TokenTypes.LITERAL_FOR,
            TokenTypes.OBJBLOCK,
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
            TokenTypes.LITERAL_FOR,
            TokenTypes.OBJBLOCK,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "32:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "35:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "testInLambdas"),
            "37:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "coding"),
            "38:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "InputUnusedLocalVariable"),
            "54:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "58:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "66:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "90:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "92:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "96:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "106:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "109:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "116:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "130:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "133:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "145:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "146:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "158:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "161:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "181:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "182:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Test"),
            "183:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "203:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "214:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "215:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "216:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "247:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

    @Test
    public void testClearState() throws Exception {
        final UnusedLocalVariableCheck check = new UnusedLocalVariableCheck();
        final Optional<DetailAST> methodDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputUnusedLocalVariable.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.METHOD_DEF);
        assertWithMessage("Ast should contain METHOD_DEF")
                .that(methodDef.isPresent())
                .isTrue();
        final DetailAST variableDef = methodDef.get().getLastChild()
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, variableDef,
                        "variables",
                        variables -> ((Collection<?>) variables).isEmpty()))
                .isTrue();
    }
}
