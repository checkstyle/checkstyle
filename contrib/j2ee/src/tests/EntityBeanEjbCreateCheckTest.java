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
            "91:33: Method ejbCreate must be non-void.",
            "91:33: Method ejbCreate must be public.",
            "91:33: Method ejbCreate must not have modifier final.",
            "91:33: Method ejbCreate must not have modifier static.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
