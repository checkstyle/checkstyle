///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneStatementPerLineCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline";
    }

    @Test
    public void testMultiCaseClass() throws Exception {
        final String[] expected = {
            "13:59: " + getCheckMessage(MSG_KEY),
            "93:21: " + getCheckMessage(MSG_KEY),
            "120:14: " + getCheckMessage(MSG_KEY),
            "146:15: " + getCheckMessage(MSG_KEY),
            "158:23: " + getCheckMessage(MSG_KEY),
            "178:19: " + getCheckMessage(MSG_KEY),
            "181:59: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLine.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
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
    public void testWithMultilineStatements() throws Exception {
        final String[] expected = {
            "49:21: " + getCheckMessage(MSG_KEY),
            "66:17: " + getCheckMessage(MSG_KEY),
            "74:17: " + getCheckMessage(MSG_KEY),
            "86:10: " + getCheckMessage(MSG_KEY),
            "95:28: " + getCheckMessage(MSG_KEY),
            "140:39: " + getCheckMessage(MSG_KEY),
            "173:46: " + getCheckMessage(MSG_KEY),
            "184:47: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultiline.java"),
            expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final String[] expected = {
            "39:4: " + getCheckMessage(MSG_KEY),
            "44:54: " + getCheckMessage(MSG_KEY),
            "45:54: " + getCheckMessage(MSG_KEY),
            "45:70: " + getCheckMessage(MSG_KEY),
            "46:46: " + getCheckMessage(MSG_KEY),
            "50:81: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

    @Test
    public void testResourceReferenceVariableIgnored() throws Exception {
        final String[] expected = {
            "32:42: " + getCheckMessage(MSG_KEY),
            "36:43: " + getCheckMessage(MSG_KEY),
            "42:46: " + getCheckMessage(MSG_KEY),
            "46:46: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResources.java"),
                expected);
    }

    @Test
    public void testResourcesIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResourcesIgnore.java"),
                expected);
    }

    @Test
    public void testClearStateForisInLambda() throws Exception {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        final Optional<DetailAST> lambda = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputOneStatementPerLineBeginTreeTest.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.LAMBDA);
        assertWithMessage("ast should contain lambda")
                .that(lambda.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, lambda.get(),
                        "isInLambda", isInLambda -> {
                            return !((boolean) isInLambda);
                        }))
                .isTrue();
    }

    @Test
    public void testClearStateForinForHeader() throws Exception {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        final Optional<DetailAST> forInit = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputOneStatementPerLineBeginTreeTest.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.FOR_INIT);
        assertWithMessage("ast should contain for init")
                .that(forInit.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, forInit.get(),
                        "inForHeader", inForHeader -> {
                            return !(boolean) inForHeader;
                        }))
                .isTrue();
    }

    @Test
    public void testClearStateForStatementEnd() throws Exception {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
        final Optional<DetailAST> forIterator = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputOneStatementPerLineBeginTreeTest.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.FOR_ITERATOR);
        assertWithMessage("ast should contain forIterator")
                .that(forIterator.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, forIterator.get(),
                        "forStatementEnd", forStatementEnd -> {
                            return (int) forStatementEnd == -1;
                        }))
                .isTrue();
    }
}
