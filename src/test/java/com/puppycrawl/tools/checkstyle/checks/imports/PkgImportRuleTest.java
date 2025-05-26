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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class PkgImportRuleTest {

    @Test
    public void testPkgImportRule() {
        final PkgImportRule rule = new PkgImportRule(true, false, "package", false, false);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkga"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testPkgImportRuleExactMatch() {
        final PkgImportRule rule = new PkgImportRule(true, false, "package", true, false);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testPkgImportRuleRegexpSimple() {
        final PkgImportRule rule = new PkgImportRule(true, false, "package", false, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkga"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testPkgImportRuleExactMatchRegexpSimple() {
        final PkgImportRule rule = new PkgImportRule(true, false, "package", true, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testPkgImportRuleRegexp() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(package|hallo)", false, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkga"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("halloa"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a.b"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testPkgImportRuleNoRegexp() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(package|hallo)", false, false);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkga"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("halloa"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("(package|hallo).a"))
            .isEqualTo(AccessResult.ALLOWED);
    }

    @Test
    public void testPkgImportRuleExactMatchRegexp() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(package|hallo)", true, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("asda"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("p"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("package"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("hallo"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

}
