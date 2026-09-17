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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedTryResourceShouldBeUnnamedCheck.MSG_UNUSED_TRY_RESOURCE;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnusedTryResourceShouldBeUnnamedCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedtryresourceshouldbeunnamed";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedTryResourceShouldBeUnnamedCheck checkObj =
                            new UnusedTryResourceShouldBeUnnamedCheck();
        final int[] expected = {
            TokenTypes.LITERAL_TRY,
            TokenTypes.IDENT,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamed() throws Exception {
        final String[] expected = {
            "11:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "25:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "e"),
            "38:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "56:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "autoCloseable2"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamed.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamed2() throws Exception {
        final String[] expected = {
            "13:23: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "30:23: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "31:23: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "32:23: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "c"),
            "86:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamed2.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedInsideAnonClass() throws Exception {
        final String[] expected = {
            "15:30: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "25:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "30:32: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedInsideAnonClass.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedNested() throws Exception {
        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "28:32: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "47:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "51:32: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedNested.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedNested2() throws Exception {
        final String[] expected = {
            "17:36: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "21:40: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "47:34: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "54:44: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "79:30: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "83:42: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "e"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedNested2.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedStatic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedStatic.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedForVariable() throws Exception {
        final String[] expected = {
            "48:66: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "i2"),
            "58:60: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "m3"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedForVariable.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedEdgeCases() throws Exception {
        final String[] expected = {
            "41:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "e"),
            "49:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "lock"),
            "59:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "r"),
            "99:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "close"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamedEdgeCases.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedEdgeCases2() throws Exception {
        final String[] expected = {
            "15:32: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "s"),
            "26:34: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "f"),
            "39:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "54:32: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "inner"),
            "61:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "x"),
            "71:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "res"),
            "80:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "unused1"),
            "82:28: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "unused2"),
        };
        verifyWithInlineConfigParser(
            getPath("InputUnusedTryResourceShouldBeUnnamedEdgeCases2.java"),
            expected);
    }

    @Test
    public void testClearState() throws Exception {
        final UnusedTryResourceShouldBeUnnamedCheck check =
                new UnusedTryResourceShouldBeUnnamedCheck();

        final DetailAST root = JavaParser.parseFile(
                new File(getNonCompilablePath(
                        "InputUnusedTryResourceShouldBeUnnamed.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> literalTry = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.LITERAL_TRY);

        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalTry.orElseThrow(),
                        "tryResources",
                        tryResources -> {
                            return ((Collection<?>) tryResources).isEmpty();
                        }))
                .isTrue();
    }

}
