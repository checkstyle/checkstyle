package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.ParameterNumberCheck;

public class ParameterNumberCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ParameterNumberCheck.class.getName());
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
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ParameterNumberCheck.class.getName());
        checkConfig.addProperty("max", "2");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "71:9: More than 2 parameters.",
            "194:10: More than 2 parameters.",
        };
        verify(c, fname, expected);
    }
}
