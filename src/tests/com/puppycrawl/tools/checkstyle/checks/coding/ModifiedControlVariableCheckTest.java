package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ModifiedControlVariableCheckTest
    extends BaseCheckTestCase
{
    public void testModifiedControlVariable() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifiedControlVariableCheck.class);
        final String[] expected = {
            "14:14: Control variable 'i' is modified.",
            "17:15: Control variable 'i' is modified.",
            "20:37: Control variable 'i' is modified.",
            "21:17: Control variable 'i' is modified.",
            "25:14: Control variable 'j' is modified.",
            "49:15: Control variable 's' is modified.",
        };
        verify(checkConfig, getPath("coding/InputModifiedControl.java"), expected);
    }
}
