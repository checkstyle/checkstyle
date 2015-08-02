////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class IllegalInstantiationCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
            "classes",
            "java.lang.Boolean,"
                + "com.puppycrawl.tools.checkstyle.InputModifier,"
                + "java.io.File,"
                + "java.awt.Color");
        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "24:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "31:16: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "38:21: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.InputModifier"),
            "41:18: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "44:21: " + getCheckMessage(MSG_KEY, "java.awt.Color"),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testJava8() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalInstantiationCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                          + "coding/InputIllegalInstantiationCheckTest2.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "java.lang.Boolean");
        final String[] expected = {
            "3:19: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
        };
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                          + "coding/InputIllegalInstantiationCheckNoPackage.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testJavaLangPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "java.lang.Boolean,java.lang.String");
        final String[] expected = {
            "4:19: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "11:20: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                          + "coding/InputIllegalInstantiationCheckLang.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testWrongPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "jjva.lang.Boolean,java.lang*Boolean");
        final String[] expected = {};
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                          + "coding/InputIllegalInstantiationCheckLang.java").getCanonicalPath(),
                expected);
    }

    @Test
    public void testNullClassLoader() throws Exception {
        DetailAST exprAst = new DetailAST();
        exprAst.setType(TokenTypes.EXPR);

        DetailAST newAst = new DetailAST();
        newAst.setType(TokenTypes.LITERAL_NEW);
        newAst.setLineNo(1);
        newAst.setColumnNo(1);

        DetailAST identAst = new DetailAST();
        identAst.setType(TokenTypes.IDENT);
        identAst.setText("Boolean");

        DetailAST lparenAst = new DetailAST();
        lparenAst.setType(TokenTypes.LPAREN);

        DetailAST elistAst = new DetailAST();
        elistAst.setType(TokenTypes.ELIST);

        DetailAST rparenAst = new DetailAST();
        rparenAst.setType(TokenTypes.RPAREN);

        exprAst.addChild(newAst);
        newAst.addChild(identAst);
        identAst.setNextSibling(lparenAst);
        lparenAst.setNextSibling(elistAst);
        elistAst.setNextSibling(rparenAst);

        IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        File inputFile = new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                + "coding/InputIllegalInstantiationCheckLang.java");
        check.setFileContents(new FileContents(new FileText(inputFile, "UTF-8")));
        check.configure(createCheckConfig(IllegalInstantiationCheck.class));
        check.setMessages(new LocalizedMessages());

        check.setClasses("java.lang.Boolean");
        check.visitToken(newAst);
        check.finishTree(newAst);
    }

    @Test
    public void testTokensNotNull() {
        IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testImproperToken() throws Exception {
        IllegalInstantiationCheck check = new IllegalInstantiationCheck();

        DetailAST lambdaAst = new DetailAST();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            Assert.fail();
        }
        catch (IllegalArgumentException e) {
            // it is OK
        }
    }
}
