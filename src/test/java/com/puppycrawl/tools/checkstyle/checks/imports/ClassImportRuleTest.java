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

public class ClassImportRuleTest {

    private static void assertAccess(ClassImportRule rule, String forImport,
            AccessResult expected) {
        assertWithMessage("Invalid access result for %s", forImport)
            .that(rule.verifyImport(forImport))
            .isEqualTo(expected);
    }

    @Test
    public void testClassImportRule() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", false);
        assertWithMessage("Class import rule should not be null")
            .that(rule)
            .isNotNull();
        assertAccess(rule, "other", AccessResult.UNKNOWN);
        assertAccess(rule, "p", AccessResult.UNKNOWN);
        assertAccess(rule, "pkgextra", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg.a", AccessResult.ALLOWED);
        assertAccess(rule, "pkg.a.b", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg", AccessResult.UNKNOWN);
    }

    @Test
    public void testClassImportRuleRegexpSimple() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", true);
        assertWithMessage("Class import rule should not be null")
            .that(rule)
            .isNotNull();
        assertAccess(rule, "other", AccessResult.UNKNOWN);
        assertAccess(rule, "p", AccessResult.UNKNOWN);
        assertAccess(rule, "pkgextra", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg.a", AccessResult.ALLOWED);
        assertAccess(rule, "pkg.a.b", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg", AccessResult.UNKNOWN);
    }

    @Test
    public void testClassImportRuleRegexp() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pk[gx]\\.a", true);
        assertWithMessage("Class import rule should not be null")
            .that(rule)
            .isNotNull();
        assertAccess(rule, "other", AccessResult.UNKNOWN);
        assertAccess(rule, "p", AccessResult.UNKNOWN);
        assertAccess(rule, "pkgextra", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg.a", AccessResult.ALLOWED);
        assertAccess(rule, "pkx.a", AccessResult.ALLOWED);
        assertAccess(rule, "pkg.a.b", AccessResult.UNKNOWN);
        assertAccess(rule, "pkg", AccessResult.UNKNOWN);
    }

}
