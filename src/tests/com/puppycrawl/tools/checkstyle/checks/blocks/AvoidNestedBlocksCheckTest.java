package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AvoidNestedBlocksCheckTest
        extends BaseCheckTestCase
{
    public void testIt()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidNestedBlocksCheck.class);
        final String[] expected = {
            "22:9: Avoid nested blocks.",
            "38:17: Avoid nested blocks.",
        };
        verify(checkConfig, getPath("InputNestedBlocks.java"), expected);
    }
}
