package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class FallThroughCheckTest extends BaseCheckTestCase
{
    public void testIt()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FallThroughCheck.class);
        final String[] expected = {
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }
}
