package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.ParameterNumberCheck;

public class ParameterNumberCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNumberCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "194:10: More than 7 parameters.",
        };
        verify(c, fname, expected);
    }

    public void testNum()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNumberCheck.class);
        checkConfig.addAttribute("max", "2");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "71:9: More than 2 parameters.",
            "194:10: More than 2 parameters.",
        };
        verify(c, fname, expected);
    }
}
