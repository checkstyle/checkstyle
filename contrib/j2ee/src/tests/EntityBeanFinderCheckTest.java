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
            "95:33: Method ejbFindSomething has illegal modifier final.",
            "95:33: Method ejbFindSomething has illegal modifier static.",
            "95:33: Method ejbFindSomething is not public.",
            "95:33: Void ejbFindSomething method.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
