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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ExecutableStatementCountCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/executablestatementcount";
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStatefulFieldsClearedOnBeginTree() throws Exception {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.STATIC_INIT);
        final ExecutableStatementCountCheck check = new ExecutableStatementCountCheck();
        assertTrue(
                TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "contextStack",
                    contextStack -> ((Collection<Context>) contextStack).isEmpty()),
                "Stateful field is not cleared after beginTree");
    }

    @Test
    public void testMaxZero() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "8:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "11:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "21:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "31:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "38:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "52:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "62:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "71:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "80:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "83:13: " + getCheckMessage(MSG_KEY, 1, 0),
            "94:29: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountMaxZero.java"), expected);
    }

    @Test
    public void testMethodDef() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "12:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "22:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "32:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "39:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "57:13: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountMethodDef.java"), expected);
    }

    @Test
    public void testCtorDef() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "CTOR_DEF");

        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "19:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountCtorDef.java"), expected);
    }

    @Test
    public void testStaticInit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "STATIC_INIT");

        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountStaticInit.java"), expected);
    }

    @Test
    public void testInstanceInit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "INSTANCE_INIT");

        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountInstanceInit.java"), expected);
    }

    @Test
    public void testVisitTokenWithWrongTokenType() {
        final ExecutableStatementCountCheck checkObj =
            new ExecutableStatementCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(
            new CommonHiddenStreamToken(TokenTypes.ENUM, "ENUM"));
        try {
            checkObj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("ENUM[0x-1]", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testLeaveTokenWithWrongTokenType() {
        final ExecutableStatementCountCheck checkObj =
            new ExecutableStatementCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(
            new CommonHiddenStreamToken(TokenTypes.ENUM, "ENUM"));
        try {
            checkObj.leaveToken(ast);
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("ENUM[0x-1]", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputExecutableStatementCountDefaultConfig.java"), expected);
    }

    @Test
    public void testExecutableStatementCountRecords() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ExecutableStatementCountCheck.class);
        checkConfig.addAttribute("max", "1");

        final int max = 1;

        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_KEY, 3, max),
            "20:9: " + getCheckMessage(MSG_KEY, 3, max),
            "29:9: " + getCheckMessage(MSG_KEY, 3, max),
            "37:9: " + getCheckMessage(MSG_KEY, 4, max),
            "47:9: " + getCheckMessage(MSG_KEY, 6, max),
            "61:17: " + getCheckMessage(MSG_KEY, 6, max),
        };

        verify(checkConfig,
                getNonCompilablePath("InputExecutableStatementCountRecords.java"),
                expected);
    }

    @Test
    public void testExecutableStatementCountLambdas() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ExecutableStatementCountCheck.class);
        checkConfig.addAttribute("max", "1");
        checkConfig.addAttribute("tokens", "LAMBDA");

        final int max = 1;

        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, 6, max),
            "23:22: " + getCheckMessage(MSG_KEY, 2, max),
            "24:26: " + getCheckMessage(MSG_KEY, 2, max),
            "28:26: " + getCheckMessage(MSG_KEY, 4, max),
        };

        verify(checkConfig, getPath("InputExecutableStatementCountLambdas.java"), expected);
    }

}
