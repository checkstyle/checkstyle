package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class SuperFinalizeCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SuperFinalizeCheck.class);
        final String[] expected = {
            "27:17: Method 'finalize' should call 'super.finalize'.",
            "34:17: Method 'finalize' should call 'super.finalize'.",
            "58:17: Method 'finalize' should call 'super.finalize'.",
        };
        verify(checkConfig, getPath("coding/InputFinalize.java"), expected);
    }
}
