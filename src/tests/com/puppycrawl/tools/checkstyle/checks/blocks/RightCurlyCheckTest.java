package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RightCurlyCheckTest extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    public void setUp()
    {
        mCheckConfig = createCheckConfig(RightCurlyCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testSame() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.SAME.toString());
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testAlone() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        final String[] expected = {
            "93:27: '}' should be alone on a line.",
            "93:27: '}' should be on a new line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testShouldStartLine() throws Exception
    {
        mCheckConfig.addAttribute("option", RightCurlyOption.ALONE.toString());
        mCheckConfig.addAttribute("shouldStartLine", "false");
        final String[] expected = {
            "93:27: '}' should be alone on a line.",
        };
        verify(mCheckConfig, getPath("InputLeftCurlyOther.java"), expected);
    }
}
