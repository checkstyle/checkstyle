package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * @author lkuehne
 */
public class DoubleCheckedLockingCheckTest
    extends BaseCheckTestCase
{
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DoubleCheckedLockingCheck.class);
        final String[] expected = {
            "34:17: The double-checked locking idiom is broken and should be avoided.",
        };
        verify(checkConfig, getPath("InputDoubleCheckedLocking.java"), expected);

    }
}
