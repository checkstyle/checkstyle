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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.utils.CommonUtils.EMPTY_BYTE_ARRAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Enter a description of class PackageNamesLoaderTest.java.
 * @author Rick Giles
 * @author lkuehne
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PackageNamesLoader.class, Closeables.class})
public class PackageNamesLoaderTest extends AbstractPathTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/packagenamesloader";
    }

    @Test
    public void testDefault()
            throws CheckstyleException {
        final Set<String> packageNames = PackageNamesLoader
                .getPackageNames(Thread.currentThread()
                        .getContextClassLoader());
        assertEquals("pkgNames.length.", 0,
                packageNames.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPackagesFile() throws Exception {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(InputStream.class));

        final Method processFileMethod = PackageNamesLoader.class.getDeclaredMethod("processFile",
                URL.class, PackageNamesLoader.class);
        processFileMethod.setAccessible(true);
        final Constructor<PackageNamesLoader> constructor = PackageNamesLoader.class
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        final PackageNamesLoader namesLoader = constructor.newInstance();
        final URL input = new File(getPath("InputPackageNamesLoaderFile.xml")).toURI().toURL();

        processFileMethod.invoke(null, input, namesLoader);

        final Field packageNamesField = PackageNamesLoader.class.getDeclaredField("packageNames");
        packageNamesField.setAccessible(true);

        final Set<String> actualPackageNames = (Set<String>) packageNamesField.get(namesLoader);
        final String[] expectedPackageNames = {
            "com.puppycrawl.tools.checkstyle",
            "com.puppycrawl.tools.checkstyle.checks",
            "com.puppycrawl.tools.checkstyle.checks.annotation",
            "com.puppycrawl.tools.checkstyle.checks.blocks",
            "com.puppycrawl.tools.checkstyle.checks.coding",
            "com.puppycrawl.tools.checkstyle.checks.design",
            "com.puppycrawl.tools.checkstyle.checks.header",
            "com.puppycrawl.tools.checkstyle.checks.imports",
            "com.puppycrawl.tools.checkstyle.checks.indentation",
            "com.puppycrawl.tools.checkstyle.checks.javadoc",
            "com.puppycrawl.tools.checkstyle.checks.metrics",
            "com.puppycrawl.tools.checkstyle.checks.modifier",
            "com.puppycrawl.tools.checkstyle.checks.naming",
            "com.puppycrawl.tools.checkstyle.checks.regexp",
            "com.puppycrawl.tools.checkstyle.checks.sizes",
            "com.puppycrawl.tools.checkstyle.checks.whitespace",
            "com.puppycrawl.tools.checkstyle.filefilters",
            "com.puppycrawl.tools.checkstyle.filters",
        };

        assertEquals("Invalid package names length.", expectedPackageNames.length,
            actualPackageNames.size());
        final Set<String> checkstylePackagesSet =
                new HashSet<>(Arrays.asList(expectedPackageNames));
        assertEquals("Invalid names set.", checkstylePackagesSet, actualPackageNames);

        verifyStatic(times(1));
        Closeables.closeQuietly(any(InputStream.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPackagesWithDots() throws Exception {

        final Constructor<PackageNamesLoader> constructor =
                PackageNamesLoader.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        final PackageNamesLoader loader = constructor.newInstance();

        final Attributes attributes = mock(Attributes.class);
        when(attributes.getValue("name")).thenReturn("coding.");
        loader.startElement("", "", "package", attributes);
        loader.endElement("", "", "package");

        final Field field = PackageNamesLoader.class.getDeclaredField("packageNames");
        field.setAccessible(true);
        final Set<String> list = (Set<String>) field.get(loader);
        assertEquals("Invalid package name", "coding.", list.iterator().next());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPackagesWithSaxException() throws Exception {

        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(
                new ByteArrayInputStream(EMPTY_BYTE_ARRAY));

        final URL url = getMockUrl(mockConnection);

        final Enumeration<URL> enumeration = mock(Enumeration.class);
        when(enumeration.hasMoreElements()).thenReturn(true);
        when(enumeration.nextElement()).thenReturn(url);

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.getResources("checkstyle_packages.xml")).thenReturn(enumeration);

        try {
            PackageNamesLoader.getPackageNames(classLoader);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception cause class", ex.getCause() instanceof SAXException);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPackagesWithIoException() throws Exception {

        final URLConnection mockConnection = Mockito.mock(URLConnection.class);
        when(mockConnection.getInputStream()).thenReturn(null);

        final URL url = getMockUrl(mockConnection);

        final Enumeration<URL> enumer = mock(Enumeration.class);
        when(enumer.hasMoreElements()).thenReturn(true);
        when(enumer.nextElement()).thenReturn(url);

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.getResources("checkstyle_packages.xml")).thenReturn(enumer);

        try {
            PackageNamesLoader.getPackageNames(classLoader);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception cause class", ex.getCause() instanceof IOException);
            assertNotEquals("Invalid exception message",
                    "unable to get package file resources", ex.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPackagesWithIoExceptionGetResources() throws Exception {

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.getResources("checkstyle_packages.xml")).thenThrow(IOException.class);

        try {
            PackageNamesLoader.getPackageNames(classLoader);
            fail("CheckstyleException is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception cause class", ex.getCause() instanceof IOException);
            assertEquals("Invalid exception message",
                    "unable to get package file resources", ex.getMessage());
        }
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
