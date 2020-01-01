////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(ModifiedControlVariableCheck.class);
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_KEY, "i"),
            "17:15: " + getCheckMessage(MSG_KEY, "i"),
            "20:37: " + getCheckMessage(MSG_KEY, "i"),
            "21:17: " + getCheckMessage(MSG_KEY, "i"),
            "49:15: " + getCheckMessage(MSG_KEY, "s"),
            "56:14: " + getCheckMessage(MSG_KEY, "m"),
            "67:15: " + getCheckMessage(MSG_KEY, "i"),
            "68:15: " + getCheckMessage(MSG_KEY, "k"),
            "78:15: " + getCheckMessage(MSG_KEY, "v"),
        };
        verify(checkConfig, getPath("InputModifiedControlVariableBothForLoops.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ModifiedControlVariableCheck.class);
        checkConfig.addAttribute("skipEnhancedForLoopVariable", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputModifiedControlVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ModifiedControlVariableCheck.class);

        final String[] expected = {
            "9:18: " + getCheckMessage(MSG_KEY, "line"),
        };
        verify(checkConfig, getPath("InputModifiedControlVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariable2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ModifiedControlVariableCheck.class);
        checkConfig.addAttribute("skipEnhancedForLoopVariable", "true");

        final String[] expected = {
            "14:18: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputModifiedControlVariableEnhancedForLoopVariable2.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testImproperToken() {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();

        final DetailAstImpl classDefAst = new DetailAstImpl();
        classDefAst.setType(TokenTypes.CLASS_DEF);

        try {
            check.visitToken(classDefAst);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }

        try {
            check.leaveToken(classDefAst);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
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

        assertTrue(methodDef.isPresent(), "Ast should contain METHOD_DEF");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, methodDef.get(),
                "variableStack",
                variableStack -> ((Collection<Set<String>>) variableStack).isEmpty()),
                "State is not cleared on beginTree");
    }

}
