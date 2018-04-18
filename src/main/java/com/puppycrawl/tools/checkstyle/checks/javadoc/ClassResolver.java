////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to resolve a class name to an actual class. Note that loaded
 * classes are not initialized.
 * <p>Limitations: this does not handle inner classes very well.</p>
 *
 */
public class ClassResolver {

    /** Period literal. */
    private static final String PERIOD = ".";
    /** Dollar sign literal. */
    private static final String DOLLAR_SIGN = "$";

    /** Name of the package to check if the class belongs to. **/
    private final String pkg;
    /** Set of imports to check against. **/
    private final Set<String> imports;
    /** Use to load classes. **/
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
    // -@cs[ForbidWildcardAsReturnType] This method can return any type, so no way to avoid wildcard
    public Class<?> resolve(String name, String currentClass)
            throws ClassNotFoundException {
        // See if the class is full qualified
        Class<?> clazz = resolveQualifiedName(name);
        if (clazz == null) {
            // try matching explicit imports
            clazz = resolveMatchingExplicitImport(name);

            if (clazz == null) {
                // See if in the package
                clazz = resolveInPackage(name);

                if (clazz == null) {
                    // see if inner class of this class
                    clazz = resolveInnerClass(name, currentClass);

                    if (clazz == null) {
                        clazz = resolveByStarImports(name);
                        // -@cs[NestedIfDepth] it is better to have single return point from method
                        if (clazz == null) {
                            throw new ClassNotFoundException(name);
                        }
                    }
                }
            }
        }
        return clazz;
    }

    /**
     * Try to find class by search in package.
     * @param name class name
     * @return class object
     */
    private Class<?> resolveInPackage(String name) {
        Class<?> clazz = null;
        if (pkg != null && !pkg.isEmpty()) {
            final Class<?> classFromQualifiedName = resolveQualifiedName(pkg + PERIOD + name);
            if (classFromQualifiedName != null) {
                clazz = classFromQualifiedName;
            }
        }
        return clazz;
    }

    /**
     * Try to find class by matching explicit Import.
     * @param name class name
     * @return class object
     */
    private Class<?> resolveMatchingExplicitImport(String name) {
        Class<?> clazz = null;
        for (String imp : imports) {
            // Very important to add the "." in the check below. Otherwise you
            // when checking for "DataException", it will match on
            // "SecurityDataException". This has been the cause of a very
            // difficult bug to resolve!
            if (imp.endsWith(PERIOD + name)) {
                clazz = resolveQualifiedName(imp);
                if (clazz != null) {
                    break;
                }
            }
        }
        return clazz;
    }

    /**
     * See if inner class of this class.
     * @param name name of the search Class to search
     * @param currentClass class where search in
     * @return class if found , or null if not resolved
     * @throws ClassNotFoundException  if an error occurs
     */
    private Class<?> resolveInnerClass(String name, String currentClass)
            throws ClassNotFoundException {
        Class<?> clazz = null;
        if (!currentClass.isEmpty()) {
            String innerClass = currentClass + DOLLAR_SIGN + name;

            if (!pkg.isEmpty()) {
                innerClass = pkg + PERIOD + innerClass;
            }

            if (isLoadable(innerClass)) {
                clazz = safeLoad(innerClass);
            }
        }
        return clazz;
    }

    /**
     * Try star imports.
     * @param name name of the Class to search
     * @return  class if found , or null if not resolved
     */
    private Class<?> resolveByStarImports(String name) {
        Class<?> clazz = null;
        for (String imp : imports) {
            if (imp.endsWith(".*")) {
                final String fqn = imp.substring(0, imp.lastIndexOf('.') + 1) + name;
                clazz = resolveQualifiedName(fqn);
                if (clazz != null) {
                    break;
                }
            }
        }
        return clazz;
    }

    /**
     * Checks if the given class name can be loaded.
     * @param name name of the class to check
     * @return whether a specified class is loadable with safeLoad().
     */
    public boolean isLoadable(String name) {
        boolean result;
        try {
            safeLoad(name);
            result = true;
        }
        catch (final ClassNotFoundException | NoClassDefFoundError ignored) {
            result = false;
        }
        return result;
    }

    /**
     * Will load a specified class is such a way that it will NOT be
     * initialised.
     * @param name name of the class to load
     * @return the {@code Class} for the specified class
     * @throws ClassNotFoundException if an error occurs
     * @throws NoClassDefFoundError if an error occurs
     */
    // -@cs[ForbidWildcardAsReturnType] The class is deprecated and will be removed soon.
    private Class<?> safeLoad(String name) throws ClassNotFoundException, NoClassDefFoundError {
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
        Class<?> classObj = null;
        try {
            if (isLoadable(name)) {
                classObj = safeLoad(name);
            }
            else {
                //Perhaps it's fully-qualified inner class
                final int dot = name.lastIndexOf('.');
                if (dot != -1) {
                    final String innerName =
                        name.substring(0, dot) + DOLLAR_SIGN + name.substring(dot + 1);
                    classObj = resolveQualifiedName(innerName);
                }
            }
        }
        catch (final ClassNotFoundException ex) {
            // we shouldn't get this exception here,
            // so this is unexpected runtime exception
            throw new IllegalStateException(ex);
        }
        return classObj;
    }

}
