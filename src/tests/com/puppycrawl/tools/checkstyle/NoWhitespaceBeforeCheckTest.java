package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.NoWhitespaceBeforeCheck;

public class NoWhitespaceBeforeCheckTest
    extends BaseCheckTestCase
{
    private CheckConfiguration checkConfig;

    public void setUp() {
        checkConfig = new CheckConfiguration();
        checkConfig.setClassname(NoWhitespaceBeforeCheck.class.getName());
    }

    public void testDefault() throws Exception
    {
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "30:14: '++' is preceeded with whitespace.",
            "30:21: '--' is preceeded with whitespace.",
        };
        verify(c, fname, expected);
    }

    public void testDot() throws Exception
    {
        checkConfig.addTokens("DOT");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:12: '.' is preceeded with whitespace.",
            "6:4: '.' is preceeded with whitespace.",
            "129:17: '.' is preceeded with whitespace.",
            "135:12: '.' is preceeded with whitespace.",
            "136:10: '.' is preceeded with whitespace.",
        };
        verify(c, fname, expected);
    }


    public void testDotAllowLineBreaks() throws Exception
    {
        checkConfig.addTokens("DOT");
        checkConfig.addProperty("allowLineBreaks", "yes");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:12: '.' is preceeded with whitespace.",
            "129:17: '.' is preceeded with whitespace.",
            "136:10: '.' is preceeded with whitespace.",
        };
        verify(c, fname, expected);
    }

}
