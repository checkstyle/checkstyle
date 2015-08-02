////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;

import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * Tests to ensure that default messagebundle is determined correctly.
 *
 * @author lkuehne
 */
public class AbstractViolationReporterTest extends BaseCheckTestSupport {
    private final Check emptyCheck = new Check() {
        @Override
        public int[] getDefaultTokens() {
            return new int[0];
        }
    };

    @Test
    public void testGetMessageBundleWithPackage() {
        assertEquals("com.mycompany.checks.messages",
            AbstractViolationReporter.getMessageBundle("com.mycompany.checks.MyCoolCheck"));
    }

    @Test
    public void testGetMessageBundleWithoutPackage() {
        assertEquals("messages",
            AbstractViolationReporter.getMessageBundle("MyCoolCheck"));
    }

    @Test
    public void testCustomId() throws Exception {
        emptyCheck.setId("MyId");
        assertEquals("MyId", emptyCheck.getId());
    }

    @Test
    public void testCustomMessage() throws Exception {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey");

        SortedSet<LocalizedMessage> messages = collector.getMessages();
        Assert.assertTrue(messages.size() == 1);
        assertEquals("This is a custom message.", messages.first()
                .getMessage());
    }

    @Test
    public void testCustomMessageWithParameters() throws Exception {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message with {0}.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey", "TestParam");

        SortedSet<LocalizedMessage> messages = collector.getMessages();
        Assert.assertTrue(messages.size() == 1);

        assertEquals("This is a custom message with TestParam.",
                messages.first().getMessage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCustomMessageWithParametersNegative() throws Exception {
        DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message {0.");
        emptyCheck.configure(config);

        LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey", "TestParam");

        SortedSet<LocalizedMessage> messages = collector.getMessages();
        Assert.assertTrue(messages.size() == 1);

        //we expect an exception here because of the bogus custom message
        //format
        messages.first().getMessage();
    }
}
