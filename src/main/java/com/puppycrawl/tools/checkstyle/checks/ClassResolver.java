////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import java.util.Set;

/**
 * Utility class to resolve a class name to an actual class. Note that loaded
 * classes are not initialized.
 * <p>Limitations: this does not handle inner classes very well.</p>
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class ClassResolver
{
    /** name of the package to check if the class belongs to **/
    private final String mPkg;
    /** set of imports to check against **/
    private final Set<String> mImports;
    /** use to load classes **/
    private final ClassLoader mLoader;

    /**
     * Creates a new <code>ClassResolver</code> instance.
     *
     * @param aLoader the ClassLoader to load classes with.
     * @param aPkg the name of the package the class may belong to
     * @param aImports set of imports to check if the class belongs to
     */
    public ClassResolver(ClassLoader aLoader, String aPkg, Set<String> aImports)
    {
        mLoader = aLoader;
        mPkg = aPkg;
        mImports = aImports;
        mImports.add("java.lang.*");
    }

    /**
     * Attempts to resolve the Class for a specified name. The algorithm is
     * to check:
     * - fully qualified name
     * - explicit imports
     * - enclosing package
     * - star imports
     * @param aName name of the class to resolve
     * @param aCurrentClass name of current class (for inner classes).
     * @return the resolved class
     * @throws ClassNotFoundException if unable to resolve the class
     */
    public Class<?> resolve(String aName, String aCurrentClass)
        throws ClassNotFoundException
    {
        // See if the class is full qualified
        Class<?> clazz = resolveQualifiedName(aName);
        if (clazz != null) {
            return clazz;
        }

        // try matching explicit imports
        for (String imp : mImports) {
            // Very important to add the "." in the check below. Otherwise you
            // when checking for "DataException", it will match on
            // "SecurityDataException". This has been the cause of a very
            // difficult bug to resolve!
            if (imp.endsWith("." + aName)) {
                clazz = resolveQualifiedName(imp);
                if (clazz != null) {
                    return clazz;
                }

            }
        }

        // See if in the package
        if (!"".equals(mPkg)) {
            clazz = resolveQualifiedName(mPkg + "." + aName);
            if (clazz != null) {
                return clazz;
            }
        }

        //inner class of this class???
        if (!"".equals(aCurrentClass)) {
            final String innerClass = (!"".equals(mPkg) ? (mPkg + ".") : "")
                + aCurrentClass + "$" + aName;
            if (isLoadable(innerClass)) {
                return safeLoad(innerClass);
            }
        }

        // try star imports
        for (String imp : mImports) {
            if (imp.endsWith(".*")) {
                final String fqn = imp.substring(0, imp.lastIndexOf('.') + 1)
                    + aName;
                clazz = resolveQualifiedName(fqn);
                if (clazz != null) {
                    return clazz;
                }
            }
        }

        // Giving up, the type is unknown, so load the class to generate an
        // exception
        return safeLoad(aName);
    }

    /**
     * @return whether a specified class is loadable with safeLoad().
     * @param aName name of the class to check
     */
    public boolean isLoadable(String aName)
    {
        try {
            safeLoad(aName);
            return true;
        }
        catch (final ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Will load a specified class is such a way that it will NOT be
     * initialised.
     * @param aName name of the class to load
     * @return the <code>Class</code> for the specified class
     * @throws ClassNotFoundException if an error occurs
     */
    public Class<?> safeLoad(String aName)
        throws ClassNotFoundException
    {
        // The next line will load the class using the specified class
        // loader. The magic is having the "false" parameter. This means the
        // class will not be initialised. Very, very important.
        return Class.forName(aName, false, mLoader);
    }

    /**
     * Tries to resolve a class for fully-specified name.
     * @param aName a given name of class.
     * @return Class object for the given name or null.
     */
    private Class<?> resolveQualifiedName(final String aName)
    {
        try {
            if (isLoadable(aName)) {
                return safeLoad(aName);
            }
            //Perhaps it's fully-qualified inner class
            final int dot = aName.lastIndexOf(".");
            if (dot != -1) {
                final String innerName =
                    aName.substring(0, dot) + "$" + aName.substring(dot + 1);
                if (isLoadable(innerName)) {
                    return safeLoad(innerName);
                }
            }
        }
        catch (final ClassNotFoundException ex) {
            // we shouldn't get this exception here,
            // so this is unexpected runtime exception
            throw new RuntimeException(ex);
        }

        return null;
    }
}
