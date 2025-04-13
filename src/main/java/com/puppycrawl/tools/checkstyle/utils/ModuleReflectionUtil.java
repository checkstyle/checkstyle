////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;
import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.RootModule;

/**
 * Contains utility methods for module reflection.
 */
public final class ModuleReflectionUtil {

    /** Prevent instantiation. */
    private ModuleReflectionUtil() {
    }

    /**
     * Gets checkstyle's modules classes (directly, not recursively) in the given packages.
     *
     * @param packages the collection of package names to use
     * @param loader the class loader used to load Checkstyle package names
     * @return the set of checkstyle's module classes
     * @throws IOException if the attempt to read class path resources failed
     * @see #isCheckstyleModule(Class)
     */
    public static Set<Class<?>> getCheckstyleModules(
        Collection<String> packages, ClassLoader loader) throws IOException {
        final ClassPath classPath = ClassPath.from(loader);
        return packages.stream()
            .flatMap(pkg -> classPath.getTopLevelClasses(pkg).stream())
            .map(ClassPath.ClassInfo::load)
            .filter(ModuleReflectionUtil::isCheckstyleModule)
            .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks whether a class may be considered as a checkstyle module.
     * Checkstyle's modules are classes which extend 'AutomaticBean', is
     * non-abstract, and has a default constructor.
     *
     * @param clazz class to check.
     * @return true if a class may be considered a valid production class.
     */
    public static boolean isCheckstyleModule(Class<?> clazz) {
        return AbstractAutomaticBean.class.isAssignableFrom(clazz)
            && !Modifier.isAbstract(clazz.getModifiers())
            && hasDefaultConstructor(clazz)
            && isNotXpathFileGenerator(clazz);
    }

    /**
     * Checks if the class has a default constructor.
     *
     * @param clazz class to check
     * @return true if the class has a default constructor.
     */
    private static boolean hasDefaultConstructor(Class<?> clazz) {
        boolean result = false;
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterCount() == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks whether a class may be considered as the checkstyle check
     * which has TreeWalker as a parent.
     * Checkstyle's checks are classes which implement 'AbstractCheck' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle check.
     */
    public static boolean isCheckstyleTreeWalkerCheck(Class<?> clazz) {
        return AbstractCheck.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle file set.
     * Checkstyle's file sets are classes which implement 'AbstractFileSetCheck' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle file set.
     */
    public static boolean isFileSetModule(Class<?> clazz) {
        return AbstractFileSetCheck.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle filter.
     * Checkstyle's filters are classes which implement 'Filter' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle filter.
     */
    public static boolean isFilterModule(Class<?> clazz) {
        return Filter.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle file filter.
     * Checkstyle's file filters are classes which implement 'BeforeExecutionFileFilter' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle file filter.
     */
    public static boolean isFileFilterModule(Class<?> clazz) {
        return BeforeExecutionFileFilter.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle audit listener module.
     * Checkstyle's audit listener modules are classes which implement 'AuditListener' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle audit listener module.
     */
    public static boolean isAuditListener(Class<?> clazz) {
        return AuditListener.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle root module.
     * Checkstyle's root modules are classes which implement 'RootModule' interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle root module.
     */
    public static boolean isRootModule(Class<?> clazz) {
        return RootModule.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle {@code TreeWalker} filter.
     * Checkstyle's {@code TreeWalker} filters are classes which implement 'TreeWalkerFilter'
     * interface.
     *
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle {@code TreeWalker} filter.
     */
    public static boolean isTreeWalkerFilterModule(Class<?> clazz) {
        return TreeWalkerFilter.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class is {@code XpathFileGeneratorAstFilter} or
     * {@code XpathFileGeneratorAuditListener}.
     * See issue <a href="https://github.com/checkstyle/checkstyle/issues/102">#102</a>
     *
     * @param clazz class to check.
     * @return true if a class name starts with `XpathFileGenerator`.
     */
    private static boolean isNotXpathFileGenerator(Class<?> clazz) {
        return !clazz.getSimpleName().startsWith("XpathFileGenerator");
    }
}
