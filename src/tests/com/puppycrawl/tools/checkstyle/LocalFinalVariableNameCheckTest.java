package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.LocalFinalVariableNameCheck;

public class LocalFinalVariableNameCheckTest
    extends BaseCheckTestCase
{
    public LocalFinalVariableNameCheckTest(String aName)
    {
        super(aName);
    }

    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(LocalFinalVariableNameCheck.class.getName());;
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "123:19: Name 'CDE' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        verify(c, fname, expected);
    }
}

