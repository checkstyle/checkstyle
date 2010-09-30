package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class IllegalCatchCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);

        String[] expected = {
            "6:11: Catching 'RuntimeException' is not allowed.",
            "7:11: Catching 'Exception' is not allowed.",
            "8:11: Catching 'Throwable' is not allowed.",
            "14:11: Catching 'java.lang.RuntimeException' is not allowed.",
            "15:11: Catching 'java.lang.Exception' is not allowed.",
            "16:11: Catching 'java.lang.Throwable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalCatchCheck.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(IllegalCatchCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, java.lang.Throwable");

        String[] expected = {
            "7:11: Catching 'Exception' is not allowed.",
            "8:11: Catching 'Throwable' is not allowed.",
            "15:11: Catching 'java.lang.Exception' is not allowed.",
            "16:11: Catching 'java.lang.Throwable' is not allowed.",
        };

        verify(checkConfig, getPath("coding" + File.separator + "InputIllegalCatchCheck.java"), expected);
    }
}
