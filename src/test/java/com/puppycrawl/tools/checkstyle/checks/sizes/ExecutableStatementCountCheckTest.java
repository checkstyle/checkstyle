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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck.MSG_KEY;

import java.util.Collection;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
    public void testStatefulFieldsClearedOnBeginTree() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.STATIC_INIT);
        final ExecutableStatementCountCheck check = new ExecutableStatementCountCheck();
        assertWithMessage("Stateful field is not cleared after beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "contextStack",
                        contextStack -> ((Collection<Context>) contextStack).isEmpty()))
                .isTrue();
    }

    @Test
    public void testMaxZero() throws Exception {

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "15:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "25:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "35:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "42:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "56:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "66:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "75:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "84:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "87:13: " + getCheckMessage(MSG_KEY, 1, 0),
            "98:29: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountMaxZero.java"), expected);
    }

    @Test
    public void testMethodDef() throws Exception {

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "15:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "25:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "35:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "42:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "60:13: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountMethodDef.java"), expected);
    }

    @Test
    public void testCtorDef() throws Exception {

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "22:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountCtorDef.java"), expected);
    }

    @Test
    public void testStaticInit() throws Exception {

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountStaticInit.java"), expected);
    }

    @Test
    public void testInstanceInit() throws Exception {

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountInstanceInit.java"), expected);
    }

    @Test
    public void testVisitTokenWithWrongTokenType() {
        final ExecutableStatementCountCheck checkObj =
            new ExecutableStatementCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(
            new CommonToken(TokenTypes.ENUM, "ENUM"));
        try {
            checkObj.visitToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("ENUM[0x-1]");
        }
    }

    @Test
    public void testLeaveTokenWithWrongTokenType() {
        final ExecutableStatementCountCheck checkObj =
            new ExecutableStatementCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(
            new CommonToken(TokenTypes.ENUM, "ENUM"));
        try {
            checkObj.leaveToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("ENUM[0x-1]");
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountDefaultConfig.java"), expected);
    }

    @Test
    public void testExecutableStatementCountRecords() throws Exception {

        final int max = 1;

        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY, 3, max),
            "24:9: " + getCheckMessage(MSG_KEY, 3, max),
            "33:9: " + getCheckMessage(MSG_KEY, 3, max),
            "41:9: " + getCheckMessage(MSG_KEY, 4, max),
            "51:9: " + getCheckMessage(MSG_KEY, 6, max),
            "65:17: " + getCheckMessage(MSG_KEY, 6, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputExecutableStatementCountRecords.java"),
                expected);
    }

    @Test
    public void testExecutableStatementCountLambdas() throws Exception {

        final int max = 1;

        final String[] expected = {
            "16:22: " + getCheckMessage(MSG_KEY, 6, max),
            "25:22: " + getCheckMessage(MSG_KEY, 2, max),
            "26:26: " + getCheckMessage(MSG_KEY, 2, max),
            "30:26: " + getCheckMessage(MSG_KEY, 4, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountLambdas.java"), expected);
    }

}
