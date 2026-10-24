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
import static com.puppycrawl.tools.checkstyle.checks.coding.NoCauseGetMessageInExceptionConstructorCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class NoCauseGetMessageInExceptionConstructorCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding"
                + "/nocausegetmessageinexceptionconstructor";
    }

    @Test
    public void testGetAcceptableTokens() {
        final NoCauseGetMessageInExceptionConstructorCheck check =
            new NoCauseGetMessageInExceptionConstructorCheck();
        final int[] expected = {
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_CATCH,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "ex"),
            "28:13: " + getCheckMessage(MSG_KEY, "exception"),
            "38:13: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorDefault.java"), expected);
    }

    @Test
    public void testNoViolations() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorNoViolations.java"), expected);
    }

    @Test
    public void testCorrectUsage() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorCorrect.java"), expected);
    }

    @Test
    public void testDotIsNull() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorDotIsNull.java"),
                expected);
    }

    @Test
    public void testNested() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, "ex"),
            "33:17: " + getCheckMessage(MSG_KEY, "inner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorNested.java"), expected);
    }

    @Test
    public void testMultipleCatches() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_KEY, "ex"),
            "37:13: " + getCheckMessage(MSG_KEY, "ex"),
            "52:13: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorMultipleCatches.java"),
                expected);
    }

    @Test
    public void testOnlyOneViolationReported() throws Exception {
        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorMultipleThrows.java"),
                expected);
    }

    @Test
    public void testNonMethodCalls() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorNonMethodCalls.java"),
                expected);
    }

    @Test
    public void testSiblingTraversal() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_KEY, "ex"),
            "28:13: " + getCheckMessage(MSG_KEY, "ex"),
            "37:13: " + getCheckMessage(MSG_KEY, "ex"),
            "46:13: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorSiblingTraversal.java"),
                expected);
    }

    @Test
    public void testCoverage() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoCauseGetMessageInExceptionConstructorCoverage.java"), expected);
    }

    @Test
    public void testClearState() throws Exception {
        final NoCauseGetMessageInExceptionConstructorCheck check =
            new NoCauseGetMessageInExceptionConstructorCheck();
        final DetailAST root = JavaParser.parseFile(
            new File(getPath("InputNoCauseGetMessageInExceptionConstructorDefault.java")),
            JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> literalCatch = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.LITERAL_CATCH);

        assertWithMessage("Ast should contain LITERAL_CATCH")
                .that(literalCatch.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalCatch.orElseThrow(),
                        "catchScopes",
                        catchScopes -> ((Collection<?>) catchScopes).isEmpty()))
                .isTrue();
    }

}
