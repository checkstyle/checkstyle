package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Locale;

public class LocalizedMessageTest
    extends TestCase
{
    public LocalizedMessageTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        LocalizedMessage.setLocale(Locale.ENGLISH);
    }

    public void testMisc()
    {
        LocalizedMessage lm =
            new LocalizedMessage(0, "DefaultLogger.auditFinished", null);
        assertNotNull(lm);
        assertEquals("Audit done.", lm.getMessage());
        lm = new LocalizedMessage(0, "DefaultLogger.addException",
                                  new String[]{"Claira"});
        assertNotNull(lm);
        assertEquals("Error auditing Claira", lm.getMessage());

        lm = new LocalizedMessage(0, "ws.notFollowed", new String[] {"{"});
        assertNotNull(lm);
        assertEquals("'{' is not followed by whitespace.", lm.getMessage());
    }
}
