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
            "12:14: Session bean must have a public constructor with no parameters.",
            "12:14: Session bean must have method ejbCreate.",
            "53:16: Session bean must be public.",
            "53:16: Session bean must have a public constructor with no parameters.",
            "53:16: Session bean must have method ejbCreate.",
            "53:16: Session bean must not have modifier abstract.",
            "58:13: Session bean must be public.",
            "58:13: Session bean must not have modifier final.",
        };
        verify(checkConfig, getPath("InputSessionBean.java"), expected);
    }
}
