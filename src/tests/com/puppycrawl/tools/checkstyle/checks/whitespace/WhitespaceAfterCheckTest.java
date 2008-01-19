package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class WhitespaceAfterCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(WhitespaceAfterCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "42:40: ',' is not followed by whitespace.",
            "71:30: ',' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testCast() throws Exception
    {
        final String[] expected = {
            "88:21: 'cast' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSemi() throws Exception
    {
        final String[] expected = {
            "58:23: ';' is not followed by whitespace.",
            "58:29: ';' is not followed by whitespace.",
            "107:19: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception
    {
        final String[] expected = {
            "14:31: ';' is not followed by whitespace.",
            "17:31: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception
    {
        final String[] expected = {
            "11:21: ',' is not followed by whitespace.",
            "11:23: ',' is not followed by whitespace.",
            "11:41: ',' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879() throws Exception
    {
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}
