package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class BooleanExpressionComplexityCheckTest extends BaseCheckTestCase
{
    public void test() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(BooleanExpressionComplexityCheck.class);

        String[] expected = {
            "10:9: Boolean expression complexity is 3 (max allowed is 2).",
            "13:9: Boolean expression complexity is 4 (max allowed is 2).",
        };

        verify(checkConfig, getPath("metrics" + File.separator + "BooleanExpressionComplexityCheckTestInput.java"), expected);
    }
}
