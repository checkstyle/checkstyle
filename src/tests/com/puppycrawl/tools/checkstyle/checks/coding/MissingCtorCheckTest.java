package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class MissingCtorCheckTest extends BaseCheckTestSupport
{
    @Test
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
