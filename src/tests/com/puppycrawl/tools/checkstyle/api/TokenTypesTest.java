package com.puppycrawl.tools.checkstyle.api;

import junit.framework.TestCase;

public class TokenTypesTest extends TestCase
{
    public void testGetShortDescription() {
        assertEquals("short description for EQUAL", "The <code>==</code> (equal) operator.", TokenTypes.getShortDescription("EQUAL"));
    }
}
