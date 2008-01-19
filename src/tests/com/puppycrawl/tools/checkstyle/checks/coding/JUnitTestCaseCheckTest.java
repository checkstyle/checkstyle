package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class JUnitTestCaseCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(JUnitTestCaseCheck.class);

        String[] expected = {
            "14:5: The method 'setUp' must be public or protected.",
            "15:5: The method 'tearDown' shouldn't be static.",
            "16:5: The method 'suite' must be declared static.",
            "21:5: The method 'SetUp' should be named 'setUp'.",
            "22:5: The method 'tearDown' must be declared with a void return type.",
            "23:5: The method 'suite' must be declared with a junit.framework.Test return type.",
            "28:5: The method 'setUp' must be declared with no parameters.",
            "30:5: The method 'suite' must be declared static.",
            "31:5: The method 'tearDown' must be declared with no parameters.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputJUnitTest.java"), expected);
    }
}
