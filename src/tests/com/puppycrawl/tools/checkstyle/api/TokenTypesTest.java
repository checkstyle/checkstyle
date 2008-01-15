package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenTypesTest
{
    @Test
    public void testGetShortDescription()
    {
        assertEquals("short description for EQUAL",
                "The <code>==</code> (equal) operator.", TokenTypes
                        .getShortDescription("EQUAL"));
    }
}
