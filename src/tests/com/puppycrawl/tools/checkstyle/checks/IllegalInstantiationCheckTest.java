package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalInstantiationCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
            "classes",
            "java.lang.Boolean,"
                + "com.puppycrawl.tools.checkstyle.InputModifier,"
                + "java.io.File,"
                + "java.awt.Color");
        final String[] expected = {
            "19:21: Avoid instantiation of java.lang.Boolean.",
            "24:21: Avoid instantiation of java.lang.Boolean.",
            "31:16: Avoid instantiation of java.lang.Boolean.",
            "38:21: Avoid instantiation of " +
                "com.puppycrawl.tools.checkstyle.InputModifier.",
            "41:18: Avoid instantiation of java.io.File.",
            "44:21: Avoid instantiation of java.awt.Color."
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}
