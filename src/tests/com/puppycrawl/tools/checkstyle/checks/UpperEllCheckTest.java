package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UpperEllCheckTest
    extends BaseCheckTestCase
{
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UpperEllCheck.class);
        final String[] expected = {
            "94:43: Should use uppercase 'L'.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
