package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.MethodLeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.LeftCurlyOption;

public class MethodLeftCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(MethodLeftCurlyCheck.class.getName());
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
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(MethodLeftCurlyCheck.class.getName());
        checkConfig.addProperty("option", LeftCurlyOption.NL.toString());
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
