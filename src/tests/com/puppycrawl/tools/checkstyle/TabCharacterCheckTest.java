package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.TabCharacterCheck;

public class TabCharacterCheckTest
        extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TabCharacterCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "19:25: Line contains a tab character.",
            "145:35: Line contains a tab character.",
            "146:64: Line contains a tab character.",
            "154:9: Line contains a tab character.",
            "155:10: Line contains a tab character.",
            "156:1: Line contains a tab character.",
            "157:3: Line contains a tab character.",
            "158:3: Line contains a tab character."
        };
        verify(c, fname, expected);
    }
}
