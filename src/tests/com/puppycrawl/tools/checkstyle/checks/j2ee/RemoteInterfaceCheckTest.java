package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.RemoteInterfaceCheck;

public class RemoteInterfaceCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RemoteInterfaceCheck.class);
        final String[] expected = {
            "21:17: Method 'invalid' must throw 'java.rmi.RemoteException'.",
        };
        verify(checkConfig, getPath("j2ee/InputRemoteInterface.java"), expected);
    }
}
