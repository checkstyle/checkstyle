import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanCheck;

public class EntityBeanCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanCheck.class);
        final String[] expected = {
            "13:14: Entity bean does not have a public constructor with no parameters.",
            "83:13: Entity bean has illegal modifier final.",
            "83:13: Entity bean is not public.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
