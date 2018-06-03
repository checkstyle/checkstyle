////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "10:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "22:5: " + getCheckMessage(MSG_KEY, 10, 0),
            "35:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "45:5: " + getCheckMessage(MSG_KEY, 7, 0),
            "63:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "88:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "104:13: " + getCheckMessage(MSG_KEY, 2, 0),
            "113:5: " + getCheckMessage(MSG_KEY, 48, 0),
            "123:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "124:5: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputNPathComplexityDefault.java"), expected);
    }

    @Test
    public void testCalculation2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "11:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "18:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "33:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "49:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "65:5: " + getCheckMessage(MSG_KEY, 15, 0),
            "90:5: " + getCheckMessage(MSG_KEY, 11, 0),
            "100:5: " + getCheckMessage(MSG_KEY, 8, 0),
            "113:5: " + getCheckMessage(MSG_KEY, 120, 0),
            "125:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "135:5: " + getCheckMessage(MSG_KEY, 21, 0),
            "148:5: " + getCheckMessage(MSG_KEY, 35, 0),
            "156:5: " + getCheckMessage(MSG_KEY, 25, 0),
            "171:5: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig, getPath("InputNPathComplexity.java"), expected);
    }

    @Test
    public void testIntegerOverflow() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final long largerThanMaxInt = 3_486_784_401L;

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, largerThanMaxInt, 0),
        };

        verify(checkConfig, getPath("InputNPathComplexityOverflow.java"), expected);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStatefulFieldsClearedOnBeginTree1() throws Exception {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_ELSE);

        final NPathComplexityCheck check = new NPathComplexityCheck();
        Assert.assertTrue("Stateful field is not cleared after beginTree",
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "rangeValues",
                rangeValues -> ((Collection<Context>) rangeValues).isEmpty()));
        Assert.assertTrue("Stateful field is not cleared after beginTree",
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "expressionValues",
                expressionValues -> ((Collection<Context>) expressionValues).isEmpty()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStatefulFieldsClearedOnBeginTree2() throws Exception {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_RETURN);
        ast.setLineNo(5);
        final DetailAST child = new DetailAST();
        child.setType(TokenTypes.SEMI);
        ast.addChild(child);

        final NPathComplexityCheck check = new NPathComplexityCheck();
        Assert.assertTrue("Stateful field is not cleared after beginTree",
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, ast, "isAfterValues",
                isAfterValues -> ((Collection<Context>) isAfterValues).isEmpty()));
    }

    @Test
    public void testStatefulFieldsClearedOnBeginTree3() throws Exception {
        final NPathComplexityCheck check = new NPathComplexityCheck();
        final Optional<DetailAST> question = TestUtil.findTokenInAstByPredicate(
            JavaParser.parseFile(new File(getPath("InputNPathComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.QUESTION);

        Assert.assertTrue("Ast should contain QUESTION", question.isPresent());

        Assert.assertTrue("State is not cleared on beginTree",
            TestUtil.isStatefulFieldClearedDuringBeginTree(
                check,
                question.get(),
                "processingTokenEnd",
                processingTokenEnd -> {
                    try {
                        return (Integer) TestUtil.getClassDeclaredField(
                            processingTokenEnd.getClass(), "endLineNo").get(
                            processingTokenEnd) == 0
                            && (Integer) TestUtil.getClassDeclaredField(
                                processingTokenEnd.getClass(), "endColumnNo").get(
                                processingTokenEnd) == 0;
                    }
                    catch (IllegalAccessException | NoSuchFieldException ex) {
                        throw new IllegalStateException(ex);
                    }
                }));
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NPathComplexityCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNPathComplexityDefault.java"), expected);
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
        };
        Assert.assertNotNull("Acceptable tokens should not be null", actual);
        Assert.assertArrayEquals("Invalid acceptable tokens", expected, actual);
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
        };
        Assert.assertNotNull("Required tokens should not be null", actual);
        Assert.assertArrayEquals("Invalid required tokens", expected, actual);
    }

    @Test
    public void testDefaultHooks() {
        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));

        npathComplexityCheckObj.visitToken(ast);
        final SortedSet<LocalizedMessage> messages1 = npathComplexityCheckObj.getMessages();

        Assert.assertEquals("No exception messages expected", 0, messages1.size());

        npathComplexityCheckObj.leaveToken(ast);
        final SortedSet<LocalizedMessage> messages2 = npathComplexityCheckObj.getMessages();

        Assert.assertEquals("No exception messages expected", 0, messages2.size());
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
     * @throws Exception if there is an error.
     */
    @Test
    public void testTokenEndIsAfterSameLineColumn() throws Exception {
        final NPathComplexityCheck check = new NPathComplexityCheck();
        final Object tokenEnd = TestUtil.getClassDeclaredField(NPathComplexityCheck.class,
                "processingTokenEnd").get(check);
        final DetailAST token = new DetailAST();
        token.setLineNo(0);
        token.setColumnNo(0);

        Assert.assertTrue("isAfter must be true for same line/column",
                (Boolean) TestUtil.getClassDeclaredMethod(tokenEnd.getClass(), "isAfter")
                    .invoke(tokenEnd, token));
    }

    @Test
    public void testVisitTokenBeforeExpressionRange() {
        // Create first ast
        final DetailAST astIf = mockAST(TokenTypes.LITERAL_IF, "if", "mockfile", 2, 2);
        final DetailAST astIfLeftParen = mockAST(TokenTypes.LPAREN, "(", "mockfile", 3, 3);
        astIf.addChild(astIfLeftParen);
        final DetailAST astIfTrue =
                mockAST(TokenTypes.LITERAL_TRUE, "true", "mockfile", 3, 3);
        astIf.addChild(astIfTrue);
        final DetailAST astIfRightParen = mockAST(TokenTypes.RPAREN, ")", "mockfile", 4, 4);
        astIf.addChild(astIfRightParen);
        // Create ternary ast
        final DetailAST astTernary = mockAST(TokenTypes.QUESTION, "?", "mockfile", 1, 1);
        final DetailAST astTernaryTrue =
                mockAST(TokenTypes.LITERAL_TRUE, "true", "mockfile", 1, 2);
        astTernary.addChild(astTernaryTrue);

        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();

        // visiting first ast, set expressionSpatialRange to [2,2 - 4,4]
        npathComplexityCheckObj.visitToken(astIf);
        final SortedSet<LocalizedMessage> messages1 = npathComplexityCheckObj.getMessages();

        Assert.assertEquals("No exception messages expected", 0, messages1.size());

        //visiting ternary, it lies before expressionSpatialRange
        npathComplexityCheckObj.visitToken(astTernary);
        final SortedSet<LocalizedMessage> messages2 = npathComplexityCheckObj.getMessages();

        Assert.assertEquals("No exception messages expected", 0, messages2.size());
    }

    /**
     * Creates MOCK lexical token and returns AST node for this token.
     * @param tokenType type of token
     * @param tokenText text of token
     * @param tokenFileName file name of token
     * @param tokenRow token position in a file (row)
     * @param tokenColumn token position in a file (column)
     * @return AST node for the token
     */
    private static DetailAST mockAST(final int tokenType, final String tokenText,
            final String tokenFileName, final int tokenRow, final int tokenColumn) {
        final CommonHiddenStreamToken tokenImportSemi = new CommonHiddenStreamToken();
        tokenImportSemi.setType(tokenType);
        tokenImportSemi.setText(tokenText);
        tokenImportSemi.setLine(tokenRow);
        tokenImportSemi.setColumn(tokenColumn);
        tokenImportSemi.setFilename(tokenFileName);
        final DetailAST astSemi = new DetailAST();
        astSemi.initialize(tokenImportSemi);
        return astSemi;
    }

}
