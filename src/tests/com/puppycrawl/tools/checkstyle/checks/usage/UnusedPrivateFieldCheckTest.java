package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class UnusedPrivateFieldCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnusedPrivateFieldCheck.class);
        final String[] expected = {
            "10:17: Unused field 'mUnreadPrimitive'.",
            "16:19: Unused field 'mUnreadArray'.",
            "20:17: Unused field 'mUnused2'.",
            "22:30: Unused field 'SUNUSED'.",
            "64:17: Unused field 'mUnused'.",           
        };
        verify(checkConfig, getPath("usage/InputUnusedField.java"), expected);
    }
    
    public void testIgnoreFormat() throws Exception
        {
            final DefaultConfiguration checkConfig =
                createCheckConfig(UnusedPrivateFieldCheck.class);
            checkConfig.addAttribute("ignoreFormat", "Array$");
            final String[] expected = {
                "10:17: Unused field 'mUnreadPrimitive'.",
                "20:17: Unused field 'mUnused2'.",
                "22:30: Unused field 'SUNUSED'.",
                "64:17: Unused field 'mUnused'.",
            };
            verify(checkConfig, getPath("usage/InputUnusedField.java"), expected);
        }
}
