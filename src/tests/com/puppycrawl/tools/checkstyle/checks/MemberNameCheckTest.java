package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MemberNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^m[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "35:17: Name 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }
    
    public void testInnerClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        final String[] expected = {
            "56:25: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }
}

