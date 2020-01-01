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
            "4:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "7:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "17:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "27:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "48:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "58:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "67:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "79:13: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
    }

    @Test
    public void testMethodDef() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "7:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "17:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "27:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "79:13: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
    }

    @Test
    public void testCtorDef() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "CTOR_DEF");

        final String[] expected = {
            "48:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
    }

    @Test
    public void testStaticInit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "STATIC_INIT");

        final String[] expected = {
            "58:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
    }

    @Test
    public void testInstanceInit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "INSTANCE_INIT");

        final String[] expected = {
            "67:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
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
        verify(checkConfig, getPath("InputExecutableStatementCount.java"), expected);
    }

}
