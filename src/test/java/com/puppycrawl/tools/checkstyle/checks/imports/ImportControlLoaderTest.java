////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportControlLoader.class, URI.class})
public class ImportControlLoaderTest {
    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/checks/imports/" + filename;
    }

    @Test
    public void testLoad() throws CheckstyleException {
        final ImportControl root =
                ImportControlLoader.load(new File(getPath("import-control_complete.xml")).toURI());
        assertNotNull(root);
    }

    @Test
    public void testWrongFormatUri() throws Exception {
        try {
            ImportControlLoader.load(new URI("aaa://" + getPath("import-control_complete.xml")));
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertSame(MalformedURLException.class, ex.getCause().getClass());
            assertEquals("unknown protocol: aaa", ex.getCause().getMessage());
        }
    }

    @Test
    public void testExtraElementInConfig() throws Exception {
        final ImportControl root =
                ImportControlLoader.load(
                    new File(getPath("import-control_WithNewElement.xml")).toURI());
        assertNotNull(root);
    }

    @Test
    // UT uses Reflection to avoid removing null-validation from static method
    public void testSafeGetThrowsException() throws Exception {
        final AttributesImpl attr = new AttributesImpl() {
            @Override
            public String getValue(int index) {
                return null;
                }
            };
        try {
            final Class<?> clazz = ImportControlLoader.class;
            final Method privateMethod = clazz.getDeclaredMethod("safeGet",
                Attributes.class, String.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(null, attr, "you_cannot_find_me");
            fail("exception expected");
        }
        catch (InvocationTargetException ex) {
            assertSame(SAXException.class, ex.getCause().getClass());
            assertEquals("missing attribute you_cannot_find_me", ex.getCause().getMessage());
        }
    }

    @Test
    // UT uses Reflection to cover IOException from 'loader.parseInputSource(source);'
    // because this is possible situation (though highly unlikely), which depends on hardware
    // and is difficult to emulate
    public void testLoadThrowsException() throws Exception {
        final InputSource source = new InputSource();
        try {
            final Class<?> clazz = ImportControlLoader.class;
            final Method privateMethod = clazz.getDeclaredMethod("load", InputSource.class,
                URI.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(null, source,
                    new File(getPath("import-control_complete.xml")).toURI());
            fail("exception expected");
        }
        catch (InvocationTargetException ex) {
            assertSame(CheckstyleException.class, ex.getCause().getClass());
            assertTrue(ex.getCause().getMessage().startsWith("unable to read"));
        }
    }

    @Test
    public void testInputStreamThatFailsOnClose() throws Exception {
        final InputStream inputStream = PowerMockito.mock(InputStream.class);
        Mockito.doThrow(IOException.class).when(inputStream).close();
        final int available = Mockito.doThrow(IOException.class).when(inputStream).available();

        final URL url = PowerMockito.mock(URL.class);
        BDDMockito.given(url.openStream()).willReturn(inputStream);

        final URI uri = PowerMockito.mock(URI.class);
        BDDMockito.given(uri.toURL()).willReturn(url);

        try {
            ImportControlLoader.load(uri);
            //Using available to bypass 'ignored result' warning
            fail("exception expected " + available);
        }
        catch (CheckstyleException ex) {
            assertSame(IOException.class, ex.getCause().getClass());
        }
        Mockito.verify(inputStream).close();
    }

    @Test
    public void testInputStreamFailsOnRead() throws Exception {
        final InputStream inputStream = PowerMockito.mock(InputStream.class);
        final int available = Mockito.doThrow(IOException.class).when(inputStream).available();

        final URL url = PowerMockito.mock(URL.class);
        BDDMockito.given(url.openStream()).willReturn(inputStream);

        final URI uri = PowerMockito.mock(URI.class);
        BDDMockito.given(uri.toURL()).willReturn(url);

        try {
            ImportControlLoader.load(uri);
            //Using available to bypass 'ignored result' warning
            fail("exception expected " + available);
        }
        catch (CheckstyleException ex) {
            assertSame(SAXParseException.class, ex.getCause().getClass());
        }
    }
}
