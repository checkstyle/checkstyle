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
            "11:18: Home interface 'InputLocalHomeInterface' must have method 'findByPrimaryKey()'.",
            "17:19: Method 'createSomething' must be non-void.",
            "17:19: Method 'createSomething' must throw 'javax.ejb.CreateException'.",
            "19:19: Method 'findSomething' must be non-void.",
            "19:19: Method 'findSomething' must throw 'javax.ejb.FinderException'.",
            "24:17: Method 'method' must not throw 'java.rmi.RemoteException'.",
        };
        verify(checkConfig, getPath("j2ee/InputLocalHomeInterface.java"), expected);
    }
    
    public void testDuplicateFind() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalHomeInterfaceCheck.class);
        final String[] expected =
            {
                "17:19: Home interface can have only one 'findByPrimaryKey()' method.",
                "17:19: Method 'findByPrimaryKey' must have 1 parameter(s).",
                };
        verify(
            checkConfig,
            getPath("j2ee/InputLocalHomeInterfaceDuplicateFind.java"),
            expected);
    }

    public void testNoFind() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalHomeInterfaceCheck.class);
        final String[] expected =
            {
                "14:19: Method 'findByPrimaryKey' must have 1 parameter(s).",
                "17:19: Home interface can have only one 'findByPrimaryKey()' method.",
                "17:19: Method 'findByPrimaryKey' must have 1 parameter(s).",
                };
        verify(
            checkConfig,
            getPath("j2ee/InputLocalHomeInterfaceNoFind.java"),
            expected);
    }
}
