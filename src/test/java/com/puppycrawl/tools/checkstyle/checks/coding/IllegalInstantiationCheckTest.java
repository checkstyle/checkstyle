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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputIllegalInstantiationSemantic.java"), expected);
    }

    @Test
    public void testClasses() throws Exception {
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
    public void testSameClassNameAsJavaLang() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
            "classes",
            "java.lang.InputTest");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputIllegalInstantiationSameClassNameJavaLang.java"),
                expected);
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
            "2:20: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
        };
        verify(checkConfig, getPath("InputIllegalInstantiationNoPackage.java"), expected);
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
    public void testTokensNotNull() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testImproperToken() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();

        final DetailAstImpl lambdaAst = new DetailAstImpl();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            // it is OK
        }
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClearStateClassNames() throws Exception {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputIllegalInstantiationSemantic.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertTrue(classDef.isPresent(), "Ast should contain CLASS_DEF");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.get(), "classNames",
                classNames -> ((Collection<String>) classNames).isEmpty()),
                "State is not cleared on beginTree");
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testClearStateImports() throws Exception {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        final DetailAST root = JavaParser.parseFile(new File(
                getPath("InputIllegalInstantiationSemantic.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> importDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.IMPORT);

        assertTrue(importDef.isPresent(), "Ast should contain IMPORT_DEF");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, importDef.get(), "imports",
                imports -> ((Collection<?>) imports).isEmpty()),
                "State is not cleared on beginTree");
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClearStateInstantiations() throws Exception {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        final DetailAST root = JavaParser.parseFile(new File(
                getNonCompilablePath("InputIllegalInstantiationLang.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> literalNew = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.LITERAL_NEW);

        assertTrue(literalNew.isPresent(), "Ast should contain LITERAL_NEW");
        assertTrue(
            TestUtil.isStatefulFieldClearedDuringBeginTree(check, literalNew.get(),
                "instantiations",
                instantiations -> ((Collection<DetailAST>) instantiations).isEmpty()),
            "State is not cleared on beginTree");
    }
}
