package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NPathComplexityCheckTest extends BaseCheckTestCase {
    public void testCalculation() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        String[] expected = {
            "4:5: NPath Complexity is 2 (max allowed is 0).",
            "7:17: NPath Complexity is 2 (max allowed is 0).",
            "17:5: NPath Complexity is 5 (max allowed is 0).",
            "27:5: NPath Complexity is 3 (max allowed is 0).",
            "34:5: NPath Complexity is 7 (max allowed is 0).",
            "48:5: NPath Complexity is 3 (max allowed is 0).",
            "58:5: NPath Complexity is 3 (max allowed is 0).",
            "67:5: NPath Complexity is 3 (max allowed is 0).",
            "76:5: NPath Complexity is 1 (max allowed is 0).",
            "79:13: NPath Complexity is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }

    public void testIntegerOverflow() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(NPathComplexityCheck.class);

        checkConfig.addAttribute("max", "0");
        String[] expected = {
            "9:5: NPath Complexity is 3,486,784,401 (max allowed is 0)." // larger than MAXINT
        };

        verify(checkConfig, getPath("ComplexityOverflow.java"), expected);
    }
}
