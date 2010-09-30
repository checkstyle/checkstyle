package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class LocalFinalVariableNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

    @Test
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

    @Test
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalFinalVariableNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputInner.java"), expected);
    }
}

