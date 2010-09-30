package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class ParameterNumberCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNumberCheck.class);
        final String[] expected = {
            "194:10: More than 7 parameters.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testNum()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterNumberCheck.class);
        checkConfig.addAttribute("max", "2");
        final String[] expected = {
            "71:9: More than 2 parameters.",
            "194:10: More than 2 parameters.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
