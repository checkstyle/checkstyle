package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MissingSwitchDefaultCheckTest
    extends BaseCheckTestCase
{
    private DefaultConfiguration mCheckConfig;

    public void setUp()
    {
        mCheckConfig = createCheckConfig(MissingSwitchDefaultCheck.class);
    }

    public void testMissingSwitchDefault() throws Exception
    {
        final String[] expected = {
            "15: switch without \"default\" clause.",
        };
        verify(
            mCheckConfig,
            getPath("InputMissingSwitchDefault.java"),
            expected);
    }
}
