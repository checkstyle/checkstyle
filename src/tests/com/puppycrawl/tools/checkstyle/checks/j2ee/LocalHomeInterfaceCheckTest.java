package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.LocalHomeInterfaceCheck;

public class LocalHomeInterfaceCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalHomeInterfaceCheck.class);
        final String[] expected = {
            "17:19: Method 'createSomething' must be non-void.",
            "17:19: Method 'createSomething' must throw 'javax.ejb.CreateException'.",
            "24:17: Method 'method' must not throw 'java.rmi.RemoteException'.",
        };
        verify(checkConfig, getPath("j2ee/InputLocalHomeInterface.java"), expected);
    }
}
