package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import java.io.File;

public class RequireThisCheckTest extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        final String[] expected = {
            "4:9: Reference to instance variable 'i' needs \"this.\".",
            "10:9: Method call to 'method1' needs \"this.\".",
            "11:9: Unable find where 'j' is declared.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }

    public void testMethodsOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkFields", "false");
        final String[] expected = {
            "10:9: Method call to 'method1' needs \"this.\".",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }

    public void testFieldsOnly() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RequireThisCheck.class);
        checkConfig.addAttribute("checkMethods", "false");
        final String[] expected = {
            "4:9: Reference to instance variable 'i' needs \"this.\".",
            "11:9: Unable find where 'j' is declared.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputRequireThis.java"),
               expected);
    }
}
