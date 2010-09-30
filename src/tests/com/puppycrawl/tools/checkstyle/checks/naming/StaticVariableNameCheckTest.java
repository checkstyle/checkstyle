package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class StaticVariableNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

    @Test
    public void testAccessTuning()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(StaticVariableNameCheck.class);
        checkConfig.addAttribute("format", "^s[A-Z][a-zA-Z0-9]*$");
        checkConfig.addAttribute("applyToPrivate", "false"); // allow method names and class names to equal
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}

