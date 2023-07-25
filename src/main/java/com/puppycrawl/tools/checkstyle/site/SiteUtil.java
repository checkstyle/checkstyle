///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;

/**
 * Utility class for site generation.
 */
public final class SiteUtil {

    /** Class name and their corresponding parent module name.. */
    private static final Map<Class<?>, String> CLASS_TO_PARENT_MODULE = Map.ofEntries(
        Map.entry(AbstractCheck.class, TreeWalker.class.getSimpleName()),
        Map.entry(TreeWalkerFilter.class, TreeWalker.class.getSimpleName()),
        Map.entry(AbstractFileSetCheck.class, Checker.class.getSimpleName()),
        Map.entry(Filter.class, Checker.class.getSimpleName())
    );

    /**
     * Private utility constructor.
     */
    private SiteUtil() {
    }

    /**
     * Gets the check's messages keys.
     *
     * @param module class to examine.
     * @param checkInstance instance of the check.
     * @return a list of checkstyle's module message fields.
     * @throws MacroExecutionException if the attempt to read a protected class fails.
     * @noinspection InstanceofChain
     * @noinspectionreason InstanceofChain - We will deal with this at
     *                     <a href="https://github.com/checkstyle/checkstyle/issues/13500">13500</a>
     */
    public static List<String> getCheckMessageKeys(Class<?> module, Object checkInstance)
            throws MacroExecutionException {
        try {
            final List<String> checkstyleMessageKeys = new ArrayList<>();

            // get all fields from current class
            final Field[] fields = module.getDeclaredFields();

            for (Field field : fields) {
                if (field.getName().startsWith("MSG_")) {
                    checkstyleMessageKeys.add(getFieldValue(field, checkInstance));
                }
            }

            // deep scan class through hierarchy
            final Class<?> superModule = module.getSuperclass();

            if (superModule != null) {
                checkstyleMessageKeys.addAll(getCheckMessageKeys(superModule, checkInstance));
            }

            // special cases that require additional classes
            if (module == RegexpMultilineCheck.class) {
                checkstyleMessageKeys.addAll(getCheckMessageKeys(
                        Class.forName(
                                "com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector"
                        ), checkInstance)
                );
            }
            else if (module == RegexpSinglelineCheck.class
                    || module == RegexpSinglelineJavaCheck.class) {
                checkstyleMessageKeys.addAll(getCheckMessageKeys(
                        Class.forName(
                                "com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector"
                        ), checkInstance)
                );
            }

            return checkstyleMessageKeys;
        }
        catch (ClassNotFoundException ex) {
            final String message = String.format(Locale.ROOT, "Couldn't find class: %s",
                    module.getName());
            throw new MacroExecutionException(message, ex);
        }
    }

    /**
     * Returns the value of the given field.
     *
     * @param field the field.
     * @param instance the instance of the module.
     * @return the value of the field.
     * @throws MacroExecutionException if the value could not be retrieved.
     */
    private static String getFieldValue(Field field, Object instance)
            throws MacroExecutionException {
        try {
            // required for package/private classes
            field.trySetAccessible();
            return field.get(instance).toString();
        }
        catch (IllegalAccessException ex) {
            throw new MacroExecutionException("Couldn't get field value", ex);
        }
    }

    /**
     * Returns the instance of the module with the given name.
     *
     * @param moduleName the name of the module.
     * @return the instance of the module.
     * @throws MacroExecutionException if the module could not be created.
     */
    public static Object getModuleInstance(String moduleName) throws MacroExecutionException {
        final ModuleFactory factory = getPackageObjectFactory();
        try {
            return factory.createModule(moduleName);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't find class: " + moduleName, ex);
        }
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     *
     * @return the default PackageObjectFactory.
     * @throws MacroExecutionException if the PackageObjectFactory cannot be created.
     */
    private static PackageObjectFactory getPackageObjectFactory() throws MacroExecutionException {
        try {
            final ClassLoader cl = ViolationMessagesMacro.class.getClassLoader();
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
            return new PackageObjectFactory(packageNames, cl);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't load checkstyle modules", ex);
        }
    }

    /**
     * Construct a string with a leading newline character and followed by
     * the given amount of spaces. We use this method only to match indentation in
     * regular xdocs and have minimal diff when parsing the templates.
     * This method exists until
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">13426</a>
     *
     * @param amountOfSpaces the amount of spaces to add after the newline.
     * @return the constructed string.
     */
    public static String getNewlineAndIndentSpaces(int amountOfSpaces) {
        return System.lineSeparator() + " ".repeat(amountOfSpaces);
    }

    /**
     * Gets xdocs template file paths. These are files ending with .xml.template.
     * This method will be changed to gather .xml once
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">#13426</a> is resolved.
     *
     * @return a set of xdocs template file paths.
     * @throws IOException if an I/O error occurs.
     */
    public static Set<Path> getXdocsTemplatesFilePaths() throws IOException {
        final Path directory = Paths.get("src/xdocs");
        try (Stream<Path> stream = Files.find(directory, Integer.MAX_VALUE,
                (path, attr) -> {
                    return attr.isRegularFile()
                            && path.toString().endsWith(".xml.template");
                })) {
            return stream.collect(Collectors.toSet());
        }
    }

    /**
     * Returns the parent module name for the given module class. Returns either
     * "TreeWalker" or "Checker". Returns null if the module class is null.
     *
     * @param moduleClass the module class.
     * @return the parent module name as a string.
     * @throws MacroExecutionException if the parent module cannot be found.
     */
    public static String getParentModule(Class<?> moduleClass)
                throws MacroExecutionException {
        String parentModuleName = "";
        Class<?> parentClass = moduleClass.getSuperclass();

        while (parentClass != null && parentModuleName.isEmpty()) {
            parentModuleName = CLASS_TO_PARENT_MODULE.get(parentClass);
            parentClass = parentClass.getSuperclass();
        }

        if (parentModuleName == null || parentModuleName.isEmpty()) {
            final String message = String.format(Locale.ROOT,
                    "Failed to find parent module for %s", moduleClass.getSimpleName());
            throw new MacroExecutionException(message);
        }

        return parentModuleName;
    }
}
