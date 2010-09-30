package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class CovariantEqualsCheckTest
    extends BaseCheckTestSupport
{
    @Test
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

