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

import java.lang.reflect.Constructor;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * A factory for creating objects from package names and names.
 * @author Rick Giles
 * @author lkuehne
 */
class PackageObjectFactory implements ModuleFactory {
    /** Logger for PackageObjectFactory. */
    private static final Log LOG = LogFactory.getLog(PackageObjectFactory.class);

    /** Log message when ignoring exception. */
    private static final String IGNORING_EXCEPTION_MESSAGE = "Keep looking, ignoring exception";

    /** Exception message when it is unable to create a class instance. */
    private static final String UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE =
        "PackageObjectFactory.unableToInstantiateExceptionMessage";

    /** A list of package names to prepend to class names. */
    private final Set<String> packages;

    /** The class loader used to load Checkstyle core and custom modules. */
    private final ClassLoader moduleClassLoader;

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     * @param packageNames the list of package names to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     */
    PackageObjectFactory(Set<String> packageNames,
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
     * a class name, creates an instance of the named class. Otherwise, creates
     * an instance of a class name obtained by concatenating the given
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
            LOG.debug(IGNORING_EXCEPTION_MESSAGE, ex);
        }

        //now try packages
        for (String packageName : packages) {

            final String className = packageName + name;
            try {
                return createObject(className);
            }
            catch (final CheckstyleException ex) {
                LOG.debug(IGNORING_EXCEPTION_MESSAGE, ex);
            }
        }
        final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
            new String[] {name, joinPackageNamesWithClassName(name)},
            null, getClass(), null);
        throw new CheckstyleException(exceptionMessage.getMessage());
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
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        }
        catch (final ReflectiveOperationException | NoClassDefFoundError exception) {
            throw new CheckstyleException("Unable to find class for " + className, exception);
        }
    }

    /**
     * Creates a new instance of a class from a given name, or that name
     * concatenated with &quot;Check&quot;. If the name is
     * a class name, creates an instance of the named class. Otherwise, creates
     * an instance of a class name obtained by concatenating the given name
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
                final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, joinPackageNamesWithClassName(name)},
                    null, getClass(), null);
                throw new CheckstyleException(exceptionMessage.getMessage(), ex2);
            }
        }
    }

    /**
     * Creates a string by joining package names with a class name.
     * @param className name of the class for joining.
     * @return a string which is obtained by joining package names with a class name.
     */
    private String joinPackageNamesWithClassName(String className) {
        final Joiner joiner = Joiner.on(className + ", ").skipNulls();
        return joiner.join(packages) + className;
    }
}
