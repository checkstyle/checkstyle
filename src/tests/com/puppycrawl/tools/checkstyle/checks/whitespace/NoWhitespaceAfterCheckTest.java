package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoWhitespaceAfterCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration checkConfig;

    public void setUp()
    {
        checkConfig = createCheckConfig(NoWhitespaceAfterCheck.class);
    }

    public void testDefault() throws Exception
    {
        checkConfig.addAttribute("allowLineBreaks", "false");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "6:12: '.' is followed by whitespace.",
            "29:14: '-' is followed by whitespace.",
            "29:21: '+' is followed by whitespace.",
            "31:15: '++' is followed by whitespace.",
            "31:22: '--' is followed by whitespace.",
            "87:28: ')' is followed by whitespace.",
            "89:23: ')' is followed by whitespace.",
            "90:21: ')' is followed by whitespace.",
            "111:22: '!' is followed by whitespace.",
            "112:23: '~' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "132:11: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace.",
            "241:22: ')' is followed by whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    public void testDotAllowLineBreaks() throws Exception
    {
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:14: '.' is followed by whitespace.",
            "129:24: '.' is followed by whitespace.",
            "136:12: '.' is followed by whitespace."
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

}
