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

import static com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck.MSG_KEY_VOID;
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

public class ReturnCountCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/returncount";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ReturnCountCheck.class);
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_VOID, 7, 1),
            "30:5: " + getCheckMessage(MSG_KEY_VOID, 2, 1),
            "35:17: " + getCheckMessage(MSG_KEY_VOID, 6, 1),
            "49:5: " + getCheckMessage(MSG_KEY, 7, 2),
        };
        verify(checkConfig, getPath("InputReturnCountSwitches.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("format", "^$");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_KEY, 7, 2),
            "18:5: " + getCheckMessage(MSG_KEY_VOID, 7, 1),
            "30:5: " + getCheckMessage(MSG_KEY_VOID, 2, 1),
            "35:17: " + getCheckMessage(MSG_KEY_VOID, 6, 1),
            "49:5: " + getCheckMessage(MSG_KEY, 7, 2),
        };
        verify(checkConfig, getPath("InputReturnCountSwitches.java"), expected);
    }

    @Test
    public void testMethodsAndLambdas() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("max", "1");
        final String[] expected = {
            "15:55: " + getCheckMessage(MSG_KEY, 2, 1),
            "27:49: " + getCheckMessage(MSG_KEY, 2, 1),
            "34:42: " + getCheckMessage(MSG_KEY, 3, 1),
            "41:5: " + getCheckMessage(MSG_KEY, 2, 1),
            "49:57: " + getCheckMessage(MSG_KEY, 2, 1),
        };
        verify(checkConfig, getPath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testLambdasOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "34:42: " + getCheckMessage(MSG_KEY, 3, 2),
        };
        verify(checkConfig, getPath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, 3, 2),
            "33:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "41:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "56:5: " + getCheckMessage(MSG_KEY, 3, 2),
        };
        verify(checkConfig, getPath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testWithReturnOnlyAsTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_RETURN");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testImproperToken() {
        final ReturnCountCheck check = new ReturnCountCheck();

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

    @Test
    public void testMaxForVoid() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("max", "2");
        checkConfig.addAttribute("maxForVoid", "0");
        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "8:5: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "14:5: " + getCheckMessage(MSG_KEY_VOID, 2, 0),
            "30:5: " + getCheckMessage(MSG_KEY, 3, 2),
            "41:5: " + getCheckMessage(MSG_KEY_VOID, 2, 0),
        };
        verify(checkConfig, getPath("InputReturnCountVoid.java"), expected);
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
        final ReturnCountCheck check = new ReturnCountCheck();
        final Optional<DetailAST> methodDef = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputReturnCountVoid.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.METHOD_DEF);

        assertTrue(methodDef.isPresent(), "Ast should contain METHOD_DEF");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, methodDef.get(), "contextStack",
                contextStack -> ((Collection<Set<String>>) contextStack).isEmpty()),
                "State is not cleared on beginTree");
    }

}
