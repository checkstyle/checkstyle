///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
        final URI uri = new URI("aaa://" + getPath("InputImportControlLoaderComplete.xml"));
        try {
            ImportControlLoader.load(uri);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception class")
                .that(exc.getCause())
                .isInstanceOf(MalformedURLException.class);
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("syntax error in url " + uri);
            assertWithMessage("Invalid exception message")
                .that(exc)
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
            TestUtil.invokeVoidStaticMethod(clazz, "safeGet", attr, "you_cannot_find_me");
            assertWithMessage("exception expected").fail();
        }
        catch (ReflectiveOperationException exc) {
            assertWithMessage("Invalid exception class")
                .that(exc.getCause())
                .isInstanceOf(SAXException.class);
            assertWithMessage("Invalid exception message")
                .that(exc)
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
        final URI uri = new File(getPath("InputImportControlLoaderComplete.xml")).toURI();
        try {
            final Class<?> clazz = ImportControlLoader.class;
            TestUtil.invokeVoidStaticMethod(clazz, "load", source,
                    uri);
            assertWithMessage("exception expected").fail();
        }
        catch (ReflectiveOperationException exc) {
            assertWithMessage("Invalid exception class")
                .that(exc.getCause())
                .isInstanceOf(CheckstyleException.class);
            assertWithMessage("Invalid exception message: %s", exc.getCause().getMessage())
                    .that(exc)
                    .hasCauseThat()
                    .hasMessageThat()
                    .isEqualTo("unable to read " + uri);
        }
    }

    @Test
    public void testInputStreamFailsOnRead() throws Exception {
        try (InputStream inputStream = mock()) {
            final int available = doThrow(IOException.class).when(inputStream).available();
            final URL url = mock();
            when(url.openStream()).thenReturn(inputStream);
            final URI uri = mock();
            when(uri.toURL()).thenReturn(url);

            final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class, () -> {
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
