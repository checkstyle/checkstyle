package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.ModifierCheck;

public class ModifierCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(ModifierCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String filepath = getPath("InputModifier.java");
        assertNotNull(c);
        final String[] expected = {
            "14:10: 'final' modifier out of order with the JLS suggestions.",
            "18:12: 'private' modifier out of order with the JLS suggestions.",
            "24:14: 'private' modifier out of order with the JLS suggestions.",
        };
        verify(c, filepath, expected);

    }
}
