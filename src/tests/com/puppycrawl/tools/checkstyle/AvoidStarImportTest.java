package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;

public class AvoidStarImportTest
    extends BaseCheckTestCase
{
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            new DefaultConfiguration("test");
        checkConfig.addAttribute("classname", AvoidStarImport.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "7: Avoid using the '.*' form of import - com.puppycrawl.tools.checkstyle.*.",
            "9: Avoid using the '.*' form of import - java.io.*.",
            "10: Avoid using the '.*' form of import - java.lang.*.",
        };
        verify(c, fname, expected);
    }
}
