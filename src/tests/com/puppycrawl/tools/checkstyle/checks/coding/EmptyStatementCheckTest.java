package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;


public class EmptyStatementCheckTest
    extends BaseCheckTestCase
{
    public void testEmptyStatements()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyStatementCheck.class);
        final String[] expected = {
           "12:7: Empty statement.",
           "17:7: Empty statement.",
           "22:19: Empty statement.",
           "26:10: Empty statement.",
           "29:16: Empty statement.",
           "33:10: Empty statement.",
           "43:10: Empty statement.",
           "49:13: Empty statement.",
           "51:13: Empty statement.",
           "54:19: Empty statement.",
           "58:10: Empty statement.",
           "61:9: Empty statement.",
           "66:10: Empty statement.",
           "72:10: Empty statement.",
           "76:10: Empty statement.",
           "80:10: Empty statement.",
        };

        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }
}
