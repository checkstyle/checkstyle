package com.puppycrawl.tools.checkstyle.checks.j2ee;
import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.RemoteHomeInterfaceCheck;

public class HomeInterfaceCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RemoteHomeInterfaceCheck.class);
        final String[] expected = {
            "14:18: Home interface must have method findByPrimaryKey.",
            "20:19: Method createSomething must be non-void.",
            "20:19: Method createSomething must be public.",
            "20:19: Method createSomething must throw java.rmi.RemoteException.",
            "20:19: Method createSomething must throw javax.ejb.CreateException.",
            "22:19: Method findSomething must be non-void.",
            "22:19: Method findSomething must be public.",
            "22:19: Method findSomething must throw java.rmi.RemoteException.",
            "22:19: Method findSomething must throw javax.ejb.FinderException.",
        };
        verify(checkConfig, getPath("j2ee/InputHomeInterface.java"), expected);
    }
}
