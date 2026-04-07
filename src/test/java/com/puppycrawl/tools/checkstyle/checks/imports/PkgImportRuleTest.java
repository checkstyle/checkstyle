///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public final class PkgImportRuleTest {

    @Test
    public void testPkgImportRuleNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, false);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, ALLOWED",
        "pkgextra, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRule(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, false);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @Test
    public void testPkgImportRuleExactMatchNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, false);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleExactMatch(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, false);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @Test
    public void testPkgImportRuleRegexpSimpleNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, ALLOWED",
        "pkgextra, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleRegexpSimple(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, true);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @Test
    public void testPkgImportRuleExactMatchRegexpSimpleNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleExactMatchRegexpSimple(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, true);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @Test
    public void testPkgImportRuleRegexpNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", false, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "hallo, UNKNOWN",
        "hallo.a, ALLOWED",
        "hallo.a.b, ALLOWED",
        "halloa, UNKNOWN",
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, ALLOWED",
        "pkgextra, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleRegexp(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", false, true);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @CsvSource({
        "(pkg|hallo).a, ALLOWED",
        "hallo, UNKNOWN",
        "hallo.a, UNKNOWN",
        "hallo.a.b, UNKNOWN",
        "halloa, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, UNKNOWN",
        "pkg.a.b, UNKNOWN",
        "pkgextra, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleNoRegexp(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", false, false);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

    @Test
    public void testPkgImportRuleExactMatchRegexpNotNull() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", true, true);
        assertWithMessage("Rule must not be null")
            .that(rule)
            .isNotNull();
    }

    @CsvSource({
        "hallo, UNKNOWN",
        "hallo.a, ALLOWED",
        "hallo.a.b, UNKNOWN",
        "other, UNKNOWN",
        "p, UNKNOWN",
        "pkg, UNKNOWN",
        "pkg.a, ALLOWED",
        "pkg.a.b, UNKNOWN"
    })
    @ParameterizedTest
    public void testPkgImportRuleExactMatchRegexp(String input, AccessResult expected) {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", true, true);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport(input))
            .isEqualTo(expected);
    }

}
