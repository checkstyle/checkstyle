package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ParenPadCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "58:12: '(' is followed by whitespace.",
            "58:36: ')' is preceded with whitespace.",
            "74:13: '(' is followed by whitespace.",
            "74:18: ')' is preceded with whitespace.",
            "232:27: ')' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    public void testSpace()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "29:20: '(' is not followed by whitespace.",
            "29:23: ')' is not preceded with whitespace.",
            "37:22: '(' is not followed by whitespace.",
            "37:26: ')' is not preceded with whitespace.",
            "41:15: '(' is not followed by whitespace.",
            "41:33: ')' is not preceded with whitespace.",
            "76:20: '(' is not followed by whitespace.",
            "76:21: ')' is not preceded with whitespace.",
            "97:22: '(' is not followed by whitespace.",
            "97:28: ')' is not preceded with whitespace.",
            "98:14: '(' is not followed by whitespace.",
            "98:18: ')' is not preceded with whitespace.",
            "150:28: '(' is not followed by whitespace.",
            "150:32: ')' is not preceded with whitespace.",
            "153:16: '(' is not followed by whitespace.",
            "153:20: ')' is not preceded with whitespace.",
            "160:21: '(' is not followed by whitespace.",
            "160:34: ')' is not preceded with whitespace.",
            "162:20: '(' is not followed by whitespace.",
            "165:10: ')' is not preceded with whitespace.",
            "178:14: '(' is not followed by whitespace.",
            "178:36: ')' is not preceded with whitespace.",
            "225:14: '(' is not followed by whitespace.",
            "235:14: '(' is not followed by whitespace.",
            "235:39: ')' is not preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
    
    public void testDefaultForIterator()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "17:34: ')' is preceded with whitespace.",
            "20:35: ')' is preceded with whitespace.",
            "40:14: '(' is followed by whitespace.",
            "40:36: ')' is preceded with whitespace.",
            "43:14: '(' is followed by whitespace.",
            "48:27: ')' is preceded with whitespace.",
            "51:26: ')' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    public void testSpaceEmptyForIterator()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "11:14: '(' is not followed by whitespace.",
            "11:35: ')' is not preceded with whitespace.",
            "14:14: '(' is not followed by whitespace.",
            "14:34: ')' is not preceded with whitespace.",
            "17:14: '(' is not followed by whitespace.",
            "20:14: '(' is not followed by whitespace.",
            "23:14: '(' is not followed by whitespace.",
            "27:14: '(' is not followed by whitespace.",
            "32:14: '(' is not followed by whitespace.",
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }
}
