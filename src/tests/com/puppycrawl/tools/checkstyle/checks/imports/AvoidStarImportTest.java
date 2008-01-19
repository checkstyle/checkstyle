package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class AvoidStarImportTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefaultOperation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        final String[] expected = {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.imports.*.",
            "9: Using the '.*' form of import should be avoided - java.io.*.",
            "10: Using the '.*' form of import should be avoided - java.lang.*.",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }

    @Test
    public void testExcludes()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("excludes", "java.io,java.lang");
        // allow the java.io/java.lang star imports
        final String[] expected2 = new String[] {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.imports.*."
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected2);
    }
}
