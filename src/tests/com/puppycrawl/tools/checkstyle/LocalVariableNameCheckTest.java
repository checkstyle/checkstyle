package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.LocalVariableNameCheck;

public class LocalVariableNameCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "119:13: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "130:18: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "132:20: Name 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(c, fname, expected);
    }
}

