package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.NoWhitespaceAfterCheck;

public class NoWhitespaceAfterCheckTest
    extends BaseCheckTestCase
{
    private CheckConfiguration checkConfig;

    public void setUp() {
        checkConfig = new CheckConfiguration();
        checkConfig.setClassname(NoWhitespaceAfterCheck.class.getName());
    }

    public void testDefault() throws Exception
    {
        checkConfig.addProperty("allowLineBreaks", "false");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "6:12: '.' is followed by whitespace.",
            "29:14: '-' is followed by whitespace.",
            "29:21: '+' is followed by whitespace.",
            "31:15: '++' is followed by whitespace.",
            "31:22: '--' is followed by whitespace.",
            "111:22: '!' is followed by whitespace.",
            "112:23: '~' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "132:11: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace."
        };
        verify(c, fname, expected);
    }

    public void testDotAllowLineBreaks() throws Exception
    {
        checkConfig.addTokens("DOT");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace."
        };
        verify(c, fname, expected);
    }

}
