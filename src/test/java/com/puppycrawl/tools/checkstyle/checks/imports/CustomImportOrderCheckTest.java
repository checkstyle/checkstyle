////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LEX;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LINE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_IMPORT;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_ORDER;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class CustomImportOrderCheckTest extends BaseCheckTestSupport {
    /** Shortcuts to make code more compact. */
    private static final String STATIC = CustomImportOrderCheck.STATIC_RULE_GROUP;
    private static final String SAME = CustomImportOrderCheck.SAME_PACKAGE_RULE_GROUP;
    private static final String THIRD = CustomImportOrderCheck.THIRD_PARTY_PACKAGE_RULE_GROUP;
    private static final String STD = CustomImportOrderCheck.STANDARD_JAVA_PACKAGE_RULE_GROUP;
    private static final String SPECIAL = CustomImportOrderCheck.SPECIAL_IMPORTS_RULE_GROUP;

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "imports" + File.separator + "customimportorder" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "imports" + File.separator + "customimportorder" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final CustomImportOrderCheck checkObj = new CustomImportOrderCheck();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testCustom() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("standardPackageRegExp", "^(java|javax)\\.");
        checkConfig.addAttribute("thirdPartyPackageRegExp", "com|org");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "5: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                "java.io.File.createTempFile"),
            "8: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Button"),
            "9: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Frame"),
            "10: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.Dialog"),
            "11: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.color.ColorSpace"),
            "12: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.awt.event.ActionEvent"),
            "13: " + getCheckMessage(MSG_ORDER, STD, SAME, "javax.swing.JComponent"),
            "14: " + getCheckMessage(MSG_ORDER, STD, SAME, "javax.swing.JTable"),
            "15: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.File"),
            "16: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.IOException"),
            "17: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.InputStream"),
            "18: " + getCheckMessage(MSG_ORDER, STD, SAME, "java.io.Reader"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
    }

    /**
     * Checks different group orderings and imports which are out of those ones
     * specified in the configuration.
     */
    @Test
    public void testStaticStandardThird() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "com.|org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "5: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                "java.io.File.createTempFile"),
            "10: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "15: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "16: " + getCheckMessage(MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "17: " + getCheckMessage(MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "18: " + getCheckMessage(MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "22: " + getCheckMessage(MSG_LEX, "com.google.common.collect.*",
                "com.puppycrawl.tools.*"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
    }

    /**
     * Checks different combinations for same_package group.
     */
    @Test
    public void testNonSpecifiedImports() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "5: " + getCheckMessage(MSG_LEX, "java.awt.print.Paper.*",
                "java.io.File.createTempFile"),
            "10: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "15: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "16: " + getCheckMessage(MSG_LEX, "java.io.IOException", "javax.swing.JTable"),
            "17: " + getCheckMessage(MSG_LEX, "java.io.InputStream", "javax.swing.JTable"),
            "18: " + getCheckMessage(MSG_LEX, "java.io.Reader", "javax.swing.JTable"),
            "20: " + getCheckMessage(MSG_ORDER, SAME, THIRD, "com.puppycrawl.tools.*"),
            "22: " + getCheckMessage(MSG_NONGROUP_IMPORT, "com.google.common.collect.*"),
            "23: " + getCheckMessage(MSG_LINE_SEPARATOR, "org.junit.*"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
    }

    @Test
    public void testOrderRuleWithOneGroup() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "7: " + getCheckMessage(MSG_LEX, "java.util.List", "javax.swing.WindowConstants.*"),
            "8: " + getCheckMessage(MSG_LEX, "java.util.StringTokenizer",
                "javax.swing.WindowConstants.*"),
            "9: " + getCheckMessage(MSG_LEX, "java.util.*", "javax.swing.WindowConstants.*"),
            "10: " + getCheckMessage(MSG_LEX, "java.util.concurrent.AbstractExecutorService",
                "javax.swing.WindowConstants.*"),
            "11: " + getCheckMessage(MSG_LEX, "java.util.concurrent.*",
                "javax.swing.WindowConstants.*"),
            "14: " + getCheckMessage(MSG_LEX, "com.*", "com.puppycrawl.tools.*"),
            "16: " + getCheckMessage(MSG_LEX, "com.google.common.base.*", "com.puppycrawl.tools.*"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderDefault2.java"), expected);
    }

    @Test
    public void testStaticSamePackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "6: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "javax.swing.WindowConstants.*"),
            "9: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.*", "java.util.StringTokenizer"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.io.File.createTempFile"),
            "12: " + getCheckMessage(MSG_LEX, "com.*", "java.util.StringTokenizer"),
        };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackage.java"),
            expected);
    }

    @Test
    public void testWithoutLineSeparator() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "org.");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SAME_PACKAGE(3)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_LEX, "java.util.*", "java.util.StringTokenizer"),
            "6: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "javax.swing.WindowConstants.*"),
            "9: " + getCheckMessage(MSG_LEX, "com.puppycrawl.tools.*", "java.util.StringTokenizer"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.io.File.createTempFile"),
            "12: " + getCheckMessage(MSG_LEX, "com.*", "java.util.StringTokenizer"),
        };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackage.java"),
            expected);
    }

    @Test
    public void testWithoutLineSeparator2() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LEX, "java.io.File.createTempFile",
                "javax.swing.WindowConstants.*"),
            "8: " + getCheckMessage(MSG_LEX, "com.*", "com.puppycrawl.tools.*"),
        };

        verify(checkConfig, getPath("InputCustomImportOrder_NoSeparator.java"), expected);
    }

    @Test
    public void testNoValid() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", ".*");
        checkConfig.addAttribute("specialImportsRegExp", "com.google");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCustomImportOrderNoValid.java"), expected);
    }

    @Test
    public void testPossibleIndexOutOfBoundsException() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", ".*");
        checkConfig.addAttribute("specialImportsRegExp", "com.google");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_NONGROUP_EXPECTED, THIRD, "org.w3c.dom.Node"),
        };

        verify(checkConfig,
            getPath("InputCustomImportOrderPossibleIndexOutOfBoundsException.java"), expected);
    }

    @Test
    public void testDefaultPackage2() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("thirdPartyPackageRegExp", "com|org");
        checkConfig.addAttribute("customImportOrderRules",
            "STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");

        final String[] expected = {
            "7: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT",
                "java.io.File.createTempFile"),
            "10: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Button"),
            "11: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Frame"),
            "12: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.Dialog"),
            "13: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.awt.event.ActionEvent"),
            "14: " + getCheckMessage(MSG_ORDER, STD, THIRD, "javax.swing.JComponent"),
            "15: " + getCheckMessage(MSG_ORDER, STD, THIRD, "javax.swing.JTable"),
            "16: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.File"),
            "17: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.IOException"),
            "18: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.InputStream"),
            "19: " + getCheckMessage(MSG_ORDER, STD, THIRD, "java.io.Reader"),
            "23: " + getCheckMessage(MSG_LEX, "com.google.common.*", "com.puppycrawl.tools.*"),
        };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderDefaultPackage.java"),
            expected);
    }

    @Test
    public void testWithoutThirdPartyPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("separateLineBetweenGroups", "true");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE###STATIC");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LINE_SEPARATOR, "org.junit.*"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderThirdPartyPackage.java"), expected);
    }

    @Test
    public void testThirdPartyAndSpecialImports() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("specialImportsRegExp", "antlr.*");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS");
        final String[] expected = {
            "11: " + getCheckMessage(MSG_ORDER, THIRD, SPECIAL,
                "com.google.common.collect.HashMultimap"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderThirdPartyAndSpecial.java"), expected);
    }

    @Test
    public void testImportsContainingJava() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck"),
        };

        verify(checkConfig, getPath("InputCustomImportOrderImportsContainingJava.java"), expected);
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

        assertArrayEquals(expected, actual);
    }

    @Test
    // UT uses Reflection to avoid removing null-validation from static method,
    // which is a candidate for utility method in the future
    public void testGetFullImportIdent() {
        Object actual;
        try {
            final Class<?> clazz = CustomImportOrderCheck.class;
            final Object t = clazz.getConstructor().newInstance();
            final Method method = clazz.getDeclaredMethod("getFullImportIdent", DetailAST.class);
            method.setAccessible(true);
            actual = method.invoke(t, (DetailAST) null);
        }
        catch (NoSuchMethodException | InstantiationException
                  | IllegalAccessException | InvocationTargetException ignored) {
            actual = null;
        }

        final String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void testSamePackageDepth2() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(2)");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.*"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.List"),
            "9: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.StringTokenizer"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.AbstractExecutorService"),
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.locks.LockSupport"),
            "13: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Pattern"),
            "14: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Matcher"),
            };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackageDepth25.java"),
            expected);
    }

    @Test
    public void testSamePackageDepth3() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.AbstractExecutorService"),
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.locks.LockSupport"),
            };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackageDepth25.java"),
            expected);
    }

    @Test
    public void testSamePackageDepth4() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(4)");
        final String[] expected = {
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME,
                "java.util.concurrent.locks.LockSupport"),
            };

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackageDepth25.java"),
            expected);
    }

    @Test
    public void testSamePackageDepthLongerThenActualPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(5)");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("InputCustomImportOrderSamePackageDepth25.java"),
                expected);
    }

    @Test
    public void testSamePackageDepthNegative() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(-1)");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'customImportOrderRules' to "
                            + "'SAME_PACKAGE(-1)' in module"));
        }
    }

    @Test
    public void testSamePackageDepthZero() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(0)");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'customImportOrderRules' to "
                            + "'SAME_PACKAGE(0)' in module"));
        }
    }

    @Test
    public void testUnsupportedRule() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        // #AAA##BBBB###CCCC####DDDD
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)###UNSUPPORTED_RULE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'customImportOrderRules' to "
                            + "'SAME_PACKAGE(3)###UNSUPPORTED_RULE' in module"));
        }
    }

    @Test
    public void testSamePackageDepthNotInt() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(INT_IS_REQUIRED_HERE)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'customImportOrderRules' to "
                            + "'SAME_PACKAGE(INT_IS_REQUIRED_HERE)' in module"));
        }
    }

    @Test
    public void testNoImports() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCustomImportOrder_NoImports.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCustomImportOrderDefault.java"), expected);
    }

    @Test
    public void testRulesWithOverlappingPatterns() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "THIRD_PARTY_PACKAGE###SAME_PACKAGE(6)###STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS");
        checkConfig.addAttribute("standardPackageRegExp", "com.puppycrawl.tools.*Check$");
        checkConfig.addAttribute("specialImportsRegExp", "com.puppycrawl.tools.*Tag*");
        checkConfig.addAttribute("thirdPartyPackageRegExp",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.*Javadoc*");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_ORDER, THIRD, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl"),
            "21: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck"),
            "25: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SPECIAL,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag"),
            "26: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"),
            "27: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck"),
            };

        createChecker(checkConfig);
        verify(checkConfig, getPath("InputCustomImportOrder_OverlappingPatterns.java"), expected);
    }

    @Test
    public void testMultiplePatternMatchesSecondPatternIsLonger() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("specialImportsRegExp", "org");
        checkConfig.addAttribute("standardPackageRegExp", "junit");

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCustomImportOrder_MultiplePatternMatches.java"),
            expected);
    }

    @Test
    public void testMultiplePatternMatchesFirstPatternHasLaterPosition() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("specialImportsRegExp", "Test");
        checkConfig.addAttribute("standardPackageRegExp", "unit");

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCustomImportOrder_MultiplePatternMatches.java"),
            expected);
    }

    @Test
    public void testMultiplePatternMatchesFirstPatternHasEarlierPosition() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE");
        checkConfig.addAttribute("specialImportsRegExp", "unit");
        checkConfig.addAttribute("standardPackageRegExp", "Test");

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputCustomImportOrder_MultiplePatternMatches.java"),
            expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###THIRD_PARTY_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("separateLineBetweenGroups", "true");

        createChecker(checkConfig);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_LINE_SEPARATOR, "java.util.*"),
        };
        verify(checkConfig, getPath("InputCustomImportOrderNoPackage.java"),
            expected);
    }

    @Test
    public void testNoPackage2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules",
                "STATIC###THIRD_PARTY_PACKAGE");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        checkConfig.addAttribute("separateLineBetweenGroups", "true");

        createChecker(checkConfig);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.puppycrawl.tools.checkstyle.utils.AnnotationUtility.containsAnnotation"),
            "7: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "com.sun.accessibility.internal.resources.*"),
            "11: " + getCheckMessage(MSG_LINE_SEPARATOR, "java.util.Arrays"),
            "19: " + getCheckMessage(MSG_LINE_SEPARATOR,
                "org.apache.commons.beanutils.converters.ArrayConverter"),
        };
        verify(checkConfig, getPath("InputCustomImportOrderNoPackage2.java"),
            expected);
    }
}
