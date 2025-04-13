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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiationSemantic.java"), expected);
    }

    @Test
    public void testClasses() throws Exception {
        final String[] expected = {
            "24:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "29:21: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "36:16: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "43:21: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding."
                + "illegalinstantiation.InputModifier"),
            "46:18: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "49:21: " + getCheckMessage(MSG_KEY, "java.awt.Color"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiationSemantic2.java"), expected);
    }

    @Test
    public void testSameClassNameAsJavaLang() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiationSameClassNameJavaLang.java"),
                expected);
    }

    @Test
    public void testJava8() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiation.java"),
                expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final String[] expected = {
            "10:20: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiationNoPackage.java"), expected);
    }

    @Test
    public void testJavaLangPackage() throws Exception {
        final String[] expected = {
            "13:19: " + getCheckMessage(MSG_KEY, "java.lang.Boolean"),
            "21:20: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalInstantiationLang.java"),
                expected);
    }

    @Test
    public void testWrongPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalInstantiationLang2.java"),
                expected);
    }

    @Test
    public void testJavaLangPackage3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalInstantiationLang3.java"),
                expected);
    }

    @Test
    public void testNameSimilarToStandardClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputIllegalInstantiationNameSimilarToStandardClasses.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testImproperToken() {
        final IllegalInstantiationCheck check = new IllegalInstantiationCheck();

        final DetailAstImpl lambdaAst = new DetailAstImpl();
        lambdaAst.setType(TokenTypes.LAMBDA);

        try {
            check.visitToken(lambdaAst);
            assertWithMessage("IllegalArgumentException is expected").fail();
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

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
            .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.orElseThrow(),
                    "classNames", classNames -> ((Collection<String>) classNames).isEmpty()))
            .isTrue();
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

        assertWithMessage("Ast should contain IMPORT_DEF")
                .that(importDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
            .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, importDef.orElseThrow(),
                    "imports", imports -> ((Collection<?>) imports).isEmpty()))
            .isTrue();
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

        assertWithMessage("Ast should contain LITERAL_NEW")
                .that(literalNew.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        literalNew.orElseThrow(), "instantiations",
                        instantiations -> ((Collection<DetailAST>) instantiations).isEmpty()))
                .isTrue();
    }

    @Test
    public void testStateIsClearedOnBeginTreePackageName() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalInstantiationCheck.class);
        checkConfig.addProperty("classes",
                "java.lang.Boolean,com.puppycrawl.tools.checkstyle.checks.coding."
                        + "illegalinstantiation.InputIllegalInstantiationBeginTree2."
                        + "InputModifier");
        final String file1 = getPath(
                "InputIllegalInstantiationBeginTree1.java");
        final String file2 = getPath(
                "InputIllegalInstantiationBeginTree2.java");
        final List<String> expectedFirstInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final File[] inputs = {new File(file1), new File(file2)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            file1, expectedFirstInput,
            file2, expectedSecondInput));
    }
}
