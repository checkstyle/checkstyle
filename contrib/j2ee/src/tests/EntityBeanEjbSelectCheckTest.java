import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanEjbSelectCheck;

public class EntityBeanEjbSelectCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanEjbSelectCheck.class);
        final String[] expected = {
            "110:33: Method ejbSelectSomething must be abstract.",
            "110:33: Method ejbSelectSomething must be public.",
           "110:33: Method ejbSelectSomething must throw javax.ejb.FinderException.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
