package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.ConstantNameCheck;

public class ConstantNameCheckTest
    extends BaseCheckTestCase
{
    public void testIllegalRegexp()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ConstantNameCheck.class.getName());
        checkConfig.addProperty("format", "\\");
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
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

    public void testInterface()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ConstantNameCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputInner.java");
        final String[] expected = {
            "24:16: Name 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'."
        };
        verify(c, fname, expected);
    }
}
