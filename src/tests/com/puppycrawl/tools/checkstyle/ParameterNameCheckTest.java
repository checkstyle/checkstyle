package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.ParameterNameCheck;

public class ParameterNameCheckTest
    extends BaseCheckTestCase
{
    public void testCatch()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ParameterNameCheck.class.getName());
        checkConfig.addProperty("format", "^NO_WAY_MATEY$");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testSpecified()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ParameterNameCheck.class.getName());
        checkConfig.addProperty("format", "^a[A-Z][a-zA-Z0-9]*$");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "71:19: Name 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "71:34: Name 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "72:25: Name 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(c, fname, expected);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ParameterNameCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
