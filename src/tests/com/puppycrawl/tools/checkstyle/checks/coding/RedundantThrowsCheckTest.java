package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RedundantThrowsCheckTest
    extends BaseCheckTestCase
{
    public void testDefaults() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantThrowsCheck.class);
        final String[] expected = {
            "7:37: Redundant throws: 'java.io.FileNotFoundException' is subclass of 'java.io.IOException'.",
            "13:16: Redundant throws: 'RuntimeException' is unchecked exception.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
//             "25:16: Unable to get class information for WrongException.",
//             "35:27: Unable to get class information for WrongException.",
            "39:27: Redundant throws: 'NullPointerException' is subclass of 'RuntimeException'.",
            "39:27: Redundant throws: 'NullPointerException' is unchecked exception.",
            "39:49: Redundant throws: 'RuntimeException' is unchecked exception.",
        };
        verify(checkConfig, getPath("InputRedundantThrows.java"), expected);
    }

    public void testAllowUnchecked() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantThrowsCheck.class);
        checkConfig.addAttribute("allowUnchecked", "true");
        final String[] expected = {
            "7:37: Redundant throws: 'java.io.FileNotFoundException' is subclass of 'java.io.IOException'.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
//             "35:27: Unable to get class information for WrongException.",
            "39:27: Redundant throws: 'NullPointerException' is subclass of 'RuntimeException'.",
        };
        verify(checkConfig, getPath("InputRedundantThrows.java"), expected);
    }

    public void testAllowSubclasses() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantThrowsCheck.class);
        checkConfig.addAttribute("allowSubclasses", "true");
        final String[] expected = {
            "13:16: Redundant throws: 'RuntimeException' is unchecked exception.",
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
//             "25:16: Unable to get class information for WrongException.",
//             "35:27: Unable to get class information for WrongException.",
            "39:27: Redundant throws: 'NullPointerException' is unchecked exception.",
            "39:49: Redundant throws: 'RuntimeException' is unchecked exception.",
        };
        verify(checkConfig, getPath("InputRedundantThrows.java"), expected);
    }

    public void testRejectDuplicatesOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantThrowsCheck.class);
        checkConfig.addAttribute("allowSubclasses", "true");
        checkConfig.addAttribute("allowUnchecked", "true");
        final String[] expected = {
            "19:29: Redundant throws: 'java.io.IOException' listed more then one time.",
        };
        verify(checkConfig, getPath("InputRedundantThrows.java"), expected);
    }
}
