package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.LocalInterfaceCheck;

public class LocalInterfaceCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalInterfaceCheck.class);
        final String[] expected = {
            "15:17: Method 'invalid1' must not throw 'java.rmi.RemoteException'.",
            "18:17: Method 'invalid2' must not throw 'java.rmi.RemoteException'.",
        };
        verify(checkConfig, getPath("j2ee/InputLocalInterface.java"), expected);
    }
}
