package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck;

public class RedundantModifierTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "57:9: Redundant 'public' modifier.",
            "63:9: Redundant 'abstract' modifier.",
            "66:9: Redundant 'public' modifier.",
            "69:9: Redundant 'abstract' modifier.",
            "72:9: Redundant 'final' modifier.",
            "79:13: Redundant 'final' modifier.",
            "88:12: Redundant 'final' modifier.",
            "116:5: Redundant 'public' modifier.",
            "117:5: Redundant 'final' modifier.",
            "118:5: Redundant 'static' modifier.",
            "120:5: Redundant 'public' modifier.",
            "121:5: Redundant 'abstract' modifier.",
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}
