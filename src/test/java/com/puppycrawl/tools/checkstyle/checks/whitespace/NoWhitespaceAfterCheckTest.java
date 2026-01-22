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

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceAfterCheckTest
    extends AbstractModuleTestSupport {

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
            "118:21: " + getCheckMessage(MSG_KEY, "!"),
            "119:22: " + getCheckMessage(MSG_KEY, "~"),
            "136:23: " + getCheckMessage(MSG_KEY, "."),
            "139:10: " + getCheckMessage(MSG_KEY, "."),
            "143:11: " + getCheckMessage(MSG_KEY, "."),
            "271:1: " + getCheckMessage(MSG_KEY, "."),
            "296:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:5: " + getCheckMessage(MSG_KEY, "@"),
            "298:5: " + getCheckMessage(MSG_KEY, "@"),
            "303:28: " + getCheckMessage(MSG_KEY, "int"),
            "315:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
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
            "64:31: " + getCheckMessage(MSG_KEY, "Integer"),
            "66:21: " + getCheckMessage(MSG_KEY, "]"),
            "72:29: " + getCheckMessage(MSG_KEY, ">"),
            "72:32: " + getCheckMessage(MSG_KEY, "]"),
            "72:35: " + getCheckMessage(MSG_KEY, "]"),
            "80:35: " + getCheckMessage(MSG_KEY, "int"),
            "82:15: " + getCheckMessage(MSG_KEY, "g"),
            "83:18: " + getCheckMessage(MSG_KEY, "]"),
            "84:15: " + getCheckMessage(MSG_KEY, "g"),
            "84:19: " + getCheckMessage(MSG_KEY, "]"),
            "84:23: " + getCheckMessage(MSG_KEY, "]"),
            "94:55: " + getCheckMessage(MSG_KEY, "create"),
            "94:63: " + getCheckMessage(MSG_KEY, "]"),
            "102:33: " + getCheckMessage(MSG_KEY, "boolean"),
            "104:48: " + getCheckMessage(MSG_KEY, "String"),
            "104:52: " + getCheckMessage(MSG_KEY, "]"),
            "108:37: " + getCheckMessage(MSG_KEY, "String"),
            "120:41: " + getCheckMessage(MSG_KEY, "Integer"),
            "125:15: " + getCheckMessage(MSG_KEY, "char"),
            "126:53: " + getCheckMessage(MSG_KEY, "A"),
            "128:70: " + getCheckMessage(MSG_KEY, "Object"),
            "132:43: " + getCheckMessage(MSG_KEY, ")"),
            "132:52: " + getCheckMessage(MSG_KEY, "]"),
            "137:37: " + getCheckMessage(MSG_KEY, "Object"),
            "140:46: " + getCheckMessage(MSG_KEY, ")"),
            "143:43: " + getCheckMessage(MSG_KEY, "Object"),
            "146:45: " + getCheckMessage(MSG_KEY, "]"),
            "154:33: " + getCheckMessage(MSG_KEY, "Object"),
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
        try {
            check.visitToken(astArrayDeclarator);
            assertWithMessage("no intended exception thrown").fail();
        }
        catch (IllegalStateException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
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
            "35:13: " + getCheckMessage(MSG_KEY, "++"),
            "35:20: " + getCheckMessage(MSG_KEY, "--"),
            "94:20: " + getCheckMessage(MSG_KEY, ")"),
            "96:13: " + getCheckMessage(MSG_KEY, ")"),
            "97:13: " + getCheckMessage(MSG_KEY, ")"),
            "118:21: " + getCheckMessage(MSG_KEY, "!"),
            "119:22: " + getCheckMessage(MSG_KEY, "~"),
            "136:23: " + getCheckMessage(MSG_KEY, "."),
            "139:10: " + getCheckMessage(MSG_KEY, "."),
            "143:11: " + getCheckMessage(MSG_KEY, "."),
            "248:17: " + getCheckMessage(MSG_KEY, ")"),
            "271:1: " + getCheckMessage(MSG_KEY, "."),
            "296:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:5: " + getCheckMessage(MSG_KEY, "@"),
            "298:5: " + getCheckMessage(MSG_KEY, "@"),
            "303:28: " + getCheckMessage(MSG_KEY, "int"),
            "307:18: " + getCheckMessage(MSG_KEY, ")"),
            "315:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
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
            "61:42: " + getCheckMessage(MSG_KEY, "transformers"),
            "63:39: " + getCheckMessage(MSG_KEY, "transformers"),
            "68:19: " + getCheckMessage(MSG_KEY, "a"),
            "69:25: " + getCheckMessage(MSG_KEY, "]"),
            "73:18: " + getCheckMessage(MSG_KEY, "]"),
            "74:25: " + getCheckMessage(MSG_KEY, "]"),
            "92:21: " + getCheckMessage(MSG_KEY, ")"),
            "94:57: " + getCheckMessage(MSG_KEY, "KeyManager"),
            "128:28: " + getCheckMessage(MSG_KEY, ")"),
            "129:28: " + getCheckMessage(MSG_KEY, ")"),
            "133:29: " + getCheckMessage(MSG_KEY, ")"),
            "152:28: " + getCheckMessage(MSG_KEY, "byte"),
            "186:12: " + getCheckMessage(MSG_KEY, "String"),
            "186:20: " + getCheckMessage(MSG_KEY, "f"),
            "221:6: " + getCheckMessage(MSG_KEY, "]"),
            "222:13: " + getCheckMessage(MSG_KEY, "]"),
            "226:5: " + getCheckMessage(MSG_KEY, "]"),
            "227:13: " + getCheckMessage(MSG_KEY, "]"),
            "233:15: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
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
                getPath("InputNoWhitespaceAfterUnnamedPattern.java"),
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
