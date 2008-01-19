package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class TypeNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

    @Test
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
