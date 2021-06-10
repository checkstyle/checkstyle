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
        checkConfig.addAttribute("allowLineBreaks", "false");
        final String[] expected = {
            "1:13: " + getCheckMessage(MSG_KEY, "."),
            "2:11: " + getCheckMessage(MSG_KEY, "."),
            "25:13: " + getCheckMessage(MSG_KEY, "-"),
            "25:20: " + getCheckMessage(MSG_KEY, "+"),
            "27:13: " + getCheckMessage(MSG_KEY, "++"),
            "27:20: " + getCheckMessage(MSG_KEY, "--"),
            "107:21: " + getCheckMessage(MSG_KEY, "!"),
            "108:22: " + getCheckMessage(MSG_KEY, "~"),
            "125:23: " + getCheckMessage(MSG_KEY, "."),
            "128:10: " + getCheckMessage(MSG_KEY, "."),
            "132:11: " + getCheckMessage(MSG_KEY, "."),
            "260:1: " + getCheckMessage(MSG_KEY, "."),
            "285:5: " + getCheckMessage(MSG_KEY, "@"),
            "286:5: " + getCheckMessage(MSG_KEY, "@"),
            "287:5: " + getCheckMessage(MSG_KEY, "@"),
            "292:28: " + getCheckMessage(MSG_KEY, "int"),
            "304:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestDefault.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "1:13: " + getCheckMessage(MSG_KEY, "."),
            "125:23: " + getCheckMessage(MSG_KEY, "."),
            "132:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestAllowLineBreaks.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "83:20: " + getCheckMessage(MSG_KEY, ")"),
            "85:13: " + getCheckMessage(MSG_KEY, ")"),
            "237:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestTypecast.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        checkConfig.addAttribute("tokens", "INDEX_OP");
        final String[] expected = {
            "10:12: " + getCheckMessage(MSG_KEY, "Object"),
            "12:23: " + getCheckMessage(MSG_KEY, "someStuff3"),
            "13:9: " + getCheckMessage(MSG_KEY, "int"),
            "14:14: " + getCheckMessage(MSG_KEY, "s"),
            "15:14: " + getCheckMessage(MSG_KEY, "d"),
            "20:15: " + getCheckMessage(MSG_KEY, "get"),
            "22:9: " + getCheckMessage(MSG_KEY, "int"),
            "23:35: " + getCheckMessage(MSG_KEY, "get1"),
            "32:9: " + getCheckMessage(MSG_KEY, "int"),
            "33:13: " + getCheckMessage(MSG_KEY, "cba"),
            "35:27: " + getCheckMessage(MSG_KEY, "String"),
            "36:28: " + getCheckMessage(MSG_KEY, "String"),
            "43:12: " + getCheckMessage(MSG_KEY, "ar"),
            "43:25: " + getCheckMessage(MSG_KEY, "int"),
            "44:17: " + getCheckMessage(MSG_KEY, "int"),
            "47:65: " + getCheckMessage(MSG_KEY, "getLongMultiArray"),
            "51:27: " + getCheckMessage(MSG_KEY, "}"),
            "53:23: " + getCheckMessage(MSG_KEY, "int"),
            "54:25: " + getCheckMessage(MSG_KEY, "]"),
            "55:36: " + getCheckMessage(MSG_KEY, "}"),
            "56:39: " + getCheckMessage(MSG_KEY, "]"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testArrayDeclarations2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        checkConfig.addAttribute("tokens", "INDEX_OP");
        final String[] expected = {
            "16:31: " + getCheckMessage(MSG_KEY, "]"),
            "21:41: " + getCheckMessage(MSG_KEY, "create"),
            "22:32: " + getCheckMessage(MSG_KEY, "int"),
            "33:26: " + getCheckMessage(MSG_KEY, "]"),
            "34:29: " + getCheckMessage(MSG_KEY, "int"),
            "34:42: " + getCheckMessage(MSG_KEY, "]"),
            "34:66: " + getCheckMessage(MSG_KEY, "]"),
            "39:47: " + getCheckMessage(MSG_KEY, "int"),
            "39:57: " + getCheckMessage(MSG_KEY, "]"),
            "40:19: " + getCheckMessage(MSG_KEY, "e"),
            "40:24: " + getCheckMessage(MSG_KEY, "]"),
            "40:44: " + getCheckMessage(MSG_KEY, "]"),
            "41:15: " + getCheckMessage(MSG_KEY, "e"),
            "41:19: " + getCheckMessage(MSG_KEY, "]"),
            "46:31: " + getCheckMessage(MSG_KEY, "Integer"),
            "47:21: " + getCheckMessage(MSG_KEY, "]"),
            "52:29: " + getCheckMessage(MSG_KEY, ">"),
            "52:32: " + getCheckMessage(MSG_KEY, "]"),
            "52:35: " + getCheckMessage(MSG_KEY, "]"),
            "56:35: " + getCheckMessage(MSG_KEY, "int"),
            "58:15: " + getCheckMessage(MSG_KEY, "g"),
            "59:18: " + getCheckMessage(MSG_KEY, "]"),
            "60:15: " + getCheckMessage(MSG_KEY, "g"),
            "60:19: " + getCheckMessage(MSG_KEY, "]"),
            "60:23: " + getCheckMessage(MSG_KEY, "]"),
            "66:55: " + getCheckMessage(MSG_KEY, "create"),
            "66:63: " + getCheckMessage(MSG_KEY, "]"),
            "71:33: " + getCheckMessage(MSG_KEY, "boolean"),
            "73:48: " + getCheckMessage(MSG_KEY, "String"),
            "73:52: " + getCheckMessage(MSG_KEY, "]"),
            "74:37: " + getCheckMessage(MSG_KEY, "String"),
            "85:41: " + getCheckMessage(MSG_KEY, "Integer"),
            "89:15: " + getCheckMessage(MSG_KEY, "char"),
            "90:53: " + getCheckMessage(MSG_KEY, "A"),
            "91:70: " + getCheckMessage(MSG_KEY, "Object"),
            "94:43: " + getCheckMessage(MSG_KEY, ")"),
            "94:52: " + getCheckMessage(MSG_KEY, "]"),
            "96:37: " + getCheckMessage(MSG_KEY, "Object"),
            "98:46: " + getCheckMessage(MSG_KEY, ")"),
            "101:43: " + getCheckMessage(MSG_KEY, "Object"),
            "104:45: " + getCheckMessage(MSG_KEY, "]"),
            "112:33: " + getCheckMessage(MSG_KEY, "Object"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations2.java"), expected);
    }

    @Test
    public void testArrayDeclarations3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        checkConfig.addAttribute("tokens", "INDEX_OP");
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations3.java"));
    }

    @Test
    public void testSynchronized() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SYNCHRONIZED");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "synchronized"),
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
            "11:41: " + getCheckMessage(MSG_KEY, "int"),
            "12:62: " + getCheckMessage(MSG_KEY, "String"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestMethodRef.java"), expected);
    }

    @Test
    public void testMethodReferenceAfter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_REF");
        final String[] expected = {
            "21:35: " + getCheckMessage(MSG_KEY, "::"),
            "22:64: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestMethodRefAfter.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        final String[] expected = {
            "15:32: " + getCheckMessage(MSG_KEY, "Entry"),
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
        checkConfig.addAttribute("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        checkConfig.addAttribute("allowLineBreaks", "false");
        final String[] expected = {
            "1:13: " + getCheckMessage(MSG_KEY, "."),
            "2:11: " + getCheckMessage(MSG_KEY, "."),
            "28:13: " + getCheckMessage(MSG_KEY, "-"),
            "28:20: " + getCheckMessage(MSG_KEY, "+"),
            "30:13: " + getCheckMessage(MSG_KEY, "++"),
            "30:20: " + getCheckMessage(MSG_KEY, "--"),
            "86:20: " + getCheckMessage(MSG_KEY, ")"),
            "88:13: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "110:21: " + getCheckMessage(MSG_KEY, "!"),
            "111:22: " + getCheckMessage(MSG_KEY, "~"),
            "128:23: " + getCheckMessage(MSG_KEY, "."),
            "131:10: " + getCheckMessage(MSG_KEY, "."),
            "135:11: " + getCheckMessage(MSG_KEY, "."),
            "240:17: " + getCheckMessage(MSG_KEY, ")"),
            "263:1: " + getCheckMessage(MSG_KEY, "."),
            "288:5: " + getCheckMessage(MSG_KEY, "@"),
            "289:5: " + getCheckMessage(MSG_KEY, "@"),
            "290:5: " + getCheckMessage(MSG_KEY, "@"),
            "295:28: " + getCheckMessage(MSG_KEY, "int"),
            "299:18: " + getCheckMessage(MSG_KEY, ")"),
            "307:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterTestAllTokens.java"), expected);
    }

    @Test
    public void testArrayDeclarationsAndAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputNoWhitespaceAfterArrayDeclarationsAndAnno.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, "
                + "BNOT, LNOT, DOT, TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, "
                + "METHOD_REF");
        checkConfig.addAttribute("allowLineBreaks", "false");

        final String[] expected = {
            "44:17: " + getCheckMessage(MSG_KEY, "ci"),
            "45:27: " + getCheckMessage(MSG_KEY, "int"),
            "46:16: " + getCheckMessage(MSG_KEY, "double"),
            "47:62: " + getCheckMessage(MSG_KEY, "cZ"),
            "51:42: " + getCheckMessage(MSG_KEY, "transformers"),
            "52:39: " + getCheckMessage(MSG_KEY, "transformers"),
            "57:17: " + getCheckMessage(MSG_KEY, "a"),
        };

        verify(checkConfig,
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
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
