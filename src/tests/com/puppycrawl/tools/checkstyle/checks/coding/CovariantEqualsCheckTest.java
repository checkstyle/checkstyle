package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class CovariantEqualsCheckTest
    extends BaseCheckTestCase
{
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(CovariantEqualsCheck.class);
        final String[] expected = {
            "11:24: covariant equals without overriding equals(java.lang.Object).",
            "30:20: covariant equals without overriding equals(java.lang.Object).",
            "64:20: covariant equals without overriding equals(java.lang.Object).",
            "78:28: covariant equals without overriding equals(java.lang.Object).",
        };
        verify(checkConfig, getPath("InputCovariant.java"), expected);
    }
}

