package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.IllegalInstantiationCheck;

public class IllegalInstantiationCheckTest
        extends BaseCheckTestCase
{
    public IllegalInstantiationCheckTest(String aName)
    {
        super(aName);
    }

    public void testIt() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(IllegalInstantiationCheck.class.getName());
        checkConfig.addProperty("classes",
                "java.lang.Boolean,"
                + "com.puppycrawl.tools.checkstyle.InputModifier,"
                + "java.io.File,"
                + "java.awt.Color");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "19:21: Avoid instantiation of java.lang.Boolean.",
            "24:21: Avoid instantiation of java.lang.Boolean.",
            "30:16: Avoid instantiation of java.lang.Boolean.",
            "37:21: Avoid instantiation of " +
                "com.puppycrawl.tools.checkstyle.InputModifier.",
            "40:18: Avoid instantiation of java.io.File.",
            "43:21: Avoid instantiation of java.awt.Color."
        };
        verify(c, fname, expected);
    }
}
