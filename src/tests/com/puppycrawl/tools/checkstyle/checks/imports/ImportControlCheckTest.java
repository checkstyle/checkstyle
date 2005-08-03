package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;

public class ImportControlCheckTest extends BaseCheckTestCase
{
    public void testOne() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_one.xml");
        final String[] expected = {"5:1: Disallowed import - java.io.File."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    public void testTwo() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_two.xml");
        final String[] expected = {"3:1: Disallowed import - java.awt.Image.",
                "4:1: Disallowed import - javax.swing.border.*.",
                "6:1: Disallowed import - java.awt.Button.ABORT."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    public void testWrong() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_wrong.xml");
        final String[] expected = {"1:40: Import control file does not handle this package."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    public void testMissing() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        final String[] expected = {"1:40: Missing an import control file."};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    public void testEmpty() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "   ");
        final String[] expected = {"1:40: Missing an import control file."};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    public void testUnknown() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "unknown-file");
        final String[] expected = {};
        try {
            verify(checkConfig, getPath("imports" + File.separator
                    + "InputImportControl.java"), expected);
            fail("should fail");
        }
        catch (CheckstyleException ex) {
            ;
        }
    }

    public void testBroken() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_broken.xml");
        final String[] expected = {};
        try {
            verify(checkConfig, getPath("imports" + File.separator
                    + "InputImportControl.java"), expected);
            fail("should fail");
        }
        catch (CheckstyleException ex) {
            ;
        }
    }
}
