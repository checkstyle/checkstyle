import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.HomeInterfaceCheck;

public class HomeInterfaceCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(HomeInterfaceCheck.class);
        final String[] expected = {
            "20:19: Method createSomething does not throw java.rmi.RemoteException.",
            "20:19: Method createSomething does not throw javax.ejb.CreateException.",
            "20:19: Method createSomething is not public.",
            "20:19: Void createSomething method.",
            "22:19: Method findSomething does not throw java.rmi.RemoteException.",
            "22:19: Method findSomething does not throw javax.ejb.FinderException.",
            "22:19: Method findSomething is not public.",
            "22:19: Void findSomething method.",
        };
        verify(checkConfig, getPath("InputHomeInterface.java"), expected);
    }
}
