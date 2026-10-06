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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkLineWrappingCheck.MSG_ERROR_MULTI;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OpenjdkLineWrappingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/openjdklinewrapping";
    }

    @Test
    public void testTokens() {
        final OpenjdkLineWrappingCheck check = new OpenjdkLineWrappingCheck();
        final int[] expected = {
            TokenTypes.EXPR, TokenTypes.ARRAY_INIT,
            TokenTypes.SLIST, TokenTypes.OBJBLOCK, TokenTypes.LITERAL_SWITCH,
            TokenTypes.METHOD_CALL, TokenTypes.CTOR_CALL, TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_NEW, TokenTypes.DOT, TokenTypes.TYPE_ARGUMENTS,
            TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
            TokenTypes.LAND, TokenTypes.LOR, TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR,
            TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.LT, TokenTypes.LE,
            TokenTypes.GT, TokenTypes.GE, TokenTypes.SL, TokenTypes.SR, TokenTypes.BSR,
            TokenTypes.QUESTION, TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN, TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN, TokenTypes.SL_ASSIGN, TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN, TokenTypes.BAND_ASSIGN, TokenTypes.BOR_ASSIGN,
            TokenTypes.BXOR_ASSIGN, TokenTypes.UNARY_PLUS, TokenTypes.UNARY_MINUS,
            TokenTypes.INC, TokenTypes.DEC, TokenTypes.POST_INC, TokenTypes.POST_DEC,
            TokenTypes.LNOT, TokenTypes.BNOT, TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPECAST, TokenTypes.INDEX_OP, TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.METHOD_REF, TokenTypes.LAMBDA,
        };
        assertWithMessage("Default tokens")
                .that(check.getDefaultTokens()).isEqualTo(expected);
        assertWithMessage("Acceptable tokens")
                .that(check.getAcceptableTokens()).isEqualTo(expected);
        assertWithMessage("Required tokens")
                .that(check.getRequiredTokens()).isEqualTo(expected);
    }

    @Test
    public void testExpressions() throws Exception {
        final String[] expected = {
            "61:14: " + getCheckMessage(MSG_ERROR_MULTI, "+", 13, "16, 24"),
            "63:20: " + getCheckMessage(MSG_ERROR_MULTI, "second", 19, "16, 23, 24"),
            "65:22: " + getCheckMessage(MSG_ERROR_MULTI, "+", 21, "16, 20, 28"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrapping.java"), expected);
    }

    @Test
    public void testNestedExpressions() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_ERROR_MULTI, "second", 12, "16, 20"),
            "18:13: " + getCheckMessage(MSG_ERROR_MULTI, "sum", 12, "16, 24, 31"),
            "21:13: " + getCheckMessage(MSG_ERROR_MULTI, "sum", 12, "16, 25, 32"),
            "22:19: " + getCheckMessage(MSG_ERROR_MULTI, "3", 18, "16, 20, 32"),
            "28:22: " + getCheckMessage(MSG_ERROR_MULTI, "3", 21, "16, 20, 24, 28"),
            "62:41: " + getCheckMessage(MSG_ERROR_MULTI, "\"x\"", 40, "16, 29"),
            "64:21: " + getCheckMessage(MSG_ERROR_MULTI, "3", 20, "16, 28"),
            "68:14: " + getCheckMessage(MSG_ERROR_MULTI, "2", 13, "16, 27, 31"),
            "70:14: " + getCheckMessage(MSG_ERROR_MULTI, "TYPE_ARGUMENT", 13, "16, 33"),
            "78:14: " + getCheckMessage(MSG_ERROR_MULTI, "+", 13, "15, 16, 23"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingNested.java"), expected);
    }

    @Test
    public void testArrayInitializers() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_ERROR_MULTI, "sum", 12, "16, 25, 32"),
            "25:13: " + getCheckMessage(MSG_ERROR_MULTI, "sum", 12, "16, 37, 40, 47"),
            "32:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "16, 32, 38, 39"),
            "34:21: " + getCheckMessage(MSG_ERROR_MULTI, "4", 20, "16, 17, 24, 38"),
            "47:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "16, 31, 38"),
            "52:18: " + getCheckMessage(MSG_ERROR_MULTI, "+", 17, "20, 28, 36"),
            "59:18: " + getCheckMessage(MSG_ERROR_MULTI, "+", 17, "20, 28, 36"),
            "65:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "16"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingArrays.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(MSG_ERROR_MULTI, "+", 11, "12, 16, 24"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputOpenjdkLineWrappingCompact.java"), expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_ERROR_MULTI, "+", 12, "16, 20, 28"),
            "18:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "16, 29, 36"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingTabs.java"), expected);
    }

    @Test
    public void testCalls() throws Exception {
        final String[] expected = {
            "25:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "13, 16"),
            "30:13: " + getCheckMessage(MSG_ERROR_MULTI, "2", 12, "14, 16"),
            "38:13: " + getCheckMessage(MSG_ERROR_MULTI, ".", 12, "16, 29, 33"),
            "46:13: " + getCheckMessage(MSG_ERROR_MULTI, "strip", 12, "16, 35"),
            "48:13: " + getCheckMessage(MSG_ERROR_MULTI, "\"text\"", 12, "16, 33"),
            "57:20: " + getCheckMessage(MSG_ERROR_MULTI, "5", 19, "16, 27, 35"),
            "65:25: " + getCheckMessage(MSG_ERROR_MULTI, "toString", 24, "16, 32, 33"),
            "67:13: " + getCheckMessage(MSG_ERROR_MULTI, "(", 12, "15, 16"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingCalls.java"), expected);
    }

    @Test
    public void testOperators() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_ERROR_MULTI, "+", 12, "16, 27, 35"),
            "18:13: " + getCheckMessage(MSG_ERROR_MULTI, "second", 12, "16, 28, 36"),
            "22:13: " + getCheckMessage(MSG_ERROR_MULTI, "second", 12, "16"),
            "26:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 20, 33"),
            "30:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 25, 32, 33"),
            "34:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 41"),
            "38:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 20, 35"),
            "42:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 25, 33"),
            "46:13: " + getCheckMessage(MSG_ERROR_MULTI, "TYPE", 12, "16, 32, 40"),
            "51:13: " + getCheckMessage(MSG_ERROR_MULTI, "first", 12, "16, 27, 35"),
            "55:13: " + getCheckMessage(MSG_ERROR_MULTI, "second", 12, "16, 55, 63"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingOperators.java"), expected);
    }

}
