package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DescendantTokenCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
    
    public void testMaximumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        final String[] expected = {
            "20:12: Count of 1 for 'LITERAL_NATIVE' descendant 'LITERAL_NATIVE' exceeds maximum count 0.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMessage()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''native'' is not allowed.");
        final String[] expected = {
            "20:12: Using 'native' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    public void testMinimumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("minimumNumber", "2");
        final String[] expected = {
            "11:9: Count of 1 for 'LITERAL_SWITCH' descendant 'LITERAL_DEFAULT' is less than minimum count 2.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMinimumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("minimumDepth", "3");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
     
    public void testMaximumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "1");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }
}
