package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class FinalClassCheckTest
    extends BaseCheckTestCase
{
    public void testFianlClass() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalClassCheck.class);
        final String[] expected = {
            "7: Class InputFinalClass should be declared as final.",
            "15: Class test4 should be declared as final.",
            "109: Class someinnerClass should be declared as final.",
        };
        verify(checkConfig, getPath("InputFinalClass.java"), expected);
    }
}
