package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ParameterNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testCatch()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^NO_WAY_MATEY$");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    @Test
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        checkConfig.addAttribute("format", "^a[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "71:19: Name 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "71:34: Name 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "72:25: Name 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
