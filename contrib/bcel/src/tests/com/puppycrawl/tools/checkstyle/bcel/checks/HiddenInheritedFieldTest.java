package com.puppycrawl.tools.checkstyle.bcel.checks;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bcel.BcelCheckTestCase;

public class HiddenInheritedFieldTest
    extends BcelCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HiddenInheritedFieldCheck.class);

        final String[] expected = {
            "0: Field 'private int subClassPrivate' hides field in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
            "0: Field 'protected int differentType' hides field in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
            "0: Field 'protected int reusedName' hides field in class 'com.puppycrawl.tools.checkstyle.bcel.checks.SuperClass'.",
        };
        verify(checkConfig, getPath("com\\puppycrawl\\tools\\checkstyle\\bcel\\checks\\SubClass.class"), expected);
    }
}
