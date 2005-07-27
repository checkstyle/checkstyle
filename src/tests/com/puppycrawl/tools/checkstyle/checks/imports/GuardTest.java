package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.checks.imports.AccessResult;
import com.puppycrawl.tools.checkstyle.checks.imports.Guard;
import junit.framework.TestCase;

public class GuardTest extends TestCase
{
    public void testGuard()
    {
        final Guard g = new Guard(true, "pkg");
        assertNotNull(g);
        assertEquals(AccessResult.UNKNOWN, g.verify("asda"));
        assertEquals(AccessResult.UNKNOWN, g.verify("p"));
        assertEquals(AccessResult.ALLOWED, g.verify("pkg.a"));
        assertEquals(AccessResult.UNKNOWN, g.verify("pkg"));
    }
}
