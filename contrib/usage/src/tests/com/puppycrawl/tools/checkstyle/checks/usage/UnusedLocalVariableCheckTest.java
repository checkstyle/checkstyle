package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UnusedLocalVariableCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedLocalVariableCheck.class);
        final String[] expected = {
            "13:13: Unused local variable 'mUnreadPrimitive'.",
            "24:16: Unused local variable 'unreadObject'.",
            "36:15: Unused local variable 'unreadArray'.",
            "45:13: Unused local variable 'java'.",
        };
        verify(checkConfig, getPath("usage/InputUnusedLocal.java"), expected);
    }
    
    public void testIgnoreFormat() throws Exception
        {
            final DefaultConfiguration checkConfig =
                createCheckConfig(UnusedLocalVariableCheck.class);
            checkConfig.addAttribute("ignoreFormat", "Array$");
            final String[] expected = {
                "13:13: Unused local variable 'mUnreadPrimitive'.",
                "24:16: Unused local variable 'unreadObject'.",
                "45:13: Unused local variable 'java'.",
            };
            verify(checkConfig, getPath("usage/InputUnusedLocal.java"), expected);
        }
}
