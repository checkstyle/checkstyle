package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class DefaultComesLastCheckTest extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DefaultComesLastCheck.class);
        final String[] expected = {
            "22:9: Default should be last label in the switch."
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputDefaultComesLast.java"),
               expected);
    }
}
