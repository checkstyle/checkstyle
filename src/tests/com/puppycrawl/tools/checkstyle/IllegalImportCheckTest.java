package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.IllegalImportCheck;

public class IllegalImportCheckTest
extends BaseCheckTestCase
{
    public void testWithSupplied()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java.io");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "9:1: Import from illegal package - java.io.*.",
        };
        verify(c, fname, expected);
    }

    public void testWithDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputImport.java");
        final String[] expected = {
            "15:1: Import from illegal package - sun.net.ftpclient.FtpClient.",
        };
        verify(c, fname, expected);
    }
}
