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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Responsible for loading the contents of an import control configuration file.
 * @author Oliver Burn
 */
final class ImportControlLoader extends AbstractLoader {
    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Import Control 1.0//EN";

    /** The public ID for the configuration dtd. */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Import Control 1.1//EN";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_0 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_0.dtd";

    /** The resource for the configuration dtd. */
    private static final String DTD_RESOURCE_NAME_1_1 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_1.dtd";

    /** The map to lookup the resource name by the id. */
    private static final Map<String, String> DTD_RESOURCE_BY_ID = new HashMap<>();

    /** Name for attribute 'pkg'. */
    private static final String PKG_ATTRIBUTE_NAME = "pkg";

    /** Qualified name for element 'subpackage'. */
    private static final String SUBPACKAGE_ELEMENT_NAME = "subpackage";

    /** Qualified name for element 'allow'. */
    private static final String ALLOW_ELEMENT_NAME = "allow";

    /** Used to hold the {@link PkgControl} objects. */
    private final Deque<PkgControl> stack = new ArrayDeque<>();

    static {
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_0, DTD_RESOURCE_NAME_1_0);
        DTD_RESOURCE_BY_ID.put(DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1);
    }

    /**
     * Constructs an instance.
     * @throws ParserConfigurationException if an error occurs.
     * @throws SAXException if an error occurs.
     */
    private ImportControlLoader() throws ParserConfigurationException,
            SAXException {
        super(DTD_RESOURCE_BY_ID);
    }

    @Override
    public void startElement(final String namespaceUri,
                             final String localName,
                             final String qName,
                             final Attributes attributes)
            throws SAXException {
        if ("import-control".equals(qName)) {
            final String pkg = safeGet(attributes, PKG_ATTRIBUTE_NAME);
            stack.push(new PkgControl(pkg));
        }
        else if (SUBPACKAGE_ELEMENT_NAME.equals(qName)) {
            final String name = safeGet(attributes, "name");
            stack.push(new PkgControl(stack.peek(), name));
        }
        else if (ALLOW_ELEMENT_NAME.equals(qName) || "disallow".equals(qName)) {
            // Need to handle either "pkg" or "class" attribute.
            // May have "exact-match" for "pkg"
            // May have "local-only"
            final boolean isAllow = ALLOW_ELEMENT_NAME.equals(qName);
            final boolean isLocalOnly = attributes.getValue("local-only") != null;
            final String pkg = attributes.getValue(PKG_ATTRIBUTE_NAME);
            final boolean regex = attributes.getValue("regex") != null;
            final Guard guard;
            if (pkg == null) {
                // handle class names which can be normal class names or regular
                // expressions
                final String clazz = safeGet(attributes, "class");
                guard = new Guard(isAllow, isLocalOnly, clazz, regex);
            }
            else {
                final boolean exactMatch =
                        attributes.getValue("exact-match") != null;
                guard = new Guard(isAllow, isLocalOnly, pkg, exactMatch, regex);
            }

            final PkgControl pkgControl = stack.peek();
            pkgControl.addGuard(guard);
        }
    }

    @Override
    public void endElement(final String namespaceUri, final String localName,
        final String qName) {
        if (SUBPACKAGE_ELEMENT_NAME.equals(qName)) {
            stack.pop();
        }
    }

    /**
     * Loads the import control file from a file.
     * @param uri the uri of the file to load.
     * @return the root {@link PkgControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    public static PkgControl load(final URI uri) throws CheckstyleException {
        final InputStream inputStream;
        try {
            inputStream = uri.toURL().openStream();
        }
        catch (final MalformedURLException ex) {
            throw new CheckstyleException("syntax error in url " + uri, ex);
        }
        catch (final IOException ex) {
            throw new CheckstyleException("unable to find " + uri, ex);
        }
        final InputSource source = new InputSource(inputStream);
        return load(source, uri);
    }

    /**
     * Loads the import control file from a {@link InputSource}.
     * @param source the source to load from.
     * @param uri uri of the source being loaded.
     * @return the root {@link PkgControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    private static PkgControl load(final InputSource source,
        final URI uri) throws CheckstyleException {
        try {
            final ImportControlLoader loader = new ImportControlLoader();
            loader.parseInputSource(source);
            return loader.getRoot();
        }
        catch (final ParserConfigurationException | SAXException ex) {
            throw new CheckstyleException("unable to parse " + uri
                    + " - " + ex.getMessage(), ex);
        }
        catch (final IOException ex) {
            throw new CheckstyleException("unable to read " + uri, ex);
        }
    }

    /**
     * @return the root {@link PkgControl} object loaded.
     */
    private PkgControl getRoot() {
        return stack.peek();
    }

    /**
     * Utility to safely get an attribute. If it does not exist an exception
     * is thrown.
     * @param attributes collect to get attribute from.
     * @param name name of the attribute to get.
     * @return the value of the attribute.
     * @throws SAXException if the attribute does not exist.
     */
    private static String safeGet(final Attributes attributes, final String name)
            throws SAXException {
        final String returnValue = attributes.getValue(name);
        if (returnValue == null) {
            throw new SAXException("missing attribute " + name);
        }
        return returnValue;
    }
}
