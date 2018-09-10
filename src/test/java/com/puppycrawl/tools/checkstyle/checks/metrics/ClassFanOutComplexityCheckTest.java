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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck.MSG_KEY;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ClassFanOutComplexityCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/classfanoutcomplexity";
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassFanOutComplexity.java"), expected);
    }

    @Test
    public void testExcludedPackagesDirectPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.c,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.b");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig,
            getPath("InputClassFanOutComplexityExcludedPackagesDirectPackages.java"), expected);
    }

    @Test
    public void testExcludedPackagesCommonPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "12:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "18:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verify(checkConfig,
            getPath("InputClassFanOutComplexityExcludedPackagesCommonPackage.java"), expected);
    }

    @Test
    public void testExcludedPackagesCommonPackagesWithEndingDot() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.");

        try {
            createChecker(checkConfig);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            final String messageStart =
                "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "Cannot set property 'excludedPackages' to "
                    + "'com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.' in module "
                    + "com.puppycrawl.tools.checkstyle.checks.metrics."
                    + "ClassFanOutComplexityCheck";

            assertTrue("Invalid exception message, should start with: " + messageStart,
                ex.getMessage().startsWith(messageStart));
        }
    }

    @Test
    public void testExcludedPackagesAllIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.a.aa,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity."
                    + "inputs.a.ab,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.b,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity.inputs.c");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputClassFanOutComplexityExcludedPackagesAllIgnored.java"), expected);
    }

    @Test
    public void test15() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassFanOutComplexity15Extensions.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassFanOutComplexityCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassFanOutComplexity.java"), expected);
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
        };
        Assert.assertNotNull("Acceptable tokens should not be null", actual);
        Assert.assertArrayEquals("Invalid acceptable tokens", expected, actual);
    }

    @Test
    public void testRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludeClassesRegexps", "^Inner.*");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassFanOutComplexity.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludeClassesRegexps", "");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassFanOutComplexity.java"), expected);
    }

    @Test
    public void testWithMultiDimensionalArray() throws Exception {
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(moduleConfig,
                getPath("InputClassFanOutComplexityMultiDimensionalArray.java"), expected);
    }

    @Test
    public void testPackageName() throws Exception {
        final DefaultConfiguration moduleConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(moduleConfig,
                getPath("InputClassFanOutComplexityPackageName.java"), expected);
    }

    @Test
    public void testExtends() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);
        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verify(checkConfig,
                getPath("InputClassFanOutComplexityExtends.java"), expected);
    }

    @Test
    public void testImplements() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);
        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verify(checkConfig,
                getPath("InputClassFanOutComplexityImplements.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassFanOutComplexityCheck.class);
        checkConfig.addAttribute("max", "0");
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "25:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 3, 0),
            "44:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "59:1: " + getCheckMessage(MSG_KEY, 1, 0),
            "79:1: " + getCheckMessage(MSG_KEY, 1, 0),
            "82:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };
        verify(checkConfig,
                getPath("InputClassFanOutComplexityAnnotations.java"), expected);
    }

}
