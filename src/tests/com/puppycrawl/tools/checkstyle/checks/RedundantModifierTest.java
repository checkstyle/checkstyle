package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RedundantModifierTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "32:9: Redundant 'public' modifier.",
            "38:9: Redundant 'abstract' modifier.",
            "41:9: Redundant 'public' modifier.",
            "44:9: Redundant 'abstract' modifier.",
            "47:9: Redundant 'final' modifier.",
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}
