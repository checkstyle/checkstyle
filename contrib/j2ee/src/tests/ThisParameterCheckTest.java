import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisParameterCheck;

public class ThisParameterCheckTest extends BaseCheckTestCase
{
    public void testEntityBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "117:20: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
    
    public void testMessageBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "56:19: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("InputMessageBean.java"), expected);
    }
    
    public void testSessionBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisParameterCheck.class);
        final String[] expected = {
            "77:19: Do not pass 'this' as a parameter.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
