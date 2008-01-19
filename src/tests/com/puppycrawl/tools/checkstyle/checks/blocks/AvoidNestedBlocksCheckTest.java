package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class AvoidNestedBlocksCheckTest
        extends BaseCheckTestSupport
{
    @Test
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

    @Test
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
