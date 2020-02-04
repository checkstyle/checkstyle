////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
            "5:13: " + getCheckMessage(MSG_KEY, "."),
            "6:11: " + getCheckMessage(MSG_KEY, "."),
            "29:13: " + getCheckMessage(MSG_KEY, "-"),
            "29:20: " + getCheckMessage(MSG_KEY, "+"),
            "31:13: " + getCheckMessage(MSG_KEY, "++"),
            "31:20: " + getCheckMessage(MSG_KEY, "--"),
            "111:21: " + getCheckMessage(MSG_KEY, "!"),
            "112:22: " + getCheckMessage(MSG_KEY, "~"),
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "132:10: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
            "264:1: " + getCheckMessage(MSG_KEY, "."),
            "289:5: " + getCheckMessage(MSG_KEY, "@"),
            "290:5: " + getCheckMessage(MSG_KEY, "@"),
            "291:5: " + getCheckMessage(MSG_KEY, "@"),
            "296:28: " + getCheckMessage(MSG_KEY, "int"),
            "308:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfter.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:13: " + getCheckMessage(MSG_KEY, "."),
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfter.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "241:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfter.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        checkConfig.addAttribute("tokens", "INDEX_OP");
        final String[] expected = {
            "6:12: " + getCheckMessage(MSG_KEY, "Object"),
            "8:23: " + getCheckMessage(MSG_KEY, "someStuff3"),
            "9:9: " + getCheckMessage(MSG_KEY, "int"),
            "10:14: " + getCheckMessage(MSG_KEY, "s"),
            "11:14: " + getCheckMessage(MSG_KEY, "d"),
            "16:15: " + getCheckMessage(MSG_KEY, "get"),
            "18:9: " + getCheckMessage(MSG_KEY, "int"),
            "19:35: " + getCheckMessage(MSG_KEY, "get1"),
            "28:9: " + getCheckMessage(MSG_KEY, "int"),
            "29:13: " + getCheckMessage(MSG_KEY, "cba"),
            "31:27: " + getCheckMessage(MSG_KEY, "String"),
            "32:28: " + getCheckMessage(MSG_KEY, "String"),
            "39:12: " + getCheckMessage(MSG_KEY, "ar"),
            "39:25: " + getCheckMessage(MSG_KEY, "int"),
            "40:17: " + getCheckMessage(MSG_KEY, "int"),
            "43:65: " + getCheckMessage(MSG_KEY, "getLongMultiArray"),
            "47:27: " + getCheckMessage(MSG_KEY, "}"),
            "49:23: " + getCheckMessage(MSG_KEY, "int"),
            "50:25: " + getCheckMessage(MSG_KEY, "]"),
            "51:36: " + getCheckMessage(MSG_KEY, "}"),
            "52:39: " + getCheckMessage(MSG_KEY, "]"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testArrayDeclarations2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        checkConfig.addAttribute("tokens", "INDEX_OP");
        final String[] expected = {
            "12:31: " + getCheckMessage(MSG_KEY, "]"),
            "17:41: " + getCheckMessage(MSG_KEY, "create"),
            "18:32: " + getCheckMessage(MSG_KEY, "int"),
            "29:26: " + getCheckMessage(MSG_KEY, "]"),
            "30:29: " + getCheckMessage(MSG_KEY, "int"),
            "30:42: " + getCheckMessage(MSG_KEY, "]"),
            "30:66: " + getCheckMessage(MSG_KEY, "]"),
            "35:47: " + getCheckMessage(MSG_KEY, "int"),
            "35:57: " + getCheckMessage(MSG_KEY, "]"),
            "36:19: " + getCheckMessage(MSG_KEY, "e"),
            "36:24: " + getCheckMessage(MSG_KEY, "]"),
            "36:44: " + getCheckMessage(MSG_KEY, "]"),
            "37:15: " + getCheckMessage(MSG_KEY, "e"),
            "37:19: " + getCheckMessage(MSG_KEY, "]"),
            "42:31: " + getCheckMessage(MSG_KEY, "Integer"),
            "43:21: " + getCheckMessage(MSG_KEY, "]"),
            "48:29: " + getCheckMessage(MSG_KEY, ">"),
            "48:32: " + getCheckMessage(MSG_KEY, "]"),
            "48:35: " + getCheckMessage(MSG_KEY, "]"),
            "52:35: " + getCheckMessage(MSG_KEY, "int"),
            "54:15: " + getCheckMessage(MSG_KEY, "g"),
            "55:18: " + getCheckMessage(MSG_KEY, "]"),
            "56:15: " + getCheckMessage(MSG_KEY, "g"),
            "56:19: " + getCheckMessage(MSG_KEY, "]"),
            "56:23: " + getCheckMessage(MSG_KEY, "]"),
            "62:55: " + getCheckMessage(MSG_KEY, "create"),
            "62:63: " + getCheckMessage(MSG_KEY, "]"),
            "67:33: " + getCheckMessage(MSG_KEY, "boolean"),
            "69:48: " + getCheckMessage(MSG_KEY, "String"),
            "69:52: " + getCheckMessage(MSG_KEY, "]"),
            "70:37: " + getCheckMessage(MSG_KEY, "String"),
            "81:41: " + getCheckMessage(MSG_KEY, "Integer"),
            "85:15: " + getCheckMessage(MSG_KEY, "char"),
            "86:53: " + getCheckMessage(MSG_KEY, "A"),
            "87:70: " + getCheckMessage(MSG_KEY, "Object"),
            "90:43: " + getCheckMessage(MSG_KEY, ")"),
            "90:52: " + getCheckMessage(MSG_KEY, "]"),
            "92:37: " + getCheckMessage(MSG_KEY, "Object"),
            "94:46: " + getCheckMessage(MSG_KEY, ")"),
            "97:43: " + getCheckMessage(MSG_KEY, "Object"),
            "100:45: " + getCheckMessage(MSG_KEY, "]"),
            "108:33: " + getCheckMessage(MSG_KEY, "Object"),
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
            "14:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterSynchronized.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        verify(checkConfig, getPath("InputNoWhitespaceAfterFormerNpe.java"));
    }

    @Test
    public void testMethodReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        final String[] expected = {
            "9:41: " + getCheckMessage(MSG_KEY, "int"),
            "10:62: " + getCheckMessage(MSG_KEY, "String"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterMethodRef.java"), expected);
    }

    @Test
    public void testMethodReferenceAfter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_REF");
        final String[] expected = {
            "17:35: " + getCheckMessage(MSG_KEY, "::"),
            "18:64: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterBadMethodRef.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceAfterCheck.class);
        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
        final String[] expected = {
            "11:32: " + getCheckMessage(MSG_KEY, "Entry"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfterArrayDeclarator.java"), expected);
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
            "5:13: " + getCheckMessage(MSG_KEY, "."),
            "6:11: " + getCheckMessage(MSG_KEY, "."),
            "29:13: " + getCheckMessage(MSG_KEY, "-"),
            "29:20: " + getCheckMessage(MSG_KEY, "+"),
            "31:13: " + getCheckMessage(MSG_KEY, "++"),
            "31:20: " + getCheckMessage(MSG_KEY, "--"),
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "90:13: " + getCheckMessage(MSG_KEY, ")"),
            "111:21: " + getCheckMessage(MSG_KEY, "!"),
            "112:22: " + getCheckMessage(MSG_KEY, "~"),
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "132:10: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
            "241:17: " + getCheckMessage(MSG_KEY, ")"),
            "264:1: " + getCheckMessage(MSG_KEY, "."),
            "289:5: " + getCheckMessage(MSG_KEY, "@"),
            "290:5: " + getCheckMessage(MSG_KEY, "@"),
            "291:5: " + getCheckMessage(MSG_KEY, "@"),
            "296:28: " + getCheckMessage(MSG_KEY, "int"),
            "300:18: " + getCheckMessage(MSG_KEY, ")"),
            "308:5: " + getCheckMessage(MSG_KEY, "someStuff8"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceAfter.java"), expected);
    }

    /**
     * Creates MOCK lexical token and returns AST node for this token.
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
