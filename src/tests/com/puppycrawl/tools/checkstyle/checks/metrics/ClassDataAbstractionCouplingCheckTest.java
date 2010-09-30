package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ClassDataAbstractionCouplingCheckTest extends BaseCheckTestSupport
{
    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
            "6:1: Class Data Abstraction Coupling is 4 (max allowed is 0) classes [AnotherInnerClass, HashMap, HashSet, InnerClass].",
            "7:5: Class Data Abstraction Coupling is 1 (max allowed is 0) classes [ArrayList].",
            "27:1: Class Data Abstraction Coupling is 2 (max allowed is 0) classes [HashMap, HashSet].",
        };

        verify(checkConfig,
               getPath("metrics" + File.separator + "ClassCouplingCheckTestInput.java"),
               expected);
    }
}
