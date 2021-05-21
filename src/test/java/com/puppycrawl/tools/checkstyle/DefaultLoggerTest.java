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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
import com.puppycrawl.tools.checkstyle.api.Violation;

public class DefaultLoggerTest {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private final DefaultLogger.LocalizedMessage auditStartMessage =
            new DefaultLogger.LocalizedMessage("DefaultLogger.auditStarted", null);

    private final DefaultLogger.LocalizedMessage auditFinishMessage =
            new DefaultLogger.LocalizedMessage("DefaultLogger.auditFinished", null);

    @Test
    public void testCtor() throws UnsupportedEncodingException {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.addException(new AuditEvent(5000, "myfile"), new IllegalStateException("upsss"));
        dl.auditFinished(new AuditEvent(6000, "myfile"));
        final String output = errorStream.toString(StandardCharsets.UTF_8.name());
        final DefaultLogger.LocalizedMessage addExceptionMessage =
                new DefaultLogger.LocalizedMessage(DefaultLogger.ADD_EXCEPTION_MESSAGE,
                new String[] {"myfile"});

        assertTrue(output.contains(addExceptionMessage.getMessage()), "Invalid exception");
        assertTrue(output.contains("java.lang.IllegalStateException: upsss"),
                "Invalid exception class");
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
            // assert required to calm down eclipse's 'The allocated object is never used'
            // DefaultLogger.LocalizedMessage
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
            // assert required to calm down eclipse's 'The allocated object is never used'
            // DefaultLogger.LocalizedMessage
            assertNotNull(logger, "Null instance");
            fail("Exception was expected");
        }
        catch (IllegalArgumentException exception) {
            assertEquals("Parameter errorStreamOptions can not be null",
                    exception.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testAddError() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE, errorStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, null, getClass(), "customViolation")));
        dl.auditFinished(null);
        assertEquals(auditStartMessage.getMessage() + System.lineSeparator()
                + auditFinishMessage.getMessage() + System.lineSeparator(), infoStream.toString(),
                "expected output");
        assertEquals("[ERROR] fileName:1:2: customViolation [DefaultLoggerTest]"
                + System.lineSeparator(), errorStream.toString(), "expected output");
    }

    @Test
    public void testAddErrorModuleId() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final OutputStream errorStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                errorStream, OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.addError(new AuditEvent(this, "fileName", new Violation(1, 2, "bundle", "key",
                null, "moduleId", getClass(), "customViolation")));
        dl.auditFinished(null);
        assertEquals(auditStartMessage.getMessage() + System.lineSeparator()
                + auditFinishMessage.getMessage() + System.lineSeparator(), infoStream.toString(),
                "expected output");
        assertEquals("[ERROR] fileName:1:2: customViolation [moduleId]"
                + System.lineSeparator(), errorStream.toString(), "expected output");
    }

    @Test
    public void testFinishLocalSetup() {
        final OutputStream infoStream = new ByteArrayOutputStream();
        final DefaultLogger dl = new DefaultLogger(infoStream,
                AutomaticBean.OutputStreamOptions.CLOSE);
        dl.finishLocalSetup();
        dl.auditStarted(null);
        dl.auditFinished(null);
        assertNotNull(dl, "instance should not be null");
    }

    /**
     * Verifies that the language specified with the system property {@code user.language} exists.
     */
    @Test
    public void testLanguageIsValid() {
        final String language = DEFAULT_LOCALE.getLanguage();
        if (!language.isEmpty()) {
            assertThat("Invalid language",
                    Arrays.asList(Locale.getISOLanguages()), hasItem(language));
        }
    }

    /**
     * Verifies that the country specified with the system property {@code user.country} exists.
     */
    @Test
    public void testCountryIsValid() {
        final String country = DEFAULT_LOCALE.getCountry();
        if (!country.isEmpty()) {
            assertThat("Invalid country",
                    Arrays.asList(Locale.getISOCountries()), hasItem(country));
        }
    }

    /**
     * Verifies that if the language is specified explicitly (and it is not English),
     * the message does not use the default value.
     */
    @Test
    public void testLocaleIsSupported() {
        final String language = DEFAULT_LOCALE.getLanguage();
        if (!language.isEmpty() && !Locale.ENGLISH.getLanguage().equals(language)) {
            final DefaultLogger.LocalizedMessage message = createSampleLocalizedMessage();
            assertThat("Unsupported language: " + DEFAULT_LOCALE,
                    message.getMessage(), not("Error auditing {0}"));
        }
    }

    @DefaultLocale("fr")
    @Test
    public void testCleatBundleCache() {
        final DefaultLogger.LocalizedMessage message = createSampleLocalizedMessage();

        assertEquals("Une erreur est survenue {0}", message.getMessage(), "Invalid message");

        final Map<String, ResourceBundle> bundleCache =
                Whitebox.getInternalState(DefaultLogger.LocalizedMessage.class, "BUNDLE_CACHE");

        assertEquals(1, bundleCache.size(), "Invalid bundle cache size");
    }

    @Test
    public void testNullArgs() {
        final DefaultLogger.LocalizedMessage message = new DefaultLogger.LocalizedMessage(
                DefaultLogger.ADD_EXCEPTION_MESSAGE, new String[] {"myfile"});
        final String output = message.getMessage();
        assertTrue(output.contains("Error auditing myfile"),
                "Violation should contain exception info, but was " + output);

        final DefaultLogger.LocalizedMessage messageWithNullArgs =
                new DefaultLogger.LocalizedMessage(DefaultLogger.ADD_EXCEPTION_MESSAGE, null);
        final String outputForNullArgs = messageWithNullArgs.getMessage();
        assertTrue(outputForNullArgs.contains("Error auditing {0}"),
                "Violation should contain exception info, but was " + outputForNullArgs);
    }

    private static DefaultLogger.LocalizedMessage createSampleLocalizedMessage() {
        return new DefaultLogger.LocalizedMessage(DefaultLogger.ADD_EXCEPTION_MESSAGE, null);
    }

    @AfterEach
    public void tearDown() {
        DefaultLogger.LocalizedMessage.clearCache();
    }
}
