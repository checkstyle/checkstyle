////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Test;
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

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LocalizedMessage.class)
                .usingGetClass().report();
        assertEquals("Error: " + ev.getMessage(), EqualsVerifierReport.SUCCESS, ev);
    }

    @Test
    public void testGetSeverityLevel() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid severity level", SeverityLevel.ERROR,
                localizedMessage.getSeverityLevel());
    }

    @Test
    public void testGetModuleId() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid module id", "module", localizedMessage.getModuleId());
    }

    @Test
    public void testGetSourceName() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid source name", "com.puppycrawl.tools.checkstyle.api.LocalizedMessage",
                localizedMessage.getSourceName());
    }

    @Test
    public void testMessageInEnglish() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.ENGLISH);

        assertEquals("Invalid message", "Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
        assertNull("Bundle should be null when reload is true and URL is null", bundle);
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

        assertNotNull("Bundle should not be null when stream is not null", bundle);
        assertFalse("connection should not be using caches", urlConnection.getUseCaches());
        assertTrue("connection should be closed", closed.get());
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

        assertNotNull("Bundle should not be null when stream is not null", bundle);
        assertTrue("connection should not be using caches", urlConnection.getUseCaches());
        assertTrue("connection should be closed", closed.get());
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
        assertNull("Bundle should be null when stream is null", bundle);
    }

    @Test
    public void testMessageInFrench() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.FRENCH);

        assertEquals("Invalid message", "Instruction vide.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid message", "Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid message", "Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testGetKey() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid message key", "empty.statement", localizedMessage.getKey());
    }

    @Test
    public void testCleatBundleCache() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid message", "Empty statement.", localizedMessage.getMessage());

        final Map<String, ResourceBundle> bundleCache =
                Whitebox.getInternalState(LocalizedMessage.class, "BUNDLE_CACHE");

        assertEquals("Invalid bundle cache size", 1, bundleCache.size());

        LocalizedMessage.setLocale(Locale.CHINA);

        assertEquals("Invalid bundle cache size", 0, bundleCache.size());
    }

    @Test
    public void testTokenType() {
        final LocalizedMessage localizedMessage1 = new LocalizedMessage(1, 1, TokenTypes.CLASS_DEF,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final LocalizedMessage localizedMessage2 = new LocalizedMessage(1, 1, TokenTypes.OBJBLOCK,
                "messages.properties", "key", EMPTY_OBJECT_ARRAY, SeverityLevel.ERROR, null,
                getClass(), null);

        assertEquals("Invalid token type", TokenTypes.CLASS_DEF, localizedMessage1.getTokenType());
        assertEquals("Invalid token type", TokenTypes.OBJBLOCK, localizedMessage2.getTokenType());
    }

    @Test
    public void testGetColumnCharIndex() {
        final LocalizedMessage localizedMessage1 = new LocalizedMessage(1, 1, 123,
                TokenTypes.CLASS_DEF, "messages.properties", "key", null, SeverityLevel.ERROR,
                null, getClass(), null);

        assertEquals("Invalid column char index", 123, localizedMessage1.getColumnCharIndex());
    }

    @Test
    public void testCompareToWithDifferentModuleId() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithId("module1");
        final LocalizedMessage message2 = createSampleLocalizedMessageWithId("module2");
        final LocalizedMessage messageNull = createSampleLocalizedMessageWithId(null);

        assertTrue("Invalid comparing result", message1.compareTo(messageNull) > 0);
        assertTrue("Invalid comparing result", messageNull.compareTo(message1) < 0);
        assertTrue("Invalid comparing result", message1.compareTo(message2) < 0);
    }

    @Test
    public void testCompareToWithDifferentLines() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithLine(1);
        final LocalizedMessage message1a = createSampleLocalizedMessageWithLine(1);
        final LocalizedMessage message2 = createSampleLocalizedMessageWithLine(2);

        assertTrue("Invalid comparing result", message1.compareTo(message2) < 0);
        assertTrue("Invalid comparing result", message2.compareTo(message1) > 0);
        assertEquals("Invalid comparing result", 0, message1.compareTo(message1a));
    }

    @Test
    public void testCompareToWithDifferentColumns() {
        final LocalizedMessage message1 = createSampleLocalizedMessageWithColumn(1);
        final LocalizedMessage message1a = createSampleLocalizedMessageWithColumn(1);
        final LocalizedMessage message2 = createSampleLocalizedMessageWithColumn(2);

        assertTrue("Invalid comparing result", message1.compareTo(message2) < 0);
        assertTrue("Invalid comparing result", message2.compareTo(message1) > 0);
        assertEquals("Invalid comparing result", 0, message1.compareTo(message1a));
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

    @After
    public void tearDown() {
        Locale.setDefault(DEFAULT_LOCALE);
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
