package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class MultipleVariableDeclarationsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expected = {
            "3:5: Each variable declaration must be in its own statement.",
            "4:5: Only one variable definition per line allowed.",
            "7:9: Each variable declaration must be in its own statement.",
            "8:9: Only one variable definition per line allowed.",
            "12:5: Only one variable definition per line allowed.",
            "15:5: Only one variable definition per line allowed.",
            "20:14: Each variable declaration must be in its own statement.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputMultipleVariableDeclarations.java"),
               expected);
    }
}
