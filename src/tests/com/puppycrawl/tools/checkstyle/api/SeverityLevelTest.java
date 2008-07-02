package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class SeverityLevelTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testMisc()
    {
        final SeverityLevel o = SeverityLevel.getInstance("info");
        assertNotNull(o);
        assertEquals("info", o.toString());
        assertEquals("info", o.getName());

        SeverityLevel.getInstance("unknown"); // will fail
    }

    @Test
    public void testMixedCaseSpaces()
    {
        SeverityLevel.getInstance("IgnoRe ");
        SeverityLevel.getInstance(" iNfo");
        SeverityLevel.getInstance(" WarniNg");
        SeverityLevel.getInstance("    ERROR ");
    }
}
