package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UncommentedMainCheckTest
    extends BaseCheckTestCase
{
    public void testDefaults()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UncommentedMainCheck.class);
        final String[] expected = {
            "14: Uncommented main method found.",
            "23: Uncommented main method found.",
            "32: Uncommented main method found.",
        };
        verify(checkConfig, getPath("InputUncommentedMain.java"), expected);
    }

    public void testExcludedClasses()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UncommentedMainCheck.class);
        checkConfig.addAttribute("excludedClasses", "\\.Main$");
        final String[] expected = {
            "14: Uncommented main method found.",
            "32: Uncommented main method found.",
        };
        verify(checkConfig, getPath("InputUncommentedMain.java"), expected);
    }
}
