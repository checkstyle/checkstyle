package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OtherLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OtherLeftCurlyCheck.class);
        final String[] expected = {
            "19:9: '{' should be on the previous line.",
            "21:13: '{' should be on the previous line.",
            "23:17: '{' should be on the previous line.",
            "30:17: '{' should be on the previous line.",
            "34:17: '{' should be on the previous line.",
            "42:13: '{' should be on the previous line.",
            "46:13: '{' should be on the previous line.",
            "52:9: '{' should be on the previous line.",
            "54:13: '{' should be on the previous line.",
            "63:9: '{' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testNL()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OtherLeftCurlyCheck.class);
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
            "26:33: '{' should be on a new line."
        };
        verify(checkConfig, getPath("InputLeftCurlyOther.java"), expected);
    }

    public void testMissingBraces()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OtherLeftCurlyCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }
}
