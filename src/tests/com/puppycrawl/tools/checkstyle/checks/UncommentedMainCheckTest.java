package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class UncommentedMainCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

    @Test
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
