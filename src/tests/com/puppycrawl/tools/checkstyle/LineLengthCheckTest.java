package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.LineLengthCheck;

public class LineLengthCheckTest extends BaseCheckTestCase
{
    public void testSimple()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LineLengthCheck.class);
        checkConfig.addAttribute("max", "80");
        checkConfig.addAttribute("ignorePattern",  "^.*is OK.*regexp.*$");
        final Checker c = createChecker(checkConfig);
        final String filepath = getPath("InputSimple.java");
        final String[] expected = {
            "18: Line is longer than 80 characters.",
            "145: Line is longer than 80 characters.",
        };
        verify(c, filepath, expected);
    }
}
