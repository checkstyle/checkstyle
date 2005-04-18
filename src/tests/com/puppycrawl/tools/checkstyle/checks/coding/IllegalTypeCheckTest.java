package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class IllegalTypeCheckTest extends BaseCheckTestCase {
    public void testDefaults() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalTypeCheck.class);

        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    public void testIgnoreMethodNames() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("ignoredMethodNames", "table2");

        String[] expected = {
            "6:13: Declaring variables, return values or parameters of type 'AbstractClass' is not allowed.",
            "9:13: Declaring variables, return values or parameters of type "
                + "'com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.AbstractClass'"
                + " is not allowed.",
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }

    public void testFormat() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("format", "^$");

        String[] expected = {
            "16:13: Declaring variables, return values or parameters of type 'java.util.Hashtable' is not allowed.",
            "17:13: Declaring variables, return values or parameters of type 'Hashtable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalType.java"), expected);
    }
}
