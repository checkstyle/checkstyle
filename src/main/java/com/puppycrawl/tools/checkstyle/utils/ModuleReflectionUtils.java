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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.RootModule;

/**
 * Contains utility methods for module reflection.
 * @author LuoLiangchen
 */
public final class ModuleReflectionUtils {

    /** Prevent instantiation. */
    private ModuleReflectionUtils() {
    }

    /**
     * Gets checkstyle's modules (directly, not recursively) in the given packages.
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
                .filter(ModuleReflectionUtils::isCheckstyleModule)
                .collect(Collectors.toSet());
    }

    /**
     * Checks whether a class may be considered as a checkstyle module. Checkstyle's modules are
     * non-abstract classes, which are either checkstyle's checks, file sets, filters, file filters,
     * {@code TreeWalker} filters or root module.
     * @param clazz class to check.
     * @return true if the class may be considered as the checkstyle module.
     */
    public static boolean isCheckstyleModule(Class<?> clazz) {
        return isValidCheckstyleClass(clazz)
            && (isCheckstyleCheck(clazz)
                    || isFileSetModule(clazz)
                    || isFilterModule(clazz)
                    || isFileFilterModule(clazz)
                    || isTreeWalkerFilterModule(clazz)
                    || isRootModule(clazz));
    }

    /**
     * Checks whether a class extends 'AutomaticBean' and is non-abstract.
     * @param clazz class to check.
     * @return true if a class may be considered a valid production class.
     */
    public static boolean isValidCheckstyleClass(Class<?> clazz) {
        return AutomaticBean.class.isAssignableFrom(clazz)
                && !Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * Checks whether a class may be considered as the checkstyle check.
     * Checkstyle's checks are classes which implement 'AbstractCheck' interface.
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle check.
     */
    public static boolean isCheckstyleCheck(Class<?> clazz) {
        return AbstractCheck.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle file set.
     * Checkstyle's file sets are classes which implement 'AbstractFileSetCheck' interface.
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle file set.
     */
    public static boolean isFileSetModule(Class<?> clazz) {
        return AbstractFileSetCheck.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle filter.
     * Checkstyle's filters are classes which implement 'Filter' interface.
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle filter.
     */
    public static boolean isFilterModule(Class<?> clazz) {
        return Filter.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle file filter.
     * Checkstyle's file filters are classes which implement 'BeforeExecutionFileFilter' interface.
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle file filter.
     */
    public static boolean isFileFilterModule(Class<?> clazz) {
        return BeforeExecutionFileFilter.class.isAssignableFrom(clazz);
    }

    /**
     * Checks whether a class may be considered as the checkstyle root module.
     * Checkstyle's root modules are classes which implement 'RootModule' interface.
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
     * @param clazz class to check.
     * @return true if a class may be considered as the checkstyle {@code TreeWalker} filter.
     */
    public static boolean isTreeWalkerFilterModule(Class<?> clazz) {
        return TreeWalkerFilter.class.isAssignableFrom(clazz);
    }
}
