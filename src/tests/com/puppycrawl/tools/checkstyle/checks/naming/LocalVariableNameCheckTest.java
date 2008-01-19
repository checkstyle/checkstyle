package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class LocalVariableNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final String[] expected = {
            "119:13: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "130:18: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "132:20: Name 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "207:21: Name 'O' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testCatchParameter()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        checkConfig.addAttribute("tokens", "PARAMETER_DEF");
        checkConfig.addAttribute("format", "^e$");
        final String[] expected = {
            "74:24: Name 'ex' must match pattern '^e$'.",
        };
        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }
}

