import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanFinderCheck;

public class EntityBeanFinderCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanFinderCheck.class);
        final String[] expected = {
            "95:33: Method ejbFindSomething must be non-void.",
            "95:33: Method ejbFindSomething must be public.",
            "95:33: Method ejbFindSomething must not have modifier final.",
            "95:33: Method ejbFindSomething must not have modifier static.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
