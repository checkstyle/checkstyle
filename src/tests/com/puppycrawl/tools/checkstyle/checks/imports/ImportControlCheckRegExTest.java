package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ImportControlCheckRegExTest extends BaseCheckTestSupport
{
    @Test
    public void testOne() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_one-re.xml");
        final String[] expected = {"5:1: Disallowed import - java.io.File."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_two-re.xml");
        final String[] expected = {"3:1: Disallowed import - java.awt.Image.",
                "4:1: Disallowed import - javax.swing.border.*.",
                "6:1: Disallowed import - java.awt.Button.ABORT."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }
}
