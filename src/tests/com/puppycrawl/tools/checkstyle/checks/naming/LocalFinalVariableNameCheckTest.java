package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class LocalFinalVariableNameCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalFinalVariableNameCheck.class);
        final String[] expected = {
            "123:19: Name 'CDE' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    public void testSet()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalFinalVariableNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");
        final String[] expected = {
            "122:19: Name 'cde' must match pattern '[A-Z]+'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
    
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalFinalVariableNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputInner.java"), expected);
    }
}

