package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;

public class AvoidStarImportTest
extends BaseCheckTestCase
{
    public AvoidStarImportTest(String aName)
    {
        super(aName);
    }

    public void testWithChecker()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(AvoidStarImport.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "7:1: Avoid using the '.*' form of import.",
            "9:1: Avoid using the '.*' form of import.",
            "10:1: Avoid using the '.*' form of import.",
        };
        verify(c, fname, expected);
    }
}
