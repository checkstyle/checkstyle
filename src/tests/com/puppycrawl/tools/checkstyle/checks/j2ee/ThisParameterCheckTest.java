package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisParameterCheck;

public class ThisParameterCheckTest extends BaseCheckTestCase
{
    public void testEntityBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "23:20: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("j2ee/InputEntityBean.java"), expected);
    }
    
    public void testMessageBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "63:19: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("j2ee/InputMessageBean.java"), expected);
    }
    
    public void testSessionBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "76:19: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("j2ee/InputSessionBean.java"), expected);
    }
}
