////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.utils.CommonUtils.EMPTY_BYTE_ARRAY;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtils.EMPTY_OBJECT_ARRAY;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Locale;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import nl.jqno.equalsverifier.EqualsVerifier;

public class LocalizedMessageTest {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(LocalizedMessage.class).usingGetClass().verify();
    }

    @Test
    public void testGetModuleId() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("module", localizedMessage.getModuleId());
    }

    @Test
    public void testMessageInEnglish() {
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.ENGLISH);

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testBundleReloadUrlNull() throws IOException {
        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        control.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
    }

    @Test
    public void testBundleReloadUrlNotNull() throws IOException {

        final ClassLoader classloader = mock(ClassLoader.class);
        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(
                new ByteArrayInputStream(EMPTY_BYTE_ARRAY));

        final URL url = getMockUrl(mockConnection);
        final String resource =
            "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";
        when(classloader.getResource(resource)).thenReturn(url);

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        control.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);
    }

    @Test
    public void testBundleReloadUrlNotNullStreamNull() throws IOException {

        final ClassLoader classloader = mock(ClassLoader.class);
        final String resource =
            "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";

        final URL url = getMockUrl(null);
        when(classloader.getResource(resource)).thenReturn(url);

        final LocalizedMessage.Utf8Control control = new LocalizedMessage.Utf8Control();
        control.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);
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

        assertEquals("Instruction vide.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.US);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.ROOT);
        final LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    private static LocalizedMessage createSampleLocalizedMessage() {
        return new LocalizedMessage(0, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", EMPTY_OBJECT_ARRAY, "module", LocalizedMessage.class, null);
    }

    @After
    public void tearDown() {
        Locale.setDefault(DEFAULT_LOCALE);
        LocalizedMessage.clearCache();
        LocalizedMessage.setLocale(DEFAULT_LOCALE);
    }
}
