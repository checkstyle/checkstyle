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

package com.puppycrawl.tools.checkstyle;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * A factory for creating objects from package names and names.
 * @author Rick Giles
 * @author lkuehne
 */
class PackageObjectFactory implements ModuleFactory {
    /** Logger for PackageObjectFactory. */
    private static final Log LOG = LogFactory.getLog(PackageObjectFactory.class);

    /** a list of package names to prepend to class names */
    private final Set<String> packages;

    /** the class loader used to load Checkstyle core and custom modules. */
    private final ClassLoader moduleClassLoader;

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     * @param packageNames the list of package names to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     */
    public PackageObjectFactory(Set<String> packageNames,
            ClassLoader moduleClassLoader) {
        if (moduleClassLoader == null) {
            throw new IllegalArgumentException(
                    "moduleClassLoader must not be null");
        }

        //create a copy of the given set, but retain ordering
        packages = Sets.newLinkedHashSet(packageNames);
        this.moduleClassLoader = moduleClassLoader;
    }

    /**
     * Registers a package name to use for shortName resolution.
     * @param packageName the package name
     */
    void addPackage(String packageName) {
        packages.add(packageName);
    }

    /**
     * Creates a new instance of a class from a given name. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given
     * to a package name from a given list of package names.
     * @param name the name of a class.
     * @return the {@code Object}
     * @throws CheckstyleException if an error occurs.
     */
    private Object doMakeObject(String name)
        throws CheckstyleException {
        //try name first
        try {
            return createObject(name);
        }
        catch (final CheckstyleException ex) {
            LOG.debug("Keep looking, ignoring exception", ex);
        }

        //now try packages
        for (String packageName : packages) {

            final String className = packageName + name;
            try {
                return createObject(className);
            }
            catch (final CheckstyleException ex) {
                LOG.debug("Keep looking, ignoring exception", ex);
            }
        }

        throw new CheckstyleException("Unable to instantiate " + name);
    }

    /**
     * Creates a new instance of a named class.
     * @param className the name of the class to instantiate.
     * @return the {@code Object} created by loader.
     * @throws CheckstyleException if an error occurs.
     */
    private Object createObject(String className)
        throws CheckstyleException {
        try {
            final Class<?> clazz = Class.forName(className, true, moduleClassLoader);
            return clazz.getDeclaredConstructor().newInstance();
        }
        catch (final ReflectiveOperationException exception) {
            throw new CheckstyleException("Unable to find class for " + className, exception);
        }
    }

    /**
     * Creates a new instance of a class from a given name, or that name
     * concatenated with &quot;Check&quot;. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given name
     * to a package name from a given list of package names.
     * @param name the name of a class.
     * @return the {@code Object} created by loader.
     * @throws CheckstyleException if an error occurs.
     */
    @Override
    public Object createModule(String name)
        throws CheckstyleException {
        try {
            return doMakeObject(name);
        }
        catch (final CheckstyleException ignored) {
            //try again with suffix "Check"
            try {
                return doMakeObject(name + "Check");
            }
            catch (final CheckstyleException ex2) {
                throw new CheckstyleException(
                    "Unable to instantiate " + name, ex2);
            }
        }
    }
}
