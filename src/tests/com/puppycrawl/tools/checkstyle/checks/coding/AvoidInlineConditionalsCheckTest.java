package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AvoidInlineConditionalsCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidInlineConditionalsCheck.class);
        final String[] expected = {
            "97:29: Avoid inline conditionals.",
            "98:20: Avoid inline conditionals.",
            "150:34: Avoid inline conditionals.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
}
