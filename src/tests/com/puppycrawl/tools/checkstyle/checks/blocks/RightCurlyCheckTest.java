package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RightCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RightCurlyCheck.class);
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testSame()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RightCurlyCheck.class);
        checkConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testAlone()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RightCurlyCheck.class);
        checkConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }
}
