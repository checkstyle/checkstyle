package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class InnerAssignmentCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerAssignmentCheck.class);
        final String[] expected = {
            "102:15: Avoid inner assignments.",
            "102:19: Avoid inner assignments.",
            "104:39: Avoid inner assignments.",
            "106:35: Avoid inner assignments.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
