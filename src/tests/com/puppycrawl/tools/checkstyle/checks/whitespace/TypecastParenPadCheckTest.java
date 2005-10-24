package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TypecastParenPadCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        final String[] expected = {
            "89:14: '(' is followed by whitespace.",
            "89:21: ')' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    public void testSpace()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "87:21: '(' is not followed by whitespace.",
            "87:27: ')' is not preceded with whitespace.",
            "88:14: '(' is not followed by whitespace.",
            "88:20: ')' is not preceded with whitespace.",
            "90:14: '(' is not followed by whitespace.",
            "90:20: ')' is not preceded with whitespace.",
            "241:18: '(' is not followed by whitespace.",
            "241:21: ')' is not preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    public void test1322879() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypecastParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}
