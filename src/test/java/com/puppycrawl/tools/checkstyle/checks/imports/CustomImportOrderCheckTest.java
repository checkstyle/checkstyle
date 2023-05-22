///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LEX;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LINE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_IMPORT;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_SEPARATED_IN_GROUP;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CustomImportOrderCheckTest extends AbstractModuleTestSupport {

    /** Shortcuts to make code more compact. */
    private static final String STATIC = CustomImportOrderCheck.STATIC_RULE_GROUP;
    private static final String SAME = CustomImportOrderCheck.SAME_PACKAGE_RULE_GROUP;
    private static final String THIRD = CustomImportOrderCheck.THIRD_PARTY_PACKAGE_RULE_GROUP;
    private static final String STD = CustomImportOrderCheck.STANDARD_JAVA_PACKAGE_RULE_GROUP;
    private static final String SPECIAL = CustomImportOrderCheck.SPECIAL_IMPORTS_RULE_GROUP;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/customimportorder";
    }

    @Test
    public void testGetRequiredTokens() {
        final CustomImportOrderCheck checkObj = new CustomImportOrderCheck();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testCustom() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                    "java.io.File.createTempFile"),
            "17:1: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                    "java.io.File.createTempFile"),
            "20:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Button"),
            "21:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Frame"),
            "22:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Dialog"),
            "23:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.color.ColorSpace"),
            "24:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.event.ActionEvent"),
            "25:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "javax.swing.JComponent"),
            "26:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "javax.swing.JTable"),
            "27:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.File"),
            "28:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.IOException"),
            "29:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.InputStream"),
            "30:1: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.Reader"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderDefault.java"), expected);
    }

    /**
     * Checks different group orderings and imports which are out of those
     * specified in the configuration.
     */
    @Test
    public void testStaticStandardThird() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                    "java.io.File.createTempFile"),
            "17:1: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                    "java.io.File.createTempFile"),
            "22:1: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "27:1: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "28:1: " + getCheckMessage(MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "29:1: " + getCheckMessage(MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "30:1: " + getCheckMessage(MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "34:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "com.google.common.collect.*"),
            "34:1: " + getCheckMessage(MSG_LEX, "com.google.common.collect.*",
                    "com.puppycrawl.tools.checkstyle.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderDefault3.java"), expected);
    }

    /**
     * Checks different combinations for same_package group.
     */
    @Test
    public void testNonSpecifiedImports() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "17:1: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                "java.io.File.createTempFile"),
            "22:1: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "27:1: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "28:1: " + getCheckMessage(MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "29:1: " + getCheckMessage(MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "30:1: " + getCheckMessage(MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "32:1: " + getCheckMessage(MSG_ORDER, SAME, THIRD, "com.puppycrawl.tools.checkstyle.*"),
            "34:1: " + getCheckMessage(MSG_NONGROUP_IMPORT, "com.google.common.collect.*"),
            "35:1: " + getCheckMessage(MSG_LINE_SEPARATOR, "org.junit.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderDefault4.java"), expected);
    }

    @Test
    public void testOrderRuleEmpty() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.List"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderEmptyRule.java"), expected);
    }

    @Test
    public void testOrderRuleWithOneGroup() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                    "java.io.File.createTempFile"),
            "19:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.List"),
            "19:1: " + getCheckMessage(MSG_LEX, "java.util.List", "javax.swing.WindowConstants.*"),
            "20:1: " + getCheckMessage(MSG_LEX, "java.util.StringTokenizer",
                    "javax.swing.WindowConstants.*"),
            "21:1: " + getCheckMessage(MSG_LEX, "java.util.*", "javax.swing.WindowConstants.*"),
            "22:1: " + getCheckMessage(MSG_LEX, "java.util.concurrent.AbstractExecutorService",
                    "javax.swing.WindowConstants.*"),
            "23:1: " + getCheckMessage(MSG_LEX, "java.util.concurrent.*",
                    "javax.swing.WindowConstants.*"),
            "26:1: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.checkstyle.*",
                    "com.puppycrawl.tools.checkstyle.checks.*"),
            "28:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "com.google.common.base.*"),
            "28:1: " + getCheckMessage(MSG_LEX, "com.google.common.base.*",
                    "com.puppycrawl.tools.checkstyle.checks.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderDefault2.java"), expected);
    }

    @Test
    public void testStaticSamePackage() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "18:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "19:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "20:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                    "javax.swing.WindowConstants.*"),
            "21:1: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.*",
                    "java.util.StringTokenizer"),
            "22:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                    "java.util.concurrent.AbstractExecutorService"),
            "23:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                    "java.io.File.createTempFile"),
            "24:1: " + getCheckMessage(MSG_LEX, "com.*", "java.util.StringTokenizer"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackage.java"),
            expected);
    }

    @Test
    public void testWithoutLineSeparator() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "18:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "19:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "20:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                    "javax.swing.WindowConstants.*"),
            "21:1: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.*",
                    "java.util.StringTokenizer"),
            "22:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                    "java.util.concurrent.AbstractExecutorService"),
            "23:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                    "java.io.File.createTempFile"),
            "24:1: " + getCheckMessage(MSG_LEX, "com.*", "java.util.StringTokenizer"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackage2.java"),
            expected);
    }

    @Test
    public void testWithoutLineSeparator2() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.io.File.createTempFile",
                "javax.swing.WindowConstants.*"),
            "20:1: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.checkstyle.*",
                "com.puppycrawl.tools.checkstyle.checks.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_NoSeparator.java"), expected);
    }

    @Test
    public void testNoValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderNoValid.java"), expected);
    }

    @Test
    public void testPossibleIndexOutOfBoundsException() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, THIRD, "org.w3c.dom.Node"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderPossibleIndexOutOfBoundsException.java"), expected);
    }

    @Test
    public void testDefaultPackage2() throws Exception {

        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                    "java.io.File.createTempFile"),
            "22:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Button"),
            "23:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Frame"),
            "24:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Dialog"),
            "25:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.event.ActionEvent"),
            "26:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "javax.swing.JComponent"),
            "27:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "javax.swing.JTable"),
            "28:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.File"),
            "29:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.IOException"),
            "30:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.InputStream"),
            "31:1: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.Reader"),
            "35:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "com.google.common.*"),
            "35:1: " + getCheckMessage(MSG_LEX, "com.google.common.*", "com.puppycrawl.tools.*"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderDefaultPackage.java"),
            expected);
    }

    @Test
    public void testWithoutThirdPartyPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderThirdPartyPackage.java"), expected);
    }

    @Test
    public void testThirdPartyAndSpecialImports() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_ORDER, THIRD, SPECIAL,
                "com.google.common.collect.HashMultimap"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderThirdPartyAndSpecial.java"), expected);
    }

    @Test
    public void testCompareImports() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_LEX, "java.util.Map",
                "java.util.Map.Entry"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderCompareImports.java"), expected);
    }

    @Test
    public void testFindBetterPatternMatch() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_ORDER, THIRD, SPECIAL,
                "com.google.common.annotations.Beta"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderFindBetterPatternMatch.java"), expected);
    }

    @Test
    public void testBeginTreeClear() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(CustomImportOrderCheck.class);
        checkConfig.addProperty("specialImportsRegExp", "com");
        checkConfig.addProperty("separateLineBetweenGroups", "false");
        checkConfig.addProperty("customImportOrderRules",
            "STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Checker checker = createChecker(checkConfig);
        final String fileName1 = getPath("InputCustomImportOrderImportsContainingJava.java");
        final String fileName2 = getPath("InputCustomImportOrderNoValid.java");
        final File[] files = {
            new File(fileName1),
            new File(fileName2),
        };
        verify(checker, files, fileName1, expected);
    }

    @Test
    public void testImportsContainingJava() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderImportsContainingJava.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final CustomImportOrderCheck testCheckObject =
                new CustomImportOrderCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    // UT uses Reflection to avoid removing null-validation from static method,
    // which is a candidate for utility method in the future
    public void testGetFullImportIdent() throws Exception {
        final Class<?> clazz = CustomImportOrderCheck.class;
        final Object t = clazz.getConstructor().newInstance();
        final Method method = clazz.getDeclaredMethod("getFullImportIdent", DetailAST.class);
        method.setAccessible(true);
        final Object actual = method.invoke(t, new Object[] {null});

        final String expected = "";
        assertWithMessage("Invalid getFullImportIdent result")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testSamePackageDepth2() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.*"),
            "21:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.List"),
            "22:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.StringTokenizer"),
            "23:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "24:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                    "java.util.concurrent.AbstractExecutorService"),
            "25:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                    "java.util.concurrent.locks.LockSupport"),
            "26:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Pattern"),
            "27:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Matcher"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackageDepth25.java"),
            expected);
    }

    @Test
    public void testSamePackageDepth3() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "24:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.AbstractExecutorService"),
            "25:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.locks.LockSupport"),
            };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackageDepth252.java"),
            expected);
    }

    @Test
    public void testSamePackageDepth4() throws Exception {
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.locks.LockSupport"),
            };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackageDepth253.java"),
            expected);
    }

    @Test
    public void testSamePackageDepthLongerThenActualPackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSamePackageDepth254.java"),
                expected);
    }

    @Test
    public void testSamePackageDepthNegative() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputCustomImportOrderDefault5.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                        + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                        + ".imports.CustomImportOrderCheck - "
                        + "Cannot set property 'customImportOrderRules' to "
                        + "'SAME_PACKAGE(-1)'");
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("SAME_PACKAGE rule parameter should be positive integer: "
                        + "SAME_PACKAGE(-1)");
        }
    }

    @Test
    public void testSamePackageDepthZero() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputCustomImportOrderDefault6.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                        + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                        + ".imports.CustomImportOrderCheck - "
                        + "Cannot set property 'customImportOrderRules' to "
                        + "'SAME_PACKAGE(0)'");
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("SAME_PACKAGE rule parameter should be positive integer: "
                        + "SAME_PACKAGE(0)");
        }
    }

    @Test
    public void testUnsupportedRule() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputCustomImportOrderDefault7.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                        + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                        + ".imports.CustomImportOrderCheck - "
                        + "Cannot set property 'customImportOrderRules' to "
                        + "'SAME_PACKAGE(3)###UNSUPPORTED_RULE'");
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("Unexpected rule: UNSUPPORTED_RULE");
        }
    }

    @Test
    public void testSamePackageDepthNotInt() throws Exception {
        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputCustomImportOrderDefault8.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                        + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                        + ".imports.CustomImportOrderCheck - "
                        + "Cannot set property 'customImportOrderRules' to "
                        + "'SAME_PACKAGE(INT_IS_REQUIRED_HERE)'");
            assertWithMessage("Invalid exception message")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("For input string: \"INT_IS_REQUIRED_HERE\"");
        }
    }

    @Test
    public void testNoImports() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_NoImports.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.awt.Button"),
            "32:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "com.puppycrawl.tools.checkstyle.*"),
            "34:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "com.google.common.collect.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderDefault9.java"), expected);
    }

    @Test
    public void testRulesWithOverlappingPatterns() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_ORDER, THIRD, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl"),
            "27:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck"),
            "33:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocTag"),
            "35:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck"),
            "39:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SPECIAL,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag"),
            "40:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"),
            "41:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck"),
            };

        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_OverlappingPatterns.java"), expected);
    }

    @Test
    public void testMultiplePatternMatchesSecondPatternIsLonger() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_MultiplePatternMatches.java"),
            expected);
    }

    @Test
    public void testMultiplePatternMatchesFirstPatternHasLaterPosition() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_MultiplePatternMatches2.java"),
            expected);
    }

    @Test
    public void testMultiplePatternMatchesFirstPatternHasEarlierPosition() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_MultiplePatternMatches3.java"),
            expected);
    }

    @Test
    public void testMultiplePatternMultipleImportFirstPatternHasLaterPosition() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "org.junit.Test"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrder_MultiplePatternMultipleImport.java"),
            expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.*"),
            "19:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.HashMap"),
            "23:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "javax.net.ServerSocketFactory"),
            "26:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "javax.net.SocketFactory"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderNoPackage.java"),
            expected);
    }

    @Test
    public void testNoPackage2() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.sun.accessibility.internal.resources.*"),
            "22:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.Arrays"),
            "30:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "org.apache.commons.beanutils.converters.ArrayConverter"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderNoPackage2.java"),
            expected);
    }

    @Test
    public void testNoPackage3() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "java.util.Map"),
            "25:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "org.apache.*"),
            "29:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "antlr.*"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderNoPackage3.java"),
            expected);
    }

    @Test
    public void testInputCustomImportOrderSingleLine() throws Exception {
        final String[] expected = {
            "14:112: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "java.util.Map"),
            "15:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.google.common.annotations.Beta"),
            "22:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.puppycrawl.tools.checkstyle.*"),
            "26:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "picocli.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderSingleLine.java"),
            expected);
    }

    @Test
    public void testInputCustomImportOrderSingleLine2() throws Exception {
        final String[] expected = {
            "14:118: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "java.util.Map"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderSingleLine2.java"),
            expected);
    }

    @Test
    public void testInputCustomImportOrderThirdPartyAndSpecial2() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "javax.swing.WindowConstants.*"),
            "24:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "java.awt.Button"),
            "28:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "java.awt.Dialog"),
            "34:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "com.google.common.*"),
            "40:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "org.apache.commons.collections.*"),
            "45:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportRule"),
            "51:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "antlr.Token"),
            "53:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "antlr.collections.AST"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderThirdPartyAndSpecial2.java"), expected);
    }

    @Test
    public void testInputCustomImportOrderMultipleViolationsSameLine() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                "java.util.Collections.*"),
            "18:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC,
                "java.lang.String.CASE_INSENSITIVE_ORDER"),
            "21:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                "java.net.Socket"),
            "21:1: " + getCheckMessage(MSG_LEX, "java.net.Socket",
                "java.util.*"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderViolationsSameLine.java"),
            expected);
    }

    @Test
    public void testInputCustomImportOrderSpanMultipleLines() throws Exception {
        final String[] expected = {
            "30:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.BitSet"),
            "45:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "java.util.HashSet"),
            "49:1: " + getCheckMessage(MSG_LINE_SEPARATOR, "org.apache.tools.ant.*"),
            "54:1: " + getCheckMessage(MSG_LINE_SEPARATOR, "com.puppycrawl.tools.checkstyle.*"),
            "58:1: " + getCheckMessage(MSG_LINE_SEPARATOR, "picocli.*"),
            "61:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "picocli.CommandLine"),
        };
        verifyWithInlineConfigParser(
                getPath("InputCustomImportOrderSpanMultipleLines.java"), expected);
    }

    @Test
    public void testInputCustomImportOrderEclipseDefaultPositive() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.awt.Button"),
            "23:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.awt.Dialog"),
            "24:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.io.InputStream"),
            "26:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SPECIAL, "javax.swing.JComponent"),
            "27:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SPECIAL, "javax.swing.JTable"),
            "29:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, THIRD, "org.junit.Test"),
            "30:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, THIRD,
                    "org.mockito.Mock"),
            "34:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "sun.tools.java.ArrayType"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputCustomImportOrderEclipseDefaultPositive.java"),
                expected);
    }
}
