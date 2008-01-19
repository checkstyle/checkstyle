package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class UpperEllCheckTest
    extends BaseCheckTestSupport
{
    @Test
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
