package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class StringLiteralEqualityCheckTest
        extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(StringLiteralEqualityCheck.class);
        final String[] expected = {
            "11:18: Literal Strings should be compared using equals(), not '=='.",
            "16:20: Literal Strings should be compared using equals(), not '=='.",
            "21:22: Literal Strings should be compared using equals(), not '=='.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputStringLiteralEquality.java"), expected);
    }
}

