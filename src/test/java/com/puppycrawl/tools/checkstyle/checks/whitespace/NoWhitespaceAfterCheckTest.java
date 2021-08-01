////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("allowLineBreaks", "false");
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
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestDefault.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "DOT");
        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "."),
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestAllowLineBreaks.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "TYPECAST");
        final String[] expected = {
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "241:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestTypecast.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_DECLARATOR");
        checkConfig.addProperty("tokens", "INDEX_OP");
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
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testArrayDeclarations2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_DECLARATOR");
        checkConfig.addProperty("tokens", "INDEX_OP");
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
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations2.java"), expected);
    }

    @Test
    public void testArrayDeclarations3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_DECLARATOR");
        checkConfig.addProperty("tokens", "INDEX_OP");
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations3.java"));
    }

    @Test
    public void testSynchronized() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SYNCHRONIZED");
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestSynchronized.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestNpe.java"));
    }

    @Test
    public void testMethodReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_KEY, "int"),
            "18:62: " + getCheckMessage(MSG_KEY, "String"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestMethodRef.java"), expected);
    }

    @Test
    public void testMethodReferenceAfter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "METHOD_REF");
        final String[] expected = {
            "25:35: " + getCheckMessage(MSG_KEY, "::"),
            "26:64: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestMethodRefAfter.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_DECLARATOR");
        final String[] expected = {
            "19:32: " + getCheckMessage(MSG_KEY, "Entry"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestArrayDeclarator.java"), expected);
    }

    @Test
    public void testVisitTokenSwitchReflection() {
        // unexpected parent for ARRAY_DECLARATOR token
        final DetailAstImpl astImport = mockAST(TokenTypes.IMPORT, "import", "mockfile");
        final DetailAstImpl astArrayDeclarator = mockAST(TokenTypes.ARRAY_DECLARATOR, "[",
                "mockfile");
        final DetailAstImpl astRightBracket = mockAST(TokenTypes.RBRACK, "[", "mockfile");
        astImport.addChild(astArrayDeclarator);
        astArrayDeclarator.addChild(astRightBracket);

        final NoWhitespaceAfterCheck check = new NoWhitespaceAfterCheck();
        try {
            check.visitToken(astArrayDeclarator);
            fail("no intended exception thrown");
        }
        catch (IllegalStateException ex) {
            assertEquals("unexpected ast syntax import[0x-1]", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testAllTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        checkConfig.addProperty("allowLineBreaks", "false");
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
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestAllTokens.java"), expected);
    }

    @Test
    public void testArrayDeclarationsAndAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputNoWhitespaceAfterArrayDeclarationsAndAnno.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addProperty("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        checkConfig.addProperty("allowLineBreaks", "false");

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

        verify(checkConfig,
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
    }

    @Test
    public void testArrayNewGenericTypeArgument() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);

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

        verify(checkConfig,
                getPath("InputNoWhitespaceAfterNewGenericTypeArgument.java"), expected);
    }

    /**
     * Creates MOCK lexical token and returns AST node for this token.
     *
     * @param tokenType type of token
     * @param tokenText text of token
     * @param tokenFileName file name of token
     * @return AST node for the token
     */
    private static DetailAstImpl mockAST(final int tokenType, final String tokenText,
            final String tokenFileName) {
        final CommonHiddenStreamToken tokenImportSemi = new CommonHiddenStreamToken();
        tokenImportSemi.setType(tokenType);
        tokenImportSemi.setText(tokenText);
        tokenImportSemi.setFilename(tokenFileName);
        final DetailAstImpl astSemi = new DetailAstImpl();
        astSemi.initialize(tokenImportSemi);
        return astSemi;
    }

}
