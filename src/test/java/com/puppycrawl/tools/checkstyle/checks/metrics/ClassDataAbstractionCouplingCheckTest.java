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

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ClassDataAbstractionCouplingCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/classdataabstractioncoupling";
    }

    @Test
    public void testTokens() {
        final ClassDataAbstractionCouplingCheck check = new ClassDataAbstractionCouplingCheck();
        assertNotNull("Required tokens should not be null", check.getRequiredTokens());
        assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        assertArrayEquals("Invalid default tokens", check.getDefaultTokens(),
                check.getAcceptableTokens());
        assertArrayEquals("Invalid acceptable tokens", check.getDefaultTokens(),
                check.getRequiredTokens());
    }

    @Test
    public void test() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "27:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verify(checkConfig, getPath("InputClassDataAbstractionCoupling.java"), expected);
    }

    @Test
    public void testExcludedPackageDirectPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs.c,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling."
                + "inputs.b");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
        };

        verify(checkConfig,
            getPath("InputClassDataAbstractionCouplingExcludedPackagesDirectPackages.java"),
                expected);
    }

    @Test
    public void testExcludedPackageCommonPackages() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a");

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
            "12:5: " + getCheckMessage(MSG_KEY, 2, 0, "[BClass, CClass]"),
            "18:1: " + getCheckMessage(MSG_KEY, 1, 0, "[CClass]"),
        };
        verify(checkConfig,
            getPath("InputClassDataAbstractionCouplingExcludedPackagesCommonPackage.java"),
                expected);
    }

    @Test
    public void testExcludedPackageWithEndingDot() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

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
                    + "ClassDataAbstractionCouplingCheck";

            assertTrue("Invalid exception message, should start with: " + messageStart,
                ex.getMessage().startsWith(messageStart));
        }
    }

    @Test
    public void testExcludedPackageCommonPackagesAllIgnored() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedPackages",
            "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling.inputs."
                    + "a.aa,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling."
                    + "inputs.a.ab,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling."
                    + "inputs.b,"
                + "com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling."
                    + "inputs.c");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputClassDataAbstractionCouplingExcludedPackagesAllIgnored.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputClassDataAbstractionCoupling.java"), expected);
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
            assertEquals("Invalid exception message",
                "Unknown type: ctor[0x-1]", ex.getMessage());
        }
    }

    @Test
    public void testRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");
        checkConfig.addAttribute("excludeClassesRegexps", "^Hash.*");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AnotherInnerClass, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
        };

        verify(checkConfig, getPath("InputClassDataAbstractionCoupling.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");
        checkConfig.addAttribute("excludeClassesRegexps", "");

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "27:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verify(checkConfig, getPath("InputClassDataAbstractionCoupling.java"), expected);
    }

}
