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
            "37:26: Parameter i should be final.",
            "42:26: Parameter s should be final.",
            "52:17: Parameter s should be final.",
            "68:17: Parameter s should be final.",
            "74:17: Parameter s should be final.",
            "89:38: Parameter e should be final.",
            "92:36: Parameter e should be final.",
            "109:18: Parameter aParam should be final.",
            "112:18: Parameter args should be final.",
            "115:18: Parameter args should be final.",
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
            "37:26: Parameter i should be final.",
            "42:26: Parameter s should be final.",
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
            "52:17: Parameter s should be final.",
            "68:17: Parameter s should be final.",
            "74:17: Parameter s should be final.",
            "89:38: Parameter e should be final.",
            "92:36: Parameter e should be final.",
            "109:18: Parameter aParam should be final.",
            "112:18: Parameter args should be final.",
            "115:18: Parameter args should be final.",
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
            "124:16: Parameter e should be final.",
            "127:16: Parameter npe should be final.",
            "130:16: Parameter e should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    public void testForEachClauseToken()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "FOR_EACH_CLAUSE");
        final String[] expected = {
            "149:13: Parameter s should be final.",
            "157:13: Parameter s should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }
}
