////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class LocalizedMessageTest {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LocalizedMessage.class)
                .usingGetClass().report();
        assertEquals("Error: " + ev.getMessage(), EqualsVerifierReport.SUCCESS, ev);
    }

    @Test
    public void testGetModuleId() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Invalid module id", "module", localizedMessage.getModuleId());
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

    @Test
    public void testBundleReloadUrlNotNull() throws IOException {
        final ClassLoader classloader = mock(ClassLoader.class);
        final String resource =
                "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";
        final URLConnection mockUrlCon = mock(URLConnection.class);
        final URLStreamHandler stubUrlHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockUrlCon;
            }
        };
        final URL url = new URL("foo", "bar", 99, "/foobar", stubUrlHandler);
        final InputStream inputStreamMock = mock(InputStream.class);
        when(classloader.getResource(resource)).thenReturn(url);
        when(mockUrlCon.getInputStream()).thenReturn(inputStreamMock);
        when(inputStreamMock.read(any(), anyInt(), anyInt())).thenReturn(-1);

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        control.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);

        verify(mockUrlCon, times(1)).setUseCaches(false);
        verify(inputStreamMock, times(1)).close();
    }

    @Test
    public void testBundleReloadUrlNotNullStreamNull() throws IOException {
        final ClassLoader classloader = mock(ClassLoader.class);
        final String resource =
            "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";

        final URL url = getMockUrl(null);
        when(classloader.getResource(resource)).thenReturn(url);

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        final ResourceBundle bundle = control.newBundle(
                "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);
        assertNull("Bundle should be null when stream is null", bundle);
    }

    private static URL getMockUrl(final URLConnection connection) throws IOException {
        final URLStreamHandler handler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(final URL url) {
                return connection;
            }
        };
        return new URL("http://foo.bar", "foo.bar", 80, "", handler);
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

    private static LocalizedMessage createSampleLocalizedMessage() {
        return createSampleLocalizedMessageWithId("module");
    }

    private static LocalizedMessage createSampleLocalizedMessageWithId(String id) {
        return new LocalizedMessage(1, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, id, LocalizedMessage.class, null);
    }

    @After
    public void tearDown() {
        Locale.setDefault(DEFAULT_LOCALE);
        LocalizedMessage.clearCache();
        LocalizedMessage.setLocale(DEFAULT_LOCALE);
    }

}
