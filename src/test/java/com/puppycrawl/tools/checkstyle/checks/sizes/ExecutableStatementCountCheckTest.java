///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/executablestatementcount";
    }

    @SuppressWarnings("unchecked")
    @Test
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
            "13:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "17:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "28:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "39:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "47:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "62:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "73:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "83:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "93:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "97:13: " + getCheckMessage(MSG_KEY, 1, 0),
            "109:29: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountMaxZero.java"), expected);
    }

    @Test
    public void testMethodDef() throws Exception {

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "17:17: " + getCheckMessage(MSG_KEY, 1, 0),
            "28:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "39:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "47:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "66:13: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountMethodDef.java"), expected);
    }

    @Test
    public void testCtorDef() throws Exception {

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "24:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountCtorDef.java"), expected);
    }

    @Test
    public void testStaticInit() throws Exception {

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountStaticInit.java"), expected);
    }

    @Test
    public void testInstanceInit() throws Exception {

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 2, 0),
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
        final IllegalStateException visit =
                getExpectedThrowable(IllegalStateException.class,
                        () -> checkObj.visitToken(ast));
        assertWithMessage("Invalid exception message")
            .that(visit.getMessage())
            .isEqualTo("ENUM[0x-1]");
    }

    @Test
    public void testLeaveTokenWithWrongTokenType() {
        final ExecutableStatementCountCheck checkObj =
            new ExecutableStatementCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(
            new CommonToken(TokenTypes.ENUM, "ENUM"));
        final IllegalStateException leave =
                getExpectedThrowable(IllegalStateException.class,
                        () -> checkObj.leaveToken(ast));
        assertWithMessage("Invalid exception message")
            .that(leave.getMessage())
            .isEqualTo("ENUM[0x-1]");
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
            "16:9: " + getCheckMessage(MSG_KEY, 3, max),
            "26:9: " + getCheckMessage(MSG_KEY, 3, max),
            "36:9: " + getCheckMessage(MSG_KEY, 3, max),
            "45:9: " + getCheckMessage(MSG_KEY, 4, max),
            "56:9: " + getCheckMessage(MSG_KEY, 6, max),
            "71:17: " + getCheckMessage(MSG_KEY, 6, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountRecords.java"),
                expected);
    }

    @Test
    public void testExecutableStatementCountLambdas() throws Exception {

        final int max = 1;

        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_KEY, 6, max),
            "27:22: " + getCheckMessage(MSG_KEY, 2, max),
            "29:26: " + getCheckMessage(MSG_KEY, 2, max),
            "34:26: " + getCheckMessage(MSG_KEY, 4, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputExecutableStatementCountLambdas.java"), expected);
    }

}
