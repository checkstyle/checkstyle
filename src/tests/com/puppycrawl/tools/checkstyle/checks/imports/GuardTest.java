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
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga", "ignored"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a", "ignored"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a.b", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg", "ignored"));
    }

    public void testPkgGuard2()
    {
        final Guard g = new Guard(true, false, "pkg", true);
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p", "ignored"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg", "ignored"));
    }

    public void testClassGuard()
    {
        final Guard g = new Guard(true, false, "pkg.a");
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("asda", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("p", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkga", "ignored"));
        assertEquals(AccessResult.ALLOWED, g.verifyImport("pkg.a", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg.a.b", "ignored"));
        assertEquals(AccessResult.UNKNOWN, g.verifyImport("pkg", "ignored"));
    }
}
