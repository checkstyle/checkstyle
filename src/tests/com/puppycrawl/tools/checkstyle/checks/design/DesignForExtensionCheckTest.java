package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DesignForExtensionCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DesignForExtensionCheck.class);
        final String[] expected = {
            "46:5: Method 'doh' is not designed for extension - needs to be abstract, final or empty.",
            "54:5: Method 'aNativeMethod' is not designed for extension - needs to be abstract, final or empty.",
        };
        verify(checkConfig, getPath("InputDesignForExtension.java"), expected);

    }

}
