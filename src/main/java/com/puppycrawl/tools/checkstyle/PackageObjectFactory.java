////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * A factory for creating objects from package names and names,
 * considering:
 * <ul>
 *     <li>module name - name of java class that represents module;</li>
 *     <li>module full name - fully qualifies name of java class that represents module;</li>
 *     <li>check module short name - name of Check without 'Check' suffix;</li>
 *     <li>check module name - name of java class that represents Check (with 'Check' suffix);</li>
 *     <li>
 *         check module full name - fully qualifies name of java class
 *         that represents Check (with 'Check' suffix).
 *     </li>
 * </ul>
 * @author Rick Giles
 * @author lkuehne
 */
public class PackageObjectFactory implements ModuleFactory {

    /** Map of Checkstyle module names to their fully qualified names. */
    private static final Map<String, String> NAME_TO_FULL_MODULE_NAME =
            generateNameToFullModuleNameMap(Thread.currentThread().getContextClassLoader());

    /** Exception message when null class loader is given. */
    private static final String NULL_LOADER_MESSAGE = "moduleClassLoader must not be null";

    /** Exception message when it is unable to create a class instance. */
    private static final String UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE =
        "PackageObjectFactory.unableToInstantiateExceptionMessage";

    /** Separator to use in strings. */
    private static final String STRING_SEPARATOR = ", ";

    /** Suffix of checks. */
    private static final String CHECK_SUFFIX = "Check";

