package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.LineLengthCheck;

public class LineLengthCheckTest extends BaseCheckTestCase
{
    public void testSimple()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(LineLengthCheck.class.getName());
        checkConfig.addProperty("max", "80");
        checkConfig.addProperty("ignorePattern",  "^.*is OK.*regexp.*$");
        final Checker c = createChecker(checkConfig);
        final String filepath = getPath("InputSimple.java");
        final String[] expected = {
            "18: Line is longer than 80 characters.",
            "145: Line is longer than 80 characters.",
        };
        verify(c, filepath, expected);
    }

}
