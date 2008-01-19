package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ArrayTrailingCommaCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ArrayTrailingCommaCheck.class);
        final String[] expected = {
            "17: Array should contain trailing comma.",
            "34: Array should contain trailing comma.",
            "37: Array should contain trailing comma.",
        };
        verify(checkConfig, getPath("InputArrayTrailingComma.java"), expected);
    }
}
