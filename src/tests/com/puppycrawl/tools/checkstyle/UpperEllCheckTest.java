package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.UpperEllCheck;

public class UpperEllCheckTest
extends BaseCheckTestCase
{
    public UpperEllCheckTest(String aName)
    {
        super(aName);
    }

    public void testWithChecker()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(UpperEllCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "94:43: Should use uppercase 'L'.",
        };
        verify(c, fname, expected);
    }
}
