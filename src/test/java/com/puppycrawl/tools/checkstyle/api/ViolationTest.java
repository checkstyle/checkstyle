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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_OBJECT_ARRAY;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.powermock.reflect.Whitebox;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

/**
 * Custom class loader is needed to pass URLs to pretend these are loaded from the classpath
 * though we can't add/change the files for testing. The class loader is nested in this class,
 * so the custom class loader we are using is safe.
 *
 * @noinspection ClassLoaderInstantiation
 */
public class ViolationTest {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

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
            final Violation violation = createSampleViolation();
            assertThat("Unsupported language: " + DEFAULT_LOCALE,
                    violation.getMessage(), not("Empty statement."));
        }
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(Violation.class)
                .usingGetClass().report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testGetSeverityLevel() {
        final Violation violation = createSampleViolation();

        assertEquals(SeverityLevel.ERROR,
                violation.getSeverityLevel(), "Invalid severity level");
    }

    @Test
    public void testGetModuleId() {
        final Violation violation = createSampleViolation();

        assertEquals("module", violation.getModuleId(), "Invalid module id");
    }

    @Test
    public void testGetSourceName() {
        final Violation violation = createSampleViolation();

        assertEquals("com.puppycrawl.tools.checkstyle.api.Violation",
                violation.getSourceName(), "Invalid source name");
    }

    @Test
    public void testMessageInEnglish() {
        final Violation violation = createSampleViolation();
        violation.setLocale(Locale.ENGLISH);

        assertEquals("Empty statement.", violation.getMessage(), "Invalid message");
    }

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final Violation.Utf8Control control = new Violation.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
        assertNull(bundle, "Bundle should be null when reload is true and URL is null");
    }

    /**
     * Ignore resource errors for testing.
     *
     * @noinspection resource, IOResourceOpenedButNotSafelyClosed
     */
    @Test
    public void testBundleReloadUrlNotNull() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();

        final InputStream inputStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() {
                closed.set(true);
            }
        };
        final URLConnection urlConnection = new URLConnection(null) {
            @Override
            public void connect() {
                // no code
            }

            @Override
            public InputStream getInputStream() {
                return inputStream;
            }
        };
        final URL url = new URL("test", null, 0, "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return urlConnection;
            }
        });

        final Violation.Utf8Control control = new Violation.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", Locale.ENGLISH,
                "java.class", new TestUrlsClassLoader(url), true);

        assertNotNull(bundle, "Bundle should not be null when stream is not null");
        assertFalse(urlConnection.getUseCaches(), "connection should not be using caches");
        assertTrue(closed.get(), "connection should be closed");
    }

    /**
     * Ignore resource errors for testing.
     *
     * @noinspection resource, IOResourceOpenedButNotSafelyClosed
     */
    @Test
    public void testBundleReloadUrlNotNullFalseReload() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();

        final InputStream inputStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }

            @Override
            public void close() {
                closed.set(true);
            }
        };
        final URLConnection urlConnection = new URLConnection(null) {
            @Override
            public void connect() {
                // no code
            }

            @Override
            public InputStream getInputStream() {
                return inputStream;
            }
        };
        final URL url = new URL("test", null, 0, "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return urlConnection;
            }
        });

        final Violation.Utf8Control control = new Violation.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", Locale.ENGLISH,
                "java.class", new TestUrlsClassLoader(url), false);

        assertNotNull(bundle, "Bundle should not be null when stream is not null");
        assertTrue(urlConnection.getUseCaches(), "connection should not be using caches");
        assertTrue(closed.get(), "connection should be closed");
    }

    @Test
    public void testBundleReloadUrlNotNullStreamNull() throws IOException {
        final URL url = new URL("test", null, 0, "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return null;
            }
        });

        final Violation.Utf8Control control = new Violation.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                new TestUrlsClassLoader(url), true);
        assertNull(bundle, "Bundle should be null when stream is null");
    }

    @Test
    public void testMessageInFrench() {
        final Violation violation = createSampleViolation();
        violation.setLocale(Locale.FRENCH);

        assertEquals("Instruction vide.", violation.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        Violation.setLocale(Locale.US);
        final Violation violation = createSampleViolation();

        assertEquals("Empty statement.", violation.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        Violation.setLocale(Locale.ROOT);
        final Violation violation = createSampleViolation();

        assertEquals("Empty statement.", violation.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testGetKey() {
        Violation.setLocale(Locale.US);
        final Violation violation = createSampleViolation();

        assertEquals("empty.statement", violation.getKey(), "Invalid message key");
    }

    @DefaultLocale("fr")
    @Test
    public void testCleatBundleCache() {
        Violation.setLocale(Locale.ROOT);
        final Violation violation = createSampleViolation();

        assertEquals("Empty statement.", violation.getMessage(), "Invalid message");

        final Map<String, ResourceBundle> bundleCache =
                Whitebox.getInternalState(Violation.class, "BUNDLE_CACHE");

        assertEquals(1, bundleCache.size(), "Invalid bundle cache size");

        Violation.setLocale(Locale.CHINA);

        assertEquals(0, bundleCache.size(), "Invalid bundle cache size");
    }

    @Test
    public void testTokenType() {
        final Violation violation1 = new Violation(1, 1, TokenTypes.CLASS_DEF,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final Violation violation2 = new Violation(1, 1, TokenTypes.OBJBLOCK,
                "messages.properties", "key", EMPTY_OBJECT_ARRAY, SeverityLevel.ERROR, null,
                getClass(), null);

        assertEquals(TokenTypes.CLASS_DEF, violation1.getTokenType(), "Invalid token type");
        assertEquals(TokenTypes.OBJBLOCK, violation2.getTokenType(), "Invalid token type");
    }

    @Test
    public void testGetColumnCharIndex() {
        final Violation violation1 = new Violation(1, 1, 123,
                TokenTypes.CLASS_DEF, "messages.properties", "key", null, SeverityLevel.ERROR,
                null, getClass(), null);

        assertEquals(123, violation1.getColumnCharIndex(), "Invalid column char index");
    }

    @Test
    public void testCompareToWithDifferentModuleId() {
        final Violation message1 = createSampleViolationWithId("module1");
        final Violation message2 = createSampleViolationWithId("module2");
        final Violation messageNull = createSampleViolationWithId(null);

        assertTrue(message1.compareTo(messageNull) > 0, "Invalid comparing result");
        assertTrue(messageNull.compareTo(message1) < 0, "Invalid comparing result");
        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
    }

    @Test
    public void testCompareToWithDifferentLines() {
        final Violation message1 = createSampleViolationWithLine(1);
        final Violation message1a = createSampleViolationWithLine(1);
        final Violation message2 = createSampleViolationWithLine(2);

        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
        assertTrue(message2.compareTo(message1) > 0, "Invalid comparing result");
        final int actual = message1.compareTo(message1a);
        assertEquals(0, actual, "Invalid comparing result");
    }

    @Test
    public void testCompareToWithDifferentColumns() {
        final Violation message1 = createSampleViolationWithColumn(1);
        final Violation message1a = createSampleViolationWithColumn(1);
        final Violation message2 = createSampleViolationWithColumn(2);

        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
        assertTrue(message2.compareTo(message1) > 0, "Invalid comparing result");
        final int actual = message1.compareTo(message1a);
        assertEquals(0, actual, "Invalid comparing result");
    }

    private static Violation createSampleViolation() {
        return createSampleViolationWithId("module");
    }

    private static Violation createSampleViolationWithId(String id) {
        return new Violation(1, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, id, Violation.class, null);
    }

    private static Violation createSampleViolationWithLine(int line) {
        return new Violation(line, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, "module", Violation.class, null);
    }

    private static Violation createSampleViolationWithColumn(int column) {
        return new Violation(1, column,
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", "empty.statement",
                EMPTY_OBJECT_ARRAY, "module", Violation.class, null);
    }

    @AfterEach
    public void tearDown() {
        Violation.clearCache();
        Violation.setLocale(DEFAULT_LOCALE);
    }

    /**
     * Custom class loader is needed to pass URLs to pretend these are loaded from the classpath
     * though we can't add/change the files for testing.
     *
     * @noinspection CustomClassloader
     */
    private static class TestUrlsClassLoader extends ClassLoader {

        private final URL url;

        /* package */ TestUrlsClassLoader(URL url) {
            this.url = url;
        }

        @Override
        public URL getResource(String name) {
            return url;
        }
    }

}
