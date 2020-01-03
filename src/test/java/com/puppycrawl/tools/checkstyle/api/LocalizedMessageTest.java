////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
 * @noinspection ClassLoaderInstantiation
 */
public class LocalizedMessageTest {

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
            final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
            assertThat("Unsupported language: " + DEFAULT_LOCALE,
                    localizedMessage.getMessage(), not("Empty statement."));
        }
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LocalizedMessage.class)
                .usingGetClass().report();
        assertEquals(EqualsVerifierReport.SUCCESS, ev, "Error: " + ev.getMessage());
    }

    @Test
    public void testGetSeverityLevel() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals(SeverityLevel.ERROR,
                localizedMessage.getSeverityLevel(), "Invalid severity level");
    }

    @Test
    public void testGetModuleId() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("module", localizedMessage.getModuleId(), "Invalid module id");
    }

    @Test
    public void testGetSourceName() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("com.puppycrawl.tools.checkstyle.api.LocalizedMessage",
                localizedMessage.getSourceName(), "Invalid source name");
    }

    @Test
    public void testMessageInEnglish() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.ENGLISH);

        assertEquals("Empty statement.", localizedMessage.getMessage(), "Invalid message");
    }

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
        assertNull(bundle, "Bundle should be null when reload is true and URL is null");
    }

    /**
     * Ignore resource errors for testing.
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

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", Locale.ENGLISH,
                "java.class", new TestUrlsClassLoader(url), true);

        assertNotNull(bundle, "Bundle should not be null when stream is not null");
        assertFalse(urlConnection.getUseCaches(), "connection should not be using caches");
        assertTrue(closed.get(), "connection should be closed");
    }

    /**
     * Ignore resource errors for testing.
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

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
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

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                new TestUrlsClassLoader(url), true);
        assertNull(bundle, "Bundle should be null when stream is null");
    }

    @Test
    public void testMessageInFrench() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.FRENCH);

        assertEquals("Instruction vide.", localizedMessage.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage(), "Invalid message");
    }

    @DefaultLocale("fr")
    @Test
    public void testGetKey() {
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("empty.statement", localizedMessage.getKey(), "Invalid message key");
    }

    @DefaultLocale("fr")
    @Test
    public void testCleatBundleCache() {
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage(), "Invalid message");

        final Map<String, ResourceBundle> bundleCache =
                Whitebox.getInternalState(LocalizedMessage.class, "BUNDLE_CACHE");

        assertEquals(1, bundleCache.size(), "Invalid bundle cache size");

        LocalizedMessage.setLocale(Locale.CHINA);

        assertEquals(0, bundleCache.size(), "Invalid bundle cache size");
    }

    @Test
    public void testTokenType() {
        final LocalizedMessage localizedMessage1 = new LocalizedMessage(1, 1, TokenTypes.CLASS_DEF,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final LocalizedMessage localizedMessage2 = new LocalizedMessage(1, 1, TokenTypes.OBJBLOCK,
                "messages.properties", "key", EMPTY_OBJECT_ARRAY, SeverityLevel.ERROR, null,
                getClass(), null);

        assertEquals(TokenTypes.CLASS_DEF, localizedMessage1.getTokenType(), "Invalid token type");
        assertEquals(TokenTypes.OBJBLOCK, localizedMessage2.getTokenType(), "Invalid token type");
    }

    @Test
    public void testGetColumnCharIndex() {
        final LocalizedMessage localizedMessage1 = new LocalizedMessage(1, 1, 123,
                TokenTypes.CLASS_DEF, "messages.properties", "key", null, SeverityLevel.ERROR,
                null, getClass(), null);

        assertEquals(123, localizedMessage1.getColumnCharIndex(), "Invalid column char index");
    }

    @Test
    public void testCompareToWithDifferentModuleId() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithId("module1");
        final LocalizedMessage message2 = createSampleLocalizedMessageWithId("module2");
        final LocalizedMessage messageNull = createSampleLocalizedMessageWithId(null);

        assertTrue(message1.compareTo(messageNull) > 0, "Invalid comparing result");
        assertTrue(messageNull.compareTo(message1) < 0, "Invalid comparing result");
        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
    }

    @Test
    public void testCompareToWithDifferentLines() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithLine(1);
        final LocalizedMessage message1a = createSampleLocalizedMessageWithLine(1);
        final LocalizedMessage message2 = createSampleLocalizedMessageWithLine(2);

        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
        assertTrue(message2.compareTo(message1) > 0, "Invalid comparing result");
        final int actual = message1.compareTo(message1a);
        assertEquals(0, actual, "Invalid comparing result");
    }

    @Test
    public void testCompareToWithDifferentColumns() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithColumn(1);
        final LocalizedMessage message1a = createSampleLocalizedMessageWithColumn(1);
        final LocalizedMessage message2 = createSampleLocalizedMessageWithColumn(2);

        assertTrue(message1.compareTo(message2) < 0, "Invalid comparing result");
        assertTrue(message2.compareTo(message1) > 0, "Invalid comparing result");
        final int actual = message1.compareTo(message1a);
        assertEquals(0, actual, "Invalid comparing result");
    }

    private static LocalizedMessage createSampleLocalizedMessage() {
        return createSampleLocalizedMessageWithId("module");
    }

    private static LocalizedMessage createSampleLocalizedMessageWithId(String id) {
        return new LocalizedMessage(1, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, id, LocalizedMessage.class, null);
    }

    private static LocalizedMessage createSampleLocalizedMessageWithLine(int line) {
        return new LocalizedMessage(line, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, "module", LocalizedMessage.class, null);
    }

    private static LocalizedMessage createSampleLocalizedMessageWithColumn(int column) {
        return new LocalizedMessage(1, column,
                "com.puppycrawl.tools.checkstyle.checks.coding.messages", "empty.statement",
                EMPTY_OBJECT_ARRAY, "module", LocalizedMessage.class, null);
    }

    @AfterEach
    public void tearDown() {
        LocalizedMessage.clearCache();
        LocalizedMessage.setLocale(DEFAULT_LOCALE);
    }

    /**
     * Custom class loader is needed to pass URLs to pretend these are loaded from the classpath
     * though we can't add/change the files for testing.
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
