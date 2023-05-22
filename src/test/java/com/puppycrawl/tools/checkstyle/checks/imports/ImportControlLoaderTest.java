///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class ImportControlLoaderTest {

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/"
                + "checkstyle/checks/imports/importcontrolloader/" + filename;
    }

    @Test
    public void testLoad() throws CheckstyleException {
        final AbstractImportControl root =
                ImportControlLoader.load(
                    new File(getPath("InputImportControlLoaderComplete.xml")).toURI());
        assertWithMessage("Import root should not be null")
            .that(root)
            .isNotNull();
    }

    @Test
    public void testWrongFormatUri() throws Exception {
        try {
            ImportControlLoader.load(new URI("aaa://"
                    + getPath("InputImportControlLoaderComplete.xml")));
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception class")
                .that(ex.getCause())
                .isInstanceOf(MalformedURLException.class);
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("unknown protocol: aaa");
        }
    }

    @Test
    public void testExtraElementInConfig() throws Exception {
        final AbstractImportControl root =
                ImportControlLoader.load(
                    new File(getPath("InputImportControlLoaderWithNewElement.xml")).toURI());
        assertWithMessage("Import root should not be null")
            .that(root)
            .isNotNull();
    }

    @Test
    // UT uses Reflection to avoid removing null-validation from static method
    public void testSafeGetThrowsException() {
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
            assertWithMessage("exception expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception class")
                .that(ex.getCause())
                .isInstanceOf(SAXException.class);
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("missing attribute you_cannot_find_me");
        }
    }

    @Test
    // UT uses Reflection to cover IOException from 'loader.parseInputSource(source);'
    // because this is possible situation (though highly unlikely), which depends on hardware
    // and is difficult to emulate
    public void testLoadThrowsException() {
        final InputSource source = new InputSource();
        try {
            final Class<?> clazz = ImportControlLoader.class;
            final Method privateMethod = clazz.getDeclaredMethod("load", InputSource.class,
                URI.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(null, source,
                    new File(getPath("InputImportControlLoaderComplete.xml")).toURI());
            assertWithMessage("exception expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid exception class")
                .that(ex.getCause())
                .isInstanceOf(CheckstyleException.class);
            assertWithMessage("Invalid exception message: " + ex.getCause().getMessage())
                    .that(ex)
                    .hasCauseThat()
                    .hasMessageThat()
                    .startsWith("unable to read");
        }
    }

    @Test
    public void testInputStreamFailsOnRead() throws Exception {
        try (InputStream inputStream = mock(InputStream.class)) {
            final int available = doThrow(IOException.class).when(inputStream).available();
            final URL url = mock(URL.class);
            when(url.openStream()).thenReturn(inputStream);
            final URI uri = mock(URI.class);
            when(uri.toURL()).thenReturn(url);

            final CheckstyleException ex = assertThrows(CheckstyleException.class, () -> {
                ImportControlLoader.load(uri);
            });
            assertWithMessage("Invalid exception class")
                    .that(ex)
                    .hasCauseThat()
                            .isInstanceOf(SAXParseException.class);
            // Workaround for warning "Result of InputStream.available() is ignored"
            assertWithMessage("")
                    .that(available)
                    .isEqualTo(0);
        }
    }

}
