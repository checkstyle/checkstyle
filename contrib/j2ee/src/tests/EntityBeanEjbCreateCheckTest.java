import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanEjbCreateCheck;

public class EntityBeanEjbCreateCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanEjbCreateCheck.class);
        final String[] expected = {
            "91:33: Method ejbCreate has illegal modifier final.",
            "91:33: Method ejbCreate has illegal modifier static.",
            "91:33: Method ejbCreate is not public.",
            "91:33: Void ejbCreate method.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
