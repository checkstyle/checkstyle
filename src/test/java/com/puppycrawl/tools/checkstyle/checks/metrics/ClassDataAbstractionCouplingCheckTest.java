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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
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
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertArrayEquals(check.getDefaultTokens(),
                check.getAcceptableTokens(), "Invalid default tokens");
        assertArrayEquals(check.getDefaultTokens(),
                check.getRequiredTokens(), "Invalid acceptable tokens");
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
            assertEquals("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "metrics.ClassDataAbstractionCouplingCheck - "
                    + "Cannot set property 'excludedPackages' to "
                    + "'com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.'",
                ex.getMessage(), "Invalid exception message");
            assertEquals("the following values are not valid identifiers: ["
                    + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.]", ex
                    .getCause().getCause().getCause().getCause().getMessage(),
                    "Invalid exception message,");
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
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            classDataAbstractionCouplingCheckObj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unknown type: ctor[0x-1]", ex.getMessage(), "Invalid exception message");
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
