package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AvoidStarImportTest
    extends BaseCheckTestCase
{
    public void testDefaultOperation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        final String[] expected = {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.*.",
            "9: Using the '.*' form of import should be avoided - java.io.*.",
            "10: Using the '.*' form of import should be avoided - java.lang.*.",
        };

        verify(checkConfig, getPath("InputImport.java"), expected);
    }

    public void testExcludes()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("excludes", "java.io,java.lang");
        // allow the java.io/java.lang star imports
        final String[] expected2 = new String[] {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.*."
        };
        verify(checkConfig, getPath("InputImport.java"), expected2);
    }
}
