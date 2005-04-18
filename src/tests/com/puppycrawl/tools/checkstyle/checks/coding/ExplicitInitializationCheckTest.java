package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class ExplicitInitializationCheckTest extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExplicitInitializationCheck.class);
        final String[] expected = {
            "2:17: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "3:20: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "7:18: Variable 'y4' explicitly initialized to '0' (default value for its type).",
            "8:21: Variable 'b1' explicitly initialized to 'false' (default value for its type).",
            "12:22: Variable 'str1' explicitly initialized to 'null' (default value for its type).",
            "12:35: Variable 'str3' explicitly initialized to 'null' (default value for its type).",
            "13:9: Variable 'ar1' explicitly initialized to 'null' (default value for its type).",
            "16:11: Variable 'f1' explicitly initialized to '0' (default value for its type).",
            "17:12: Variable 'd1' explicitly initialized to '0' (default value for its type).",
            "20:17: Variable 'ch1' explicitly initialized to '\\0' (default value for its type).",
            "21:17: Variable 'ch2' explicitly initialized to '\\0' (default value for its type).",
            "37:25: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "38:27: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
            "45:21: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "46:29: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "47:31: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
            "50:17: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "51:25: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "52:27: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
        };
        verify(checkConfig, 
               getPath("coding" + File.separator + "InputExplicitInit.java"),
               expected);
    }
}
