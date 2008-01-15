package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests to ensure that default messagebundle is determined correctly.
 *
 * @author lkuehne
 */
public class AbstractViolationReporterTest
{
    private final Check emptyCheck = new Check()
    {
        @Override
        public int[] getDefaultTokens()
        {
            return new int[0];
        }
    };

    @Test
    public void testGetMessageBundleWithPackage()
    {
        assertEquals("com.mycompany.checks.messages",
            emptyCheck.getMessageBundle("com.mycompany.checks.MyCoolCheck"));
    }

    @Test
    public void testGetMessageBundleWithoutPackage()
    {
        assertEquals("messages",
            emptyCheck.getMessageBundle("MyCoolCheck"));
    }

}
