package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AvoidStarImportTest
    extends BaseCheckTestCase
{
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImport.class);
        final String[] expected = {
            "7: Avoid using the '.*' form of import - com.puppycrawl.tools.checkstyle.*.",
            "9: Avoid using the '.*' form of import - java.io.*.",
            "10: Avoid using the '.*' form of import - java.lang.*.",
        };
        verify(checkConfig, getPath("InputImport.java"), expected);
    }
}
