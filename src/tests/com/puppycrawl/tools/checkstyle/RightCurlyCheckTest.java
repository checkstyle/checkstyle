package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.RightCurlyOption;

public class RightCurlyCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(RightCurlyCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
        };
        verify(c, fname, expected);
    }

    public void testSame()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(RightCurlyCheck.class.getName());
        checkConfig.addProperty("option", RightCurlyOption.SAME.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
            "25:17: '}' should be on the same line.",
            "28:17: '}' should be on the same line.",
            "40:13: '}' should be on the same line.",
            "44:13: '}' should be on the same line.",
        };
        verify(c, fname, expected);
    }

    public void testAlone()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(RightCurlyCheck.class.getName());
        checkConfig.addProperty("option", RightCurlyOption.ALONE.toString());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputLeftCurlyOther.java");
        final String[] expected = {
        };
        verify(c, fname, expected);
    }
}
