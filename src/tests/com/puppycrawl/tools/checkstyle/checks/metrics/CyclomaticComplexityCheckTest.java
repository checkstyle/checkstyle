package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class CyclomaticComplexityCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void test() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CyclomaticComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        final String[] expected = {
            "4:5: Cyclomatic Complexity is 2 (max allowed is 0).",
            "7:17: Cyclomatic Complexity is 2 (max allowed is 0).",
            "17:5: Cyclomatic Complexity is 6 (max allowed is 0).",
            "27:5: Cyclomatic Complexity is 3 (max allowed is 0).",
            "34:5: Cyclomatic Complexity is 5 (max allowed is 0).",
            "48:5: Cyclomatic Complexity is 3 (max allowed is 0).",
            "58:5: Cyclomatic Complexity is 3 (max allowed is 0).",
            "67:5: Cyclomatic Complexity is 3 (max allowed is 0).",
            "76:5: Cyclomatic Complexity is 1 (max allowed is 0).",
            "79:13: Cyclomatic Complexity is 2 (max allowed is 0).",
        };

        verify(checkConfig, getPath("ComplexityCheckTestInput.java"), expected);
    }
}
