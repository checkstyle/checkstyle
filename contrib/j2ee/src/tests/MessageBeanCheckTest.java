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
            "12:14: Message bean must have a public constructor with no parameters.",
            "12:14: Message bean must have method ejbCreate.",
            "41:16: Message bean must be public.",
            "41:16: Message bean must have a public constructor with no parameters.",
            "41:16: Message bean must have method ejbCreate.",
            "41:16: Message bean must not have modifier abstract.",
            "46:13: Message bean must be public.",
            "46:13: Message bean must not have modifier final.",
        };
        verify(checkConfig, getPath("InputMessageBean.java"), expected);
    }
}
