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
            "13:13: Unread local variable 'mUnreadPrimitive'.",
            "24:16: Unread local variable 'unreadObject'.",
            "36:15: Unread local variable 'unreadArray'.",
        };
        verify(checkConfig, getPath("usage/InputUnusedLocal.java"), expected);
    }
    
    public void testIgnoreFormat() throws Exception
        {
            final DefaultConfiguration checkConfig =
                createCheckConfig(UnusedLocalVariableCheck.class);
            checkConfig.addAttribute("ignoreFormat", "Array$");
            final String[] expected = {
                "13:13: Unread local variable 'mUnreadPrimitive'.",
                "24:16: Unread local variable 'unreadObject'.",
            };
            verify(checkConfig, getPath("usage/InputUnusedLocal.java"), expected);
        }
}
