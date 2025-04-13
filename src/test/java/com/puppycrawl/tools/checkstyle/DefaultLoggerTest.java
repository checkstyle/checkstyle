///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class DefaultLoggerTest {

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    @AfterEach
    public void tearDown() {
        ResourceBundle.clearCache();
    }

    @Test
    public void testCtor() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString(StandardCharsets.UTF_8);
        final LocalizedMessage addExceptionMessage = getAddExceptionMessageClass("myfile");
        final String message = addExceptionMessage.getMessage();
        assertWithMessage("Invalid exception")
                .that(output)
                .contains(message);
        assertWithMessage("Invalid exception class")
                .that(output)
                .contains("java.lang.IllegalStateException: upsss");
    }

    /**
     * We keep this test for 100% coverage. Until #12873.
     */
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

    /**
     * We keep this test for 100% coverage. Until #12873.
     */
    @Test
    public void testCtorWithTwoParametersCloseStreamOptions() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        final boolean closeInfo = TestUtil.getInternalState(dl, "closeInfo");

        assertWithMessage("closeInfo should be true")
                .that(closeInfo)
                .isTrue();
    }

    /**
     * We keep this test for 100% coverage. Until #12873.
     */
    @Test
    public void testCtorWithTwoParametersNoneStreamOptions() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.NONE);
        final boolean closeInfo = TestUtil.getInternalState(dl, "closeInfo");

        assertWithMessage("closeInfo should be false")
                .that(closeInfo)
                .isFalse();
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
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.NONE);
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
                () -> new DefaultLogger(outputStream, (OutputStreamOptions) null),
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
    public void testAddError() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final String auditStartMessage = getAuditStartMessage();
        final String auditFinishMessage = getAuditFinishMessage();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                OutputStreamOptions.CLOSE, errorStream,
                OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, null, getClass(), "customViolation")));
        dl.auditFinished(null);
        assertWithMessage("expected output")
            .that(infoStream.toString())
            .isEqualTo(auditStartMessage
                        + System.lineSeparator()
                        + auditFinishMessage
                        + System.lineSeparator());
        assertWithMessage("expected output")
            .that(errorStream.toString())
            .isEqualTo("[ERROR] fileName:1:2: customViolation [DefaultLoggerTest]"
                + System.lineSeparator());
    }

    @Test
    public void testAddErrorModuleId() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final String auditFinishMessage = getAuditFinishMessage();
        final String auditStartMessage = getAuditStartMessage();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, "moduleId", getClass(), "customViolation")));
        dl.auditFinished(null);
        assertWithMessage("expected output")
            .that(infoStream.toString())
            .isEqualTo(auditStartMessage
                        + System.lineSeparator()
                        + auditFinishMessage
                        + System.lineSeparator());
        assertWithMessage("expected output")
            .that(errorStream.toString())
            .isEqualTo("[ERROR] fileName:1:2: customViolation [moduleId]"
                + System.lineSeparator());
    }

    @Test
    public void testAddErrorIgnoreSeverityLevel() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger defaultLogger = new DefaultLogger(
            infoStream, OutputStreamOptions.CLOSE,
            errorStream, OutputStreamOptions.CLOSE);
        defaultLogger.finishLocalSetup();
        defaultLogger.auditStarted(null);
        final Violation ignorableViolation = new Violation(1, 2, "bundle", "key",
                                                           null, SeverityLevel.IGNORE, null,
                                                           getClass(), "customViolation");
        defaultLogger.addError(new AuditEvent(this, "fileName", ignorableViolation));
        defaultLogger.auditFinished(null);
        assertWithMessage("No violation was expected")
            .that(errorStream.toString())
            .isEmpty();
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.auditFinished(null);
        assertWithMessage("instance should not be null")
            .that(dl)
            .isNotNull();
    }

    /**
     * Verifies that the language specified with the system property {@code user.language} exists.
     */
    @Test
    public void testLanguageIsValid() {
        final String language = DEFAULT_LOCALE.getLanguage();
        assumeFalse(language.isEmpty(), "Locale not set");
        assertWithMessage("Invalid language")
                .that(Arrays.asList(Locale.getISOLanguages()))
                .contains(language);
    }

    /**
     * Verifies that the country specified with the system property {@code user.country} exists.
     */
    @Test
    public void testCountryIsValid() {
        final String country = DEFAULT_LOCALE.getCountry();
        assumeFalse(country.isEmpty(), "Locale not set");
        assertWithMessage("Invalid country")
                .that(Arrays.asList(Locale.getISOCountries()))
                .contains(country);
    }

    @Test
    public void testNewCtor() throws Exception {
        final ResourceBundle bundle = ResourceBundle.getBundle(
                Definitions.CHECKSTYLE_BUNDLE, Locale.ENGLISH);
        final String auditStartedMessage = bundle.getString(DefaultLogger.AUDIT_STARTED_MESSAGE);
        final String auditFinishedMessage = bundle.getString(DefaultLogger.AUDIT_FINISHED_MESSAGE);
        final String addExceptionMessage = new MessageFormat(bundle.getString(
                DefaultLogger.ADD_EXCEPTION_MESSAGE), Locale.ENGLISH).format(new String[] {"myfile"}
        );
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

    @Test
    public void testStreamsNotClosedByLogger() throws IOException {
        try (MockByteArrayOutputStream infoStream = new MockByteArrayOutputStream();
             MockByteArrayOutputStream errorStream = new MockByteArrayOutputStream()) {
            final DefaultLogger defaultLogger = new DefaultLogger(
                infoStream, OutputStreamOptions.NONE,
                errorStream, OutputStreamOptions.NONE);
            defaultLogger.auditStarted(null);
            defaultLogger.auditFinished(null);
            assertWithMessage("Info stream should be open")
                .that(infoStream.closedCount)
                .isEqualTo(0);
            assertWithMessage("Error stream should be open")
                .that(errorStream.closedCount)
                .isEqualTo(0);
        }
    }

    private static LocalizedMessage getAuditStartMessageClass() {
        return new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.auditStarted");
    }

    private static LocalizedMessage getAuditFinishMessageClass() {
        return new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.auditFinished");
    }

    private static LocalizedMessage getAddExceptionMessageClass(Object... arguments) {
        return new LocalizedMessage(Definitions.CHECKSTYLE_BUNDLE,
                DefaultLogger.class, "DefaultLogger.addException", arguments);
    }

    private static String getAuditStartMessage() {
        final LocalizedMessage auditStartMessage = getAuditStartMessageClass();
        return auditStartMessage.getMessage();
    }

    private static String getAuditFinishMessage() {
        final LocalizedMessage auditFinishMessage = getAuditFinishMessageClass();
        return auditFinishMessage.getMessage();
    }

    private static final class MockByteArrayOutputStream extends ByteArrayOutputStream {

        private int closedCount;

        @Override
        public void close() throws IOException {
            super.close();
            ++closedCount;
        }

    }

}
