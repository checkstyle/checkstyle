package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class MutableExceptionCheckTest extends BaseCheckTestCase {
    public void test() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(MutableExceptionCheck.class);

        String[] expected = {
            "6:9: The field '_errorCode' must be declared final.",
            "23:9: The field '_errorCode' must be declared final.",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputMutableException.java"), expected);
    }
}
