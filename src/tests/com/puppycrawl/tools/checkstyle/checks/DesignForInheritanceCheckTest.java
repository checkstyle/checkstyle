package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DesignForInheritanceCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DesignForInheritanceCheck.class);
        final String[] expected = {
            "46:5: Method 'doh' is not designed for inheritance - needs to be abstract, final or empty.",
        };
        verify(checkConfig, getPath("InputDesignForInheritance.java"), expected);

    }

}
