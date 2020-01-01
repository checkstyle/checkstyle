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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ClassImportRuleTest {

    @Test
    public void testClassImportRule() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", false);
        assertNotNull(rule, "Class import rule should not be null");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("asda"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("p"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkga"), "Invalid access result");
        assertEquals(AccessResult.ALLOWED, rule.verifyImport("pkg.a"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg"), "Invalid access result");
    }

    @Test
    public void testClassImportRuleRegexpSimple() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pkg.a", true);
        assertNotNull(rule, "Class import rule should not be null");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("asda"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("p"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkga"), "Invalid access result");
        assertEquals(AccessResult.ALLOWED, rule.verifyImport("pkg.a"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg"), "Invalid access result");
    }

    @Test
    public void testClassImportRuleRegexp() {
        final ClassImportRule rule = new ClassImportRule(true, false, "pk[gx]\\.a", true);
        assertNotNull(rule, "Class import rule should not be null");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("asda"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("p"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkga"), "Invalid access result");
        assertEquals(AccessResult.ALLOWED, rule.verifyImport("pkg.a"), "Invalid access result");
        assertEquals(AccessResult.ALLOWED, rule.verifyImport("pkx.a"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"), "Invalid access result");
        assertEquals(AccessResult.UNKNOWN, rule.verifyImport("pkg"), "Invalid access result");
    }

}
