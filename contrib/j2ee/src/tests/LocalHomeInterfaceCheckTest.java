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
            "12:18: Home interface must have method findByPrimaryKey.",
            "18:19: Method createSomething must be non-void.",
            "18:19: Method createSomething must be public.",
            "18:19: Method createSomething must throw javax.ejb.CreateException.",
            "20:19: Method findSomething must be non-void.",
            "20:19: Method findSomething must be public.",
            "20:19: Method findSomething must throw javax.ejb.FinderException.",
            "25:17: Method method must not throw java.rmi.RemoteException.",
        };
        verify(checkConfig, getPath("InputLocalHomeInterface.java"), expected);
    }
}
