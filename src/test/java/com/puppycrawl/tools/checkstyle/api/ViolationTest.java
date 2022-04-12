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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_OBJECT_ARRAY;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
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
        assumeFalse(language.isEmpty(), "Locale not set");
        assertWithMessage("Invalid language")
                .that(Locale.getISOLanguages())
                .asList()
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
                .that(Locale.getISOCountries())
                .asList()
                .contains(country);
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

        assertWithMessage("Invalid severity level")
            .that(violation.getSeverityLevel())
            .isEqualTo(SeverityLevel.ERROR);
    }

    @Test
    public void testGetModuleId() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid module id")
            .that(violation.getModuleId())
            .isEqualTo("module");
    }

    @Test
    public void testGetSourceName() {
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid source name")
            .that(violation.getSourceName())
            .isEqualTo("com.puppycrawl.tools.checkstyle.api.Violation");
    }

    @Test
    public void testMessageInEnglish() {
        final Violation violation = createSampleViolation();
        Violation.setLocale(Locale.ENGLISH);

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Empty statement.");
    }

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final Violation.Utf8Control control = new Violation.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
        assertWithMessage("Bundle should be null when reload is true and URL is null")
            .that(bundle)
            .isNull();
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

        assertWithMessage("Bundle should not be null when stream is not null")
            .that(bundle)
            .isNotNull();
        assertWithMessage("connection should not be using caches")
                .that(urlConnection.getUseCaches())
                .isFalse();
        assertWithMessage("connection should be closed")
                .that(closed.get())
                .isTrue();
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

        assertWithMessage("Bundle should not be null when stream is not null")
            .that(bundle)
            .isNotNull();
        assertWithMessage("connection should not be using caches")
                .that(urlConnection.getUseCaches())
                .isTrue();
        assertWithMessage("connection should be closed")
                .that(closed.get())
                .isTrue();
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
        assertWithMessage("Bundle should be null when stream is null")
            .that(bundle)
            .isNull();
    }

    @Test
    public void testMessageInFrench() {
        final Violation violation = createSampleViolation();
        Violation.setLocale(Locale.FRENCH);

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Instruction vide.");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        Violation.setLocale(Locale.US);
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Empty statement.");
    }

    @DefaultLocale("fr")
    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        Violation.setLocale(Locale.ROOT);
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Empty statement.");
    }

    @DefaultLocale("fr")
    @Test
    public void testGetKey() {
        Violation.setLocale(Locale.US);
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation key")
            .that(violation.getKey())
            .isEqualTo("empty.statement");
    }

    @DefaultLocale("fr")
    @Test
    public void testCleatBundleCache() {
        Violation.setLocale(Locale.ROOT);
        final Violation violation = createSampleViolation();

        assertWithMessage("Invalid violation")
            .that(violation.getViolation())
            .isEqualTo("Empty statement.");

        final Map<String, ResourceBundle> bundleCache =
                TestUtil.getInternalStaticState(Violation.class, "BUNDLE_CACHE");

        assertWithMessage("Invalid bundle cache size")
            .that(bundleCache)
            .hasSize(1);

        Violation.setLocale(Locale.CHINA);

        assertWithMessage("Invalid bundle cache size")
            .that(bundleCache)
            .isEmpty();
    }

    @Test
    public void testTokenType() {
        final Violation violation1 = new Violation(1, 1, TokenTypes.CLASS_DEF,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final Violation violation2 = new Violation(1, 1, TokenTypes.OBJBLOCK,
                "messages.properties", "key", EMPTY_OBJECT_ARRAY, SeverityLevel.ERROR, null,
                getClass(), null);

        assertWithMessage("Invalid token type")
            .that(violation1.getTokenType())
            .isEqualTo(TokenTypes.CLASS_DEF);
        assertWithMessage("Invalid token type")
            .that(violation2.getTokenType())
            .isEqualTo(TokenTypes.OBJBLOCK);
    }

    @Test
    public void testGetColumnCharIndex() {
        final Violation violation1 = new Violation(1, 1, 123,
                TokenTypes.CLASS_DEF, "messages.properties", "key", null, SeverityLevel.ERROR,
                null, getClass(), null);

        assertWithMessage("Invalid column char index")
            .that(violation1.getColumnCharIndex())
            .isEqualTo(123);
    }

    @Test
    public void testCompareToWithDifferentModuleId() {
        final Violation message1 = createSampleViolationWithId("module1");
        final Violation message2 = createSampleViolationWithId("module2");
        final Violation messageNull = createSampleViolationWithId(null);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(messageNull) > 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(messageNull.compareTo(message1) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
    }

    @Test
    public void testCompareToWithDifferentLines() {
        final Violation message1 = createSampleViolationWithLine(1);
        final Violation message1a = createSampleViolationWithLine(1);
        final Violation message2 = createSampleViolationWithLine(2);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message2.compareTo(message1) > 0)
                .isTrue();
        final int actual = message1.compareTo(message1a);
        assertWithMessage("Invalid comparing result")
            .that(actual)
            .isEqualTo(0);
    }

    @Test
    public void testCompareToWithDifferentColumns() {
        final Violation message1 = createSampleViolationWithColumn(1);
        final Violation message1a = createSampleViolationWithColumn(1);
        final Violation message2 = createSampleViolationWithColumn(2);

        assertWithMessage("Invalid comparing result")
                .that(message1.compareTo(message2) < 0)
                .isTrue();
        assertWithMessage("Invalid comparing result")
                .that(message2.compareTo(message1) > 0)
                .isTrue();
        final int actual = message1.compareTo(message1a);
        assertWithMessage("Invalid comparing result")
            .that(actual)
            .isEqualTo(0);
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
