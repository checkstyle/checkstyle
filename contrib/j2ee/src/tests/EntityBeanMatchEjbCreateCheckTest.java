import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanMatchEjbCreateCheck;

public class EntityBeanMatchEjbCreateCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EntityBeanMatchEjbCreateCheck.class);
        final String[] expected = {
            "17:42: Method ejbCreate must have a matching ejbPostCreate method.",
            "22:42: Method ejbCreate must have a matching ejbPostCreate method.",
            "31:42: Method ejbCreateThing must have a matching ejbPostCreate method.",
            "49:42: Method ejbCreateInteger must have a matching ejbPostCreate method.",
        };
        verify(checkConfig, getPath("InputEntityBeanMatchEjbCreate.java"), expected);
    }
}
