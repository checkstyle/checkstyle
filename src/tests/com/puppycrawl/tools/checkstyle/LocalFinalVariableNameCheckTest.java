package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.LocalFinalVariableNameCheck;

public class LocalFinalVariableNameCheckTest
    extends BaseCheckTestCase
{
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

    public void testSet()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(LocalFinalVariableNameCheck.class.getName());
        checkConfig.addProperty("format", "[A-Z]+");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "122:19: Name 'cde' must match pattern '[A-Z]+'.",
        };
        verify(c, fname, expected);
    }
}

