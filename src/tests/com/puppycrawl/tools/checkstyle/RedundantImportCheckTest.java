package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.RedundantImportCheck;

public class RedundantImportCheckTest
    extends BaseCheckTestCase
{
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantImportCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "7:1: Redundant import from the same package - com.puppycrawl.tools.checkstyle.*.",
            "8:38: Redundant import from the same package - com.puppycrawl.tools.checkstyle.GlobalProperties.",
            "10:1: Redundant import from the java.lang package - java.lang.*.",
            "11:1: Redundant import from the java.lang package - java.lang.String.",
            "14:1: Duplicate import to line 13 - java.util.List.",
        };
        verify(c, fname, expected);
    }
}
