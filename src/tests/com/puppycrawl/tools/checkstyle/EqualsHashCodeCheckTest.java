package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.EqualsHashCodeCheck;

public class EqualsHashCodeCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EqualsHashCodeCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSemantic.java");
        final String[] expected = {
            "126:9: Definition of 'equals()' without corresponding defnition of 'hashCode()'.",
            "163:13: Definition of 'equals()' without corresponding defnition of 'hashCode()'.",
        };
        verify(c, fname, expected);

    }

}
