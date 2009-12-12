package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ImportOrderCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        final String[] expected = {
            "3: Wrong order for 'java.awt.Dialog' import.",
            "7: Wrong order for 'javax.swing.JComponent' import.",
            "9: Wrong order for 'java.io.File' import.",
            "11: Wrong order for 'java.io.IOException' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testGroups() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "java.awt, javax.swing, java.io");
        final String[] expected = {
            "3: Wrong order for 'java.awt.Dialog' import.",
            "11: Wrong order for 'java.io.IOException' import.",
            "14: Wrong order for 'javax.swing.WindowConstants.*' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testSeparated() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "java.awt, javax.swing, java.io");
        checkConfig.addAttribute("separated", "true");
        checkConfig.addAttribute("ordered", "false");
        final String[] expected = {
            "7: 'javax.swing.JComponent' should be separated from previous imports.",
            "9: 'java.io.File' should be separated from previous imports.",
            "14: Wrong order for 'javax.swing.WindowConstants.*' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder.java"), expected);
    }

    @Test
    public void testCaseInsensitive() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("caseSensitive", "false");
        final String[] expected = {
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrderCaseInsensitive.java"), expected);
    }

    @Test
    public void testTop() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "top");
        final String[] expected = {
            "4: Wrong order for 'java.awt.Button.ABORT' import.",
            "18: Wrong order for 'java.io.File.*' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Top.java"), expected);
    }

    @Test
    public void testAbove() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "above");
        final String[] expected = {
            "5: Wrong order for 'java.awt.Button.ABORT' import.",
            "8: Wrong order for 'java.awt.Dialog' import.",
            "13: Wrong order for 'java.io.File' import.",
            "14: Wrong order for 'java.io.File.createTempFile' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Above.java"), expected);
    }

    @Test
    public void testInFlow() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "inflow");
        final String[] expected = {
            "6: Wrong order for 'java.awt.Dialog' import.",
            "11: Wrong order for 'javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE' import.",
            "12: Wrong order for 'javax.swing.WindowConstants.*' import.",
            "13: Wrong order for 'javax.swing.JTable' import.",
            "15: Wrong order for 'java.io.File.createTempFile' import.",
            "16: Wrong order for 'java.io.File' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_InFlow.java"), expected);
    }

    @Test
    public void testUnder() throws Exception
    {
        // is default (testDefault)
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "under");
        final String[] expected = {
            "5: Wrong order for 'java.awt.Dialog' import.",
            "11: Wrong order for 'java.awt.Button.ABORT' import.",
            "14: Wrong order for 'java.io.File' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Under.java"), expected);
    }

    @Test
    public void testBottom() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("option", "bottom");
        final String[] expected = {
                "15: Wrong order for 'java.io.File' import.",
                "18: Wrong order for 'java.awt.Button.ABORT' import.",
                "21: Wrong order for 'java.io.Reader' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Bottom.java"), expected);
    }

    @Test
    public void testWildcard() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("groups", "com,*,java");
        final String[] expected = {
                "9: Wrong order for 'javax.crypto.Cipher' import."
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrder_Wildcard.java"), expected);
    }
}
