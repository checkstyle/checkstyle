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
            "69:32: Method ejbCreate has illegal modifier final.",
            "69:32: Method ejbCreate has illegal modifier static.",
            "69:32: Method ejbCreate is not public.",
            "69:32: Non-void ejbCreate method.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
