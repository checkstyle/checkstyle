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
    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_0 =
        "-//Puppy Crawl//DTD Import Control 1.0//EN";

    /** the public ID for the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_1 =
        "-//Puppy Crawl//DTD Import Control 1.1//EN";

    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_0 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_0.dtd";

    /** the resource for the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_1 =
        "com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_1.dtd";

    /** the map to lookup the resource name by the id */
    private static final Map<String, String> DTD_RESOURCE_BY_ID = new HashMap<>();

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
    public void startElement(final String namespaceURI,
                             final String locqName,
                             final String qName,
                             final Attributes atts)
        throws SAXException {
        if ("import-control".equals(qName)) {
            final String pkg = safeGet(atts, "pkg");
            stack.push(new PkgControl(pkg));
        }
        else if ("subpackage".equals(qName)) {
            final String name = safeGet(atts, "name");
            stack.push(new PkgControl(stack.peek(), name));
        }
        else if ("allow".equals(qName) || "disallow".equals(qName)) {
            // Need to handle either "pkg" or "class" attribute.
            // May have "exact-match" for "pkg"
            // May have "local-only"
            final boolean isAllow = "allow".equals(qName);
            final boolean isLocalOnly = atts.getValue("local-only") != null;
            final String pkg = atts.getValue("pkg");
            final boolean regex = atts.getValue("regex") != null;
            final Guard g;
            if (pkg != null) {
                final boolean exactMatch =
                        atts.getValue("exact-match") != null;
                g = new Guard(isAllow, isLocalOnly, pkg, exactMatch, regex);
            }
            else {
                // handle class names which can be normal class names or regular
                // expressions
                final String clazz = safeGet(atts, "class");
                g = new Guard(isAllow, isLocalOnly, clazz, regex);
            }

            final PkgControl pc = stack.peek();
            pc.addGuard(g);
        }
    }

    @Override
    public void endElement(final String namespaceURI, final String localName,
        final String qName) {
        if ("subpackage".equals(qName)) {
            stack.pop();
        }
    }

    /**
     * Loads the import control file from a file.
     * @param uri the uri of the file to load.
     * @return the root {@link PkgControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    static PkgControl load(final URI uri) throws CheckstyleException {
        InputStream is;
        try {
            is = uri.toURL().openStream();
        }
        catch (final MalformedURLException e) {
            throw new CheckstyleException("syntax error in url " + uri, e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to find " + uri, e);
        }
        final InputSource source = new InputSource(is);
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
        catch (final ParserConfigurationException | SAXException e) {
            throw new CheckstyleException("unable to parse " + uri
                    + " - " + e.getMessage(), e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to read " + uri, e);
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
     * @param atts collect to get attribute from.
     * @param name name of the attribute to get.
     * @return the value of the attribute.
     * @throws SAXException if the attribute does not exist.
     */
    private static String safeGet(final Attributes atts, final String name)
        throws SAXException {
        final String retVal = atts.getValue(name);
        if (retVal == null) {
            throw new SAXException("missing attribute " + name);
        }
        return retVal;
    }
}
