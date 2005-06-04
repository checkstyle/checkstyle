package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class ClassFanOutComplexityCheckTest extends BaseCheckTestCase {
    public void test() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
            "6:1: Class Fan-Out Complexity is 7 (max allowed is 0).",
            "7:5: Class Fan-Out Complexity is 2 (max allowed is 0).",
            "27:1: Class Fan-Out Complexity is 4 (max allowed is 0).",
        };

        verify(checkConfig, getPath("metrics" + File.separator +"ClassCouplingCheckTestInput.java"), expected);
    }

    public void test15() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
        };

        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }
}
