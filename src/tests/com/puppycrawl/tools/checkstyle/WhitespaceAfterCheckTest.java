package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.WhitespaceAfterCheck;

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
        final Checker c = createChecker(mCheckConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "42:40: ',' is not followed by whitespace.",
            "71:30: ',' is not followed by whitespace.",
        };
        verify(c, fname, expected);
    }

    public void testCast() throws Exception
    {
        final Checker c = createChecker(mCheckConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "88:21: 'cast' is not followed by whitespace.",
        };
        verify(c, fname, expected);
    }

    public void testSemi() throws Exception
    {;
        final Checker c = createChecker(mCheckConfig);
        final String fname = getPath("InputBraces.java");
        final String[] expected = {
            "58:23: ';' is not followed by whitespace.",
            "58:29: ';' is not followed by whitespace.",
            "107:19: ';' is not followed by whitespace.",
        };
        verify(c, fname, expected);
    }
}
