///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Tests loading of package names from XML files.
 *
 * @noinspection ClassLoaderInstantiation
 * @noinspectionreason ClassLoaderInstantiation - Custom class loader is needed to pass URLs to
 *      pretend these are loaded from the classpath though we can't add/change the files for
 *      testing. The class loader is nested in this class, so the custom class loader we
 *      are using is safe.
 */
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
        assertWithMessage("pkgNames.length.")
            .that(packageNames)
            .isEmpty();
    }

    @Test
    public void testNoPackages() throws Exception {
        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(Collections.emptyEnumeration()));

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .isEmpty();
    }

    @Test
    public void testPackagesFile() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoaderFile.xml")).toURI().toURL()));

        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(enumeration));
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

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .hasSize(expectedPackageNames.length);
        final Set<String> checkstylePackagesSet =
                new HashSet<>(Arrays.asList(expectedPackageNames));
        assertWithMessage("Invalid names set.")
            .that(actualPackageNames)
            .isEqualTo(checkstylePackagesSet);
    }

    @Test
    public void testPackagesWithDots() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoaderWithDots.xml")).toURI().toURL()));

        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(enumeration));
        final String[] expectedPackageNames = {
            "coding.",
        };

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .hasSize(expectedPackageNames.length);
        final Set<String> checkstylePackagesSet =
                new HashSet<>(Arrays.asList(expectedPackageNames));
        assertWithMessage("Invalid names set.")
            .that(actualPackageNames)
            .isEqualTo(checkstylePackagesSet);
    }

    @Test
    public void testPackagesWithDotsEx() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoaderWithDotsEx.xml")).toURI().toURL()));

        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(enumeration));
        final String[] expectedPackageNames = {
            "coding.specific",
            "coding.",
        };

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .hasSize(expectedPackageNames.length);
        final Set<String> checkstylePackagesSet =
                new HashSet<>(Arrays.asList(expectedPackageNames));
        assertWithMessage("Invalid names set.")
            .that(actualPackageNames)
            .isEqualTo(checkstylePackagesSet);
    }

    @Test
    public void testPackagesWithSaxException() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoaderNotXml.java")).toURI().toURL()));

        try {
            PackageNamesLoader.getPackageNames(new TestUrlsClassLoader(enumeration));
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception cause class")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(SAXException.class);
        }
    }

    @Test
    public void testPackagesWithIoException() throws Exception {
        final URLConnection urlConnection = new URLConnection(null) {
            @Override
            public void connect() {
                // no code
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }
        };
        final URL url = new URL("test", null, 0, "", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return urlConnection;
            }
        });

        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(url));

        try {
            PackageNamesLoader.getPackageNames(new TestUrlsClassLoader(enumeration));
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception cause class")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(IOException.class);
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isNotEqualTo("unable to get package file resources");
        }
    }

    @Test
    public void testPackagesWithIoExceptionGetResources() {
        try {
            PackageNamesLoader.getPackageNames(new TestIoExceptionClassLoader());
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception cause class")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(IOException.class);
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("unable to get package file resources");
        }
    }

    @Test
    public void testUnmodifiableCollection() throws Exception {
        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(Collections.emptyEnumeration()));

        final Exception ex = getExpectedThrowable(UnsupportedOperationException.class,
                () -> actualPackageNames.add("com.puppycrawl.tools.checkstyle.checks.modifier"));

        assertWithMessage("Exception class is not expected")
                .that(ex.getClass())
                .isEqualTo(UnsupportedOperationException.class);
    }

    @Test
    public void testMapping() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoader1.xml")).toURI().toURL()));

        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(enumeration));

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .hasSize(3);
    }

    @Test
    public void testMapping2() throws Exception {
        final Enumeration<URL> enumeration = Collections.enumeration(Collections.singleton(
                new File(getPath("InputPackageNamesLoader2.xml")).toURI().toURL()));

        final Set<String> actualPackageNames = PackageNamesLoader
                .getPackageNames(new TestUrlsClassLoader(enumeration));

        assertWithMessage("Invalid package names length.")
            .that(actualPackageNames)
            .hasSize(3);
    }

    /**
     * Mocked ClassLoader for testing URL loading.
     *
     * @noinspection CustomClassloader
     * @noinspectionreason CustomClassloader - needed to pass URLs to pretend these are loaded
     *      from the classpath though we can't add/change the files for testing
     */
    private static final class TestUrlsClassLoader extends ClassLoader {

        private final Enumeration<URL> urls;

        private TestUrlsClassLoader(Enumeration<URL> urls) {
            this.urls = urls;
        }

        @Override
        public Enumeration<URL> getResources(String name) {
            return urls;
        }
    }

    /**
     * Mocked ClassLoader for testing exceptions.
     *
     * @noinspection CustomClassloader
     * @noinspectionreason CustomClassloader - needed to throw an exception to
     *      test a catch statement
     */
    private static final class TestIoExceptionClassLoader extends ClassLoader {
        @Override
        public Enumeration<URL> getResources(String name) throws IOException {
            throw new IOException("test");
        }
    }

}
