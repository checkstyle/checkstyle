package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class EmptyForIteratorPadCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    public void setUp()
    {
        mCheckConfig = createCheckConfig(EmptyForIteratorPadCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "27:31: ';' is followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    public void testSpaceOption() throws Exception
    {
        mCheckConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "23:30: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }
}
