////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory for creating objects from package names and names.
 * @author Rick Giles
 * @author lkuehne
 * @version $Revision$
 */
class PackageObjectFactory implements ModuleFactory
{
    /** a list of package names to prepend to class names */
    private final List<String> mPackages = new ArrayList<String>();

    /**
     * Creates a new <code>PackageObjectFactory</code> instance.
     */
    PackageObjectFactory()
    {
    }

    /**
     * Helper for testing.
     * @return the package names that have been added
     */
    String[] getPackages()
    {
        return mPackages.toArray(new String[mPackages.size()]);
    }

    /**
     * Registers a package name to use for shortName resolution.
     * @param aPackageName the package name
     */
    void addPackage(String aPackageName)
    {
        mPackages.add(aPackageName);
    }

    /**
     * Creates a new instance of a class from a given name. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given
     * to a package name from a given list of package names.
     * @param aName the name of a class.
     * @return the <code>Object</code>
     * @throws CheckstyleException if an error occurs.
     */
    private Object doMakeObject(String aName)
        throws CheckstyleException
    {
        //try aName first
        try {
            return createObject(aName);
        }
        catch (final CheckstyleException ex) {
            ; // keep looking
        }

        //now try packages
        for (int i = 0; i < mPackages.size(); i++) {
            final String packageName = mPackages.get(i);
            final String className = packageName + aName;
            try {
                return createObject(className);
            }
            catch (final CheckstyleException ex) {
                ; // keep looking
            }
        }

        throw new CheckstyleException("Unable to instantiate " + aName);
    }

    /**
     * Creates a new instance of a named class.
     * @param aClassName the name of the class to instantiate.
     * @return the <code>Object</code> created by mLoader.
     * @throws CheckstyleException if an error occurs.
     */
    private Object createObject(String aClassName)
        throws CheckstyleException
    {
        try {
            final ClassLoader loader = Thread.currentThread()
                    .getContextClassLoader();
            final Class<?> clazz = Class.forName(aClassName, true, loader);
            return clazz.newInstance();
        }
        catch (final ClassNotFoundException e) {
            throw new CheckstyleException(
                "Unable to find class for " + aClassName, e);
        }
        catch (final InstantiationException e) {
            ///CLOVER:OFF
            throw new CheckstyleException(
                "Unable to instantiate " + aClassName, e);
            ///CLOVER:ON
        }
        catch (final IllegalAccessException e) {
            ///CLOVER:OFF
            throw new CheckstyleException(
                "Unable to instantiate " + aClassName, e);
            ///CLOVER:ON
        }
    }

    /**
     * Creates a new instance of a class from a given name, or that name
     * concatenated with &quot;Check&quot;. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given name
     * to a package name from a given list of package names.
     * @param aName the name of a class.
     * @return the <code>Object</code> created by aLoader.
     * @throws CheckstyleException if an error occurs.
     */
    public Object createModule(String aName)
        throws CheckstyleException
    {
        try {
            return doMakeObject(aName);
        }
        catch (final CheckstyleException ex) {
            //try again with suffix "Check"
            try {
                return doMakeObject(aName + "Check");
            }
            catch (final CheckstyleException ex2) {
                throw new CheckstyleException(
                    "Unable to instantiate " + aName, ex2);
            }
        }
    }
}
