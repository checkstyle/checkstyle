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

package com.puppycrawl.tools.checkstyle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Loads a list of package names from a package name XML file.
 * @author Rick Giles
 */
public final class PackageNamesLoader
    extends AbstractLoader {
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID =
        "-//Puppy Crawl//DTD Package Names 1.0//EN";

    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME =
        "com/puppycrawl/tools/checkstyle/packages_1_0.dtd";

    /** Name of default checkstyle package names resource file.
     * The file must be in the classpath.
     */
    private static final String CHECKSTYLE_PACKAGES =
        "checkstyle_packages.xml";

    /** The temporary stack of package name parts */
    private final Deque<String> packageStack = new ArrayDeque<>();

    /** The fully qualified package names. */
    private final Set<String> packageNames = Sets.newLinkedHashSet();

    /**
     * Creates a new {@code PackageNamesLoader} instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private PackageNamesLoader()
        throws ParserConfigurationException, SAXException {
        super(DTD_PUBLIC_ID, DTD_RESOURCE_NAME);
    }

    /**
     * Returns the set of fully qualified package names this
     * this loader processed.
     * @return the set of package names
     */
    private Set<String> getPackageNames() {
        return packageNames;
    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes atts)
        throws SAXException {
        if ("package".equals(qName)) {
            //push package name, name is mandatory attribute with not empty value by DTD
            final String name = atts.getValue("name");
            packageStack.push(name);
        }
    }

    /**
     * Creates a full package name from the package names on the stack.
     * @return the full name of the current package.
     */
    private String getPackageName() {
        final StringBuilder buf = new StringBuilder();
        final Iterator<String> iterator = packageStack.descendingIterator();
        while (iterator.hasNext()) {
            final String subPackage = iterator.next();
            buf.append(subPackage);
            if (!Utils.endsWithChar(subPackage, '.')) {
                buf.append('.');
            }
        }
        return buf.toString();
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) {
        if ("package".equals(qName)) {

            packageNames.add(getPackageName());
            packageStack.pop();
        }
    }

    /**
     * Returns the set of package names, compiled from all
     * checkstyle_packages.xml files found on the given classloaders
     * classpath.
     * @param classLoader the class loader for loading the
     *          checkstyle_packages.xml files.
     * @return the set of package names.
     * @throws CheckstyleException if an error occurs.
     */
    public static Set<String> getPackageNames(ClassLoader classLoader)
            throws CheckstyleException {

        Set<String> result;
        try {
            //create the loader outside the loop to prevent PackageObjectFactory
            //being created anew for each file
            final PackageNamesLoader namesLoader = new PackageNamesLoader();

            final Enumeration<URL> packageFiles = classLoader.getResources(CHECKSTYLE_PACKAGES);

            while (packageFiles.hasMoreElements()) {
                final URL packageFile = packageFiles.nextElement();
                InputStream stream = null;

                try {
                    stream = new BufferedInputStream(packageFile.openStream());
                    final InputSource source = new InputSource(stream);
                    namesLoader.parseInputSource(source);
                }
                catch (IOException e) {
                    throw new CheckstyleException("unable to open " + packageFile, e);
                }
                finally {
                    Closeables.closeQuietly(stream);
                }
            }

            result = namesLoader.getPackageNames();

        }
        catch (IOException e) {
            throw new CheckstyleException("unable to get package file resources", e);
        }
        catch (ParserConfigurationException | SAXException e) {
            throw new CheckstyleException("unable to open one of package files", e);
        }

        return result;
    }
}
