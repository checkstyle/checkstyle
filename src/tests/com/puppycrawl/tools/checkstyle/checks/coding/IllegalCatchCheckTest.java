package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class IllegalCatchCheckTest extends BaseCheckTestCase
{
    public void test() throws Exception
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
}
