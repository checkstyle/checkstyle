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
            "102:15: Inner assignments should be avoided.",
            "102:19: Inner assignments should be avoided.",
            "104:39: Inner assignments should be avoided.",
            "106:35: Inner assignments should be avoided.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
