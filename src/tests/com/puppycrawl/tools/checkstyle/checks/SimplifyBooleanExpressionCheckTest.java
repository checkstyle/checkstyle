package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class SimplifyBooleanExpressionCheckTest
    extends BaseCheckTestCase
{
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
            // TODO: Change Check.log to avoid duplicate messages
            "43:32: Expression can be simplified.",
        };
        verify(checkConfig, getPath("InputSimplifyBoolean.java"), expected);
    }
}
