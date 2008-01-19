package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class IllegalTokenTextCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testCaseSensitive()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");
        final String[] expected = {
            "24:28: Token text matches the illegal pattern 'a href'.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testCaseInSensitive()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalTokenTextCheck.class);
        checkConfig.addAttribute("tokens", "STRING_LITERAL");
        checkConfig.addAttribute("format", "a href");
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "24:28: Token text matches the illegal pattern 'a href'.",
            "25:32: Token text matches the illegal pattern 'a href'.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
}

