import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.j2ee.MessageBeanCheck;

public class MessageBeanCheckTest extends BaseCheckTestCase
{
    public void testDefault()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MessageBeanCheck.class);
        final String[] expected = {
            "12:14: Message bean does not have a public constructor with no parameters.",
            "12:14: Message bean has no ejbCreate method.",
            "46:13: Message bean has illegal modifier final.",
            "46:13: Message bean is not public.",
        };
        verify(checkConfig, getPath("InputMessageBean.java"), expected);
    }
}
