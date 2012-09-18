////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Utils;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Loads a list of package names from a package name XML file.
 * @author Rick Giles
 * @version 4-Dec-2002
 */
public final class PackageNamesLoader
    extends AbstractLoader
{
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
    private final FastStack<String> mPackageStack = FastStack.newInstance();

    /** The fully qualified package names. */
    private final Set<String> mPackageNames = Sets.newLinkedHashSet();

    /**
     * Creates a new <code>PackageNamesLoader</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private PackageNamesLoader()
        throws ParserConfigurationException, SAXException
    {
        super(DTD_PUBLIC_ID, DTD_RESOURCE_NAME);
    }

    /**
     * Returns the set of fully qualified package names this
     * this loader processed.
     * @return the set of package names
     */
    private Set<String> getPackageNames()
    {
        return mPackageNames;
    }

    @Override
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
        throws SAXException
    {
        if ("package".equals(aQName)) {
            //push package name
            final String name = aAtts.getValue("name");
            if (name == null) {
                throw new SAXException("missing package name");
            }
            mPackageStack.push(name);
        }
    }

    /**
     * Creates a full package name from the package names on the stack.
     * @return the full name of the current package.
     */
    private String getPackageName()
    {
        final StringBuffer buf = new StringBuffer();
        for (String subPackage : mPackageStack) {
            buf.append(subPackage);
            if (!subPackage.endsWith(".")) {
                buf.append(".");
            }
        }
        return buf.toString();
    }

    @Override
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        if ("package".equals(aQName)) {

            mPackageNames.add(getPackageName());
            mPackageStack.pop();
        }
    }

    /**
     * Returns the set of package names, compiled from all
     * checkstyle_packages.xml files found on the given classloaders
     * classpath.
     * @param aClassLoader the class loader for loading the
     *          checkstyle_packages.xml files.
     * @return the set of package names.
     * @throws CheckstyleException if an error occurs.
     */
    public static Set<String> getPackageNames(ClassLoader aClassLoader)
        throws CheckstyleException
    {

        Enumeration<URL> packageFiles = null;
        try {
            packageFiles = aClassLoader.getResources(CHECKSTYLE_PACKAGES);
        }
        catch (IOException e) {
            throw new CheckstyleException(
                    "unable to get package file resources", e);
        }

        //create the loader outside the loop to prevent PackageObjectFactory
        //being created anew for each file
        final PackageNamesLoader namesLoader = newPackageNamesLoader();

        while ((null != packageFiles) && packageFiles.hasMoreElements()) {
            final URL aPackageFile = packageFiles.nextElement();
            InputStream stream = null;

            try {
                stream = new BufferedInputStream(aPackageFile.openStream());
                final InputSource source = new InputSource(stream);
                loadPackageNamesSource(source, "default package names",
                    namesLoader);
            }
            catch (IOException e) {
                throw new CheckstyleException(
                        "unable to open " + aPackageFile, e);
            }
            finally {
                Utils.closeQuietly(stream);
            }
        }
        return namesLoader.getPackageNames();
    }

    /**
     * Creates a PackageNamesLoader instance.
     * @return the PackageNamesLoader
     * @throws CheckstyleException if the creation failed
     */
    private static PackageNamesLoader newPackageNamesLoader()
        throws CheckstyleException
    {
        try {
            return new PackageNamesLoader();
        }
        catch (final ParserConfigurationException e) {
            throw new CheckstyleException(
                    "unable to create PackageNamesLoader ", e);
        }
        catch (final SAXException e) {
            throw new CheckstyleException(
                    "unable to create PackageNamesLoader - "
                    + e.getMessage(), e);
        }
    }

    /**
     * Returns the list of package names in a specified source.
     * @param aSource the source for the list.
     * @param aSourceName the name of the source.
     * @param aNameLoader the PackageNamesLoader instance
     * @throws CheckstyleException if an error occurs.
     */
    private static void loadPackageNamesSource(
            InputSource aSource, String aSourceName,
            PackageNamesLoader aNameLoader)
        throws CheckstyleException
    {
        try {
            aNameLoader.parseInputSource(aSource);
        }
        catch (final SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aSourceName + " - " + e.getMessage(), e);
        }
        catch (final IOException e) {
            throw new CheckstyleException("unable to read " + aSourceName, e);
        }
    }
}
