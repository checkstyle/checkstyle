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
            "18:19: Method createSomething does not throw javax.ejb.CreateException.",
            "18:19: Method createSomething is not public.",
            "18:19: Void createSomething method.",
            "20:19: Method findSomething does not throw javax.ejb.FinderException.",
            "20:19: Method findSomething is not public.",
            "20:19: Void findSomething method.",
            "25:17: Method method must not throw java.rmi.RemoteException.",
        };
        verify(checkConfig, getPath("InputLocalHomeInterface.java"), expected);
    }
}
