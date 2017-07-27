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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PkgImportRuleTest {

    @Test
    public void testPkgImportRule() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, false);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkga"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleExactMatch() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, false);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleRegexpSimple() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", false, true);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkga"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleExactMatchRegexpSimple() {
        final PkgImportRule rule = new PkgImportRule(true, false, "pkg", true, true);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleRegexp() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", false, true);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkga"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("halloa"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("hallo.a"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("hallo.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("hallo"));
    }

    @Test
    public void testPkgImportRuleExactMatchRegexp() {
        final PkgImportRule rule = new PkgImportRule(true, false, "(pkg|hallo)", true, true);
        assertNotNull("Rule must not be null", rule);
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("asda"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("p"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("pkg.a"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("pkg"));
        assertEquals("Invalid access result", AccessResult.ALLOWED, rule.verifyImport("hallo.a"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("hallo.a.b"));
        assertEquals("Invalid access result", AccessResult.UNKNOWN, rule.verifyImport("hallo"));
    }
}
