import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanEjbPostCreateCheck;

public class EntityBeanEjbPostCreateCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanEjbPostCreateCheck.class);
        final String[] expected = {
            "99:32: Method ejbPostCreate must be public.",
            "99:32: Method ejbPostCreate must be void.",
            "99:32: Method ejbPostCreate must not have modifier final.",
            "99:32: Method ejbPostCreate must not have modifier static.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
