package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class LocalizedMessageTest
    extends TestCase
{
    private final String CHECKSTYLE_CHECKS_BUNDLE =
            "com.puppycrawl.tools.checkstyle.checks.messages";

    protected void setUp() throws Exception
    {
        LocalizedMessage.setLocale(Locale.ENGLISH);
    }

    public void testMisc()
    {
        LocalizedMessage lm =
            new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                 "DefaultLogger.auditFinished", null);
        assertNotNull(lm);
        assertEquals("Audit done.", lm.getMessage());
        lm = new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                  "DefaultLogger.addException",
                                  new String[]{"Claira"});
        assertNotNull(lm);
        assertEquals("Error auditing Claira", lm.getMessage());

        lm = new LocalizedMessage(0, CHECKSTYLE_CHECKS_BUNDLE,
                                  "ws.notFollowed", new String[] {"{"});
        assertNotNull(lm);
        assertEquals("'{' is not followed by whitespace.", lm.getMessage());
    }
}
