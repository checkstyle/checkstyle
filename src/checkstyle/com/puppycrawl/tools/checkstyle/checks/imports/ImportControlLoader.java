////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Responsible for loading the contents of an import control configuration file.
 * @author Oliver Burn
 */
final class ImportControlLoader extends AbstractLoader
{
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

    /** Used to hold the {@link PkgControl} objects. */
    private final FastStack<PkgControl> mStack = FastStack.newInstance();

    /** the map to lookup the resource name by the id */
    private static final Map<String, String> DTD_RESOURCE_BY_ID =
        new HashMap<String, String>();

    /** Initialise the map */
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
            SAXException
    {
        super(DTD_RESOURCE_BY_ID);
    }

    @Override
    public void startElement(final String aNamespaceURI,
                             final String aLocalName,
                             final String aQName,
                             final Attributes aAtts)
        throws SAXException
    {
        if ("import-control".equals(aQName)) {
            final String pkg = safeGet(aAtts, "pkg");
            mStack.push(new PkgControl(pkg));
        }
        else if ("subpackage".equals(aQName)) {
            assert mStack.size() > 0;
            final String name = safeGet(aAtts, "name");
            mStack.push(new PkgControl(mStack.peek(), name));
        }
        else if ("allow".equals(aQName) || "disallow".equals(aQName)) {
            assert mStack.size() > 0;
            // Need to handle either "pkg" or "class" attribute.
            // May have "exact-match" for "pkg"
            // May have "local-only"
            final boolean isAllow = "allow".equals(aQName);
            final boolean isLocalOnly = (aAtts.getValue("local-only") != null);
            final String pkg = aAtts.getValue("pkg");
            final boolean regex = (aAtts.getValue("regex") != null);
            final Guard g;
            if (pkg != null) {
                final boolean exactMatch =
                    (aAtts.getValue("exact-match") != null);
                g = new Guard(isAllow, isLocalOnly, pkg, exactMatch, regex);
            }
            else {
                // handle class names which can be normal class names or regular
                // expressions
                final String clazz = safeGet(aAtts, "class");
                g = new Guard(isAllow, isLocalOnly, clazz, regex);
            }

            final PkgControl pc = mStack.peek();
            pc.addGuard(g);
        }
    }

    @Override
    public void endElement(final String aNamespaceURI, final String aLocalName,
        final String aQName)
    {
        if ("subpackage".equals(aQName)) {
            assert mStack.size() > 1;
            mStack.pop();
        }
    }

    /**
     * Loads the import control file from a file.
     * @param aUri the uri of the file to load.
     * @return the root {@link PkgControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    static PkgControl load(final URI aUri) throws CheckstyleException
    {
        InputStream is = null;
        try {
            is = aUri.toURL().openStream();
        }
        catch (final MalformedURLException e) {
            throw new CheckstyleException("syntax error in url " + aUri, e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to find " + aUri, e);
        }
        final InputSource source = new InputSource(is);
        return load(source, aUri);
    }

    /**
     * Loads the import control file from a {@link InputSource}.
     * @param aSource the source to load from.
     * @param aUri uri of the source being loaded.
     * @return the root {@link PkgControl} object.
     * @throws CheckstyleException if an error occurs.
     */
    private static PkgControl load(final InputSource aSource,
        final URI aUri) throws CheckstyleException
    {
        try {
            final ImportControlLoader loader = new ImportControlLoader();
            loader.parseInputSource(aSource);
            return loader.getRoot();
        }
        catch (final ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aUri, e);
        }
        catch (final SAXException e) {
            throw new CheckstyleException("unable to parse " + aUri
                    + " - " + e.getMessage(), e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to read " + aUri, e);
        }
    }

    /**
     * @return the root {@link PkgControl} object loaded.
     */
    private PkgControl getRoot()
    {
        assert mStack.size() == 1;
        return mStack.peek();
    }

    /**
     * Utility to safely get an attribute. If it does not exist an exception
     * is thrown.
     * @param aAtts collect to get attribute from.
     * @param aName name of the attribute to get.
     * @return the value of the attribute.
     * @throws SAXException if the attribute does not exist.
     */
    private String safeGet(final Attributes aAtts, final String aName)
        throws SAXException
    {
        final String retVal = aAtts.getValue(aName);
        if (retVal == null) {
            throw new SAXException("missing attibute " + aName);
        }
        return retVal;
    }
}
