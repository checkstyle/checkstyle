package com.puppycrawl.tools.checkstyle.api;

import junit.framework.TestCase;

/**
 * Tests to ensure that default messagebundle is determined correctly.
 *
 * @author lkuehne
 */
public class AbstractViolationReporterTest
    extends TestCase
{
    private final Check emptyCheck = new Check()
    {
        public int[] getDefaultTokens()
        {
            return new int[0];
        }
    };

    public void testGetMessageBundleWithPackage()
    {
        assertEquals("com.mycompany.checks.messages",
            emptyCheck.getMessageBundle("com.mycompany.checks.MyCoolCheck"));
    }

    public void testGetMessageBundleWithoutPackage()
    {
        assertEquals("messages",
            emptyCheck.getMessageBundle("MyCoolCheck"));
    }

}
