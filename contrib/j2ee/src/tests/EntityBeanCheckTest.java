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
            "13:14: Entity bean must have a public constructor with no parameters.",
            "75:16: Entity bean must be public.",
            "75:16: Entity bean must not have modifier abstract.",
            "83:13: Entity bean must be public.",
            "83:13: Entity bean must not have modifier final.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
}
