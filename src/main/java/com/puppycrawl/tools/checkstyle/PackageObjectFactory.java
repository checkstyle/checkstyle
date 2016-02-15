////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.Iterator;
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
public class PackageObjectFactory implements ModuleFactory {
    /** Logger for PackageObjectFactory. */
    private static final Log LOG = LogFactory.getLog(PackageObjectFactory.class);

    /** Log message when ignoring exception. */
    private static final String IGNORING_EXCEPTION_MESSAGE = "Keep looking, ignoring exception";

    /** Exception message when it is unable to create a class instance. */
    private static final String UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE =
        "PackageObjectFactory.unableToInstantiateExceptionMessage";

    /** Separator to use in strings. */
    private static final String STRING_SEPARATOR = ", ";

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
    public PackageObjectFactory(Set<String> packageNames, ClassLoader moduleClassLoader) {
        if (moduleClassLoader == null) {
            throw new IllegalArgumentException(
                    "moduleClassLoader must not be null");
        }

        //create a copy of the given set, but retain ordering
        packages = Sets.newLinkedHashSet(packageNames);
        this.moduleClassLoader = moduleClassLoader;
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
    public Object createModule(String name) throws CheckstyleException {
        Object instance = createObjectWithIgnoringProblems(name, getAllPossibleNames(name));
        if (instance == null) {
            final String nameCheck = name + "Check";
            instance = createObjectWithIgnoringProblems(nameCheck, getAllPossibleNames(nameCheck));
            if (instance == null) {

                final String attemptedNames = joinPackageNamesWithClassName(name)
                        + STRING_SEPARATOR + nameCheck + STRING_SEPARATOR
                        + joinPackageNamesWithClassName(nameCheck);
                final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, getClass(), null);
                throw new CheckstyleException(exceptionMessage.getMessage());
            }
        }
        return instance;
    }

    /**
     * Create a new instance of a named class.
     * @param className the name of the class to instantiate.
     * @param secondAttempt the set of names to attempt instantiation
     *                      if usage of the className was not successful.
     * @return the {@code Object} created by loader or null.
     */
    private Object createObjectWithIgnoringProblems(String className,
                                                    Set<String> secondAttempt) {
        Object instance = createObject(className);
        if (instance == null) {
            final Iterator<String> ite = secondAttempt.iterator();
            while (instance == null && ite.hasNext()) {
                instance = createObject(ite.next());
            }
        }
        return instance;
    }

    /**
     * Generate the set of all possible names for a class name.
     * @param name the name of the class get possible names for.
     * @return all possible name for a class.
     */
    private Set<String> getAllPossibleNames(String name) {
        final Set<String> names = Sets.newHashSet();
        for (String packageName : packages) {
            names.add(packageName + name);
        }
        return names;
    }

    /**
     * Creates a string by joining package names with a class name.
     * @param className name of the class for joining.
     * @return a string which is obtained by joining package names with a class name.
     */
    private String joinPackageNamesWithClassName(String className) {
        final Joiner joiner = Joiner.on(className + STRING_SEPARATOR).skipNulls();
        return joiner.join(packages) + className;
    }

    /**
     * Creates a new instance of a named class.
     * @param className the name of the class to instantiate.
     * @return the {@code Object} created by loader or null.
     */
    private Object createObject(String className) {
        Object instance = null;
        try {
            final Class<?> clazz = Class.forName(className, true, moduleClassLoader);
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            instance = declaredConstructor.newInstance();
        }
        catch (final ReflectiveOperationException | NoClassDefFoundError exception) {
            LOG.debug(IGNORING_EXCEPTION_MESSAGE, exception);
        }
        return instance;
    }
}
