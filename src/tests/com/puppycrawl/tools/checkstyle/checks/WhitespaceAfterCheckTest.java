package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class WhitespaceAfterCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    public void setUp()
    {
        mCheckConfig = createCheckConfig(WhitespaceAfterCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "42:40: ',' is not followed by whitespace.",
            "71:30: ',' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputSimple.java"), expected);
    }

    public void testCast() throws Exception
    {
        final String[] expected = {
            "88:21: 'cast' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputWhitespace.java"), expected);
    }

    public void testSemi() throws Exception
    {;
        final String[] expected = {
            "58:23: ';' is not followed by whitespace.",
            "58:29: ';' is not followed by whitespace.",
            "107:19: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputBraces.java"), expected);
    }
}
