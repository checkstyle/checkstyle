////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.BundleCache;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class DefaultLoggerTest {

    @AfterEach
    public void tearDown() throws Exception {
        BundleCache.clear();
    }

    @Test
    public void testCtor() throws Exception {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(
                infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString(StandardCharsets.UTF_8.name());
        final Object addExceptionMessage = getConstructor()
                .newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, new String[] {"myfile"});
        final Object returnValue = getMessageMethod().invoke(addExceptionMessage);
        assertTrue(output.contains(returnValue.toString()), "Invalid exception");
        assertTrue(output.contains("java.lang.IllegalStateException: upsss"), "Invalid exception class");
    }

    @Test
    public void testCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = infoStream.toString();
        assertTrue(output.contains("java.lang.IllegalStateException: upsss"),
                "Message should contain exception info, but was " + output);
    }

    @Test
    public void testCtorWithNullParameter() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000));
        final String output = infoStream.toString();
        assertTrue(output.contains("java.lang.IllegalStateException: upsss"),
                "Message should contain exception info, but was " + output);
    }

    @Test
    public void testNewCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.NONE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        assertTrue(infoStream.toString().contains("java.lang.IllegalStateException: upsss"),
                "Message should contain exception info, but was " + infoStream);
    }

    @Test
    public void testNullInfoStreamOptions() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            final DefaultLogger logger = new DefaultLogger(outputStream, null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertNotNull(logger, "Null instance");
            fail("Exception was expected");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Parameter infoStreamOptions can not be null",
                    exception.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testNullErrorStreamOptions() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            final DefaultLogger logger = new DefaultLogger(outputStream,
                AutomaticBean.OutputStreamOptions.CLOSE, outputStream, null);
            // assert required to calm down eclipse's 'The allocated object is never used' violation
            assertNotNull(logger, "Null instance");
            fail("Exception was expected");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Parameter errorStreamOptions can not be null",
                    exception.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testAddError() throws Exception {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final Method messageMethod = getMessageMethod();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE, errorStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, null, getClass(), "customViolation")));
        dl.auditFinished(null);
        final String message = messageMethod.invoke(getAuditStartMessage()) + System.lineSeparator()
                + messageMethod.invoke(getAuditFinishMessage()) + System.lineSeparator();
        assertEquals(message, infoStream.toString(), "expected output");
        assertEquals("[ERROR] fileName:1:2: customViolation [DefaultLoggerTest]" + System.lineSeparator(),
                errorStream.toString(), "expected output");
    }

    @Test
    public void testAddErrorModuleId() throws Exception {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final Method messageMethod = getMessageMethod();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, "moduleId", getClass(), "customViolation")));
        dl.auditFinished(null);
        final String message = messageMethod.invoke(getAuditStartMessage()) + System.lineSeparator()
                + messageMethod.invoke(getAuditFinishMessage()) + System.lineSeparator();
        assertEquals(message, infoStream.toString(), "expected output");
        assertEquals("[ERROR] fileName:1:2: customViolation [moduleId]" + System.lineSeparator(),
                errorStream.toString(), "expected output");
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.auditFinished(null);
        assertNotNull(dl, "instance should not be null");
    }

    @DefaultLocale("fr")
    @Test
    public void testClearBundleCache() throws Exception {
        assertEquals(Locale.getDefault(), Locale.FRENCH, "Locale not set correctly");

        final Object localizedMessage = getConstructor()
                .newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, new String[] {"FOO"});
        final String messageText = AbstractModuleTestSupport
                .getCheckMessage(DefaultLogger.class, DefaultLogger.ADD_EXCEPTION_MESSAGE, "FOO");
        assertEquals(messageText, getMessageMethod().invoke(localizedMessage), "Invalid message");

        final Map<String, ResourceBundle> bundleCache = getBundleCache();
        assertEquals(1, bundleCache.size(), "Invalid bundle cache size");
    }

    @Test
    public void testNullArgs() throws Exception {
        final Constructor<?> cons = getConstructor();
        final Method messageMethod = getMessageMethod();

        final Object messageMyFile = cons.newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, new String[] {"myfile"});
        final String output = (String) messageMethod.invoke(messageMyFile);
        final String messageText = AbstractModuleTestSupport
                .getCheckMessage(DefaultLogger.class, DefaultLogger.ADD_EXCEPTION_MESSAGE, "myfile");
        assertTrue(output.contains(messageText),
                "Violation should contain exception info, but was " + output);

        final Object messageWithoutFile = cons.newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, null);
        final String outputForNullArgs = (String) messageMethod.invoke(messageWithoutFile);
        final String messageTextForNull = AbstractModuleTestSupport
                .getCheckMessage(DefaultLogger.class, DefaultLogger.ADD_EXCEPTION_MESSAGE);
        assertTrue(outputForNullArgs.contains(messageTextForNull),
                "Violation should contain exception info, but was " + outputForNullArgs);
    }

    private static Class<?> getLocalizedMessageClass() throws Exception {
        return DefaultLogger.class.getDeclaredClasses()[0];
    }

    private static Constructor<?> getConstructor() throws Exception {
        final Constructor<?> cons = getLocalizedMessageClass().getDeclaredConstructor(String.class, String[].class);
        cons.setAccessible(true);
        return cons;
    }

    private static Object getAuditStartMessage() throws Exception {
        return getConstructor().newInstance(DefaultLogger.AUDIT_STARTED_MESSAGE, null);
    }

    private static Object getAuditFinishMessage() throws Exception {
        return getConstructor().newInstance(DefaultLogger.AUDIT_FINISHED_MESSAGE, null);
    }

    private static Method getMessageMethod() throws Exception {
        Method m = getLocalizedMessageClass().getDeclaredMethod("getMessage");
        m.setAccessible(true);
        return m;
    }

    private static Map<String, ResourceBundle> getBundleCache() throws Exception {
        return Whitebox.getInternalState(BundleCache.class, "BUNDLE_CACHE");
    }
}
