package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class FinalParametersCheckTest extends BaseCheckTestCase
{
    public void testDefaultTokens()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        final String[] expected = {
            "22:26: Parameter s should be final.",
            "32:26: Parameter s should be final.",
            "42:17: Parameter s should be final.",
            "52:17: Parameter s should be final.",
            "67:38: Parameter e should be final.",
            "81:18: Parameter aParam should be final.",
            "84:18: Parameter args should be final.",
            "87:18: Parameter args should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    public void testCtorToken()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "CTOR_DEF");
        final String[] expected = {
            "22:26: Parameter s should be final.",
            "32:26: Parameter s should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    public void testMethodToken()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        final String[] expected = {
            "42:17: Parameter s should be final.",
            "52:17: Parameter s should be final.",
            "67:38: Parameter e should be final.",
            "81:18: Parameter aParam should be final.",
            "84:18: Parameter args should be final.",
            "87:18: Parameter args should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    public void testCatchToken()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CATCH");
        final String[] expected = {
            "96:16: Parameter e should be final.",
            "99:16: Parameter npe should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }
}
