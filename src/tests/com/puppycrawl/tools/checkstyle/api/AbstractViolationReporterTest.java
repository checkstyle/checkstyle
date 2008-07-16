package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to ensure that default messagebundle is determined correctly.
 *
 * @author lkuehne
 */
public class AbstractViolationReporterTest extends BaseCheckTestSupport
{
    private final Check emptyCheck = new Check()
    {
        @Override
        public int[] getDefaultTokens()
        {
            return new int[0];
        }
    };

    @Test
    public void testGetMessageBundleWithPackage()
    {
        assertEquals("com.mycompany.checks.messages",
            emptyCheck.getMessageBundle("com.mycompany.checks.MyCoolCheck"));
    }

    @Test
    public void testGetMessageBundleWithoutPackage()
    {
        assertEquals("messages",
            emptyCheck.getMessageBundle("MyCoolCheck"));
    }

    @Test
    public void testCustomMessage() throws CheckstyleException
    {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey");

        LocalizedMessage[] messages = collector.getMessages();
        Assert.assertTrue(messages.length == 1);

        Assert.assertEquals("This is a custom message.", messages[0].getMessage());
    }

    @Test
    public void testCustomMessageWithParameters() throws CheckstyleException
    {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message with {0}.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey", "TestParam");

        LocalizedMessage[] messages = collector.getMessages();
        Assert.assertTrue(messages.length == 1);

        Assert.assertEquals("This is a custom message with TestParam.", messages[0].getMessage());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCustomMessageWithParametersNegative() throws CheckstyleException
    {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message {0.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey", "TestParam");

        LocalizedMessage[] messages = collector.getMessages();
        Assert.assertTrue(messages.length == 1);

        //we expect an exception here because of the bogus custom message
        //format
        messages[0].getMessage();
    }
}
