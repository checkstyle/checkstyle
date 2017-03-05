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

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class ClassDataAbstractionCouplingCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "metrics" + File.separator + filename);
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "27:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }

    @Test
    public void testExludedPackageDirectPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
        };

        verify(checkConfig,
            getPath("InputClassCouplingExcludedPackagesDirectPackages.java"), expected);
    }

    @Test
    public void testExludedPackageCommonPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
            "12:5: " + getCheckMessage(MSG_KEY, 2, 0, "[BClass, CClass]"),
            "18:1: " + getCheckMessage(MSG_KEY, 1, 0, "[CClass]"),
        };
        verify(checkConfig,
            getPath("InputClassCouplingExcludedPackagesCommonPackage.java"), expected);
    }

    @Test
    public void testExludedPackageWithEndingDot() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

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
                    + "ClassDataAbstractionCouplingCheck"));
        }
    }

    @Test
    public void testExludedPackageCommonPackagesAllIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.aa,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.ab,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.b,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.c");

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassCouplingExcludedPackagesAllIgnored.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }

    @Test
    public void testWrongToken() {
        final ClassDataAbstractionCouplingCheck classDataAbstractionCouplingCheckObj =
            new ClassDataAbstractionCouplingCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            classDataAbstractionCouplingCheckObj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown type: ctor[0x-1]", ex.getMessage());
        }
    }

    @Test
    public void testRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");
        checkConfig.addAttribute("excludeClassesRegexps", "^Hash.*");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AnotherInnerClass, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");
        checkConfig.addAttribute("excludeClassesRegexps", "");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "27:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verify(checkConfig, getPath("InputClassCoupling.java"), expected);
    }
}
