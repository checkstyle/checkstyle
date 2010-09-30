package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class SimplifyBooleanReturnCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SimplifyBooleanReturnCheck.class);
        final String[] expected = {
            "20:9: Conditional logic can be removed.",
            "33:9: Conditional logic can be removed.",
        };
        verify(checkConfig, getPath("InputSimplifyBoolean.java"), expected);
    }
}
