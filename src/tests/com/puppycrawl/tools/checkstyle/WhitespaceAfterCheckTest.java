package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.WhitespaceAfterCheck;

public class WhitespaceAfterCheckTest
    extends BaseCheckTestCase
{
    private CheckConfiguration checkConfig;
    
    public WhitespaceAfterCheckTest(String aName)
    {
        super(aName);
    }
    
    public void setUp() {
        checkConfig = new CheckConfiguration();
        checkConfig.setClassname(WhitespaceAfterCheck.class.getName());
    }
    
    public void testDefault() throws Exception
    {
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputSimple.java");
        final String[] expected = {
            "42:40: ',' is not followed by whitespace.",
            "71:30: ',' is not followed by whitespace.",
        };
        verify(c, fname, expected);
    }
    
    public void testCast() throws Exception
    {
        checkConfig.addTokens("TYPECAST");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "88:21: 'cast' is not followed by whitespace.",
        };
        verify(c, fname, expected);
    }
}
