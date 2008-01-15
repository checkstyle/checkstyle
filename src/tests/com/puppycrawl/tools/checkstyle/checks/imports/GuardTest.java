package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class GuardTest
{
    @Test public void testPkgGuard1()
    {
        final Guard g = new Guard(true, false, "pkg", false);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test public void testPkgGuard2()
    {
        final Guard g = new Guard(true, false, "pkg", true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    @Test public void testClassGuard()
    {
        final Guard g = new Guard(true, false, "pkg.a");
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }
}
