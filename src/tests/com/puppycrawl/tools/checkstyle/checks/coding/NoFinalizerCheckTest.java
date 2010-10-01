package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

/**
 * NoFinalizerCheck test.
 *
 * @author smckay@google.com (Steve McKay)
 */
public class NoFinalizerCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testHasFinalizer()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NoFinalizerCheck.class);
        final String[] expected = {
            "5: Avoid using finalizer method.",
        };
        verify(checkConfig, getPath("coding/InputHasFinalizer.java"), expected);
    }

    @Test
    public void testHasNoFinalizer()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NoFinalizerCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("coding/InputIllegalThrowsCheck.java"), expected);
    }
}
