package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.MethodLeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.LeftCurlyOption;

public class MethodLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodLeftCurlyCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyMethod.java");
        final String[] expected = {
            "17:5: '{' should be on the previous line.",
            "24:5: '{' should be on the previous line.",
            "31:5: '{' should be on the previous line.",
        };
        verify(c, fname, expected);
    }

    public void testNL()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodLeftCurlyCheck.class);
        checkConfig.addAttribute("option", LeftCurlyOption.NL.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyMethod.java");
        final String[] expected = {
            "14:39: '{' should be on a new line.",
            "21:20: '{' should be on a new line.",
            "34:31: '{' should be on a new line.",
        };
        verify(c, fname, expected);
    }
}
