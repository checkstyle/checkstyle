package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class MissingSwitchDefaultCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(MissingSwitchDefaultCheck.class);
    }

    @Test
    public void testMissingSwitchDefault() throws Exception
    {
        final String[] expected = {
            "15:9: switch without \"default\" clause.",
        };
        verify(
            mCheckConfig,
            getPath("InputMissingSwitchDefault.java"),
            expected);
    }
}
