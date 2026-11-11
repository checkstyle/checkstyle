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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceAfterCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespaceafter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "35:13: " + getCheckMessage(MSG_KEY, "++"),
            "35:20: " + getCheckMessage(MSG_KEY, "--"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestDefault.java"), expected);
    }

    @Test
    public void testDefault2() throws Exception {
        final String[] expected = {
            "18:21: " + getCheckMessage(MSG_KEY, "!"),
            "19:22: " + getCheckMessage(MSG_KEY, "~"),
            "36:23: " + getCheckMessage(MSG_KEY, "."),
            "39:10: " + getCheckMessage(MSG_KEY, "."),
            "43:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestDefault2.java"), expected);
    }

    @Test
    public void testDefault3() throws Exception {
        final String[] expected = {
            "61:1: " + getCheckMessage(MSG_KEY, "."),
            "86:5: " + getCheckMessage(MSG_KEY, "@"),
            "87:5: " + getCheckMessage(MSG_KEY, "@"),
            "88:5: " + getCheckMessage(MSG_KEY, "@"),
            "93:28: " + getCheckMessage(MSG_KEY, "int"),
            "105:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestDefault3.java"), expected);
    }

    @Test
    public void testAssignment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAssignment.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllowLineBreaks.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks2() throws Exception {
        final String[] expected = {
            "35:23: " + getCheckMessage(MSG_KEY, "."),
            "42:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllowLineBreaks2.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllowLineBreaks3.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final String[] expected = {
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestTypecast.java"), expected);
    }

    @Test
    public void testTypecast2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestTypecast2.java"), expected);
    }

    @Test
    public void testTypecast3() throws Exception {
        final String[] expected = {
            "37:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestTypecast3.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        final String[] expected = {
            "14:12: " + getCheckMessage(MSG_KEY, "Object"),
            "16:23: " + getCheckMessage(MSG_KEY, "someStuff3"),
            "17:9: " + getCheckMessage(MSG_KEY, "int"),
            "18:14: " + getCheckMessage(MSG_KEY, "s"),
            "19:14: " + getCheckMessage(MSG_KEY, "d"),
            "24:15: " + getCheckMessage(MSG_KEY, "get"),
            "26:9: " + getCheckMessage(MSG_KEY, "int"),
            "27:35: " + getCheckMessage(MSG_KEY, "get1"),
            "36:9: " + getCheckMessage(MSG_KEY, "int"),
            "37:13: " + getCheckMessage(MSG_KEY, "cba"),
            "39:27: " + getCheckMessage(MSG_KEY, "String"),
            "40:28: " + getCheckMessage(MSG_KEY, "String"),
            "47:12: " + getCheckMessage(MSG_KEY, "ar"),
            "47:25: " + getCheckMessage(MSG_KEY, "int"),
            "51:17: " + getCheckMessage(MSG_KEY, "int"),
            "55:65: " + getCheckMessage(MSG_KEY, "getLongMultiArray"),
            "59:27: " + getCheckMessage(MSG_KEY, "}"),
            "61:23: " + getCheckMessage(MSG_KEY, "int"),
            "62:25: " + getCheckMessage(MSG_KEY, "]"),
            "63:36: " + getCheckMessage(MSG_KEY, "}"),
            "64:39: " + getCheckMessage(MSG_KEY, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testArrayDeclarations2() throws Exception {
        final String[] expected = {
            "20:31: " + getCheckMessage(MSG_KEY, "]"),
            "25:41: " + getCheckMessage(MSG_KEY, "create"),
            "26:32: " + getCheckMessage(MSG_KEY, "int"),
            "37:26: " + getCheckMessage(MSG_KEY, "]"),
            "38:29: " + getCheckMessage(MSG_KEY, "int"),
            "38:42: " + getCheckMessage(MSG_KEY, "]"),
            "38:66: " + getCheckMessage(MSG_KEY, "]"),
            "47:47: " + getCheckMessage(MSG_KEY, "int"),
            "47:57: " + getCheckMessage(MSG_KEY, "]"),
            "51:19: " + getCheckMessage(MSG_KEY, "e"),
            "51:24: " + getCheckMessage(MSG_KEY, "]"),
            "51:44: " + getCheckMessage(MSG_KEY, "]"),
            "56:15: " + getCheckMessage(MSG_KEY, "e"),
            "56:19: " + getCheckMessage(MSG_KEY, "]"),
            "64:29: " + getCheckMessage(MSG_KEY, ">"),
            "64:32: " + getCheckMessage(MSG_KEY, "]"),
            "64:35: " + getCheckMessage(MSG_KEY, "]"),
            "72:35: " + getCheckMessage(MSG_KEY, "int"),
            "74:15: " + getCheckMessage(MSG_KEY, "g"),
            "75:18: " + getCheckMessage(MSG_KEY, "]"),
            "76:15: " + getCheckMessage(MSG_KEY, "g"),
            "76:19: " + getCheckMessage(MSG_KEY, "]"),
            "76:23: " + getCheckMessage(MSG_KEY, "]"),
            "86:55: " + getCheckMessage(MSG_KEY, "create"),
            "86:63: " + getCheckMessage(MSG_KEY, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations2.java"), expected);
    }

    @Test
    public void testArrayDeclarations4() throws Exception {
        final String[] expected = {
            "18:31: " + getCheckMessage(MSG_KEY, "Integer"),
            "20:21: " + getCheckMessage(MSG_KEY, "]"),
            "26:33: " + getCheckMessage(MSG_KEY, "boolean"),
            "28:48: " + getCheckMessage(MSG_KEY, "String"),
            "28:52: " + getCheckMessage(MSG_KEY, "]"),
            "32:37: " + getCheckMessage(MSG_KEY, "String"),
            "44:41: " + getCheckMessage(MSG_KEY, "Integer"),
            "49:15: " + getCheckMessage(MSG_KEY, "char"),
            "50:53: " + getCheckMessage(MSG_KEY, "A"),
            "52:70: " + getCheckMessage(MSG_KEY, "Object"),
            "56:43: " + getCheckMessage(MSG_KEY, ")"),
            "56:52: " + getCheckMessage(MSG_KEY, "]"),
            "61:37: " + getCheckMessage(MSG_KEY, "Object"),
            "64:46: " + getCheckMessage(MSG_KEY, ")"),
            "67:43: " + getCheckMessage(MSG_KEY, "Object"),
            "70:45: " + getCheckMessage(MSG_KEY, "]"),
            "78:33: " + getCheckMessage(MSG_KEY, "Object"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations4.java"), expected);
    }

    @Test
    public void testArrayDeclarations3() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations3.java"));
    }

    @Test
    public void testSynchronized() throws Exception {
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestSynchronized.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestNpe.java"));
    }

    @Test
    public void testMethodReference() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_KEY, "int"),
            "18:62: " + getCheckMessage(MSG_KEY, "String"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestMethodRef.java"), expected);
    }

    @Test
    public void testMethodReferenceAfter() throws Exception {
        final String[] expected = {
            "25:35: " + getCheckMessage(MSG_KEY, "::"),
            "27:64: " + getCheckMessage(MSG_KEY, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestMethodRefAfter.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final String[] expected = {
            "19:32: " + getCheckMessage(MSG_KEY, "Entry"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestArrayDeclarator.java"), expected);
    }

    @Test
    public void testVisitTokenSwitchReflection() {
        // unexpected parent for ARRAY_DECLARATOR token
        final DetailAstImpl astImport = mockAST(TokenTypes.IMPORT, "import");
        final DetailAstImpl astArrayDeclarator = mockAST(TokenTypes.ARRAY_DECLARATOR, "[");
        final DetailAstImpl astRightBracket = mockAST(TokenTypes.RBRACK, "[");
        astImport.addChild(astArrayDeclarator);
        astArrayDeclarator.addChild(astRightBracket);

        final NoWhitespaceAfterCheck check = new NoWhitespaceAfterCheck();
        final IllegalStateException exc =
            getExpectedThrowable(IllegalStateException.class, () -> {
                check.visitToken(astArrayDeclarator);
            });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("unexpected ast syntax import[0x-1]");
    }

    @Test
    public void testAllTokens() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "35:13: " + getCheckMessage(MSG_KEY, "++"),
            "35:20: " + getCheckMessage(MSG_KEY, "--"),
            "94:20: " + getCheckMessage(MSG_KEY, ")"),
            "96:13: " + getCheckMessage(MSG_KEY, ")"),
            "97:13: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllTokens.java"), expected);
    }

    @Test
    public void testAllTokens2() throws Exception {
        final String[] expected = {
            "18:21: " + getCheckMessage(MSG_KEY, "!"),
            "19:22: " + getCheckMessage(MSG_KEY, "~"),
            "36:23: " + getCheckMessage(MSG_KEY, "."),
            "39:10: " + getCheckMessage(MSG_KEY, "."),
            "43:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllTokens2.java"), expected);
    }

    @Test
    public void testAllTokens3() throws Exception {
        final String[] expected = {
            "38:17: " + getCheckMessage(MSG_KEY, ")"),
            "61:1: " + getCheckMessage(MSG_KEY, "."),
            "86:5: " + getCheckMessage(MSG_KEY, "@"),
            "87:5: " + getCheckMessage(MSG_KEY, "@"),
            "88:5: " + getCheckMessage(MSG_KEY, "@"),
            "93:28: " + getCheckMessage(MSG_KEY, "int"),
            "97:18: " + getCheckMessage(MSG_KEY, ")"),
            "105:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllTokens3.java"), expected);
    }

    @Test
    public void testArrayDeclarationsAndAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarationsAndAnno.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure() throws Exception {
        final String[] expected = {
            "53:17: " + getCheckMessage(MSG_KEY, "ci"),
            "54:27: " + getCheckMessage(MSG_KEY, "int"),
            "55:16: " + getCheckMessage(MSG_KEY, "double"),
            "56:62: " + getCheckMessage(MSG_KEY, "cZ"),
            "61:42: " + getCheckMessage(MSG_KEY, "transformers"),
            "63:39: " + getCheckMessage(MSG_KEY, "transformers"),
            "68:19: " + getCheckMessage(MSG_KEY, "a"),
            "69:25: " + getCheckMessage(MSG_KEY, "]"),
            "73:18: " + getCheckMessage(MSG_KEY, "]"),
            "74:25: " + getCheckMessage(MSG_KEY, "]"),
            "92:21: " + getCheckMessage(MSG_KEY, ")"),
            "94:57: " + getCheckMessage(MSG_KEY, "KeyManager"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure2() throws Exception {
        final String[] expected = {
            "52:28: " + getCheckMessage(MSG_KEY, ")"),
            "53:28: " + getCheckMessage(MSG_KEY, ")"),
            "57:29: " + getCheckMessage(MSG_KEY, ")"),
            "76:28: " + getCheckMessage(MSG_KEY, "byte"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure2.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure3() throws Exception {
        final String[] expected = {
            "24:12: " + getCheckMessage(MSG_KEY, "String"),
            "24:20: " + getCheckMessage(MSG_KEY, "f"),
            "59:6: " + getCheckMessage(MSG_KEY, "]"),
            "60:13: " + getCheckMessage(MSG_KEY, "]"),
            "64:5: " + getCheckMessage(MSG_KEY, "]"),
            "65:13: " + getCheckMessage(MSG_KEY, "]"),
            "71:15: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure3.java"), expected);
    }

    @Test
    public void testArrayNewGenericTypeArgument() throws Exception {

        final String[] expected = {
            "59:15: " + getCheckMessage(MSG_KEY, "i"),
            "59:21: " + getCheckMessage(MSG_KEY, "j"),
            "63:25: " + getCheckMessage(MSG_KEY, "u"),
            "64:27: " + getCheckMessage(MSG_KEY, "]"),
            "65:28: " + getCheckMessage(MSG_KEY, "w"),
            "66:21: " + getCheckMessage(MSG_KEY, "x"),
            "66:24: " + getCheckMessage(MSG_KEY, "]"),
            "71:24: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "71:27: " + getCheckMessage(MSG_KEY, "]"),
            "76:27: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "76:53: " + getCheckMessage(MSG_KEY, "ImmediateSubclass"),
            "80:13: " + getCheckMessage(MSG_KEY, "!"),
            "80:50: " + getCheckMessage(MSG_KEY, "ImmediateSubclass"),
            "86:13: " + getCheckMessage(MSG_KEY, "!"),
            "86:46: " + getCheckMessage(MSG_KEY, "FinalSubclass"),
            "92:31: " + getCheckMessage(MSG_KEY, "AnotherInterface"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewGenericTypeArgument.java"), expected);
    }

    @Test
    public void testNoWhitespaceAfterWithEmoji() throws Exception {

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_KEY, "String"),
            "16:26: " + getCheckMessage(MSG_KEY, "{"),
            "26:24: " + getCheckMessage(MSG_KEY, "char"),
            "31:22: " + getCheckMessage(MSG_KEY, ")"),
            "31:23: " + getCheckMessage(MSG_KEY, "@"),
            "41:17: " + getCheckMessage(MSG_KEY, "!"),
            "41:22: " + getCheckMessage(MSG_KEY, "."),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterWithEmoji.java"), expected);
    }

    @Test
    public void testLiteralToken() throws Exception {
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "catch"),
            "26:11: " + getCheckMessage(MSG_KEY, "while"),
            "34:9: " + getCheckMessage(MSG_KEY, "for"),
            "44:9: " + getCheckMessage(MSG_KEY, "if"),
            "55:9: " + getCheckMessage(MSG_KEY, "while"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfter.java"), expected);
    }

    @Test
    public void testLiteralTokenFalse() throws Exception {
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "catch"),
            "26:11: " + getCheckMessage(MSG_KEY, "while"),
            "29:11: " + getCheckMessage(MSG_KEY, "while"),
            "34:9: " + getCheckMessage(MSG_KEY, "for"),
            "44:9: " + getCheckMessage(MSG_KEY, "if"),
            "47:9: " + getCheckMessage(MSG_KEY, "if"),
            "55:9: " + getCheckMessage(MSG_KEY, "while"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTwo.java"), expected);
    }

    @Test
    public void testNoWhitespaceAfterSynchronized() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterSynchronized.java"),
                expected);
    }

    @Test
    public void testNoWhitespaceAfterCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "13:26: " + getCheckMessage(MSG_KEY, "."),
            "14:23: " + getCheckMessage(MSG_KEY, "!"),
            "15:20: " + getCheckMessage(MSG_KEY, "-"),
            "16:19: " + getCheckMessage(MSG_KEY, "~"),
            "17:23: " + getCheckMessage(MSG_KEY, "--"),
            "18:27: " + getCheckMessage(MSG_KEY, "numbers"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputNoWhitespaceAfterCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testNoWhitespaceAfterCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "13:26: " + getCheckMessage(MSG_KEY, "."),
            "14:23: " + getCheckMessage(MSG_KEY, "!"),
            "15:20: " + getCheckMessage(MSG_KEY, "-"),
            "16:19: " + getCheckMessage(MSG_KEY, "~"),
            "17:23: " + getCheckMessage(MSG_KEY, "--"),
            "18:27: " + getCheckMessage(MSG_KEY, "numbers"),
            "19:26: " + getCheckMessage(MSG_KEY, "."),
            "22:27: " + getCheckMessage(MSG_KEY, "-"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputNoWhitespaceAfterCompactSourceFileProperties.java"),
                expected);
    }

    @Test
    public void testNoWhitespaceAfterUnnamedPattern() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNoWhitespaceAfterUnnamedPattern.java"),
                expected);
    }

    /**
     * Creates MOCK lexical token and returns AST node for this token.
     *
     * @param tokenType type of token
     * @param tokenText text of token
     * @return AST node for the token
     */
    private static DetailAstImpl mockAST(final int tokenType, final String tokenText) {
        final DetailAstImpl astSemi = new DetailAstImpl();
        astSemi.initialize(new CommonToken(tokenType, tokenText));
        return astSemi;
    }

}
