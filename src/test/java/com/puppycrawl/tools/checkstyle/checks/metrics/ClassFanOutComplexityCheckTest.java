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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck.MSG_KEY;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class ClassFanOutComplexityCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "metrics" + File.separator + filename);
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }

    @Test
    public void testExcludedPackagesDirectPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0),
        };

        verify(checkConfig,
            getPath("InputClassFanOutComplexityExcludedPackagesDirectPackages.java"), expected);
    }

    @Test
    public void testExcludedPackagesCommonPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

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
            createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.");

        try {
            createChecker(checkConfig);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "Cannot set property 'excludedPackages' to "
                    + "'com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.' in module "
                    + "com.puppycrawl.tools.checkstyle.checks.metrics."
                    + "ClassFanOutComplexityCheck"));
        }
    }

    @Test
    public void testExcludedPackagesAllIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c");

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputClassFanOutComplexityExcludedPackagesAllIgnored.java"), expected);
    }

    @Test
    public void test15() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
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
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
        };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludeClassesRegexps", "^Inner.*");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 2, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludeClassesRegexps", "");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }
}
