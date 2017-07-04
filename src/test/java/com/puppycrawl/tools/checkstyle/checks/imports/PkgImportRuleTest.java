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
        final PkgImportRule r = new PkgImportRule(true, false, "pkg", false, false);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleExactMatch() {
        final PkgImportRule r = new PkgImportRule(true, false, "pkg", true, false);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleRegexpSimple() {
        final PkgImportRule r = new PkgImportRule(true, false, "pkg", false, true);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleExactMatchRegexpSimple() {
        final PkgImportRule r = new PkgImportRule(true, false, "pkg", true, true);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
    }

    @Test
    public void testPkgImportRuleRegexp() {
        final PkgImportRule r = new PkgImportRule(true, false, "(pkg|hallo)", false, true);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("halloa"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("hallo.a"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("hallo.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("hallo"));
    }

    @Test
    public void testPkgImportRuleExactMatchRegexp() {
        final PkgImportRule r = new PkgImportRule(true, false, "(pkg|hallo)", true, true);
        assertNotNull(r);
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("pkg"));
        assertEquals(AccessResult.ALLOWED, r.verifyImport("hallo.a"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("hallo.a.b"));
        assertEquals(AccessResult.UNKNOWN, r.verifyImport("hallo"));
    }
}
