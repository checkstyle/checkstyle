package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TypeNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        checkConfig.addAttribute("format", "^inputHe");
        final String[] expected = {
        };
        verify(checkConfig, getPath("inputHeader.java"), expected);
    }

    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeNameCheck.class);
        final String[] expected = {
            "1:48: Name 'inputHeader' must match pattern '^[A-Z][a-zA-Z0-9]*$'."
        };
        verify(checkConfig, getPath("inputHeader.java"), expected);
    }
}
