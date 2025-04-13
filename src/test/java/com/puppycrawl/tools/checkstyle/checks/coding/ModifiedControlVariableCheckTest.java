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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModifiedControlVariableCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/modifiedcontrolvariable";
    }

    @Test
    public void testModifiedControlVariable() throws Exception {
        final String[] expected = {
            "17:14: " + getCheckMessage(MSG_KEY, "i"),
            "20:15: " + getCheckMessage(MSG_KEY, "i"),
            "23:37: " + getCheckMessage(MSG_KEY, "i"),
            "24:17: " + getCheckMessage(MSG_KEY, "i"),
            "52:15: " + getCheckMessage(MSG_KEY, "s"),
            "59:14: " + getCheckMessage(MSG_KEY, "m"),
            "70:15: " + getCheckMessage(MSG_KEY, "i"),
            "71:15: " + getCheckMessage(MSG_KEY, "k"),
            "81:15: " + getCheckMessage(MSG_KEY, "v"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifiedControlVariableBothForLoops.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputModifiedControlVariableEnhancedForLoopVariable.java"),
                expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {

        final String[] expected = {
            "16:18: " + getCheckMessage(MSG_KEY, "line"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifiedControlVariableEnhancedForLoopVariable3.java"),
                expected);
    }

    @Test
    public void testEnhancedForLoopVariable2() throws Exception {

        final String[] expected = {
            "21:18: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifiedControlVariableEnhancedForLoopVariable2.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();
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
    public void testImproperToken() {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();

        final DetailAstImpl classDefAst = new DetailAstImpl();
        classDefAst.setType(TokenTypes.CLASS_DEF);

        try {
            check.visitToken(classDefAst);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }

        try {
            check.leaveToken(classDefAst);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testVariousAssignments() throws Exception {
        final String[] expected = {
            "14:15: " + getCheckMessage(MSG_KEY, "i"),
            "15:15: " + getCheckMessage(MSG_KEY, "k"),
            "21:15: " + getCheckMessage(MSG_KEY, "i"),
            "22:15: " + getCheckMessage(MSG_KEY, "k"),
            "28:15: " + getCheckMessage(MSG_KEY, "i"),
            "29:15: " + getCheckMessage(MSG_KEY, "k"),
            "35:15: " + getCheckMessage(MSG_KEY, "i"),
            "36:15: " + getCheckMessage(MSG_KEY, "k"),
            "42:15: " + getCheckMessage(MSG_KEY, "i"),
            "43:15: " + getCheckMessage(MSG_KEY, "k"),
            "48:15: " + getCheckMessage(MSG_KEY, "i"),
            "49:15: " + getCheckMessage(MSG_KEY, "k"),
            "55:15: " + getCheckMessage(MSG_KEY, "i"),
            "56:15: " + getCheckMessage(MSG_KEY, "k"),
            "62:15: " + getCheckMessage(MSG_KEY, "i"),
            "63:15: " + getCheckMessage(MSG_KEY, "k"),
            "69:15: " + getCheckMessage(MSG_KEY, "i"),
            "70:15: " + getCheckMessage(MSG_KEY, "k"),
            "76:15: " + getCheckMessage(MSG_KEY, "i"),
            "77:15: " + getCheckMessage(MSG_KEY, "k"),
            "83:14: " + getCheckMessage(MSG_KEY, "i"),
            "84:14: " + getCheckMessage(MSG_KEY, "k"),
        };
        verifyWithInlineConfigParser(
                getPath("InputModifiedControlVariableTestVariousAssignments.java"),
                expected);
    }

    @Test
    public void testRecordDecompositionInEnhancedForLoop() throws Exception {
        final String[] expected = {
            "32:15: " + getCheckMessage(MSG_KEY, "p"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModifiedControlVariableRecordDecomposition.java"),
                expected);
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClearState() throws Exception {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();
        final Optional<DetailAST> methodDef = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(
                new File(getPath("InputModifiedControlVariableEnhancedForLoopVariable.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.OBJBLOCK);

        assertWithMessage("Ast should contain METHOD_DEF")
                .that(methodDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        methodDef.orElseThrow(), "variableStack",
                        variableStack -> ((Collection<Set<String>>) variableStack).isEmpty()))
                .isTrue();
    }

}
