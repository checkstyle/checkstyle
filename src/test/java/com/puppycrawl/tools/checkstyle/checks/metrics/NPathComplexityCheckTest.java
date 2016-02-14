////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NPathComplexityCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "metrics" + File.separator + filename);
    }

    @Test
    //@Ignore
    public void testCalculation() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "4:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "7:17: " + getCheckMessage(MSG_KEY, 2, 0),
            "17:5: " + getCheckMessage(MSG_KEY, 10, 0),
            "27:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 7, 0),
            "48:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "58:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "67:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "76:5: " + getCheckMessage(MSG_KEY, 1, 0),
            "79:13: " + getCheckMessage(MSG_KEY, 2, 0),
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
            "12:5: " + getCheckMessage(MSG_KEY, 5, 0),
            "22:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 4, 0),
            "48:5: " + getCheckMessage(MSG_KEY, 6, 0),
            "62:5: " + getCheckMessage(MSG_KEY, 15, 0),
            "86:5: " + getCheckMessage(MSG_KEY, 11, 0),
            "100:5: " + getCheckMessage(MSG_KEY, 10, 0),
            "112:5: " + getCheckMessage(MSG_KEY, 120, 0),
            "124:5: " + getCheckMessage(MSG_KEY, 21, 0),
            "138:5: " + getCheckMessage(MSG_KEY, 35, 0),
            "146:5: " + getCheckMessage(MSG_KEY, 25, 0),
        };

        verify(checkConfig, getPath("InputNPathComplexity.java"), expected);
    }

    @Test
    public void testIntegerOverflow() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final long largerThanMaxInt = 3486784401L;

        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_KEY, largerThanMaxInt, 0),
        };

        verify(checkConfig, getPath("InputComplexityOverflow.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        createChecker(checkConfig);
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
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
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
        final DetailAST astIfLParen = mockAST(TokenTypes.LPAREN, "(", "mockfile", 3, 3);
        astIf.addChild(astIfLParen);
        final DetailAST astIfTrue =
                mockAST(TokenTypes.LITERAL_TRUE, "true", "mockfile", 3, 3);
        astIf.addChild(astIfTrue);
        final DetailAST astIfRParen = mockAST(TokenTypes.RPAREN, ")", "mockfile", 4, 4);
        astIf.addChild(astIfRParen);
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
