package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class OperatorWrapCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(OperatorWrapCheck.class);
    }

    @Test
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

    @Test
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

    @Test
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