    /** Base package of checkstyle modules checks. */
    private static final String BASE_PACKAGE = "com.puppycrawl.tools.checkstyle";

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
            throw new IllegalArgumentException(NULL_LOADER_MESSAGE);
        }

        //create a copy of the given set, but retain ordering
        packages = new LinkedHashSet<>(packageNames);
        this.moduleClassLoader = moduleClassLoader;
    }

    /**
     * Creates a new {@code PackageObjectFactory} instance.
     * @param packageName The package name to use
     * @param moduleClassLoader class loader used to load Checkstyle
     *          core and custom modules
     */
    public PackageObjectFactory(String packageName, ClassLoader moduleClassLoader) {
        if (moduleClassLoader == null) {
            throw new IllegalArgumentException(NULL_LOADER_MESSAGE);
        }

        packages = new LinkedHashSet<>(1);
        packages.add(packageName);
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
        Object instance = createObjectFromMap(name);
        final String nameCheck = name + CHECK_SUFFIX;
        if (instance == null) {
            instance = createObjectWithIgnoringProblems(nameCheck, getAllPossibleNames(nameCheck));
        }
        if (instance == null) {
            instance = createObjectWithIgnoringProblems(name, getAllPossibleNames(name));
            if (instance == null) {
                final String attemptedNames = joinPackageNamesWithClassName(name, packages)
                        + STRING_SEPARATOR + nameCheck + STRING_SEPARATOR
                        + joinPackageNamesWithClassName(nameCheck, packages);
                final LocalizedMessage exceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_INSTANTIATE_EXCEPTION_MESSAGE,
                    new String[] {name, attemptedNames}, null, getClass(), null);
                throw new CheckstyleException(exceptionMessage.getMessage());
            }
        }
        return instance;
    }

    /**
     * Create object with the help of Checkstyle NAME_TO_FULL_MODULE_NAME map.
     * @param name name of module.
     * @return instance of module if it is found in modules map.
     * @throws CheckstyleException if the class fails to instantiate.
     */
    private Object createObjectFromMap(String name) throws CheckstyleException {
        final String fullModuleName = NAME_TO_FULL_MODULE_NAME.get(name);
        Object instance = null;
        if (fullModuleName == null) {
            final String fullCheckModuleName = NAME_TO_FULL_MODULE_NAME.get(name + CHECK_SUFFIX);
            if (fullCheckModuleName != null) {
                instance = createObject(fullCheckModuleName);
            }
        }
        else {
            instance = createObject(fullModuleName);
        }
        return instance;
    }

    /**
     * Create a new instance of a named class.
     * @param className the name of the class to instantiate.
     * @param secondAttempt the set of names to attempt instantiation
     *                      if usage of the className was not successful.
     * @return the {@code Object} created by loader or null.
     * @throws CheckstyleException if the class fails to instantiate.
     */
    private Object createObjectWithIgnoringProblems(String className, Set<String> secondAttempt)
            throws CheckstyleException {
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
        final Set<String> names = new HashSet<>();
        names.addAll(packages.stream().map(packageName -> packageName + name)
            .collect(Collectors.toList()));
        return names;
    }

    /**
     * Creates a string by joining package names with a class name.
     * @param className name of the class for joining.
     * @param packages packages names.
     * @return a string which is obtained by joining package names with a class name.
     */
    private static String joinPackageNamesWithClassName(String className, Set<String> packages) {
        return packages.stream().filter(Objects::nonNull)
            .collect(Collectors.joining(className + STRING_SEPARATOR, "", className));
    }

    /**
     * Creates a new instance of a named class.
     * @param className the name of the class to instantiate.
     * @return the {@code Object} created by loader or null.
     * @throws CheckstyleException if the class fails to instantiate.
     */
    private Object createObject(String className) throws CheckstyleException {
        Class<?> clazz = null;

        try {
            clazz = Class.forName(className, true, moduleClassLoader);
        }
        catch (final ReflectiveOperationException | NoClassDefFoundError ignored) {
            // keep looking, ignoring exception
        }

        Object instance = null;

        if (clazz != null) {
            try {
                final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                instance = declaredConstructor.newInstance();
            }
            catch (final ReflectiveOperationException ex) {
                throw new CheckstyleException("Unable to instatiate " + className, ex);
            }
        }

        return instance;
    }

    /**
     * Generate the map of Checkstyle module names to their fully qualified names.
     * @param loader the class loader used to load Checkstyle package names
     * @return the map of Checkstyle module names to their fully qualified names
     */
    private static Map<String, String> generateNameToFullModuleNameMap(ClassLoader loader) {
        Map<String, String> returnValue;
        try {
            final Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false))
                    .setUrls(ClasspathHelper.forPackage(BASE_PACKAGE))
                    .filterInputsBy(new FilterBuilder()
                            .include(FilterBuilder.prefix(BASE_PACKAGE))));
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(loader);
            returnValue = reflections.getSubTypesOf(Object.class).stream()
                    .filter(clazz -> packageNames.contains(clazz.getPackage().getName() + "."))
                    .filter(PackageObjectFactory::shouldAddToMap)
                    .collect(Collectors.toMap(Class::getSimpleName, Class::getCanonicalName));
        } catch (CheckstyleException ignore) {
            returnValue = new HashMap<>();
        }
        return returnValue;
    }

    /**
     * Whether the class should be added to the map.
     * @param clazz the class to check
     * @return true if the class should be added to the map
     */
    private static boolean shouldAddToMap(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers())
                && !clazz.isMemberClass()
                && !clazz.getSimpleName().startsWith("Input")
                && isNameCorrespondingToCorrectPackage(
                        clazz.getSimpleName(), clazz.getPackage().getName());
    }

    /**
     * Whether the class name is corresponding to a correct package.
     * @param className the class name to check.
     * @param packageName the package of the class.
     * @return true if the class the class name is corresponding to a correct package
     */
    private static boolean isNameCorrespondingToCorrectPackage(
            String className, String packageName) {
        return packageName.contains(BASE_PACKAGE + ".checks") && className.endsWith(CHECK_SUFFIX)
                    || packageName.endsWith("filefilters") && className.endsWith("FileFilter")
                    || packageName.endsWith("filters") && className.endsWith("Filter")
                    || isModuleWithSpecialName(className);
    }

    /**
     * Whether a class not following common naming notation should be added to the map.
     * @param className the class name to check.
     * @return true if the class is not following the common naming notation and should
     *      be added to the map.
     */
    private static boolean isModuleWithSpecialName(String className) {
        final String[] specialNames = {
            "Checker", "TreeWalker", "FileContentsHolder", "SuppressWarningsHolder",
        };
        return Arrays.stream(specialNames).anyMatch(specialName -> specialName.equals(className));
    }
}
