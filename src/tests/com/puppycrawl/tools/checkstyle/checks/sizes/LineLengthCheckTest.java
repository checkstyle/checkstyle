package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class LineLengthCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testSimple()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern",  "^.*is OK.*regexp.*$");
        final String[] expected = {
            "18: Line is longer than 80 characters.",
            "145: Line is longer than 80 characters.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
}
