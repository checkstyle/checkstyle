package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.MemberNameCheck;

public class MemberNameCheckTest
    extends BaseCheckTestCase
{
    public void testSpecified()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(MemberNameCheck.class.getName());
        checkConfig.addProperty("format", "^m[A-Z][a-zA-Z0-9]*$");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "35:17: Name 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(c, fname, expected);
    }
}

