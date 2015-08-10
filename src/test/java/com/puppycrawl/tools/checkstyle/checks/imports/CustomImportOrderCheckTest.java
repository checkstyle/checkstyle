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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LEX;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LINE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_IMPORT;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_ORDER;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CustomImportOrderCheckTest extends BaseCheckTestSupport {
    /** Shortcuts to make code more compact */
    private static final String STATIC = com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.STATIC_RULE_GROUP;
    private static final String SAME = com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.SAME_PACKAGE_RULE_GROUP;
    private static final String THIRD = com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.THIRD_PARTY_PACKAGE_RULE_GROUP;
    private static final String STD = com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.STANDARD_JAVA_PACKAGE_RULE_GROUP;
    private static final String SPECIAL = com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.SPECIAL_IMPORTS_RULE_GROUP;

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
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
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

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
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
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
            "10: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "15: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "22: " + getCheckMessage(MSG_LEX, "com.google.common.collect.*", "com.puppycrawl.tools.*"),
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
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
            "4: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
            "10: " + getCheckMessage(MSG_LEX, "java.awt.Dialog", "java.awt.Frame"),
            "15: " + getCheckMessage(MSG_LEX, "java.io.File", "javax.swing.JTable"),
            "20: " + getCheckMessage(MSG_ORDER, SAME, THIRD, "com.puppycrawl.tools.*"),
            "22: " + getCheckMessage(MSG_NONGROUP_IMPORT, "com.google.common.collect.*"),
            "23: " + getCheckMessage(MSG_LINE_SEPARATOR, "org.junit.*"),
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
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
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.util.List"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.util.StringTokenizer"),
            "9: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.util.*"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STD, "java.util.concurrent.*"),
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder2.java"), expected);
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
            "6: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "javax.swing.WindowConstants.*"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.io.File.createTempFile"),
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackage.java").getCanonicalPath(), expected);
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
            "6: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.*"),
            "7: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.awt.Button.ABORT"),
            "8: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "javax.swing.WindowConstants.*"),
            "10: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.AbstractExecutorService"),
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, STATIC, "java.io.File.createTempFile"),
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackage.java").getCanonicalPath(), expected);
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
            "4: " + getCheckMessage(MSG_LEX, "java.io.File.createTempFile", "javax.swing.WindowConstants.*"),
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder_NoSeparator.java"), expected);
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
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrderNoValid.java"), expected);
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

        verify(checkConfig, getPath("imports" + File.separator
                + "DOMSource.java"), expected);
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
            "7: " + getCheckMessage(MSG_LEX, "java.awt.Button.ABORT", "java.io.File.createTempFile"),
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

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/InputDefaultPackage.java").getCanonicalPath(), expected);
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

        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrderThirdPartyPackage.java"), expected);
    }

    @Test
    public void testThirdPartyAndSpecialImports() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("specialImportsRegExp", "antlr.*");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS");
        final String[] expected = {
            "11: " + getCheckMessage(MSG_ORDER, THIRD, SPECIAL, "com.google.common.annotations.GwtCompatible"),
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrderThirdPartyAndSpecial.java"), expected);
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

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrderImportsContainingJava.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        CustomImportOrderCheck testCheckObject =
                new CustomImportOrderCheck();
        int[] actual = testCheckObject.getAcceptableTokens();
        int[] expected = new int[]{
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
            Class<?> c = Class.forName(
                    "com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck");
            Object t = c.newInstance();
            Method m = c.getDeclaredMethod("getFullImportIdent", DetailAST.class);
            m.setAccessible(true);
            actual = m.invoke(t, (DetailAST) null);
        }
        catch (Exception e) {
            e.printStackTrace();
            actual = null;
        }

        String expected = "";
        assertEquals(expected, (String) actual);
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
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.AbstractExecutorService"),
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.locks.LockSupport"),
            "13: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Pattern"),
            "14: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.regex.Matcher"),
            };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackageDepth2-5.java").getCanonicalPath(), expected);
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
            "11: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.AbstractExecutorService"),
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.locks.LockSupport"),
            };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackageDepth2-5.java").getCanonicalPath(), expected);
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
            "12: " + getCheckMessage(MSG_NONGROUP_EXPECTED, SAME, "java.util.concurrent.locks.LockSupport"),
            };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackageDepth2-5.java").getCanonicalPath(), expected);
    }

    @Test
    public void testSamePackageDepthLongerThenActualPackage() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(5)");
        final String[] expected = {};

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrderSamePackageDepth2-5.java").getCanonicalPath(), expected);
    }

    @Test(expected = CheckstyleException.class)
    public void testSamePackageDepthNegative() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(-1)");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    @Test(expected = CheckstyleException.class)
    public void testSamePackageDepthZero() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "false");
        checkConfig.addAttribute("separateLineBetweenGroups", "false");
        checkConfig.addAttribute("customImportOrderRules",
                "SAME_PACKAGE(0)");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    @Test(expected = CheckstyleException.class)
    public void testUnsupportedRule() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)###UNSUPPORTED_RULE"); //#AAA##BBBB###CCCC####DDDD
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    @Test(expected = CheckstyleException.class)
    public void testSamePackageDepthNotInt() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(INT_IS_REQUIRED_HERE)");
        checkConfig.addAttribute("sortImportsInGroupAlphabetically", "true");
        final String[] expected = {};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
    }

    @Test
    public void testNoImports() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(CustomImportOrderCheck.class);
        checkConfig.addAttribute("customImportOrderRules", "SAME_PACKAGE(3)");
        final String[] expected = {};

        verify(checkConfig, new File("src/test/resources/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputCustomImportOrder_NoImports.java").getCanonicalPath(), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CustomImportOrderCheck.class);
        String[] expected = {
        };

        try {
            createChecker(checkConfig);
            verify(checkConfig, getPath("imports" + File.separator
                + "InputCustomImportOrder.java"), expected);
        }
        catch (Exception ex) {
            // Exception is not expected
            fail();
        }
    }
}
