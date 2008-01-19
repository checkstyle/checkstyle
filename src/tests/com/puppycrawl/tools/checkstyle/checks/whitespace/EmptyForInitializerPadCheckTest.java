package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class EmptyForInitializerPadCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(EmptyForInitializerPadCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "48:14: ';' is preceded with whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception
    {
        mCheckConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "51:13: ';' is not preceded with whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }
}
