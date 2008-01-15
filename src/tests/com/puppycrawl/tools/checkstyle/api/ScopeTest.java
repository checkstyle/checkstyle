package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ScopeTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testMisc()
    {
        final Scope o = Scope.getInstance("public");
        assertNotNull(o);
        assertEquals("Scope[1 (public)]", o.toString());
        assertEquals("public", o.getName());

        Scope.getInstance("unknown"); // will fail
    }
}
