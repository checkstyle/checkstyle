package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class MethodCountCheckTest  extends BaseCheckTestSupport {

    @Test
    public void testDefaults() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);

        final String[] expected = {
        };

        verify(checkConfig,
            getSrcPath("checks/metrics/MethodCountCheckInput.java"), expected);
    }

    @Test
    public void testThrees() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MethodCountCheck.class);
        checkConfig.addAttribute("maxPrivate", "3");
        checkConfig.addAttribute("maxPackage", "3");
        checkConfig.addAttribute("maxProtected", "3");
        checkConfig.addAttribute("maxPublic", "3");
        checkConfig.addAttribute("maxTotal", "3");

        final String[] expected = {
            "3: Number of package methods is 5 (max allowed is 3).",
            "3: Number of private methods is 5 (max allowed is 3).",
            "3: Number of protected methods is 5 (max allowed is 3).",
            "3: Number of public methods is 5 (max allowed is 3).",
            "3: Total number of methods is 20 (max allowed is 3).",
            "9: Number of public methods is 5 (max allowed is 3).",
            "9: Total number of methods is 5 (max allowed is 3).",
            "45: Number of public methods is 5 (max allowed is 3).",
            "45: Total number of methods is 5 (max allowed is 3).",
        };

        verify(checkConfig,
            getSrcPath("checks/metrics/MethodCountCheckInput.java"), expected);
    }
}