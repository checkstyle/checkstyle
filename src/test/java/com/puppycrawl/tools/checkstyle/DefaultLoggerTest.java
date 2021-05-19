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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class DefaultLoggerTest {

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

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final DefaultLogger.LocalizedMessage.Utf8Control control =
                new DefaultLogger.LocalizedMessage.Utf8Control();
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

        final DefaultLogger.LocalizedMessage.Utf8Control control =
                new DefaultLogger.LocalizedMessage.Utf8Control();
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

        final DefaultLogger.LocalizedMessage.Utf8Control control =
                new DefaultLogger.LocalizedMessage.Utf8Control();
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

        final DefaultLogger.LocalizedMessage.Utf8Control control =
                new DefaultLogger.LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                new TestUrlsClassLoader(url), true);
        assertNull(bundle, "Bundle should be null when stream is null");
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
