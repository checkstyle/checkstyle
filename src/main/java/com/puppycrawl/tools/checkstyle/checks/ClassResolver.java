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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to resolve a class name to an actual class. Note that loaded
 * classes are not initialized.
 * <p>Limitations: this does not handle inner classes very well.</p>
 *
 * @author Oliver Burn
 */
public class ClassResolver {
    /** name of the package to check if the class belongs to **/
    private final String pkg;
    /** set of imports to check against **/
    private final Set<String> imports;
    /** use to load classes **/
    private final ClassLoader loader;

    /**
     * Creates a new {@code ClassResolver} instance.
     *
     * @param loader the ClassLoader to load classes with.
     * @param pkg the name of the package the class may belong to
     * @param imports set of imports to check if the class belongs to
     */
    public ClassResolver(ClassLoader loader, String pkg, Set<String> imports) {
        this.loader = loader;
        this.pkg = pkg;
        this.imports = new HashSet<>(imports);
        this.imports.add("java.lang.*");
    }

    /**
     * Attempts to resolve the Class for a specified name. The algorithm is
     * to check:
     * - fully qualified name
     * - explicit imports
     * - enclosing package
     * - star imports
     * @param name name of the class to resolve
     * @param currentClass name of current class (for inner classes).
     * @return the resolved class
     * @throws ClassNotFoundException if unable to resolve the class
     */
    public Class<?> resolve(String name, String currentClass)
        throws ClassNotFoundException {
        // See if the class is full qualified
        Class<?> clazz = resolveQualifiedName(name);
        if (clazz != null) {
            return clazz;
        }

        // try matching explicit imports
        for (String imp : imports) {
            // Very important to add the "." in the check below. Otherwise you
            // when checking for "DataException", it will match on
            // "SecurityDataException". This has been the cause of a very
            // difficult bug to resolve!
            if (imp.endsWith("." + name)) {
                clazz = resolveQualifiedName(imp);
                if (clazz != null) {
                    return clazz;
                }

            }
        }

        // See if in the package
        if (pkg != null && !pkg.isEmpty()) {
            clazz = resolveQualifiedName(pkg + "." + name);
            if (clazz != null) {
                return clazz;
            }
        }

        // see if inner class of this class
        clazz = resolveInnerClass(name, currentClass);
        if (clazz != null) {
            return clazz;
        }

        clazz = resolveByStarImports(name);
        if (clazz != null) {
            return clazz;
        }

        // Giving up, the type is unknown, so load the class to generate an
        // exception
        return safeLoad(name);
    }

    /**
     * see if inner class of this class
     * @param name name of the search Class to search
     * @param currentClass class where search in
     * @return class if found , or null if not resolved
     * @throws ClassNotFoundException  if an error occurs
     */
    private Class<?> resolveInnerClass(String name, String currentClass)
            throws ClassNotFoundException {
        Class<?> clazz = null;
        if (!currentClass.isEmpty()) {
            String innerClass = currentClass + "$" + name;

            if (!pkg.isEmpty()) {
                innerClass = pkg + "." + innerClass;
            }

            if (isLoadable(innerClass)) {
                clazz = safeLoad(innerClass);
            }
        }
        return clazz;
    }

    /**
     * try star imports
     * @param name name of the Class to search
     * @return  class if found , or null if not resolved
     */
    private Class<?> resolveByStarImports(String name) {
        Class<?> clazz = null;
        for (String imp : imports) {
            if (imp.endsWith(".*")) {
                final String fqn = imp.substring(0, imp.lastIndexOf('.') + 1)
                    + name;
                clazz = resolveQualifiedName(fqn);
                if (clazz != null) {
                    break;
                }
            }
        }
        return clazz;
    }

    /**
     * @param name name of the class to check
     * @return whether a specified class is loadable with safeLoad().
     */
    public boolean isLoadable(String name) {
        try {
            safeLoad(name);
            return true;
        }
        catch (final ClassNotFoundException | NoClassDefFoundError ignored) {
            return false;
        }
    }

    /**
     * Will load a specified class is such a way that it will NOT be
     * initialised.
     * @param name name of the class to load
     * @return the {@code Class} for the specified class
     * @throws ClassNotFoundException if an error occurs
     * @throws NoClassDefFoundError if an error occurs
     */
    public Class<?> safeLoad(String name)
        throws ClassNotFoundException, NoClassDefFoundError {
        // The next line will load the class using the specified class
        // loader. The magic is having the "false" parameter. This means the
        // class will not be initialised. Very, very important.
        return Class.forName(name, false, loader);
    }

    /**
     * Tries to resolve a class for fully-specified name.
     * @param name a given name of class.
     * @return Class object for the given name or null.
     */
    private Class<?> resolveQualifiedName(final String name) {
        try {
            if (isLoadable(name)) {
                return safeLoad(name);
            }
            //Perhaps it's fully-qualified inner class
            final int dot = name.lastIndexOf('.');
            if (dot != -1) {
                final String innerName =
                    name.substring(0, dot) + "$" + name.substring(dot + 1);
                if (isLoadable(innerName)) {
                    return safeLoad(innerName);
                }
            }
        }
        catch (final ClassNotFoundException ex) {
            // we shouldn't get this exception here,
            // so this is unexpected runtime exception
            throw new IllegalStateException(ex);
        }

        return null;
    }
}
