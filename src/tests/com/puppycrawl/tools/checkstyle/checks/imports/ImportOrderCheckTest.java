package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class ImportOrderCheckTest extends BaseCheckTestCase
{
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

    public void testCaseInsensitive() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportOrderCheck.class);
        checkConfig.addAttribute("caseSensitive", "false");
        final String[] expected = {
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImportOrderCaseInsensitive.java"), expected);
    }
}
