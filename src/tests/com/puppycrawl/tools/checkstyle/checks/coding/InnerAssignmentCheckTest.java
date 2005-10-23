package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;

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
            "11:15: Inner assignments should be avoided.",
            "11:19: Inner assignments should be avoided.",
            "13:39: Inner assignments should be avoided.",
            "15:35: Inner assignments should be avoided.",

            "33:16: Inner assignments should be avoided.",
            "34:24: Inner assignments should be avoided.",
            "35:19: Inner assignments should be avoided.",
            "36:17: Inner assignments should be avoided.",
            "37:29: Inner assignments should be avoided.",
            "38:20: Inner assignments should be avoided.",
            "39:17: Inner assignments should be avoided.",
            "39:31: Inner assignments should be avoided.",
            "39:41: Inner assignments should be avoided.",
            "40:16: Inner assignments should be avoided.",
            "40:27: Inner assignments should be avoided.",
            "41:32: Inner assignments should be avoided.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputInnerAssignment.java"), expected);
    }
}
