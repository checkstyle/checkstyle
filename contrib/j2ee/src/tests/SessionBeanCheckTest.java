import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.SessionBeanCheck;

public class SessionBeanCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SessionBeanCheck.class);
        final String[] expected = {
            "12:14: Session bean does not have a public constructor with no parameters.",
            "12:14: Session bean has no ejbCreate method.",
            "58:13: Session bean has illegal modifier final.",
            "58:13: Session bean is not public.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
