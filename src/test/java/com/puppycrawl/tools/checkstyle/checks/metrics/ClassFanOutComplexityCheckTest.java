///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ClassFanOutComplexityCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/classfanoutcomplexity";
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "59:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexity.java"), expected);
    }

    @Test
    public void testExcludedPackagesDirectPackages() throws Exception {
        final String[] expected = {
            "29:1: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityExcludedPackagesDirectPackages.java"), expected);
    }

    @Test
    public void testExcludedPackagesCommonPackages() throws Exception {
        final String[] expected = {
            "28:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "32:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityExcludedPackagesCommonPackage.java"), expected);
    }

    @Test
    public void testExcludedPackagesCommonPackagesWithEndingDot() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addProperty("max", "0");
        checkConfig.addProperty("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.");

        try {
            createChecker(checkConfig);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "metrics.ClassFanOutComplexityCheck - "
                    + "Cannot set property 'excludedPackages' to "
                    + "'com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.'");
            assertWithMessage("Invalid exception message,")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("the following values are not valid identifiers: ["
                            + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.]");
        }
    }

    @Test
    public void testExcludedPackagesAllIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityExcludedPackagesAllIgnored.java"), expected);
    }

    @Test
    public void test15() throws Exception {

        final String[] expected = {
            "29:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexity15Extensions.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexity2.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ClassFanOutComplexityCheck classFanOutComplexityCheckObj =
            new ClassFanOutComplexityCheck();
        final int[] actual = classFanOutComplexityCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.EXTENDS_CLAUSE,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.ANNOTATION,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Acceptable tokens should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testRegularExpression() throws Exception {

        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "76:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexity3.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {

        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "76:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexity4.java"), expected);
    }

    @Test
    public void testWithMultiDimensionalArray() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityMultiDimensionalArray.java"), expected);
    }

    @Test
    public void testPackageName() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityPackageName.java"), expected);
    }

    @Test
    public void testExtends() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityExtends.java"), expected);
    }

    @Test
    public void testImplements() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityImplements.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expected = {
            "29:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "45:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "54:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "64:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "79:1: " + getCheckMessage(MSG_KEY, 1, 0),
            "99:1: " + getCheckMessage(MSG_KEY, 1, 0),
            "102:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityAnnotations.java"), expected);
    }

    @Test
    public void testImplementsAndNestedCount() throws Exception {
        final String[] expected = {
            "26:1: " + getCheckMessage(MSG_KEY, 3, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityImplementsAndNestedCount.java"), expected);
    }

    @Test
    public void testClassFanOutComplexityRecords() throws Exception {
        final String[] expected = {
            "32:1: " + getCheckMessage(MSG_KEY, 4, 2),
            "53:1: " + getCheckMessage(MSG_KEY, 4, 2),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputClassFanOutComplexityRecords.java"), expected);
    }

    @Test
    public void testClassFanOutComplexityIgnoreVar() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityVar.java"), expected);
    }

    @Test
    public void testClassFanOutComplexityRemoveIncorrectAnnotationToken() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityRemoveIncorrectAnnotationToken.java"), expected);
    }

    @Test
    public void testClassFanOutComplexityRemoveIncorrectTypeParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityRemoveIncorrectTypeParameter.java"), expected);
    }

    @Test
    public void testClassFanOutComplexityMultiCatchBitwiseOr() throws Exception {
        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_KEY, 5, 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityMultiCatchBitwiseOr.java"), expected);
    }

    @Test
    public void testThrows() throws Exception {
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityThrows.java"), expected);
    }

    @Test
    public void testSealedClasses() throws Exception {
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "32:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputClassFanOutComplexitySealedClasses.java"),
                expected);
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
    public void testClearStateImportedClassPackages() throws Exception {
        final ClassFanOutComplexityCheck check = new ClassFanOutComplexityCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputClassFanOutComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> importAst = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.IMPORT);

        assertWithMessage("Ast should contain IMPORT")
                .that(importAst.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                    importAst.orElseThrow(), "importedClassPackages",
                    importedClssPackage -> ((Map<String, String>) importedClssPackage).isEmpty()))
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
    public void testClearStateClassContexts() throws Exception {
        final ClassFanOutComplexityCheck check = new ClassFanOutComplexityCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputClassFanOutComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.orElseThrow(),
                        "classesContexts",
                        classContexts -> ((Collection<?>) classContexts).size() == 1))
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
    public void testClearStatePackageName() throws Exception {
        final ClassFanOutComplexityCheck check = new ClassFanOutComplexityCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputClassFanOutComplexity.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> packageDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.PACKAGE_DEF);

        assertWithMessage("Ast should contain PACKAGE_DEF")
                .that(packageDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        packageDef.orElseThrow(), "packageName",
                        packageName -> ((String) packageName).isEmpty()))
                .isTrue();
    }

    /**
     * Test [ClassName]::new expression. Such expression will be processed as a
     * normal declaration of a new instance. We need to make sure this does not
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testLambdaNew() throws Exception {
        final String[] expected = {
            "29:1: " + getCheckMessage(MSG_KEY, 2, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassFanOutComplexityLambdaNew.java"), expected);
    }

}
