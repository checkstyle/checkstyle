package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.InnerAssignmentCheck;

public class InnerAssignmentCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(InnerAssignmentCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "102:15: Avoid inner assignments.",
            "102:19: Avoid inner assignments.",
            "104:39: Avoid inner assignments.",
            "106:35: Avoid inner assignments.",
        };
        verify(c, fname, expected);

    }
}
