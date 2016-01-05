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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.AttributesImpl;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class ImportControlLoaderTest {
    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/checks/imports/" + filename;
    }

    @Test
    public void testLoad() throws CheckstyleException {
        final PkgControl root =
                ImportControlLoader.load(new File(getPath("import-control_complete.xml")).toURI());
        assertNotNull(root);
    }

    @Test(expected = CheckstyleException.class)
    public void testWrongFormatUri() throws Exception {
        final PkgControl root =
                ImportControlLoader.load(new URI("aaa://"
                    + getPath("import-control_complete.xml")));
        assertNotNull(root);
    }

    @Test
    public void testExtraElementInConfig() throws Exception {
        final PkgControl root =
                ImportControlLoader.load(
                    new File(getPath("import-control_WithNewElement.xml")).toURI());
        assertNotNull(root);
    }

    @Test(expected = InvocationTargetException.class)
    // UT uses Reflection to avoid removing null-validation from static method
    public void testSafeGetThrowsException() throws InvocationTargetException {
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
        }
        catch (IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Test(expected = InvocationTargetException.class)
    // UT uses Reflection to cover IOException from 'loader.parseInputSource(source);'
    // because this is possible situation (though highly unlikely), which depends on hardware
    // and is difficult to emulate
    public void testLoadThrowsException() throws InvocationTargetException {
        final InputSource source = new InputSource();
        try {
            final Class<?> clazz = ImportControlLoader.class;
            final Method privateMethod = clazz.getDeclaredMethod("load", InputSource.class,
                URI.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(null, source,
                    new File(getPath("import-control_complete.xml")).toURI());
        }
        catch (IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
