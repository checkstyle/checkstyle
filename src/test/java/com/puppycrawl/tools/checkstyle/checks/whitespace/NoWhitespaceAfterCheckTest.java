///
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck.MSG_KEY;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceAfterCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespaceafter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "32:13: " + getCheckMessage(MSG_KEY, "++"),
            "32:20: " + getCheckMessage(MSG_KEY, "--"),
            "112:21: " + getCheckMessage(MSG_KEY, "!"),
            "113:22: " + getCheckMessage(MSG_KEY, "~"),
            "130:23: " + getCheckMessage(MSG_KEY, "."),
            "133:10: " + getCheckMessage(MSG_KEY, "."),
            "137:11: " + getCheckMessage(MSG_KEY, "."),
            "265:1: " + getCheckMessage(MSG_KEY, "."),
            "290:5: " + getCheckMessage(MSG_KEY, "@"),
            "291:5: " + getCheckMessage(MSG_KEY, "@"),
            "292:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:28: " + getCheckMessage(MSG_KEY, "int"),
            "309:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestDefault.java"), expected);
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
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllowLineBreaks.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final String[] expected = {
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "241:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestTypecast.java"), expected);
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
            "48:17: " + getCheckMessage(MSG_KEY, "int"),
            "51:65: " + getCheckMessage(MSG_KEY, "getLongMultiArray"),
            "55:27: " + getCheckMessage(MSG_KEY, "}"),
            "57:23: " + getCheckMessage(MSG_KEY, "int"),
            "58:25: " + getCheckMessage(MSG_KEY, "]"),
            "59:36: " + getCheckMessage(MSG_KEY, "}"),
            "60:39: " + getCheckMessage(MSG_KEY, "]"),
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
            "43:47: " + getCheckMessage(MSG_KEY, "int"),
            "43:57: " + getCheckMessage(MSG_KEY, "]"),
            "44:19: " + getCheckMessage(MSG_KEY, "e"),
            "44:24: " + getCheckMessage(MSG_KEY, "]"),
            "44:44: " + getCheckMessage(MSG_KEY, "]"),
            "45:15: " + getCheckMessage(MSG_KEY, "e"),
            "45:19: " + getCheckMessage(MSG_KEY, "]"),
            "50:31: " + getCheckMessage(MSG_KEY, "Integer"),
            "51:21: " + getCheckMessage(MSG_KEY, "]"),
            "56:29: " + getCheckMessage(MSG_KEY, ">"),
            "56:32: " + getCheckMessage(MSG_KEY, "]"),
            "56:35: " + getCheckMessage(MSG_KEY, "]"),
            "60:35: " + getCheckMessage(MSG_KEY, "int"),
            "62:15: " + getCheckMessage(MSG_KEY, "g"),
            "63:18: " + getCheckMessage(MSG_KEY, "]"),
            "64:15: " + getCheckMessage(MSG_KEY, "g"),
            "64:19: " + getCheckMessage(MSG_KEY, "]"),
            "64:23: " + getCheckMessage(MSG_KEY, "]"),
            "70:55: " + getCheckMessage(MSG_KEY, "create"),
            "70:63: " + getCheckMessage(MSG_KEY, "]"),
            "75:33: " + getCheckMessage(MSG_KEY, "boolean"),
            "77:48: " + getCheckMessage(MSG_KEY, "String"),
            "77:52: " + getCheckMessage(MSG_KEY, "]"),
            "78:37: " + getCheckMessage(MSG_KEY, "String"),
            "89:41: " + getCheckMessage(MSG_KEY, "Integer"),
            "93:15: " + getCheckMessage(MSG_KEY, "char"),
            "94:53: " + getCheckMessage(MSG_KEY, "A"),
            "95:70: " + getCheckMessage(MSG_KEY, "Object"),
            "98:43: " + getCheckMessage(MSG_KEY, ")"),
            "98:52: " + getCheckMessage(MSG_KEY, "]"),
            "100:37: " + getCheckMessage(MSG_KEY, "Object"),
            "102:46: " + getCheckMessage(MSG_KEY, ")"),
            "105:43: " + getCheckMessage(MSG_KEY, "Object"),
            "108:45: " + getCheckMessage(MSG_KEY, "]"),
            "116:33: " + getCheckMessage(MSG_KEY, "Object"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations2.java"), expected);
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
            "26:64: " + getCheckMessage(MSG_KEY, "::"),
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
        try {
            check.visitToken(astArrayDeclarator);
            assertWithMessage("no intended exception thrown").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("unexpected ast syntax import[0x-1]");
        }
    }

    @Test
    public void testAllTokens() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "32:13: " + getCheckMessage(MSG_KEY, "++"),
            "32:20: " + getCheckMessage(MSG_KEY, "--"),
            "88:20: " + getCheckMessage(MSG_KEY, ")"),
            "90:13: " + getCheckMessage(MSG_KEY, ")"),
            "91:13: " + getCheckMessage(MSG_KEY, ")"),
            "112:21: " + getCheckMessage(MSG_KEY, "!"),
            "113:22: " + getCheckMessage(MSG_KEY, "~"),
            "130:23: " + getCheckMessage(MSG_KEY, "."),
            "133:10: " + getCheckMessage(MSG_KEY, "."),
            "137:11: " + getCheckMessage(MSG_KEY, "."),
            "242:17: " + getCheckMessage(MSG_KEY, ")"),
            "265:1: " + getCheckMessage(MSG_KEY, "."),
            "290:5: " + getCheckMessage(MSG_KEY, "@"),
            "291:5: " + getCheckMessage(MSG_KEY, "@"),
            "292:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:28: " + getCheckMessage(MSG_KEY, "int"),
            "301:18: " + getCheckMessage(MSG_KEY, ")"),
            "309:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllTokens.java"), expected);
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
            "60:42: " + getCheckMessage(MSG_KEY, "transformers"),
            "61:39: " + getCheckMessage(MSG_KEY, "transformers"),
            "66:19: " + getCheckMessage(MSG_KEY, "a"),
            "67:25: " + getCheckMessage(MSG_KEY, "]"),
            "71:18: " + getCheckMessage(MSG_KEY, "]"),
            "72:25: " + getCheckMessage(MSG_KEY, "]"),
            "90:21: " + getCheckMessage(MSG_KEY, ")"),
            "92:57: " + getCheckMessage(MSG_KEY, "KeyManager"),
            "125:28: " + getCheckMessage(MSG_KEY, ")"),
            "126:28: " + getCheckMessage(MSG_KEY, ")"),
            "130:29: " + getCheckMessage(MSG_KEY, ")"),
            "149:28: " + getCheckMessage(MSG_KEY, "byte"),
            "182:12: " + getCheckMessage(MSG_KEY, "String"),
            "182:20: " + getCheckMessage(MSG_KEY, "f"),
            "214:6: " + getCheckMessage(MSG_KEY, "]"),
            "215:13: " + getCheckMessage(MSG_KEY, "]"),
            "219:5: " + getCheckMessage(MSG_KEY, "]"),
            "220:13: " + getCheckMessage(MSG_KEY, "]"),
            "226:15: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
    }

    @Test
    public void testArrayNewGenericTypeArgument() throws Exception {

        final String[] expected = {
            "59:15: " + getCheckMessage(MSG_KEY, "i"),
            "59:21: " + getCheckMessage(MSG_KEY, "j"),
            "60:25: " + getCheckMessage(MSG_KEY, "u"),
            "61:27: " + getCheckMessage(MSG_KEY, "]"),
            "62:28: " + getCheckMessage(MSG_KEY, "w"),
            "63:21: " + getCheckMessage(MSG_KEY, "x"),
            "63:24: " + getCheckMessage(MSG_KEY, "]"),
            "65:24: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "65:27: " + getCheckMessage(MSG_KEY, "]"),
            "67:27: " + getCheckMessage(MSG_KEY, "SomeClass"),
            "67:53: " + getCheckMessage(MSG_KEY, "ImmediateSubclass"),
            "68:13: " + getCheckMessage(MSG_KEY, "!"),
            "68:50: " + getCheckMessage(MSG_KEY, "ImmediateSubclass"),
            "71:13: " + getCheckMessage(MSG_KEY, "!"),
            "71:46: " + getCheckMessage(MSG_KEY, "FinalSubclass"),
            "74:31: " + getCheckMessage(MSG_KEY, "AnotherInterface"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewGenericTypeArgument.java"), expected);
    }

    @Test
    public void testNoWhitespaceAfterWithEmoji() throws Exception {

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_KEY, "String"),
            "16:26: " + getCheckMessage(MSG_KEY, "{"),
            "23:24: " + getCheckMessage(MSG_KEY, "char"),
            "28:22: " + getCheckMessage(MSG_KEY, ")"),
            "28:23: " + getCheckMessage(MSG_KEY, "@"),
            "35:17: " + getCheckMessage(MSG_KEY, "!"),
            "35:22: " + getCheckMessage(MSG_KEY, "."),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterWithEmoji.java"), expected);
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
