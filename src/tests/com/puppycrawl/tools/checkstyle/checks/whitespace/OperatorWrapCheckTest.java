package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OperatorWrapCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration checkConfig;

    public void setUp() {
        checkConfig = createCheckConfig(OperatorWrapCheck.class);
    }

    public void testDefault()
        throws Exception
    {
        final String[] expected = {
            "15:19: '+' should be on a new line.",
            "16:15: '-' should be on a new line.",
            "24:18: '&&' should be on a new line.",

        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    public void testOpWrapEOL()
        throws Exception
    {
        checkConfig.addAttribute("option", OperatorWrapOption.EOL.toString());
        final String[] expected = {
            "18:13: '-' should be on the previous line.",
            "22:13: '&&' should be on the previous line.",
            "27:13: '&&' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    public void testAssignEOL()
        throws Exception
    {
        checkConfig.addAttribute("tokens", "ASSIGN");
        checkConfig.addAttribute("option", OperatorWrapOption.EOL.toString());
        final String[] expected = {
            "33:13: '=' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }
}
