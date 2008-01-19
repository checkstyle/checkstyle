package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class EmptyForIteratorPadCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(EmptyForIteratorPadCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "27:31: ';' is followed by whitespace.",
            "43:32: ';' is followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception
    {
        mCheckConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "23:31: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }
}
