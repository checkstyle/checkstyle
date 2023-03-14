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

package com.puppycrawl.tools.checkstyle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Loads a list of package names from a package name XML file.
 */
public final class PackageNamesLoader
    extends XmlLoader {

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID =
        "-//Puppy Crawl//DTD Package Names 1.0//EN";

    /** The new public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_CS_ID =
        "-//Checkstyle//DTD Package Names Configuration 1.0//EN";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME =
        "com/puppycrawl/tools/checkstyle/packages_1_0.dtd";

    /**
     * Name of default checkstyle package names resource file.
     * The file must be in the classpath.
     */
    private static final String CHECKSTYLE_PACKAGES =
        "checkstyle_packages.xml";

    /** Qualified name for element 'package'. */
    private static final String PACKAGE_ELEMENT_NAME = "package";

    /** The temporary stack of package name parts. */
    private final Deque<String> packageStack = new ArrayDeque<>();

    /** The fully qualified package names. */
    private final Set<String> packageNames = new LinkedHashSet<>();

    /**
     * Creates a new {@code PackageNamesLoader} instance.
     *
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private PackageNamesLoader()
            throws ParserConfigurationException, SAXException {
        super(createIdToResourceNameMap());
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) {
        if (PACKAGE_ELEMENT_NAME.equals(qName)) {
            // push package name, name is mandatory attribute with not empty value by DTD
            final String name = attributes.getValue("name");
            packageStack.push(name);
        }
    }

    /**
     * Creates a full package name from the package names on the stack.
     *
     * @return the full name of the current package.
     */
    private String getPackageName() {
        final StringBuilder buf = new StringBuilder(256);
        final Iterator<String> iterator = packageStack.descendingIterator();
        while (iterator.hasNext()) {
            final String subPackage = iterator.next();
            buf.append(subPackage);
            if (!CommonUtil.endsWithChar(subPackage, '.') && iterator.hasNext()) {
                buf.append('.');
            }
        }
        return buf.toString();
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {
        if (PACKAGE_ELEMENT_NAME.equals(qName)) {
            packageNames.add(getPackageName());
            packageStack.pop();
        }
    }

    /**
     * Returns the set of package names, compiled from all
     * checkstyle_packages.xml files found on the given class loaders
     * classpath.
     *
     * @param classLoader the class loader for loading the
     *          checkstyle_packages.xml files.
     * @return the set of package names.
     * @throws CheckstyleException if an error occurs.
     */
    public static Set<String> getPackageNames(ClassLoader classLoader)
            throws CheckstyleException {
        final Set<String> result;
        try {
            // create the loader outside the loop to prevent PackageObjectFactory
            // being created anew for each file
            final PackageNamesLoader namesLoader = new PackageNamesLoader();

            final Enumeration<URL> packageFiles = classLoader.getResources(CHECKSTYLE_PACKAGES);

            while (packageFiles.hasMoreElements()) {
                processFile(packageFiles.nextElement(), namesLoader);
            }

            result = namesLoader.packageNames;
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to get package file resources", ex);
        }
        catch (ParserConfigurationException | SAXException ex) {
            throw new CheckstyleException("unable to open one of package files", ex);
        }

        return Collections.unmodifiableSet(result);
    }

    /**
     * Reads the file provided and parses it with package names loader.
     *
     * @param packageFile file from package
     * @param namesLoader package names loader
     * @throws SAXException if an error while parsing occurs
     * @throws CheckstyleException if unable to open file
     */
    private static void processFile(URL packageFile, PackageNamesLoader namesLoader)
            throws SAXException, CheckstyleException {
        try (InputStream stream = new BufferedInputStream(packageFile.openStream())) {
            final InputSource source = new InputSource(stream);
            namesLoader.parseInputSource(source);
        }
        catch (IOException ex) {
            throw new CheckstyleException("unable to open " + packageFile, ex);
        }
    }

    /**
     * Creates mapping between local resources and dtd ids.
     *
     * @return map between local resources and dtd ids.
     */
    private static Map<String, String> createIdToResourceNameMap() {
        final Map<String, String> map = new HashMap<>();
        map.put(DTD_PUBLIC_ID, DTD_RESOURCE_NAME);
        map.put(DTD_PUBLIC_CS_ID, DTD_RESOURCE_NAME);
        return map;
    }

}
