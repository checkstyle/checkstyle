package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ArrayTypeStyleCheckTest
    extends BaseCheckTestCase
{
    public void testJavaStyle()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ArrayTypeStyleCheck.class);
        final String[] expected = {
            "14:23: Array brackets at illegal position.",
            "20:44: Array brackets at illegal position.",
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }

    public void testCStyle()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ArrayTypeStyleCheck.class);
        checkConfig.addAttribute("javaStyle", "false");
        final String[] expected = {
            "13:16: Array brackets at illegal position.",
            "16:39: Array brackets at illegal position.",
            "22:18: Array brackets at illegal position.",
            "30:20: Array brackets at illegal position.",
        };
        verify(checkConfig, getPath("InputArrayTypeStyle.java"), expected);
    }
}
