package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.RedundantImportCheck;

public class RedundantImportCheckTest
    extends BaseCheckTestCase
{
    public RedundantImportCheckTest(String aName)
    {
        super(aName);
    }

    public void testWithChecker()
        throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(RedundantImportCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "7:1: Redundant import from the same package.",
            "8:38: Redundant import from the same package.",
            "10:1: Redundant import from the java.lang package.",
            "11:1: Redundant import from the java.lang package.",
        };
        verify(c, fname, expected);
    }
}
