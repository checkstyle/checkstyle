package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScopeTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testMisc()
    {
        final Scope o = Scope.getInstance("public");
        assertNotNull(o);
        assertEquals("public", o.toString());
        assertEquals("public", o.getName());

        Scope.getInstance("unknown"); // will fail
    }

    @Test
    public void testMixedCaseSpaces()
    {
        Scope.getInstance("NothinG ");
        Scope.getInstance(" PuBlic");
        Scope.getInstance(" ProteCted");
        Scope.getInstance("    PackAge ");
        Scope.getInstance("privaTe   ");
        Scope.getInstance("AnonInner");
    }

    @Test
    public void testIsInAnonInner()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.ANONINNER));
        assertTrue(Scope.PUBLIC.isIn(Scope.ANONINNER));
        assertTrue(Scope.PROTECTED.isIn(Scope.ANONINNER));
        assertTrue(Scope.PACKAGE.isIn(Scope.ANONINNER));
        assertTrue(Scope.PRIVATE.isIn(Scope.ANONINNER));
        assertTrue(Scope.ANONINNER.isIn(Scope.ANONINNER));
    }

    @Test
    public void testIsInPrivate()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.PRIVATE));
        assertTrue(Scope.PUBLIC.isIn(Scope.PRIVATE));
        assertTrue(Scope.PROTECTED.isIn(Scope.PRIVATE));
        assertTrue(Scope.PACKAGE.isIn(Scope.PRIVATE));
        assertTrue(Scope.PRIVATE.isIn(Scope.PRIVATE));
        assertTrue(!Scope.ANONINNER.isIn(Scope.PRIVATE));
    }

    @Test
    public void testIsInPackage()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.PACKAGE));
        assertTrue(Scope.PUBLIC.isIn(Scope.PACKAGE));
        assertTrue(Scope.PROTECTED.isIn(Scope.PACKAGE));
        assertTrue(Scope.PACKAGE.isIn(Scope.PACKAGE));
        assertTrue(!Scope.PRIVATE.isIn(Scope.PACKAGE));
        assertTrue(!Scope.ANONINNER.isIn(Scope.PACKAGE));
    }

    @Test
    public void testIsInProtected()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.PROTECTED));
        assertTrue(Scope.PUBLIC.isIn(Scope.PROTECTED));
        assertTrue(Scope.PROTECTED.isIn(Scope.PROTECTED));
        assertTrue(!Scope.PACKAGE.isIn(Scope.PROTECTED));
        assertTrue(!Scope.PRIVATE.isIn(Scope.PROTECTED));
        assertTrue(!Scope.ANONINNER.isIn(Scope.PROTECTED));
    }

    @Test
    public void testIsInPublic()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.PUBLIC));
        assertTrue(Scope.PUBLIC.isIn(Scope.PUBLIC));
        assertTrue(!Scope.PROTECTED.isIn(Scope.PUBLIC));
        assertTrue(!Scope.PACKAGE.isIn(Scope.PUBLIC));
        assertTrue(!Scope.PRIVATE.isIn(Scope.PUBLIC));
        assertTrue(!Scope.ANONINNER.isIn(Scope.PUBLIC));
    }

    @Test
    public void testIsInNothing()
    {
        assertTrue(Scope.NOTHING.isIn(Scope.NOTHING));
        assertTrue(!Scope.PUBLIC.isIn(Scope.NOTHING));
        assertTrue(!Scope.PROTECTED.isIn(Scope.NOTHING));
        assertTrue(!Scope.PACKAGE.isIn(Scope.NOTHING));
        assertTrue(!Scope.PRIVATE.isIn(Scope.NOTHING));
        assertTrue(!Scope.ANONINNER.isIn(Scope.NOTHING));
    }
}
