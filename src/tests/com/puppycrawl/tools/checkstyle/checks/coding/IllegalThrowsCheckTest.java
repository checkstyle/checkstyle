package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class IllegalThrowsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);

        String[] expected = {
            "9:51: Throwing 'RuntimeException' is not allowed.",
            "14:45: Throwing 'java.lang.RuntimeException' is not allowed.",
            "14:73: Throwing 'java.lang.Error' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalThrowsCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException");

        String[] expected = {
            "5:33: Throwing 'NullPointerException' is not allowed.",
            "14:73: Throwing 'java.lang.Error' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalThrowsCheck.java"), expected);
    }
}
