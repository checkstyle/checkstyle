////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.SortedSet;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Tests to ensure that default message bundle is determined correctly.
 *
 * @author lkuehne
 */
public class AbstractViolationReporterTest extends BaseCheckTestSupport {
    private final AbstractCheck emptyCheck = new EmptyCheck();

    private static Method getGetMessageBundleMethod() throws Exception {
        final Class<AbstractViolationReporter> abstractViolationReporterClass =
            AbstractViolationReporter.class;
        final Method getMessageBundleMethod =
            abstractViolationReporterClass.getDeclaredMethod("getMessageBundle", String.class);
        getMessageBundleMethod.setAccessible(true);
        return getMessageBundleMethod;
    }

    @Test
    public void testGetMessageBundleWithPackage() throws Exception {
        assertEquals("com.mycompany.checks.messages",
            getGetMessageBundleMethod().invoke(null, "com.mycompany.checks.MyCoolCheck"));
    }

    @Test
    public void testGetMessageBundleWithoutPackage() throws Exception {
        assertEquals("messages",
            getGetMessageBundleMethod().invoke(null, "MyCoolCheck"));
    }

    @Test
    public void testCustomId() {
        emptyCheck.setId("MyId");
        assertEquals("MyId", emptyCheck.getId());
    }

    @Test
    public void testCustomMessage() throws Exception {
        final DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message.");
        emptyCheck.configure(config);

        final LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey");

        final SortedSet<LocalizedMessage> messages = collector.getMessages();
        assertEquals(1, messages.size());
        assertEquals("This is a custom message.", messages.first()
                .getMessage());
    }

    @Test
    public void testCustomMessageWithParameters() throws Exception {
        final DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message with {0}.");
        emptyCheck.configure(config);

        final LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        emptyCheck.log(0, "msgKey", "TestParam");

        final SortedSet<LocalizedMessage> messages = collector.getMessages();
        assertEquals(1, messages.size());

        assertEquals("This is a custom message with TestParam.",
                messages.first().getMessage());
    }

    @Test
    public void testCustomMessageWithParametersNegative() throws Exception {
        final DefaultConfiguration config = createCheckConfig(emptyCheck.getClass());
        config.addMessage("msgKey", "This is a custom message {0.");
        emptyCheck.configure(config);

        final LocalizedMessages collector = new LocalizedMessages();
        emptyCheck.setMessages(collector);

        try {
            emptyCheck.log(0, "msgKey", "TestParam");
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unmatched braces in the pattern.", ex.getMessage());
        }
    }

    private static class EmptyCheck extends AbstractCheck {
        @Override
        public int[] getDefaultTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getAcceptableTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }

        @Override
        public int[] getRequiredTokens() {
            return CommonUtils.EMPTY_INT_ARRAY;
        }
    }
}
