package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.UpperEllCheck;

public class UpperEllCheckTest
    extends BaseCheckTestCase
{
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UpperEllCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "94:43: Should use uppercase 'L'.",
        };
        verify(c, fname, expected);
    }
}
