///////////////////////////////////////////////////////////////////////////////////////////////
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

public class UnusedTryResourceShouldBeUnnamedCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedtryresourceshouldbeunnamed";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedTryResourceShouldBeUnnamedCheck checkObj =
                            new UnusedTryResourceShouldBeUnnamedCheck();
        final int[] expected = {
            TokenTypes.LITERAL_TRY,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamed() throws Exception {
        final String[] expected = {
            "12:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "31:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "e"),
            "51:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamed.java"),
                expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamed2() throws Exception {
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "31:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "32:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "33:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "c"),
            "53:18: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "74:18: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "93:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamed2.java"),
                expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedInsideAnonClass() throws Exception {
        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "27:14: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "32:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamedInsideAnonClass.java"),
            expected);
    }

    @Test
    public void testUnusedTryResourceShouldBeUnnamedNested() throws Exception {
        final String[] expected = {
            "15:18: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "30:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "a"),
            "52:18: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "56:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "81:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "85:34: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "112:26: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "119:42: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "d"),
            "144:18: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "b"),
            "148:30: " + getCheckMessage(MSG_UNUSED_TRY_RESOURCE, "e"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputUnusedTryResourceShouldBeUnnamedNested.java"),
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
