package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class StaticVariableNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(StaticVariableNameCheck.class);
        checkConfig.addAttribute("format", "^s[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "30:24: Name 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}

