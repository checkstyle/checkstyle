package com.puppycrawl.tools.checkstyle.api;

import junit.framework.TestCase;

public class ScopeTest
    extends TestCase
{
    public void testMisc()
    {
        final Scope o = Scope.getInstance("public");
        assertNotNull(o);
        assertEquals("Scope[1 (public)]", o.toString());
        assertEquals("public", o.getName());

        try {
            Scope.getInstance("unknown");
            fail();
        }
        catch (IllegalArgumentException e) {
            // As expected
        }
    }
}
