package com.puppycrawl.tools.checkstyle.checks.j2ee;
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
            "12:14: Message bean 'InputMessageBean' must have method 'ejbCreate()'.",
            "41:16: Message bean 'AbstractMessageBean' must be public.",
            "41:16: Message bean 'AbstractMessageBean' must not have modifier 'abstract'.",
            "53:13: Message bean 'FinalMessageBean' must be public.",
            "53:13: Message bean 'FinalMessageBean' must not have modifier 'final'.",
            "67:32: Method 'ejbCreate' must be public.",
            "67:32: Method 'ejbCreate' must be void.",
            "67:32: Method 'ejbCreate' must have 0 parameter(s).",
            "67:32: Method 'ejbCreate' must not have modifier 'final'.",
            "67:32: Method 'ejbCreate' must not have modifier 'static'.",
        };
        verify(checkConfig, getPath("j2ee/InputMessageBean.java"), expected);
    }
}
