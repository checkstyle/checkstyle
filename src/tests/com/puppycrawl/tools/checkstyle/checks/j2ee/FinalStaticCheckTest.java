package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.FinalStaticCheck;

public class FinalStaticCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalStaticCheck.class);
        final String[] expected = {
            "27:16: Static field 'sInt' should be final.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
}
