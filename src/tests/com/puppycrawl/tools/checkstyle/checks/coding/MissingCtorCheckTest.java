package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class MissingCtorCheckTest extends BaseCheckTestCase
{
    public void testMissingSwitchDefault() throws Exception
    {
        DefaultConfiguration checkConfig = 
            createCheckConfig(MissingCtorCheck.class);

        final String[] expected = {
            "1:1: Class should define a constructor.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMissingCtor.java"),
               expected);
    }
}
