package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class TypeLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeLeftCurlyCheck.class);
        final String[] expected = {
            "8:1: '{' should be on the previous line.",
            "12:5: '{' should be on the previous line.",
            "21:5: '{' should be on the previous line.",
            "30:5: '{' should be on the previous line.",
            "39:5: '{' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    public void testNL()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeLeftCurlyCheck.class);
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    public void testNLOW()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(TypeLeftCurlyCheck.class);
        checkConfig.addAttribute("option", LeftCurlyOption.NLOW.toString());
        final String[] expected = {
            "8:1: '{' should be on the previous line.",
            "12:5: '{' should be on the previous line.",
            "21:5: '{' should be on the previous line.",
            "30:5: '{' should be on the previous line.",
            "39:5: '{' should be on the previous line.",
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }
}
