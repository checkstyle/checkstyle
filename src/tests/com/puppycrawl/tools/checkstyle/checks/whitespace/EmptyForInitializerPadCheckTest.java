package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EmptyForInitializerPadCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    public void setUp()
    {
        mCheckConfig = createCheckConfig(EmptyForInitializerPadCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "48:14: ';' is preceded with whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    public void testSpaceOption() throws Exception
    {
        mCheckConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "51:13: ';' is not preceded with whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }
}
