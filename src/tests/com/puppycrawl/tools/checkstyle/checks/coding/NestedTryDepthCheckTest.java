package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class NestedTryDepthCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedTryDepthCheck.class);

        final String[] expected = {
            "20:17: Nested try depth is 2 (max allowed is 1).",
            "31:17: Nested try depth is 2 (max allowed is 1).",
            "32:21: Nested try depth is 3 (max allowed is 1).",
        };

        verify(checkConfig, getPath("coding/InputNestedTryDepth.java"), expected);
    }

    @Test
    public void testCustomNestingDepth() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedTryDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
            "32:21: Nested try depth is 3 (max allowed is 2).",
        };

        verify(checkConfig, getPath("coding/InputNestedTryDepth.java"), expected);
    }
}
