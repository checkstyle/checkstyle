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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class ClassImportRuleTest {

    @Test
    public void testClassImportRule() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", false);
        assertWithMessage("Class import rule should not be null")
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
            .that(rule.verifyImport("pkg.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testClassImportRuleRegexpSimple() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", true);
        assertWithMessage("Class import rule should not be null")
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
            .that(rule.verifyImport("pkg.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

    @Test
    public void testClassImportRuleRegexp() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pk[gx]\\.a", true);
        assertWithMessage("Class import rule should not be null")
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
            .that(rule.verifyImport("pkg.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkx.a"))
            .isEqualTo(AccessResult.ALLOWED);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg.a.b"))
            .isEqualTo(AccessResult.UNKNOWN);
        assertWithMessage("Invalid access result")
            .that(rule.verifyImport("pkg"))
            .isEqualTo(AccessResult.UNKNOWN);
    }

}
