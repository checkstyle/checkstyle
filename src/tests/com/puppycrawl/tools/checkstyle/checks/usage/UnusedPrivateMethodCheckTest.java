package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UnusedPrivateMethodCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedPrivateMethodCheck.class);
        final String[] expected = {
            "7:18: Unused private method 'methodUnused0'.",
        };
        verify(checkConfig, getPath("usage/InputUnusedMethod.java"), expected);
    }
}
