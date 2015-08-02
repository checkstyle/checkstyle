////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
        LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("module", localizedMessage.getModuleId());
    }

    @Test
    public void testMessageInEnglish() {
        LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.ENGLISH);

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testBundleReload_UrlNull() throws IOException {
        LocalizedMessage.UTF8Control cntrl = new LocalizedMessage.UTF8Control();
        cntrl.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                Thread.currentThread().getContextClassLoader(), true);
    }

    @Test
    public void testBundleReload_UrlNotNull() throws IOException {

        ClassLoader classloader = mock(ClassLoader.class);
        String resource = "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";
        String urlPath = "file:com/puppycrawl/tools/checkstyle/checks/coding/messages.properties";
        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(
                new ByteArrayInputStream(new byte[]{}));

        URL url = getMockUrl(mockConnection);
        when(classloader.getResource(resource)).thenReturn(url);

        LocalizedMessage.UTF8Control cntrl = new LocalizedMessage.UTF8Control();
        cntrl.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);
    }

    @Test
    public void testBundleReload_UrlNotNullStreamNull() throws IOException {

        ClassLoader classloader = mock(ClassLoader.class);
        String resource = "com/puppycrawl/tools/checkstyle/checks/coding/messages_en.properties";
        String urlPath = "file:com/puppycrawl/tools/checkstyle/checks/coding/messages.properties";

        URL url = getMockUrl(null);
        when(classloader.getResource(resource)).thenReturn(url);

        LocalizedMessage.UTF8Control cntrl = new LocalizedMessage.UTF8Control();
        cntrl.newBundle("com.puppycrawl.tools.checkstyle.checks.coding.messages",
                Locale.ENGLISH, "java.class",
                classloader, true);
    }

    public static URL getMockUrl(final URLConnection connection) throws IOException {
        final URLStreamHandler handler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(final URL arg0) {
                return connection;
            }
        };
        final URL url = new URL("http://foo.bar", "foo.bar", 80, "", handler);
        return url;
    }

    @Test
    public void testMessageInFrench() {
        LocalizedMessage localizedMessage = createSampleLocalizedMessage();
        LocalizedMessage.setLocale(Locale.FRENCH);

        assertEquals("Instruction vide.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingUnitedStatesLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.US);
        LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    @Test
    public void testEnforceEnglishLanguageBySettingRootLocale() {
        Locale.setDefault(Locale.FRENCH);
        LocalizedMessage.setLocale(Locale.ROOT);
        LocalizedMessage localizedMessage = createSampleLocalizedMessage();

        assertEquals("Empty statement.", localizedMessage.getMessage());
    }

    private static LocalizedMessage createSampleLocalizedMessage() {
        return new LocalizedMessage(0, "com.puppycrawl.tools.checkstyle.checks.coding.messages",
                "empty.statement", new Object[]{}, "module", LocalizedMessage.class, null);
    }

    @After
    public void tearDown() {
        Locale.setDefault(DEFAULT_LOCALE);
        LocalizedMessage.clearCache();
        LocalizedMessage.setLocale(DEFAULT_LOCALE);
    }
}
