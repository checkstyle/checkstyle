import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisReturnCheck;

public class ThisReturnCheckTest extends BaseCheckTestCase
{
    public void testEntityBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "118:17: Do not return 'this'.",
        };
        verify(checkConfig, getPath("InputEntityBean.java"), expected);
    }
    
    public void testMessageBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "57:16: Do not return 'this'.",
        };
        verify(checkConfig, getPath("InputMessageBean.java"), expected);
    }
    
    public void testSessionBean()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ThisReturnCheck.class);
        final String[] expected = {
            "78:16: Do not return 'this'.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
