////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.AbstractLoader;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Stack;
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
    private static final String DEFAULT_PACKAGES =
        "com/puppycrawl/tools/checkstyle/checkstyle_packages.xml";

    /**
     * the factory to return in getModuleFactory(),
     * configured during parsing
     */
    private final PackageObjectFactory mModuleFactory =
        new PackageObjectFactory();

    /** The loaded package names */
    private Stack mPackageStack = new Stack();

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

    /** {@inheritDoc} */
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
        throws SAXException
    {
        if (aQName.equals("package")) {
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
        final Iterator it = mPackageStack.iterator();
        while (it.hasNext()) {
            final String subPackage = (String) it.next();
            buf.append(subPackage);
            if (!subPackage.endsWith(".")) {
                buf.append(".");
            }
        }
        return buf.toString();
    }

    /**
     * Returns the module factory that has just been configured.
     * @return the module factory, never null
     */
    private ModuleFactory getModuleFactory()
    {
        return mModuleFactory;
    }

    /** {@inheritDoc} */
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        if (aQName.equals("package")) {
            mModuleFactory.addPackage(getPackageName());
            mPackageStack.pop();
        }
    }

    /**
     * Returns the default list of package names.
     * @param aClassLoader the class loader that gets the
     * default package names.
     * @return the default list of package names.
     * @throws CheckstyleException if an error occurs.
     */
    public static ModuleFactory loadModuleFactory(ClassLoader aClassLoader)
        throws CheckstyleException
    {

        final InputStream stream =
            aClassLoader.getResourceAsStream(DEFAULT_PACKAGES);
        final InputSource source = new InputSource(stream);
        return loadModuleFactory(source, "default package names");
    }

    /**
     * Returns the package names in a specified file.
     * @param aFilename name of the package file.
     * @return the list of package names stored in the
     *  package file.
     * @throws CheckstyleException if an error occurs.
     */
    public static ModuleFactory loadModuleFactory(String aFilename)
        throws CheckstyleException
    {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(aFilename);
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException(
                "unable to find " + aFilename, e);
        }
        final InputSource source = new InputSource(fis);
        return loadModuleFactory(source, aFilename);
    }

    /**
     * Returns the list of package names in a specified source.
     * @param aSource the source for the list.
     * @param aSourceName the name of the source.
     * @return the list ofpackage names stored in aSource.
     * @throws CheckstyleException if an error occurs.
     */
    private static ModuleFactory loadModuleFactory(
            InputSource aSource, String aSourceName)
        throws CheckstyleException
    {
        try {
            final PackageNamesLoader nameLoader = new PackageNamesLoader();
            nameLoader.parseInputSource(aSource);
            return nameLoader.getModuleFactory();
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aSourceName, e);
        }
        catch (ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aSourceName, e);
        }
        catch (SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aSourceName + " - " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new CheckstyleException("unable to read " + aSourceName, e);
        }
    }
}
