package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.ConstantNameCheck;

public class ConstantNameCheckTest
    extends BaseCheckTestCase
{
    public ConstantNameCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ConstantNameCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "25:29: Name 'badConstant' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            "142:30: Name 'BAD__NAME' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'."
        };
        verify(c, fname, expected);
    }
}
