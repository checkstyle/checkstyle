package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisReturnCheck;

public class ThisReturnCheckTest extends BaseCheckTestCase
{
    public void testEntityBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "24:16: Do not return 'this'.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testMessageBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "64:16: Do not return 'this'.",
        };
        verify(checkConfig, getPath("j2ee/InputMessageBean.java"), expected);
    }
    
    public void testSessionBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "77:16: Do not return 'this'.",
        };
        verify(checkConfig, getPath("j2ee/InputSessionBean.java"), expected);
    }
}
