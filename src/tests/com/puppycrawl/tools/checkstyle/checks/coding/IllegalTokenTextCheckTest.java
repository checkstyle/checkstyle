package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalTokenTextCheckTest
    extends BaseCheckTestCase
{
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

