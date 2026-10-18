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
    public void testLoadWithPropertyExpansion() throws Exception {
        System.setProperty("ic.loader.root", "com");
        System.setProperty("ic.loader.sub", "child");
        System.setProperty("ic.loader.allowed", "allowed.pkg");
        System.setProperty("ic.loader.clazz", "some.Class");
        System.setProperty("ic.loader.module", "java.base");
        try {
            final AbstractImportControl root =
                    ImportControlLoader.load(
                        new File(getPath("InputImportControlLoaderPropertyExpansion.xml"))
                            .toURI());
            final AbstractImportControl sub =
                    root.locateFinest("com.example.child", "AnyFile");
            assertWithMessage("Expanded subpackage should be located")
                .that(sub)
                .isNotSameInstanceAs(root);
            assertWithMessage("Import from expanded allow pkg should be allowed")
                .that(sub.checkAccess("com.example.child", "AnyFile", "allowed.pkg.Some"))
                .isEqualTo(AccessResult.ALLOWED);
            assertWithMessage("Import of expanded disallow class should be disallowed")
                .that(sub.checkAccess("com.example.child", "AnyFile", "some.Class"))
                .isEqualTo(AccessResult.DISALLOWED);
            final AbstractImportControl literal =
                    root.locateFinest("com.example.a$b", "AnyFile");
            assertWithMessage("Import from literal dollar subpackage should be allowed")
                .that(literal.checkAccess("com.example.a$b", "AnyFile", "literal.dollar.Some"))
                .isEqualTo(AccessResult.ALLOWED);
            final AbstractImportControl escaped =
                    root.locateFinest("com.example.my$pkg", "AnyFile");
            assertWithMessage("Import from escaped dollar subpackage should be allowed")
                .that(escaped.checkAccess("com.example.my$pkg", "AnyFile",
                        "escaped.dollar.Some"))
                .isEqualTo(AccessResult.ALLOWED);
            final AbstractImportControl file =
                    root.locateFinest("com.example", "Trailing$");
            assertWithMessage("Import from trailing dollar file should be allowed")
                .that(file.checkAccess("com.example", "Trailing$", "trailing.dollar.Some"))
                .isEqualTo(AccessResult.ALLOWED);
        }
        finally {
            System.clearProperty("ic.loader.root");
            System.clearProperty("ic.loader.sub");
            System.clearProperty("ic.loader.allowed");
            System.clearProperty("ic.loader.clazz");
            System.clearProperty("ic.loader.module");
        }
    }

    @Test
    public void testLoadWithUnresolvedProperty() throws Exception {
        System.clearProperty("ic.loader.unset");
        final URI uri =
                new File(getPath("InputImportControlLoaderUnresolvedProperty.xml")).toURI();
        try {
            ImportControlLoader.load(uri);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("unable to parse " + uri);
            assertWithMessage("Invalid exception class")
                .that(exc.getCause())
                .isInstanceOf(SAXException.class);
            assertWithMessage("Invalid exception message")
                .that(exc)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Property ${ic.loader.unset} has not been set");
        }
    }

    @Test
    public void testLoadWithUnclosedProperty() throws Exception {
        final URI uri =
                new File(getPath("InputImportControlLoaderUnclosedProperty.xml")).toURI();
        try {
            ImportControlLoader.load(uri);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("unable to parse " + uri);
            assertWithMessage("Invalid exception class")
                .that(exc.getCause())
                .isInstanceOf(SAXException.class);
            assertWithMessage("Invalid exception message")
                .that(exc)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Syntax error in property: com.${ic.loader.unclosed");
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
