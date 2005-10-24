package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MethodParamPadCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration checkConfig;

    public void setUp()
    {
        checkConfig = createCheckConfig(MethodParamPadCheck.class);
    }

    public void testDefault() throws Exception
    {
        final String[] expected = {
            "11:31: 'InputMethodParamPad' is followed by whitespace.",
            "13:14: 'super' is followed by whitespace.",
            "17:9: '(' should be on the previous line.",
            "20:13: '(' should be on the previous line.",
            "27:23: 'method' is followed by whitespace.",
            "32:9: '(' should be on the previous line.",
            "36:38: 'InputMethodParamPad' is followed by whitespace.",
            "38:13: '(' should be on the previous line.",
            "42:15: 'method' is followed by whitespace.",
            "44:13: '(' should be on the previous line.",
            "50:20: 'method' is followed by whitespace.",
            "52:13: '(' should be on the previous line.",
            "56:17: 'method' is followed by whitespace.",
            "58:13: '(' should be on the previous line.",
            "61:35: 'parseInt' is followed by whitespace.",
            "63:13: '(' should be on the previous line.",
        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    public void testAllowLineBreaks() throws Exception
    {
        checkConfig.addAttribute("allowLineBreaks", "true");
        final String[] expected = {
            "11:31: 'InputMethodParamPad' is followed by whitespace.",
            "13:14: 'super' is followed by whitespace.",
            "27:23: 'method' is followed by whitespace.",
            "36:38: 'InputMethodParamPad' is followed by whitespace.",
            "42:15: 'method' is followed by whitespace.",
            "50:20: 'method' is followed by whitespace.",
            "56:17: 'method' is followed by whitespace.",
            "61:35: 'parseInt' is followed by whitespace.",
        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    public void testSpaceOption() throws Exception
    {
        checkConfig.addAttribute("option", "space");
        final String[] expected = {
            "6:31: 'InputMethodParamPad' is not followed by whitespace.",
            "8:14: 'super' is not followed by whitespace.",
            "17:9: '(' should be on the previous line.",
            "20:13: '(' should be on the previous line.",
            "23:23: 'method' is not followed by whitespace.",
            "32:9: '(' should be on the previous line.",
            "35:58: 'InputMethodParamPad' is not followed by whitespace.",
            "38:13: '(' should be on the previous line.",
            "41:15: 'method' is not followed by whitespace.",
            "44:13: '(' should be on the previous line.",
            "47:28: 'dottedCalls' is not followed by whitespace.",
            "49:20: 'method' is not followed by whitespace.",
            "52:13: '(' should be on the previous line.",
            "54:56: 'InputMethodParamPad' is not followed by whitespace.",
            "55:17: 'method' is not followed by whitespace.",
            "58:13: '(' should be on the previous line.",
            "60:35: 'parseInt' is not followed by whitespace.",
            "63:13: '(' should be on the previous line.",
            "66:25: 'newArray' is not followed by whitespace.",

        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    public void test1322879() throws Exception
    {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}
