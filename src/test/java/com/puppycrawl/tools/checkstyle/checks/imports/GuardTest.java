////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

public class GuardTest {
    @Test
    public void testPkgGuard1() {
        final Guard g = new Guard(true, false, "pkg", false, false);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testPkgGuard2() {
        final Guard g = new Guard(true, false, "pkg", true, false);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testClassGuard() {
        final Guard g = new Guard(true, false, "pkg.a", false);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testPkgGuard1re() {
        final Guard g = new Guard(true, false, "pkg", false, true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testPkgGuard2re() {
        final Guard g = new Guard(true, false, "pkg", true, true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testClassGuardre() {
        final Guard g = new Guard(true, false, "pkg.a", true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test
    public void testPkgGuard1re2() {
        final Guard g = new Guard(true, false, "(pkg|hallo)", false, true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("halloa"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("hallo.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("hallo.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("hallo"));
    }

    @Test
    public void testPkgGuard2re2() {
        final Guard g = new Guard(true, false, "(pkg|hallo)", true, true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("hallo.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("hallo.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("hallo"));
    }

    @Test
    public void testClassGuardre2() {
        final Guard g = new Guard(true, false, "pk[gx]\\.a", true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkx.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }
}
