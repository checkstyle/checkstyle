////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.nio.charset.StandardCharsets;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalInstantiationCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalinstantiation";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
            "classes",
            "java.lang.Boolean,"
                + "com.puppycrawl.tools.checkstyle.checks.coding."
                + "illegalinstantiation.InputModifier,"
                + "java.io.File,"
                + "java.awt.Color");
        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "24:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "31:16: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "38:21: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding."
                + "illegalinstantiation.InputModifier"),
            "41:18: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "44:21: " + getCheckMessage(MSG_KEY, "java.awt.Color"),
        };
        verify(checkConfig, getPath("InputIllegalInstantiationSemantic.java"), expected);
    }

    @Test
    public void testJava8() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalInstantiationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputIllegalInstantiation.java"),
                expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "java.lang.Boolean");
        final String[] expected = {
            "3:20: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputIllegalInstantiationNoPackage.java"),
                expected);
    }

    @Test
    public void testJavaLangPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "java.lang.Boolean,java.lang.String");
        final String[] expected = {
            "5:19: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "13:20: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputIllegalInstantiationLang.java"),
                expected);
    }

    @Test
    public void testWrongPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
                "classes",
                "jjva.lang.Boolean,java.lang*Boolean");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getNonCompilablePath("InputIllegalInstantiationLang.java"),
                expected);
    }

    @Test
    public void testNullClassLoader() throws Exception {
        final DetailAST exprAst = new DetailAST();
        exprAst.setType(TokenTypes.EXPR);

        final DetailAST newAst = new DetailAST();
        newAst.setType(TokenTypes.LITERAL_NEW);
        newAst.setLineNo(1);
        newAst.setColumnNo(1);

        final DetailAST identAst = new DetailAST();
        identAst.setType(TokenTypes.IDENT);
        identAst.setText("Boolean");

        final DetailAST lparenAst = new DetailAST();
        lparenAst.setType(TokenTypes.LPAREN);

        final DetailAST elistAst = new DetailAST();
        elistAst.setType(TokenTypes.ELIST);

        final DetailAST rparenAst = new DetailAST();
        rparenAst.setType(TokenTypes.RPAREN);

        exprAst.addChild(newAst);
        newAst.addChild(identAst);
        identAst.setNextSibling(lparenAst);
        lparenAst.setNextSibling(elistAst);
        elistAst.setNextSibling(rparenAst);

        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        final File inputFile = new File(getNonCompilablePath("InputIllegalInstantiationLang.java"));
        check.setFileContents(new FileContents(new FileText(inputFile,
                StandardCharsets.UTF_8.name())));
        check.configure(createModuleConfig(IllegalInstantiationCheck.class));
        check.setClasses("java.lang.Boolean");

        check.visitToken(newAst);
        final SortedSet<LocalizedMessage> messages1 = check.getMessages();

        Assert.assertEquals("No exception messages expected", 0, messages1.size());

        check.finishTree(newAst);
        final SortedSet<LocalizedMessage> messages2 = check.getMessages();

        final LocalizedMessage addExceptionMessage = new LocalizedMessage(1,
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", "instantiation.avoid",
                new String[] {"java.lang.Boolean"}, null,
                getClass(), null);
        Assert.assertEquals("Invalid exception message",
                addExceptionMessage.getMessage(),
            messages2.first().getMessage());
    }

    @Test
    public void testTokensNotNull() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertNotNull("Required tokens should not be null", check.getRequiredTokens());
    }

    @Test
    public void testImproperToken() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();

        final DetailAST lambdaAst = new DetailAST();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            Assert.fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            // it is OK
        }
    }

}
