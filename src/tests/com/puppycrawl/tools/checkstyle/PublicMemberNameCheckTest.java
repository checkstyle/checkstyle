package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.PublicMemberNameCheck;

public class PublicMemberNameCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(PublicMemberNameCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "58:16: Name 'mTest2' must match pattern '^f[A-Z][a-zA-Z0-9]*$'.",
        };
        verify(c, fname, expected);
    }
}

