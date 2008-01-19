package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class SimplifyBooleanExpressionCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SimplifyBooleanExpressionCheck.class);
        final String[] expected = {
            "20:18: Expression can be simplified.",
            "41:36: Expression can be simplified.",
            "42:36: Expression can be simplified.",
            "43:16: Expression can be simplified.",
            "43:32: Expression can be simplified.",
        };
        verify(checkConfig, getPath("InputSimplifyBoolean.java"), expected);
    }
}
