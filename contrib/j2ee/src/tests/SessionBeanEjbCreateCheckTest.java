import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.SessionBeanEjbCreateCheck;

public class SessionBeanEjbCreateCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SessionBeanEjbCreateCheck.class);
        final String[] expected = {
            "69:32: Method ejbCreate must be public.",
            "69:32: Method ejbCreate must be void.",
            "69:32: Method ejbCreate must not have modifier final.",
            "69:32: Method ejbCreate must not have modifier static.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
