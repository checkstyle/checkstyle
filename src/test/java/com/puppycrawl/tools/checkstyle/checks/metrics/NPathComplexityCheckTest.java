////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

// -@cs[AbbreviationAsWordInName] Can't change check name
public class NPathComplexityCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "metrics" + File.separator + filename);
    }

    @Test
    public void testCalculation() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

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
        };

        verify(checkConfig, getPath("InputComplexity.java"), expected);
    }

    @Test
    public void testCalculation2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

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
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final long largerThanMaxInt = 3_486_784_401L;

        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY, largerThanMaxInt, 0),
        };

        verify(checkConfig, getPath("InputComplexityOverflow.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputComplexity.java"), expected);
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
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
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
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testDefaultHooks() {
        final NPathComplexityCheck npathComplexityCheckObj = new NPathComplexityCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        npathComplexityCheckObj.visitToken(ast);
        npathComplexityCheckObj.leaveToken(ast);
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

        final NPathComplexityCheck mock = new NPathComplexityCheck();
        // visiting first ast, set expressionSpatialRange to [2,2 - 4,4]
        mock.visitToken(astIf);
        //visiting ternary, it lies before expressionSpatialRange
        mock.visitToken(astTernary);
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
