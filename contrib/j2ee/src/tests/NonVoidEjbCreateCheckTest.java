import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.NonVoidEjbCreateCheck;

public class NonVoidEjbCreateCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NonVoidEjbCreateCheck.class);
        final String[] expected = {
            "69:16: Non-void ejbCreate() method.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
