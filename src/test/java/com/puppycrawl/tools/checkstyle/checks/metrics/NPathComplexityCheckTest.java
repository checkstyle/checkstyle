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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

// -@cs[AbbreviationAsWordInName] Can't change check name
public class NPathComplexityCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/npathcomplexity";
    }

    @Test
    public void testCalculation() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "17:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "29:5: " + getCheckMessage(MSG_KEY, 10, 0),
            "42:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "52:5: " + getCheckMessage(MSG_KEY, 7, 0),
            "70:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "83:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "95:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "111:13: " + getCheckMessage(MSG_KEY, 2, 0),
            "120:5: " + getCheckMessage(MSG_KEY, 48, 0),
            "130:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "131:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "137:17: " + getCheckMessage(MSG_KEY, 3, 0),
            "151:21: " + getCheckMessage(MSG_KEY, 3, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputNPathComplexityDefault.java"), expected);
    }

    @Test
    public void testCalculation2() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "18:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "25:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "40:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "56:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "72:5: " + getCheckMessage(MSG_KEY, 15, 0),
            "97:5: " + getCheckMessage(MSG_KEY, 11, 0),
            "107:5: " + getCheckMessage(MSG_KEY, 8, 0),
            "120:5: " + getCheckMessage(MSG_KEY, 120, 0),
            "132:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "142:5: " + getCheckMessage(MSG_KEY, 21, 0),
            "155:5: " + getCheckMessage(MSG_KEY, 35, 0),
            "163:5: " + getCheckMessage(MSG_KEY, 25, 0),
            "178:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputNPathComplexity.java"), expected);
    }

    @Test
    public void testCalculation3() throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_KEY, 64, 0),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNPathComplexityDefault2.java"), expected);
    }

    @Test
    public void testIntegerOverflow() throws Exception {

        final long largerThanMaxInt = 3_486_784_401L;

        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, largerThanMaxInt, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputNPathComplexityOverflow.java"), expected);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStatefulFieldsClearedOnBeginTree1() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.LITERAL_ELSE);

        final NPathComplexityCheck check = new NPathComplexityCheck();
        assertWithMessage("Stateful field is not cleared after beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "rangeValues",
                        rangeValues -> ((Collection<Context>) rangeValues).isEmpty()))
                .isTrue();
        assertWithMessage("Stateful field is not cleared after beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "expressionValues",
                        expressionValues -> ((Collection<Context>) expressionValues).isEmpty()))
                .isTrue();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStatefulFieldsClearedOnBeginTree2() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.LITERAL_RETURN);
        ast.setLineNo(5);
        final DetailAstImpl child = new DetailAstImpl();
        child.setType(TokenTypes.SEMI);
        ast.addChild(child);

        final NPathComplexityCheck check = new NPathComplexityCheck();
        assertWithMessage("Stateful field is not cleared after beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "afterValues",
                        isAfterValues -> ((Collection<Context>) isAfterValues).isEmpty()))
                .isTrue();
    }

    @Test
    public void testStatefulFieldsClearedOnBeginTree3() throws Exception {
        final NPathComplexityCheck check = new NPathComplexityCheck();
        final Optional<DetailAST> question = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputNPathComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.QUESTION);

        assertWithMessage("Ast should contain QUESTION")
                .that(question.isPresent())
                .isTrue();

        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, question.get(),
                        "processingTokenEnd", processingTokenEnd -> {
                            return TestUtil.<Integer>getInternalState(processingTokenEnd,
                                    "endLineNo") == 0
                                    && TestUtil.<Integer>getInternalState(processingTokenEnd,
                                            "endColumnNo") == 0;
                        }))
                .isTrue();
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNPathComplexityDefault2.java"), expected);
    }

    @Test
    public void testNpathComplexityRecords() throws Exception {
        final int max = 1;

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY, 3, max),
            "25:9: " + getCheckMessage(MSG_KEY, 2, max),
            "30:21: " + getCheckMessage(MSG_KEY, 2, max),
            "44:9: " + getCheckMessage(MSG_KEY, 3, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNPathComplexityRecords.java"), expected);
    }

    @Test
    public void testNpathComplexitySwitchExpression() throws Exception {

        final int max = 1;

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, 5, max),
            "29:5: " + getCheckMessage(MSG_KEY, 5, max),
            "44:5: " + getCheckMessage(MSG_KEY, 6, max),
            "60:5: " + getCheckMessage(MSG_KEY, 6, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNPathComplexityCheckSwitchExpression.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();
        final int[] actual = npathComplexityCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.CASE_GROUP,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.SWITCH_RULE,
        };
        assertWithMessage("Acceptable tokens should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();
        final int[] actual = npathComplexityCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.CASE_GROUP,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.SWITCH_RULE,
        };
        assertWithMessage("Required tokens should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultHooks() {
        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.INTERFACE_DEF, "interface"));

        npathComplexityCheckObj.visitToken(ast);
        final SortedSet<Violation> violations1 = npathComplexityCheckObj.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations1)
            .isEmpty();

        npathComplexityCheckObj.leaveToken(ast);
        final SortedSet<Violation> violations2 = npathComplexityCheckObj.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations2)
            .isEmpty();
    }

    /**
     * This must be a reflection test as it is too difficult to hit normally and
     * the responsible code can't be removed without failing tests.
     * TokenEnd is only used for processingTokenEnd and it is only set during visitConditional
     * and visitUnitaryOperator. For it to be the same line/column, it must be the exact same
     * token or a token who has the same line/column as it's child and we visit. We never
     * visit the same token twice and we are only visiting on very specific tokens.
     * The line can't be removed or reworked as other tests fail, and regression shows us no
     * use cases to create a UT for.
     *
     * @throws Exception if there is an error.
     */
    @Test
    public void testTokenEndIsAfterSameLineColumn() throws Exception {
        final NPathComplexityCheck check = new NPathComplexityCheck();
        final Object tokenEnd = TestUtil.getInternalState(check, "processingTokenEnd");
        final DetailAstImpl token = new DetailAstImpl();
        token.setLineNo(0);
        token.setColumnNo(0);

        assertWithMessage("isAfter must be true for same line/column")
                .that(TestUtil.<Boolean>invokeMethod(tokenEnd, "isAfter", token))
                .isTrue();
    }

    @Test
    public void testVisitTokenBeforeExpressionRange() {
        // Create first ast
        final DetailAstImpl astIf = mockAST(TokenTypes.LITERAL_IF, "if", 2, 2);
        final DetailAstImpl astIfLeftParen = mockAST(TokenTypes.LPAREN, "(", 3, 3);
        astIf.addChild(astIfLeftParen);
        final DetailAstImpl astIfTrue =
                mockAST(TokenTypes.LITERAL_TRUE, "true", 3, 3);
        astIf.addChild(astIfTrue);
        final DetailAstImpl astIfRightParen = mockAST(TokenTypes.RPAREN, ")", 4, 4);
        astIf.addChild(astIfRightParen);
        // Create ternary ast
        final DetailAstImpl astTernary = mockAST(TokenTypes.QUESTION, "?", 1, 1);
        final DetailAstImpl astTernaryTrue =
                mockAST(TokenTypes.LITERAL_TRUE, "true", 1, 2);
        astTernary.addChild(astTernaryTrue);

        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();

        // visiting first ast, set expressionSpatialRange to [2,2 - 4,4]
        npathComplexityCheckObj.visitToken(astIf);
        final SortedSet<Violation> violations1 = npathComplexityCheckObj.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations1)
            .isEmpty();

        // visiting ternary, it lies before expressionSpatialRange
        npathComplexityCheckObj.visitToken(astTernary);
        final SortedSet<Violation> violations2 = npathComplexityCheckObj.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations2)
            .isEmpty();
    }

    /**
     * Creates MOCK lexical token and returns AST node for this token.
     *
     * @param tokenType type of token
     * @param tokenText text of token
     * @param tokenRow token position in a file (row)
     * @param tokenColumn token position in a file (column)
     * @return AST node for the token
     */
    private static DetailAstImpl mockAST(final int tokenType, final String tokenText,
            final int tokenRow, final int tokenColumn) {
        final CommonToken tokenImportSemi = new CommonToken(tokenType, tokenText);
        tokenImportSemi.setLine(tokenRow);
        tokenImportSemi.setCharPositionInLine(tokenColumn);
        final DetailAstImpl astSemi = new DetailAstImpl();
        astSemi.initialize(tokenImportSemi);
        return astSemi;
    }

}
