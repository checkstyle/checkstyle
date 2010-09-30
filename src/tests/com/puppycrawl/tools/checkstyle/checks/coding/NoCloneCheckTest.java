package com.puppycrawl.tools.checkstyle.checks.coding;


import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

/**
 * NoCloneCheck test.
 */
public class NoCloneCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testHasClone()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NoCloneCheck.class);
        final String[] expected = {
            "10: Avoid using clone method.",
            "27: Avoid using clone method.",
            "35: Avoid using clone method.",
            "39: Avoid using clone method.",
            "52: Avoid using clone method.",
            "60: Avoid using clone method.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputClone.java"), expected);
    }
}
