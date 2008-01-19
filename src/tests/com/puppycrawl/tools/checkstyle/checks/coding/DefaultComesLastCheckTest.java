package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class DefaultComesLastCheckTest extends BaseCheckTestSupport
{
    @Test
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
