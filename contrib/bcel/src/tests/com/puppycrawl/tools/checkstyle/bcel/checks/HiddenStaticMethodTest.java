package com.puppycrawl.tools.checkstyle.bcel.checks;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bcel.BcelCheckTestCase;

public class HiddenStaticMethodTest
    extends BcelCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenStaticMethodCheck.class);

        final String[] expected = {
            "0: Static method 'public static int staticMethod()' hides method in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
            "0: Static method 'public static int staticMethodSameParamName(int i)' hides method in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
            "0: Static method 'public static int staticMethodSameParamType(int x)' hides method in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
        };
        verify(checkConfig, getPath("com\\puppycrawl\\tools\\checkstyle\\bcel\\checks\\SubClass.class"), expected);
    }
}
