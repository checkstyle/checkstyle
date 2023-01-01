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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

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
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Invalid default tokens")
            .that(check.getAcceptableTokens())
            .isEqualTo(check.getDefaultTokens());
        assertWithMessage("Invalid acceptable tokens")
            .that(check.getRequiredTokens())
            .isEqualTo(check.getDefaultTokens());
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "17:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "37:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCoupling.java"), expected);
    }

    @Test
    public void testExcludedPackageDirectPackages() throws Exception {
        final String[] expected = {
            "30:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCouplingExcludedPackagesDirectPackages.java"),
                expected);
    }

    @Test
    public void testExcludedPackageCommonPackages() throws Exception {
        final String[] expected = {
            "28:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AAClass, ABClass]"),
            "32:5: " + getCheckMessage(MSG_KEY, 2, 0, "[BClass, CClass]"),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0, "[CClass]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCouplingExcludedPackagesCommonPackage.java"),
                expected);
    }

    @Test
    public void testExcludedPackageWithEndingDot() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ClassDataAbstractionCouplingCheck.class);

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
                    + "metrics.ClassDataAbstractionCouplingCheck - "
                    + "Cannot set property 'excludedPackages' to "
                    + "'com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.'");
            assertWithMessage("Invalid exception message,")
                .that(ex.getCause().getCause().getCause().getCause().getMessage())
                .isEqualTo("the following values are not valid identifiers: ["
                    + "com.puppycrawl.tools.checkstyle.checks.metrics.inputs.a.]");
        }
    }

    @Test
    public void testExcludedPackageCommonPackagesAllIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCouplingExcludedPackagesAllIgnored.java"),
                expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCoupling2.java"), expected);
    }

    @Test
    public void testWrongToken() {
        final ClassDataAbstractionCouplingCheck classDataAbstractionCouplingCheckObj =
            new ClassDataAbstractionCouplingCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            classDataAbstractionCouplingCheckObj.visitToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unknown type: ctor[0x-1]");
        }
    }

    @Test
    public void testRegularExpression() throws Exception {

        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_KEY, 2, 0, "[AnotherInnerClass, int]"),
            "23:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCoupling3.java"), expected);
    }

    @Test
    public void testEmptyRegularExpression() throws Exception {

        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "23:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "43:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verifyWithInlineConfigParser(
                getPath("InputClassDataAbstractionCoupling4.java"), expected);
    }

    @Test
    public void testClassDataAbstractionCouplingRecords() throws Exception {

        final int maxAbstraction = 1;
        final String[] expected = {
            "31:1: " + getCheckMessage(MSG_KEY, 2, maxAbstraction, "[Date, Time]"),
            "36:1: " + getCheckMessage(MSG_KEY, 2, maxAbstraction, "[Date, Time]"),
            "46:1: " + getCheckMessage(MSG_KEY, 2, maxAbstraction, "[Date, Time]"),
            "55:1: " + getCheckMessage(MSG_KEY, 2, maxAbstraction, "[Date, Time]"),
            "67:5: " + getCheckMessage(MSG_KEY, 3, maxAbstraction, "[Date, Place, Time]"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputClassDataAbstractionCouplingRecords.java"),
            expected);
    }

}
