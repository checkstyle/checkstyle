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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.junit.Assert.assertNull;
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
import java.util.ResourceBundle;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class LocalizedMessagePowerTest {

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

}
