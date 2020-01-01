////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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
        assertNotNull(root, "Import root should not be null");
    }

    @Test
    public void testWrongFormatUri() throws Exception {
        try {
            ImportControlLoader.load(new URI("aaa://"
                    + getPath("InputImportControlLoaderComplete.xml")));
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertSame(MalformedURLException.class, ex.getCause().getClass(),
                    "Invalid exception class");
            assertEquals("unknown protocol: aaa", ex.getCause().getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testExtraElementInConfig() throws Exception {
        final AbstractImportControl root =
                ImportControlLoader.load(
                    new File(getPath("InputImportControlLoaderWithNewElement.xml")).toURI());
        assertNotNull(root, "Import root should not be null");
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
            assertSame(SAXException.class, ex.getCause().getClass(), "Invalid exception class");
            assertEquals("missing attribute you_cannot_find_me", ex.getCause().getMessage(),
                    "Invalid exception message");
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
                    new File(getPath("InputImportControlLoaderComplete.xml")).toURI());
            fail("exception expected");
        }
        catch (InvocationTargetException ex) {
            assertSame(CheckstyleException.class, ex.getCause().getClass(),
                    "Invalid exception class");
            assertTrue(ex.getCause().getMessage().startsWith("unable to read"),
                    "Invalid exception message: " + ex.getCause().getMessage());
        }
    }

}
