package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.TypeNameCheck;

public class TypeNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("format", "^inputHe");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("inputHeader.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }

    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("inputHeader.java");
        final String[] expected = {
            "1:48: Name 'inputHeader' must match pattern '^[A-Z][a-zA-Z0-9]*$'."
        };
        verify(c, fname, expected);
    }
}
