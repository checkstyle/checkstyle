package com.puppycrawl.tools.checkstyle.checks.imports;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalImportCheckTest
extends BaseCheckTestCase
{
    public void testWithSupplied()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java.io");
        final String[] expected = {
            "9:1: Import from illegal package - java.io.*.",
            "23:1: Import from illegal package - java.io.File.listRoots.",
            "27:1: Import from illegal package - java.io.File.createTempFile.",
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }

    public void testWithDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        final String[] expected = {
            "15:1: Import from illegal package - sun.net.ftpclient.FtpClient.",
            "28:1: Import from illegal package - sun.net.ftpclient.FtpClient.*.",
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }
}
