package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NestedIfDepthCheckTest extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedIfDepthCheck.class);

        final String[] expected = {
            "17:17: Nested if-else depth is 2 (max allowed is 1).",
            "43:17: Nested if-else depth is 2 (max allowed is 1).",
        };

        verify(checkConfig, getPath("coding/InputNestedIfDepth.java"), expected);
    }
    //        checkConfig.addAttribute("max", "2");

    public void testCustomNestingDepth() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedIfDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
        };

        verify(checkConfig, getPath("coding/InputNestedIfDepth.java"), expected);
    }
}
