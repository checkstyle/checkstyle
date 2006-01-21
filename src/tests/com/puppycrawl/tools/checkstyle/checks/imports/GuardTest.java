package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.checks.imports.AccessResult;
import com.puppycrawl.tools.checkstyle.checks.imports.Guard;
import junit.framework.TestCase;

public class GuardTest extends TestCase
{
    public void testPkgGuard1()
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

    public void testPkgGuard2()
    {
        final Guard g = new Guard(true, false, "pkg", true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg"));
    }

    public void testClassGuard()
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
