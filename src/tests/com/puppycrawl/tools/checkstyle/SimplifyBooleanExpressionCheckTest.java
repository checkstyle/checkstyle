package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.SimplifyBooleanExpressionCheck;

public class SimplifyBooleanExpressionCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(SimplifyBooleanExpressionCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimplifyBoolean.java");
        final String[] expected = {
            "20:18: Expression can be simplified.",
            "41:36: Expression can be simplified.",
            "42:36: Expression can be simplified.",
            "43:16: Expression can be simplified.",
            "43:32: Expression can be simplified.",
            // TODO: Change Check.log to avoid duplicate messages
            "43:32: Expression can be simplified.",
        };
        verify(c, fname, expected);
    }
}
