////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.BundleCache;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
        final String output = errorStream.toString(StandardCharsets.UTF_8);
        final Object addExceptionMessage = getConstructor()
            .newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, new String[] {"myfile"});
        final Object returnValue = getMessageMethod().invoke(addExceptionMessage);
        assertWithMessage("Invalid exception")
                .that(output)
                .contains(returnValue.toString());
        assertWithMessage("Invalid exception class")
                .that(output)
                .contains("java.lang.IllegalStateException: upsss");
    }

    @Test
    public void testCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = infoStream.toString();
        assertWithMessage("Message should contain exception info")
                .that(output)
                .contains("java.lang.IllegalStateException: upsss");
    }

    @Test
    public void testCtorWithNullParameter() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000));
        final String output = infoStream.toString();
        assertWithMessage("Message should contain exception info")
                .that(output)
                .contains("java.lang.IllegalStateException: upsss");
    }

    @Test
    public void testNewCtorWithTwoParameters() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.NONE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        assertWithMessage("Message should contain exception info")
                .that(infoStream.toString())
                .contains("java.lang.IllegalStateException: upsss");
    }

    @Test
    public void testNullInfoStreamOptions() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new DefaultLogger(outputStream, null),
                "IllegalArgumentException expected");
        assertWithMessage("Invalid error message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Parameter infoStreamOptions can not be null");
    }

    @Test
    public void testNullErrorStreamOptions() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> {
                    final DefaultLogger defaultLogger = new DefaultLogger(outputStream,
                            OutputStreamOptions.CLOSE, outputStream, null);

                    // Workaround for Eclipse error "The allocated object is never used"
                    assertWithMessage("defaultLogger should be non-null")
                            .that(defaultLogger)
                            .isNotNull();
                },
                "IllegalArgumentException expected");
        assertWithMessage("Invalid error message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Parameter errorStreamOptions can not be null");
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
        final String message =
            messageMethod.invoke(getAuditStartMessage()) + System.lineSeparator()
                + messageMethod.invoke(getAuditFinishMessage()) + System.lineSeparator();

        assertWithMessage("expected output")
            .that(infoStream.toString())
            .isEqualTo(message);
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

        final String message =
            messageMethod.invoke(getAuditStartMessage()) + System.lineSeparator()
                + messageMethod.invoke(getAuditFinishMessage()) + System.lineSeparator();

        assertWithMessage("expected output")
            .that(infoStream.toString())
            .isEqualTo(message);

        assertWithMessage("expected output")
            .that(infoStream.toString())
            .isEqualTo("[ERROR] fileName:1:2: customViolation [moduleId]"
                           + System.lineSeparator());
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(
            infoStream, AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.auditFinished(null);
        assertWithMessage("instance should not be null")
            .that(dl)
            .isNotNull();
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
        assertWithMessage("Invalid bundle cache size")
            .that(bundleCache)
            .hasSize(1);
    }

    @Test
    public void testNullArgs() throws Exception {
        final Constructor<?> cons = getConstructor();
        final Method messageMethod = getMessageMethod();
        final Object messageMyFile = cons.newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE,
                                                      new String[] {"myfile"});

        final String output = (String) messageMethod.invoke(messageMyFile);
        final String messageText = AbstractModuleTestSupport
            .getCheckMessage(DefaultLogger.class, DefaultLogger.ADD_EXCEPTION_MESSAGE, "myfile");

        assertWithMessage("Violation should contain exception info, but was ")
                .that(output)
                .contains(messageText);

        final Object messageWithoutFile = cons.newInstance(DefaultLogger.ADD_EXCEPTION_MESSAGE, null);
        final String outputForNullArgs = (String) messageMethod.invoke(messageWithoutFile);
        final String messageTextForNull = AbstractModuleTestSupport
            .getCheckMessage(DefaultLogger.class, DefaultLogger.ADD_EXCEPTION_MESSAGE);
        assertWithMessage("\"Violation should contain exception info, but was")
                .that(outputForNullArgs)
                .contains(messageTextForNull);
    }

    @Test
    public void testNewCtor() throws Exception {
        final ResourceBundle bundle = ResourceBundle.getBundle(
                Definitions.CHECKSTYLE_BUNDLE, Locale.ROOT);
        final String auditStartedMessage = bundle.getString(DefaultLogger.AUDIT_STARTED_MESSAGE);
        final String auditFinishedMessage = bundle.getString(DefaultLogger.AUDIT_FINISHED_MESSAGE);
        final String addExceptionMessage = new MessageFormat(bundle.getString(
                DefaultLogger.ADD_EXCEPTION_MESSAGE), Locale.ROOT).format(new String[] {"myfile"});
        final String infoOutput;
        final String errorOutput;
        try (MockByteArrayOutputStream infoStream = new MockByteArrayOutputStream()) {
            try (MockByteArrayOutputStream errorStream = new MockByteArrayOutputStream()) {
                final DefaultLogger dl = new DefaultLogger(
                        infoStream, OutputStreamOptions.CLOSE,
                        errorStream, OutputStreamOptions.CLOSE);
                dl.auditStarted(null);
                dl.addException(new AuditEvent(5000, "myfile"),
                        new IllegalStateException("upsss"));
                dl.auditFinished(new AuditEvent(6000, "myfile"));
                infoOutput = infoStream.toString(StandardCharsets.UTF_8);
                errorOutput = errorStream.toString(StandardCharsets.UTF_8);

                assertWithMessage("Info stream should be closed")
                        .that(infoStream.closedCount)
                        .isGreaterThan(0);
                assertWithMessage("Error stream should be closed")
                        .that(errorStream.closedCount)
                        .isGreaterThan(0);
            }
        }
        assertWithMessage("Violation should contain message `audit started`")
                .that(infoOutput)
                .contains(auditStartedMessage);
        assertWithMessage("Violation should contain message `audit finished`")
                .that(infoOutput)
                .contains(auditFinishedMessage);
        assertWithMessage("Violation should contain exception info")
                .that(errorOutput)
                .contains(addExceptionMessage);
        assertWithMessage("Violation should contain exception message")
                .that(errorOutput)
                .contains("java.lang.IllegalStateException: upsss");
    }

    private static Class<?> getLocalizedMessageClass() throws Exception {
        return DefaultLogger.class.getDeclaredClasses()[0];
    }

    private static Constructor<?> getConstructor() throws Exception {
        final Constructor<?> cons = getLocalizedMessageClass()
            .getDeclaredConstructor(String.class, String[].class);
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
        return TestUtil.getInternalState(BundleCache.class, "BUNDLE_CACHE");
    }

    private static class MockByteArrayOutputStream extends ByteArrayOutputStream {

        private int closedCount;

        @Override
        public void close() throws IOException {
            super.close();
            ++closedCount;
        }

    }

}
