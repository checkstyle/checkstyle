package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.InnerAssignmentCheck;

public class InnerAssignmentCheckTest
        extends BaseCheckTestCase
{
    public InnerAssignmentCheckTest(String aName)
    {
        super(aName);
    }

    public void testIt() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(InnerAssignmentCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "101:15: Avoid inner assignments.",
            "101:19: Avoid inner assignments.",
            "103:39: Avoid inner assignments.",
            "105:35: Avoid inner assignments.",
        };
        verify(c, fname, expected);

    }
}
