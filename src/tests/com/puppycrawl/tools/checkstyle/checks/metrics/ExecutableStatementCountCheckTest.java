package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ExecutableStatementCountCheckTest 
    extends BaseCheckTestCase 
{
    public void testMaxZero() throws Exception 
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "4:5: Executable statement count is 3 (max allowed is 0).",
            "7:17: Executable statement count is 1 (max allowed is 0).",
            "17:5: Executable statement count is 2 (max allowed is 0).",
            "27:5: Executable statement count is 1 (max allowed is 0).",
            "34:5: Executable statement count is 3 (max allowed is 0).",
            "48:5: Executable statement count is 2 (max allowed is 0).",
            "58:5: Executable statement count is 2 (max allowed is 0).",
            "67:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
    
    public void testMethodDef() throws Exception 
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expected = {
            "4:5: Executable statement count is 3 (max allowed is 0).",
            "7:17: Executable statement count is 1 (max allowed is 0).",
            "17:5: Executable statement count is 2 (max allowed is 0).",
            "27:5: Executable statement count is 1 (max allowed is 0).",
            "34:5: Executable statement count is 3 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
    
    public void testCtorDef() throws Exception 
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "CTOR_DEF");

        final String[] expected = {
            "48:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
    
    public void testStaticInit() throws Exception 
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "STATIC_INIT");

        final String[] expected = {
            "58:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
    
    public void testInstanceInit() throws Exception 
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExecutableStatementCountCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("tokens", "INSTANCE_INIT");

        final String[] expected = {
            "67:5: Executable statement count is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
}