package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AvoidNestedBlocksCheckTest
        extends BaseCheckTestCase
{
    public void testStrictSettings()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidNestedBlocksCheck.class);
        final String[] expected = {
            "22:9: Avoid nested blocks.",
            "44:17: Avoid nested blocks.",
            "50:17: Avoid nested blocks.",
            "58:17: Avoid nested blocks.",
        };
        verify(checkConfig, getPath("InputNestedBlocks.java"), expected);
    }

    public void testAllowSwitchInCase()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidNestedBlocksCheck.class);
        checkConfig.addAttribute("allowInSwitchCase", Boolean.TRUE.toString());

        final String[] expected = {
            "22:9: Avoid nested blocks.",
            "44:17: Avoid nested blocks.",
            "58:17: Avoid nested blocks.",
        };
        verify(checkConfig, getPath("InputNestedBlocks.java"), expected);
    }
}
