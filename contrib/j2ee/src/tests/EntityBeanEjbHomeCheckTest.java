import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanEjbHomeCheck;

public class EntityBeanEjbHomeCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanEjbHomeCheck.class);
        final String[] expected = {
            "104:32: Method ejbHomeMethod must be public.",
            "104:32: Method ejbHomeMethod must not have modifier final.",
            "104:32: Method ejbHomeMethod must not have modifier static.",
            "104:32: Method ejbHomeMethod must not throw java.rmi.RemoteException.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
